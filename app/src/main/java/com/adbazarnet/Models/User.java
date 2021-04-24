package com.adbazarnet.Models;

import java.util.List;

public class User {

    private Integer id;
    private String name;
    private String avatar;
    private String email;
    private String phone_number;
    private Boolean is_online;
    private String type;
    private String membership_name;
    private List<Object> user_phone_numbers = null;

    public User(Integer id, String name, String avatar, String email, String phone_number, Boolean is_online, String type, String membership_name, List<Object> user_phone_numbers) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.email = email;
        this.phone_number = phone_number;
        this.is_online = is_online;
        this.type = type;
        this.membership_name = membership_name;
        this.user_phone_numbers = user_phone_numbers;
    }

    public User(String name, String email, String phone_number) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
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

    public String getPhone_number() {
        return phone_number;
    }

    public Boolean getIs_online() {
        return is_online;
    }

    public String getType() {
        return type;
    }

    public String getMembership_name() {
        return membership_name;
    }

    public List<Object> getUser_phone_numbers() {
        return user_phone_numbers;
    }
}
