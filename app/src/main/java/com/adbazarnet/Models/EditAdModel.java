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

    public EditAdModel(String ad_title, String condition, String price, String warranty,
                       String other_information, List<PhoneNoModel> ad_phone_numbers, String description,
                       int location, int category, List<PostImageModel> images,
                       boolean negotiable, String ad_type, boolean hide_phone) {
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
    }


    public EditAdModel(String ad_title, String condition, String price, String warranty,
                       String other_information, List<PhoneNoModel> ad_phone_numbers,
                       String description, int location, String model_and_year, String mileage,
                       int category, List<PostImageModel> images, boolean negotiable, String ad_type,
                       boolean hide_phone) {
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
    }

    public EditAdModel(String ad_title, String condition, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description,
                       int location, String address, String land, int category, List<PostImageModel> images, boolean negotiable, String ad_type, boolean hide_phone) {
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
    }

    public EditAdModel(String ad_title, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description, int location, int category,
                       List<PostImageModel> images, boolean negotiable, String ad_type, boolean hide_phone) {
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

    }

    public EditAdModel(String ad_title, String price, String other_information, List<PhoneNoModel> ad_phone_numbers, String description, int location, String address,
                       String service, int category, List<PostImageModel> images, boolean negotiable, String ad_type, boolean hide_phone) {
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

    }

    //bid
    public EditAdModel(String ad_title, String other_information, String description,
                       int location, int category, List<PostImageModel> images,
                       String ad_type) {
        this.ad_title = ad_title;
        this.other_information = other_information;
        this.description = description;
        this.location = location;
        this.category = category;
        this.images = images;
        this.ad_type = ad_type;

    }

    public EditAdModel(String ad_title, String jobType, int vacancy, String requirment, String deadline, String employeer,
                       String website, String other_information, String description, int location, String address, int category, List<PostImageModel> images, String ad_type) {
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

    }

    public EditAdModel() {
    }
}
