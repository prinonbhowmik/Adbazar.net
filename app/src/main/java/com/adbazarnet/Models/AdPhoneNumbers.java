package com.adbazarnet.Models;

public class AdPhoneNumbers {
    private int id;
    private String phone;
    private int user;
    private int ad;

    public AdPhoneNumbers(int id, String phone, int user, int ad) {
        this.id = id;
        this.phone = phone;
        this.user = user;
        this.ad = ad;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public int getUser() {
        return user;
    }

    public int getAd() {
        return ad;
    }
}
