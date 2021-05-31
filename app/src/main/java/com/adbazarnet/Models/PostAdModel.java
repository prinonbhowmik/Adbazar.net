package com.adbazarnet.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class PostAdModel {
    private boolean is_bid;
    private String ad_title;
    private String condition;
    private String price;
    private String warranty;
    private String other_information;
    private List<PhoneNoModel> ad_phone_numbers;
    private String description;
    private int location;
    private String model_and_year;
    private String mileage;
    private String address;
    private String land;
    private String service;
    private int category;
    private List<PostImageModel> images;
    private boolean negotiable;
    private String ad_type;
    private boolean hide_phone;
    private boolean is_sell;

    //sell
    public PostAdModel(String ad_title, String condition, String price, String warranty,
                       String other_information, List<PhoneNoModel> ad_phone_numbers, String description,
                       int location, int category, List<PostImageModel> images,
                       boolean negotiable, String ad_type, boolean hide_phone,boolean is_sell) {
        this.ad_title = ad_title;
        this.condition = condition;
        this.price = price;
        this.warranty = warranty;
        this.other_information = other_information;
        this.ad_phone_numbers = ad_phone_numbers;
        this.description = description;
        this.location = location;
        this.category = category;
        this.images = images;
        this.negotiable = negotiable;
        this.ad_type = ad_type;
        this.hide_phone = hide_phone;
        this.is_sell = is_sell;
    }


    public PostAdModel(String ad_title, String condition, String price, String warranty,
                       String other_information, List<PhoneNoModel> ad_phone_numbers,
                       String description, int location, String model_and_year, String mileage,
                       int category, List<PostImageModel> images, boolean negotiable, String ad_type,
                       boolean hide_phone,boolean is_sell) {
        this.ad_title = ad_title;
        this.condition = condition;
        this.price = price;
        this.warranty = warranty;
        this.other_information = other_information;
        this.ad_phone_numbers = ad_phone_numbers;
        this.description = description;
        this.location = location;
        this.model_and_year = model_and_year;
        this.mileage = mileage;
        this.category = category;
        this.images = images;
        this.negotiable = negotiable;
        this.ad_type = ad_type;
        this.hide_phone = hide_phone;
        this.is_sell = is_sell;
    }

    public PostAdModel(String ad_title, String condition, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description, int location, String address, String land, int category, List<PostImageModel> images, boolean negotiable, String ad_type, boolean hide_phone, boolean is_sell) {
        this.ad_title = ad_title;
        this.condition = condition;
        this.price = price;
        this.other_information = other_information;
        this.ad_phone_numbers = ad_phone_numbers;
        this.description = description;
        this.location = location;
        this.address = address;
        this.land = land;
        this.category = category;
        this.images = images;
        this.negotiable = negotiable;
        this.ad_type = ad_type;
        this.hide_phone = hide_phone;
        this.is_sell = is_sell;
    }

    public PostAdModel(String ad_title, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description, int location, int category, List<PostImageModel> images, boolean negotiable, String ad_type, boolean hide_phone, boolean is_sell) {
        this.ad_title = ad_title;
        this.price = price;
        this.other_information = other_information;
        this.ad_phone_numbers = ad_phone_numbers;
        this.description = description;
        this.location = location;
        this.category = category;
        this.images = images;
        this.negotiable = negotiable;
        this.ad_type = ad_type;
        this.hide_phone = hide_phone;
        this.is_sell = is_sell;
    }

    public PostAdModel(String ad_title, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description, int location, String address, String service, int category, List<PostImageModel> images, boolean negotiable, String ad_type, boolean hide_phone, boolean is_sell) {
        this.ad_title = ad_title;
        this.price = price;
        this.other_information = other_information;
        this.ad_phone_numbers = ad_phone_numbers;
        this.description = description;
        this.location = location;
        this.address = address;
        this.service = service;
        this.category = category;
        this.images = images;
        this.negotiable = negotiable;
        this.ad_type = ad_type;
        this.hide_phone = hide_phone;
        this.is_sell = is_sell;
    }

    //bid
    public PostAdModel(String ad_title, String other_information, String description,
                       int location, int category, List<PostImageModel> images,
                       String ad_type,boolean is_bid) {
        this.ad_title = ad_title;
        this.other_information = other_information;
        this.description = description;
        this.location = location;
        this.category = category;
        this.images = images;
        this.ad_type = ad_type;
        this.is_bid = is_bid;
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

    public List<PhoneNoModel> getAd_phone_numbers() {
        return ad_phone_numbers;
    }

    public List<PostImageModel> getImages() {
        return images;
    }
}
