package model;

public class User {
    private String userId;
    private String fullName;
    private String email;
    private String password;
    private Role role;
    private String companyId;

    public User(String userId, String fullName, String email ,String password, Role role, String companyId){
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.companyId = companyId;

    }

    public String getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void displayUserDetails(){
        System.out.println("UserId : " + userId);
        System.out.println("Full name: " + fullName);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
        System.out.println("company ID: " + companyId);

    }

    @Override
    public String toString() {
        return userId + "," + fullName + "," + email + "," + password + "," + role.name() + "," + companyId;
    }
}
