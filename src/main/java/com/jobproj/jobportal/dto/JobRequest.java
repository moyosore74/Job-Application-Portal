package com.jobproj.jobportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class JobRequest {

    private String companyId;

    @NotBlank
    private String jobTitle;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotBlank
    private String jobType;

    @NotBlank
    private String requiredSkills;

    @NotBlank
    private String experienceLevel;

    @Positive
    private double salary;

    private Boolean requiresInternet;
    private String workHours;
    private String officeAddress;
    private Boolean providesHousing;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Boolean getRequiresInternet() {
        return requiresInternet;
    }

    public void setRequiresInternet(Boolean requiresInternet) {
        this.requiresInternet = requiresInternet;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public Boolean getProvidesHousing() {
        return providesHousing;
    }

    public void setProvidesHousing(Boolean providesHousing) {
        this.providesHousing = providesHousing;
    }
}
