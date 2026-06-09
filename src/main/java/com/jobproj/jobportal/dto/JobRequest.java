package com.jobproj.jobportal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class JobRequest {

    @Size(max = 50)
    private String companyId;

    @NotBlank
    @Size(max = 150)
    private String jobTitle;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 150)
    private String location;

    @NotBlank
    @Size(max = 30)
    private String jobType;

    @NotBlank
    @Size(max = 500)
    private String requiredSkills;

    @NotBlank
    @Size(max = 100)
    private String experienceLevel;

    @Positive
    private double salary;

    private Boolean requiresInternet;

    @Size(max = 100)
    private String workHours;

    @Size(max = 255)
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
