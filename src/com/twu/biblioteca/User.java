package com.twu.biblioteca;

public class User {
    public static final String regexPattern = "([0-9]|[a-z]|[A-Z]){3}-([0-9]|[a-z]|[A-Z]){4}";
    private String loginID;
    private String password;
    private String name;
    private String email;
    private String phoneNum;

    public User(String loginID, String name, String email, String phoneNum) {
        if(loginID.matches(regexPattern)) {
            this.loginID = loginID;
            password = "test123!";
            this.name = name;
            this.email = email;
            this.phoneNum = phoneNum;
        }
    }

    public boolean isInit(){
        return !(loginID==null || loginID.isEmpty());
    }

    public boolean equals(User other) {
        return this.loginID.equals(other.loginID);
    }

    public String getId() {
        return loginID;
    }

    public String getPw() {
        return password;
    }

    public boolean verifyPassword(String password) {
        if(this.password.equals(password))
            return true;
        return false;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
}
