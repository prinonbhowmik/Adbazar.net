package com.adbazarnet.Models;

public class UserDetailsModel {
    private String token;
    private String error;
    private String status;
    private String old_password;
    private String new_password;
    private User user;

    public UserDetailsModel(String old_password, String new_password) {
        this.old_password = old_password;
        this.new_password = new_password;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public String getOld_password() {
        return old_password;
    }

    public String getNew_password() {
        return new_password;
    }
}
