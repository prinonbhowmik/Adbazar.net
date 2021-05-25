package com.adbazarnet.Models;

import org.json.JSONObject;

import java.util.List;

public class PostAdModel {
    private String ad_title;
    private String condition;
    private String price;
    private String warranty;
    private String other_information;
    private String description;
    private int location;
    private int category;
    private boolean negotiable;
    private String ad_type;
    private boolean hide_phone;

    public PostAdModel(String ad_title, String condition, String price, String warranty, String other_information,
                       String description, int location, int category, boolean negotiable, String ad_type,
                       boolean hide_phone) {
        this.ad_title = ad_title;
        this.condition = condition;
        this.price = price;
        this.warranty = warranty;
        this.other_information = other_information;
        this.description = description;
        this.location = location;
        this.category = category;
        this.negotiable = negotiable;
        this.ad_type = ad_type;
        this.hide_phone = hide_phone;
    }

    public PostAdModel() {
    }

    public String getAd_title() {
        return ad_title;
    }

    public String getCondition() {
        return condition;
    }

    public String getPrice() {
        return price;
    }

    public String getWarranty() {
        return warranty;
    }

    public String getOther_information() {
        return other_information;
    }

    public String getDescription() {
        return description;
    }

    public int getLocation() {
        return location;
    }

    public int getCategory() {
        return category;
    }

    public boolean isNegotiable() {
        return negotiable;
    }

    public String getAd_type() {
        return ad_type;
    }

    public boolean isHide_phone() {
        return hide_phone;
    }
}
