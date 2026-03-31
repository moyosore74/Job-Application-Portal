package com.jobproj.jobportal.controller;

import com.jobproj.jobportal.dto.ApplicationRequest;
import com.jobproj.jobportal.dto.ApplicationResponse;
import com.jobproj.jobportal.dto.ApplicationStatusUpdateRequest;
import com.jobproj.jobportal.security.UserPrincipal;
import com.jobproj.jobportal.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('APPLICANT')")
    @ResponseStatus(HttpStatus.CREATED)
    public ApplicationResponse apply(
            @Valid @RequestBody ApplicationRequest request,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return applicationService.apply(request, principal);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<ApplicationResponse> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @GetMapping("/applicant/me")
    @PreAuthorize("hasRole('APPLICANT')")
    public List<ApplicationResponse> getApplicantApplications(@AuthenticationPrincipal UserPrincipal principal) {
        return applicationService.getApplicationsByApplicantEmail(principal.getUsername());
    }

    @GetMapping("/company/{companyId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ApplicationResponse> getCompanyApplications(@PathVariable String companyId) {
        return applicationService.getApplicationsForCompany(companyId);
    }

    @GetMapping("/company/me")
    @PreAuthorize("hasRole('EMPLOYER')")
    public List<ApplicationResponse> getMyCompanyApplications(@AuthenticationPrincipal UserPrincipal principal) {
        return applicationService.getApplicationsForCompany(principal.getCompanyId());
    }

    @PatchMapping("/{applicationId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApplicationResponse updateStatus(
            @PathVariable String applicationId,
            @Valid @RequestBody ApplicationStatusUpdateRequest request
    ) {
        return applicationService.updateStatus(applicationId, request.getStatus());
    }

    @PatchMapping("/company/me/{applicationId}/status")
    @PreAuthorize("hasRole('EMPLOYER')")
    public ApplicationResponse updateMyCompanyStatus(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable String applicationId,
            @Valid @RequestBody ApplicationStatusUpdateRequest request
    ) {
        return applicationService.updateStatusForCompany(principal.getCompanyId(), applicationId, request.getStatus());
    }
}
