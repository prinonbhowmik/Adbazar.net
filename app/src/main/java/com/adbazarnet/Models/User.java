package com.adbazarnet.Models;

import java.util.List;

public class User {
    private Integer id;
    private String name;
    private String avatar;
    private String email;
    private String phoneNumber;
    private Boolean isOnline;
    private String type;
    private String membershipName;
    private List<Object> userPhoneNumbers = null;

    public User(Integer id, String name, String avatar, String email, String phoneNumber, Boolean isOnline, String type, String membershipName, List<Object> userPhoneNumbers) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isOnline = isOnline;
        this.type = type;
        this.membershipName = membershipName;
        this.userPhoneNumbers = userPhoneNumbers;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public String getType() {
        return type;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public List<Object> getUserPhoneNumbers() {
        return userPhoneNumbers;
    }
}
