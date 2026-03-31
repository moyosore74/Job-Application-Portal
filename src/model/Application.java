package model;

public class Application {

    private String applicationId;
    private String applicantName;
    private String applicantEmail;
    private String jobId;
    private String phoneNumber;
    private String applicationDate;
    private String status;

    public Application(String applicationId, String applicantName, String applicantEmail,
                       String jobId, String phoneNumber, String applicationDate, String status) {

        this.applicationId = applicationId;
        this.applicantName = applicantName;
        this.applicantEmail = applicantEmail;
        this.jobId = jobId;
        this.phoneNumber = phoneNumber;
        this.applicationDate = applicationDate;
        this.status = status;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public String getJobId() {
        return jobId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayDetails() {
        displayDetails(null);
    }

    public void displayDetails(String jobTitle) {
        System.out.println("Application ID: " + applicationId);
        System.out.println("Applicant Name: " + applicantName);
        System.out.println("Applicant Email: " + applicantEmail);
        System.out.println("Job ID: " + jobId);
        if (jobTitle != null && !jobTitle.isBlank()) {
            System.out.println("Job Title: " + jobTitle);
        }
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Application Date: " + applicationDate);
        System.out.println("Status: " + status);
        System.out.println("----------------------------------");
    }

}
