package model;
public class Remote extends Job implements interfaces.Payable {

    public final boolean requiresInternet;
    private final String workHours;


    public Remote(String jobId,String companyId, String companyName, String jobTitle, String location, String jobType, double salary, boolean requiresInternet, String workHours) {
        super(jobId, companyId, companyName, jobTitle, location, jobType, salary);
        this.requiresInternet = requiresInternet;
        this.workHours = workHours;
    }

    public String getWorkHours() {
        return workHours;
    }

    public boolean isRequiresInternet() {
        return requiresInternet;
    }

    @Override
    public void displayDetails() {
        System.out.println("********* Job Details *********");
        System.out.println("Job ID: " + getJobId());
        System.out.println("Company ID: " + getCompanyId());
        System.out.println("Company Name: " + getCompanyName());
        System.out.println("Title: " + getJobTitle());
        System.out.println("Description: " + getDescription());
        System.out.println("Location: " + getLocation());
        System.out.println("Job Type: " + getJobType());
        System.out.println("Required Skills: " + getRequiredSkills());
        System.out.println("Experience Level: " + getExperienceLevel());
        System.out.println("Uploaded Date: " + getUploadedDate());
        System.out.println("Salary: $" + getSalary());
        System.out.println("Requires Internet: " + requiresInternet);
        System.out.println("Work Hours: " + workHours);
    }

    @Override
    public double calculatePay(){
        double  base = getSalary();
        double internetAllowance = base * 0.10;
        return base + internetAllowance;
    }

}
