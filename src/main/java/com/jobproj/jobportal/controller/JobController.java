package com.jobproj.jobportal.controller;

import com.jobproj.jobportal.dto.JobRequest;
import com.jobproj.jobportal.dto.JobResponse;
import com.jobproj.jobportal.security.UserPrincipal;
import com.jobproj.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYER')")
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse createJob(
            @Valid @RequestBody JobRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return jobService.createJob(request, principal);
    }

    @GetMapping
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/search")
    public JobResponse searchJob(@RequestParam String value) {
        return jobService.searchJob(value);
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<JobResponse> getCompanyJobs(@PathVariable String companyId) {
        return jobService.getJobsForCompany(companyId);
    }

    @GetMapping("/company/{companyId}/search")
    @PreAuthorize("hasRole('ADMIN')")
    public JobResponse searchCompanyJob(@PathVariable String companyId, @RequestParam String value) {
        return jobService.searchCompanyJob(companyId, value);
    }

    @GetMapping("/company/me")
    @PreAuthorize("hasRole('EMPLOYER')")
    public List<JobResponse> getMyCompanyJobs(@AuthenticationPrincipal UserPrincipal principal) {
        return jobService.getJobsForCompany(principal.getCompanyId());
    }

    @GetMapping("/company/me/search")
    @PreAuthorize("hasRole('EMPLOYER')")
    public JobResponse searchMyCompanyJob(
            @AuthenticationPrincipal UserPrincipal principal,
            @RequestParam String value
    ) {
        return jobService.searchCompanyJob(principal.getCompanyId(), value);
    }

    @DeleteMapping("/{value}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteJob(@PathVariable String value) {
        jobService.deleteJob(value);
    }

    @DeleteMapping("/company/{companyId}/{value}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompanyJob(@PathVariable String companyId, @PathVariable String value) {
        jobService.deleteCompanyJob(companyId, value);
    }

    @DeleteMapping("/company/me/{value}")
    @PreAuthorize("hasRole('EMPLOYER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyCompanyJob(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String value
    ) {
        jobService.deleteMyCompanyJob(value, principal);
    }
}
