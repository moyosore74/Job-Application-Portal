package model;

public class Onsite extends Job implements interfaces.Payable {
    private final String officeAddress;
    private final boolean providesHousing;

    public Onsite(String jobId, String companyId ,String companyName, String jobTitle, String location, String jobType, double salary, String officeAddress, boolean providesHousing){
        super(jobId,companyId, companyName, jobTitle, location, jobType, salary);
        this.officeAddress = officeAddress;
        this.providesHousing = providesHousing;
    }

    public String getOfficeAddress(){
        return officeAddress;
    }

    public boolean isProvidesHousing() {
        return providesHousing;
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
        System.out.println("Office Address: " + officeAddress);
        System.out.println("Provides Housing: " + providesHousing);
    }


    @Override
    public double calculatePay() {
        double base = getSalary();
        double housing = providesHousing ? 200 : 0;
        return base + housing;
    }
}
