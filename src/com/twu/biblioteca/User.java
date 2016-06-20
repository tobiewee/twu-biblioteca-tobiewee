package com.twu.biblioteca;

class User {
    static final String regexPattern = "([0-9]|[a-z]|[A-Z]){3}-([0-9]|[a-z]|[A-Z]){4}";
    private String loginID;
    private String password;
    private String name;
    private String email;
    private String phoneNum;

    User(String loginID, String name, String email, String phoneNum) {
        if(loginID.matches(regexPattern)) {
            this.loginID = loginID;
            password = "test123!";
            this.name = name;
            this.email = email;
            this.phoneNum = phoneNum;
        }
    }

    boolean isInit(){
        return !(loginID==null || loginID.isEmpty());
    }

    boolean equals(User other) {
        return this.loginID.equals(other.loginID);
    }

    String getId() {
        return loginID;
    }

    String getPw() {
        return password;
    }

    boolean verifyPassword(String password) {
        if(this.password.equals(password))
            return true;
        return false;
    }

    String getName() {
        return name;
    }

    String getEmail() {
        return email;
    }

    String getPhoneNum() {
        return phoneNum;
    }
}
