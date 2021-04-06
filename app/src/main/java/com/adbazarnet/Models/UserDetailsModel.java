package com.adbazarnet.Models;

public class UserDetailsModel {
    private String token;
    private String error;
    private User user;

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }
}
