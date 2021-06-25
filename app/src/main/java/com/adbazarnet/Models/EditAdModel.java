package com.adbazarnet.Models;

import java.util.List;

public class EditAdModel {
    private boolean is_bid;
    private String ad_title;
    private String condition;
    private String job_type;
    private int total_vacancies;
    private String price;
    private String minimum_requirement;
    private String application_deadline;
    private String about_company;
    private String warranty;
    private String company_website;
    private String other_information;
    private List<PhoneNoModel> ad_phone_numbers;
    private String description;
    private int location;
    private String model_and_year;
    private String mileage;
    private String address;
    private String plot_size;
    private String service_type;
    private int category;
    private List<PostImageModel> images;
    private boolean negotiable;
    private String ad_type;
    private boolean hide_phone;
    private boolean is_sell;
    private boolean is_job;

    //electronics
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

    //vehicle
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
    //property
    public EditAdModel(String ad_title, String price, String other_information,
                       List<PhoneNoModel> ad_phone_numbers, String description, int location,
                       String address, String plot_size, int category, List<PostImageModel> images,
                       boolean negotiable, String ad_type, boolean hide_phone) {
        this.ad_title = ad_title;
        this.price = price;
        this.other_information = other_information;
        this.ad_phone_numbers = ad_phone_numbers;
        this.description = description;
        this.location = location;
        this.address = address;
        this.plot_size = plot_size;
        this.category = category;
        this.images = images;
        this.negotiable = negotiable;
        this.ad_type = ad_type;
        this.hide_phone = hide_phone;

    }

    //general
    public EditAdModel(String ad_title, String price, String other_information, List<PhoneNoModel> ad_phone_numbers,
                       String description, int location, int category, List<PostImageModel> images, boolean negotiable,
                       boolean hide_phone,String ad_type) {
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

    //service
    public EditAdModel(String ad_title,boolean hide_phone, String price, String other_information, List<PhoneNoModel> ad_phone_numbers,
                       String description, int location, String address, String service_type, int category,
                       List<PostImageModel> images, boolean negotiable, String ad_type) {
        this.ad_title = ad_title;
        this.price = price;
        this.other_information = other_information;
        this.ad_phone_numbers = ad_phone_numbers;
        this.description = description;
        this.location = location;
        this.address = address;
        this.service_type = service_type;
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

    //job
    public EditAdModel(String ad_title, String job_type, int total_vacancies, String minimum_requirement,
                       String application_deadline, String about_company, String company_website, String other_information,
                       String description, int location, String address, int category, List<PostImageModel> images,
                       String ad_type) {
        this.ad_title = ad_title;
        this.job_type = job_type;
        this.total_vacancies = total_vacancies;
        this.minimum_requirement = minimum_requirement;
        this.application_deadline = application_deadline;
        this.about_company = about_company;
        this.company_website = company_website;
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

    public boolean isIs_bid() {
        return is_bid;
    }

    public String getAd_title() {
        return ad_title;
    }

    public String getCondition() {
        return condition;
    }

    public String getJob_type() {
        return job_type;
    }

    public int getTotal_vacancies() {
        return total_vacancies;
    }

    public String getPrice() {
        return price;
    }

    public String getMinimum_requirement() {
        return minimum_requirement;
    }

    public String getApplication_deadline() {
        return application_deadline;
    }

    public String getAbout_company() {
        return about_company;
    }

    public String getWarranty() {
        return warranty;
    }

    public String getCompany_website() {
        return company_website;
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

    public String getPlot_size() {
        return plot_size;
    }

    public String getService_type() {
        return service_type;
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

    public boolean isIs_job() {
        return is_job;
    }
}
