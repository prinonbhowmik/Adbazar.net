package com.adbazarnet.Models;

public class UserDetailsModel {
    private String token;
    private String error;
    private String status;
    private String oldPass;
    private String newPass;
    private User user;

    public UserDetailsModel(String oldPass, String newPass) {
        this.oldPass = oldPass;
        this.newPass = newPass;
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

    public String getOldPass() {
        return oldPass;
    }

    public String getStatus() {
        return status;
    }

    public String getNewPass() {
        return newPass;
    }
}
