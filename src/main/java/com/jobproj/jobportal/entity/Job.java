package com.jobproj.jobportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @Column(name = "job_id", nullable = false, updatable = false, length = 50)
    private String jobId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "job_title", nullable = false, length = 150)
    private String jobTitle;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "location", nullable = false, length = 150)
    private String location;

    @Column(name = "job_type", nullable = false, length = 30)
    private String jobType;

    @Column(name = "required_skills", length = 500)
    private String requiredSkills;

    @Column(name = "experience_level", length = 100)
    private String experienceLevel;

    @Column(name = "salary", nullable = false)
    private double salary;

    @Column(name = "requires_internet")
    private Boolean requiresInternet;

    @Column(name = "work_hours", length = 100)
    private String workHours;

    @Column(name = "office_address", length = 255)
    private String officeAddress;

    @Column(name = "provides_housing")
    private Boolean providesHousing;

    @Column(name = "uploaded_date", nullable = false)
    private LocalDate uploadedDate;

    public Job() {
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public LocalDate getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(LocalDate uploadedDate) {
        this.uploadedDate = uploadedDate;
    }
}
