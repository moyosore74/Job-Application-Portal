package model;

public class Company {
    private String companyId;
    private String companyName;

    public Company(String companyId, String companyName) {
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void displayDetails(){
        System.out.println("Company Id: " + companyId);
        System.out.println("Company Name: " + companyName);
    }

}
