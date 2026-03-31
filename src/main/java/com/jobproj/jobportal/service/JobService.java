package com.jobproj.jobportal.service;

import com.jobproj.jobportal.dto.JobRequest;
import com.jobproj.jobportal.dto.JobResponse;
import com.jobproj.jobportal.entity.Company;
import com.jobproj.jobportal.entity.Job;
import com.jobproj.jobportal.entity.Role;
import com.jobproj.jobportal.repository.CompanyRepository;
import com.jobproj.jobportal.repository.JobRepository;
import com.jobproj.jobportal.security.UserPrincipal;
import com.jobproj.jobportal.util.IdGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public JobResponse createJob(JobRequest request, UserPrincipal principal) {
        String companyId = resolveCompanyIdForWrite(request.getCompanyId(), principal);

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

        Job job = new Job();
        job.setJobId(IdGenerator.next("JOB"));
        job.setCompany(company);
        job.setJobTitle(request.getJobTitle());
        job.setDescription(request.getDescription());
        job.setLocation(request.getLocation());
        job.setJobType(request.getJobType());
        job.setRequiredSkills(request.getRequiredSkills());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setSalary(request.getSalary());
        job.setRequiresInternet(request.getRequiresInternet());
        job.setWorkHours(request.getWorkHours());
        job.setOfficeAddress(request.getOfficeAddress());
        job.setProvidesHousing(request.getProvidesHousing());
        job.setUploadedDate(LocalDate.now());

        return toResponse(jobRepository.save(job));
    }

    @Transactional(readOnly = true)
    public List<JobResponse> getAllJobs() {
        return jobRepository.findAllByOrderByUploadedDateDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<JobResponse> getJobsForCompany(String companyId) {
        return jobRepository.findByCompanyCompanyIdOrderByUploadedDateDesc(companyId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public JobResponse searchJob(String value) {
        Job job = jobRepository.findByJobIdIgnoreCaseOrJobTitleIgnoreCase(value, value)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        return toResponse(job);
    }

    @Transactional(readOnly = true)
    public JobResponse searchCompanyJob(String companyId, String value) {
        Job job = findJobForCompany(companyId, value);
        return toResponse(job);
    }

    @Transactional
    public void deleteJob(String value) {
        Job job = jobRepository.findByJobIdIgnoreCaseOrJobTitleIgnoreCase(value, value)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        jobRepository.delete(job);
    }

    @Transactional
    public void deleteCompanyJob(String companyId, String value) {
        Job job = findJobForCompany(companyId, value);
        jobRepository.delete(job);
    }

    @Transactional
    public void deleteMyCompanyJob(String value, UserPrincipal principal) {
        ensureEmployer(principal);
        Job job = findJobForCompany(principal.getCompanyId(), value);
        jobRepository.delete(job);
    }

    @Transactional(readOnly = true)
    public Job getJobEntity(String jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
    }

    private String resolveCompanyIdForWrite(String requestCompanyId, UserPrincipal principal) {
        if (principal.getRole().equals(Role.ADMIN.name())) {
            if (requestCompanyId == null || requestCompanyId.isBlank()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "companyId is required for admin job creation");
            }
            return requestCompanyId.trim();
        }

        ensureEmployer(principal);

        if (principal.getCompanyId() == null || principal.getCompanyId().isBlank()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Employer account is not linked to a company");
        }

        if (requestCompanyId != null && !requestCompanyId.isBlank() &&
                !principal.getCompanyId().equalsIgnoreCase(requestCompanyId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only create jobs for your own company");
        }

        return principal.getCompanyId();
    }

    private void ensureEmployer(UserPrincipal principal) {
        if (!principal.getRole().equals(Role.EMPLOYER.name())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Employer access required");
        }
    }

    private Job findJobForCompany(String companyId, String value) {
        return jobRepository.findByCompanyCompanyIdAndJobIdIgnoreCase(companyId, value)
                .or(() -> jobRepository.findByCompanyCompanyIdAndJobTitleIgnoreCase(companyId, value))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found for company"));
    }

    private JobResponse toResponse(Job job) {
        JobResponse response = new JobResponse();
        response.setJobId(job.getJobId());
        response.setCompanyId(job.getCompany().getCompanyId());
        response.setCompanyName(job.getCompany().getCompanyName());
        response.setJobTitle(job.getJobTitle());
        response.setDescription(job.getDescription());
        response.setLocation(job.getLocation());
        response.setJobType(job.getJobType());
        response.setRequiredSkills(job.getRequiredSkills());
        response.setExperienceLevel(job.getExperienceLevel());
        response.setSalary(job.getSalary());
        response.setRequiresInternet(job.getRequiresInternet());
        response.setWorkHours(job.getWorkHours());
        response.setOfficeAddress(job.getOfficeAddress());
        response.setProvidesHousing(job.getProvidesHousing());
        response.setUploadedDate(job.getUploadedDate());
        return response;
    }
}
