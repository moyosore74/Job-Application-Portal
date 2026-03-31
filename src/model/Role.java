package model;

public enum Role {
     ADMIN(1),
    APPLICANT(2),
    EMPLOYER(3);

     private final int roleNum;
     Role(int roleNum) {
        this.roleNum = roleNum;
    }
    public int getRoleNum(){
        return this.roleNum;
    }
}
