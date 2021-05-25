package com.adbazarnet.Models;

public class SenderDetailsModel {
    private int id;
    private String name;
    private String avatar;
    private String email;
    private String phone_number;
    private boolean is_online;
    private String membership_name;
    private String type;

    public SenderDetailsModel(int id, String name, String avatar, String email, String phone_number, boolean is_online, String membership_name, String type) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
        this.phone_number = phone_number;
        this.is_online = is_online;
        this.membership_name = membership_name;
        this.type = type;
    }

    public int getId() {
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

    public String getPhone_number() {
        return phone_number;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public String getMembership_name() {
        return membership_name;
    }

    public String getType() {
        return type;
    }
}
