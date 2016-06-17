package com.twu.biblioteca;

public class User {
    private String loginID;
    private String password;

    public User(String loginID) {
        this.loginID = loginID;
        password = "test123!";
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
}
