package com.adbazarnet.Models;

import java.util.List;

public class EditAdModel {
    private String ad_title;
    private String condition;
    private String jobType;
    private int vacancy;
    private String price;
    private String requirment;
    private String deadline;
    private String employeer;
    private String warranty;
    private String website;
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
    private boolean is_bid;
    private boolean is_job;

    public EditAdModel(String ad_title, String condition, String price, String warranty,
                       String other_information, List<PhoneNoModel> ad_phone_numbers, String description,
                       int location, int category, List<PostImageModel> images,
                       boolean negotiable, String ad_type, boolean hide_phone, boolean is_sell, boolean is_bid, boolean is_job) {
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
        this.is_bid = is_bid;
        this.is_job = is_job;
    }


    public EditAdModel(String ad_title, String condition, String price, String warranty,
                       String other_information, List<PhoneNoModel> ad_phone_numbers,
                       String description, int location, String model_and_year, String mileage,
                       int category, List<PostImageModel> images, boolean negotiable, String ad_type,
                       boolean hide_phone, boolean is_sell, boolean is_bid, boolean is_job) {
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
        this.is_bid = is_bid;
        this.is_job = is_job;
    }

    public EditAdModel(String ad_title, String condition, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description,
                       int location, String address, String land, int category, List<PostImageModel> images, boolean negotiable, String ad_type,
                       boolean hide_phone, boolean is_sell, boolean is_bid, boolean is_job) {
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
        this.is_bid = is_bid;
        this.is_job = is_job;
    }

    public EditAdModel(String ad_title, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description, int location, int category,
                       List<PostImageModel> images, boolean negotiable, String ad_type, boolean hide_phone, boolean is_sell, boolean is_bid, boolean is_job) {
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
        this.is_bid = is_bid;
        this.is_job = is_job;
    }

    public EditAdModel(String ad_title, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description, int location, String address,
                       String service, int category, List<PostImageModel> images, boolean negotiable, String ad_type, boolean hide_phone,
                       boolean is_sell, boolean is_bid, boolean is_job) {
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
        this.is_bid = is_bid;
        this.is_job = is_job;
    }

    //bid
    public EditAdModel(String ad_title, String other_information, String description,
                       int location, int category, List<PostImageModel> images,
                       String ad_type, boolean is_sell, boolean is_bid, boolean is_job) {
        this.ad_title = ad_title;
        this.other_information = other_information;
        this.description = description;
        this.location = location;
        this.category = category;
        this.images = images;
        this.ad_type = ad_type;
        this.is_sell = is_sell;
        this.is_bid = is_bid;
        this.is_job = is_job;
    }

    public EditAdModel(String ad_title, String jobType, int vacancy, String requirment, String deadline, String employeer,
                       String website, String other_information, String description, int location, String address, int category, List<PostImageModel> images,
                       String ad_type,boolean is_sell, boolean is_bid, boolean is_job) {
        this.ad_title = ad_title;
        this.jobType = jobType;
        this.vacancy = vacancy;
        this.requirment = requirment;
        this.deadline = deadline;
        this.employeer = employeer;
        this.website = website;
        this.other_information = other_information;
        this.description = description;
        this.location = location;
        this.address = address;
        this.category = category;
        this.images = images;
        this.ad_type = ad_type;
        this.is_sell = is_sell;
        this.is_bid = is_bid;
        this.is_job = is_job;

    }

    public EditAdModel() {
    }

    public String getAd_title() {
        return ad_title;
    }

    public String getCondition() {
        return condition;
    }

    public String getJobType() {
        return jobType;
    }

    public int getVacancy() {
        return vacancy;
    }

    public String getPrice() {
        return price;
    }

    public String getRequirment() {
        return requirment;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getEmployeer() {
        return employeer;
    }

    public String getWarranty() {
        return warranty;
    }

    public String getWebsite() {
        return website;
    }

    public String getOther_information() {
        return other_information;
    }

    public List<PhoneNoModel> getAd_phone_numbers() {
        return ad_phone_numbers;
    }

    public String getDescription() {
        return description;
    }

    public int getLocation() {
        return location;
    }

    public String getModel_and_year() {
        return model_and_year;
    }

    public String getMileage() {
        return mileage;
    }

    public String getAddress() {
        return address;
    }

    public String getLand() {
        return land;
    }

    public String getService() {
        return service;
    }

    public int getCategory() {
        return category;
    }

    public List<PostImageModel> getImages() {
        return images;
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

    public boolean isIs_sell() {
        return is_sell;
    }

    public boolean isIs_bid() {
        return is_bid;
    }

    public boolean isIs_job() {
        return is_job;
    }
}
