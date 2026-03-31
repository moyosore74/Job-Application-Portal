package com.jobproj.jobportal.dto;

import jakarta.validation.constraints.NotBlank;

public class ApplicationRequest {

    @NotBlank
    private String jobId;

    @NotBlank
    private String phoneNumber;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
