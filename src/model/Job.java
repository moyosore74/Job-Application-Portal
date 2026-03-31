package model;

public abstract class Job {
    static int noOfJobs;
    private String jobId;
    private String companyId;
    private String companyName;
    private String jobTitle;
    private String description;
    private String location;
    private String jobType;
    private String requiredSkills;
    private String experienceLevel;
    private String uploadedDate;
    private double salary;

    Job(){
        noOfJobs++;
        jobId = "";
        companyId = "";
        companyName = " ";
        jobTitle = "Java Developer";
        description = "Short summary of the job role and expectation";
        location = "Lagos";
        jobType = "Full-time";
        requiredSkills = "Java";
        experienceLevel = "Entry-level";
        uploadedDate = "";
        salary = 150000.00;
    }

    public String getJobId(){
        return jobId;
    }

    public String getCompanyId(){
        return companyId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }

    public String getExperienceLevel() {
        return experienceLevel;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobType() {
        return jobType;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }
    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setSalary(double salary) {
        if(salary <= 0){
            System.out.println("Salary cannot be less than 0");
        }else{
            this.salary = salary;
        }
    }

    public String getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public double getSalary() {
        return salary;
    }

    public Job(String jobId, String companyId, String companyName, String jobTitle, String description, String location, String jobType, String requiredSkills, String experienceLevel, double salary){
        noOfJobs++;
        this.jobId = jobId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.description = description;
        this.location = location;
        this.jobType = jobType;
        this.requiredSkills = requiredSkills;
        this.experienceLevel = experienceLevel;
        this.uploadedDate = "";
        this.salary = salary;
    }

    public Job(String jobId, String companyId, String companyName, String jobTitle, String location, String jobType, double salary) {
        noOfJobs++;
        this.jobId = jobId;
        this.companyId = companyId;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.location = location;
        this.jobType = jobType;
        this.uploadedDate = "";
        this.salary = salary;
    }

    /*public void displayDetails(){
        System.out.println("********* Job Details *********");
        System.out.println("Title: "+ jobTitle);
        System.out.println("Description: " + description);
        System.out.println("Location: "+ location);
        System.out.println("Job Type: "+ jobType);
        System.out.println("Required Skills: "+ requiredSkills);
        System.out.println("Experience Level: "+ experienceLevel);
        System.out.println("Salary: $"+ salary);
    }*/
    public abstract void displayDetails();


    public static void showJobs(){
        System.out.println("The number of jobs are: "+ noOfJobs);
    }


}
