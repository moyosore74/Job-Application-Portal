package com.jobproj.jobportal.service;

import com.jobproj.jobportal.dto.ApplicationRequest;
import com.jobproj.jobportal.dto.ApplicationResponse;
import com.jobproj.jobportal.entity.Application;
import com.jobproj.jobportal.entity.ApplicationStatus;
import com.jobproj.jobportal.entity.Job;
import com.jobproj.jobportal.entity.Role;
import com.jobproj.jobportal.entity.User;
import com.jobproj.jobportal.repository.ApplicationRepository;
import com.jobproj.jobportal.security.UserPrincipal;
import com.jobproj.jobportal.util.IdGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final JobService jobService;

    public ApplicationService(
            ApplicationRepository applicationRepository,
            UserService userService,
            JobService jobService
    ) {
        this.applicationRepository = applicationRepository;
        this.userService = userService;
        this.jobService = jobService;
    }

    @Transactional
    public ApplicationResponse apply(ApplicationRequest request, UserPrincipal principal) {
        if (!principal.getRole().equals(Role.APPLICANT.name())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Applicant access required");
        }

        User applicant = userService.getUserById(principal.getUserId());
        Job job = jobService.getJobEntity(request.getJobId());

        if (applicationRepository.existsByApplicantUserIdAndJobJobId(applicant.getUserId(), job.getJobId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You have already applied for this job");
        }

        Application application = new Application();
        application.setApplicationId(IdGenerator.next("APP"));
        application.setApplicant(applicant);
        application.setJob(job);
        application.setPhoneNumber(request.getPhoneNumber());
        application.setApplicationDate(LocalDate.now());
        application.setStatus(ApplicationStatus.PENDING);

        return toResponse(applicationRepository.save(application));
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAllApplications() {
        return applicationRepository.findAllByOrderByApplicationDateDesc().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsByApplicantEmail(String email) {
        return applicationRepository.findByApplicantEmailIgnoreCaseOrderByApplicationDateDesc(email).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getApplicationsForCompany(String companyId) {
        return applicationRepository.findByJobCompanyCompanyIdOrderByApplicationDateDesc(companyId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ApplicationResponse updateStatus(String applicationId, String status) {
        Application application = applicationRepository.findByApplicationIdIgnoreCase(applicationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Application not found"));
        application.setStatus(parseStatus(status));
        return toResponse(applicationRepository.save(application));
    }

    @Transactional
    public ApplicationResponse updateStatusForCompany(String companyId, String applicationId, String status) {
        Application application = applicationRepository.findByApplicationIdIgnoreCaseAndJobCompanyCompanyId(applicationId, companyId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Application not found for your company's jobs"
                ));
        application.setStatus(parseStatus(status));
        return toResponse(applicationRepository.save(application));
    }

    private ApplicationStatus parseStatus(String status) {
        try {
            return ApplicationStatus.valueOf(status.trim().toUpperCase());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status. Use PENDING, REVIEWED, ACCEPTED, or REJECTED"
            );
        }
    }

    private ApplicationResponse toResponse(Application application) {
        ApplicationResponse response = new ApplicationResponse();
        response.setApplicationId(application.getApplicationId());
        response.setApplicantUserId(application.getApplicant().getUserId());
        response.setApplicantName(application.getApplicant().getFullName());
        response.setApplicantEmail(application.getApplicant().getEmail());
        response.setJobId(application.getJob().getJobId());
        response.setJobTitle(application.getJob().getJobTitle());
        response.setCompanyId(application.getJob().getCompany().getCompanyId());
        response.setCompanyName(application.getJob().getCompany().getCompanyName());
        response.setPhoneNumber(application.getPhoneNumber());
        response.setApplicationDate(application.getApplicationDate());
        response.setStatus(application.getStatus());
        return response;
    }
}
