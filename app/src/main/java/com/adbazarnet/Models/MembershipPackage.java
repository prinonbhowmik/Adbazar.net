package com.adbazarnet.Models;

public class MembershipPackage {

    private int id;
    private String name;
    private String type;
    private String color;
    private String account;
    private String message;
    private boolean is_active;
    private int ad_limit;
    private int ad_duration;
    private int membership_duration;
    private int price;
    private String created;

    public MembershipPackage(int id, String name, String type, String color, String account, String message, boolean is_active, int ad_limit, int ad_duration, int membership_duration, int price, String created) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.color = color;
        this.account = account;
        this.message = message;
        this.is_active = is_active;
        this.ad_limit = ad_limit;
        this.ad_duration = ad_duration;
        this.membership_duration = membership_duration;
        this.price = price;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public int getAd_limit() {
        return ad_limit;
    }

    public int getAd_duration() {
        return ad_duration;
    }

    public int getMembership_duration() {
        return membership_duration;
    }

    public int getPrice() {
        return price;
    }

    public String getCreated() {
        return created;
    }

    public String getAccount() {
        return account;
    }

    public String getMessage() {
        return message;
    }

    public boolean isIs_active() {
        return is_active;
    }
}
