package com.adbazarnet.Models;

import java.util.List;

public class AdDetails {
    private int id;
    private List<AdImages> ad_images;
    private SubCategoryModel category;
    private SubLocationsModel location;
    private User user;
    private List<RelatedAds> related_ads;
    private int bid_count;
    private List<AdPhoneNumbers> ad_phone_numbers;
    private String thumbnail;
    private String ad_title;
    private String ad_type;
    private String description;
    private String condition;
    private int price;
    private String other_information;
    private String warranty;
    private String model_and_year;
    private String mileage;
    private String address;
    private String plot_size;
    private String service_type;
    private String features;
    private int salary;
    private String job_type;
    private int total_vacancies;
    private String application_deadline;
    private String about_company;
    private String minimum_requirement;
    private String company_website;
    private String attached_file;
    private String publish_status;
    private String rejection_reason;
    private boolean negotiable;
    private boolean top_ad;
    private boolean active;
    private boolean is_sell;
    private boolean is_bid;
    private boolean is_job;
    private boolean hide_phone;
    private String created;
    private String updated;
    private String publish_date;
    private String expiry_date;
    private String top_ad_expiry_date;
    private int views;

    public int getId() {
        return id;
    }

    public List<AdImages> getAd_images() {
        return ad_images;
    }

    public SubCategoryModel getCategory() {
        return category;
    }

    public SubLocationsModel getLocation() {
        return location;
    }

    public User getUser() {
        return user;
    }

    public List<RelatedAds> getRelated_ads() {
        return related_ads;
    }

    public int getBid_count() {
        return bid_count;
    }

    public List<AdPhoneNumbers> getAd_phone_numbers() {
        return ad_phone_numbers;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAd_title() {
        return ad_title;
    }

    public String getAd_type() {
        return ad_type;
    }

    public String getDescription() {
        return description;
    }

    public String getCondition() {
        return condition;
    }

    public int getPrice() {
        return price;
    }

    public String getOther_information() {
        return other_information;
    }

    public String getWarranty() {
        return warranty;
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

    public String getFeatures() {
        return features;
    }

    public int getSalary() {
        return salary;
    }

    public String getJob_type() {
        return job_type;
    }

    public int getTotal_vacancies() {
        return total_vacancies;
    }

    public String getApplication_deadline() {
        return application_deadline;
    }

    public String getAbout_company() {
        return about_company;
    }

    public String getMinimum_requirement() {
        return minimum_requirement;
    }

    public String getCompany_website() {
        return company_website;
    }

    public String getAttached_file() {
        return attached_file;
    }

    public String getPublish_status() {
        return publish_status;
    }

    public String getRejection_reason() {
        return rejection_reason;
    }

    public boolean isNegotiable() {
        return negotiable;
    }

    public boolean isTop_ad() {
        return top_ad;
    }

    public boolean isActive() {
        return active;
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

    public boolean isHide_phone() {
        return hide_phone;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public String getTop_ad_expiry_date() {
        return top_ad_expiry_date;
    }

    public int getViews() {
        return views;
    }
}
