package com.twu.biblioteca;

public class User {
    private String loginID;
    private String password;
    private String name;
    private String email;
    private String phoneNum;

    public User(String loginID, String name, String email, String phoneNum) {
        this.loginID = loginID;
        password = "test123!";
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
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
