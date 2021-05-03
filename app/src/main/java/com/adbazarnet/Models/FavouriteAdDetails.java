package com.adbazarnet.Models;

public class FavouriteAdDetails {
    private int id;
    private String thumbnail;
    private String ad_title;
    private boolean top_ad;
    private String category;
    private String sub_category;
    private String location;
    private String sub_location;
    private int price;
    private String created;
    private int bid_count;
    private boolean is_bid;
    private boolean is_job;

    public FavouriteAdDetails(int id, String thumbnail, String ad_title, boolean top_ad, String category, String sub_category, String location, String sub_location, int price, String created, int bid_count, boolean is_bid, boolean is_job) {
        this.id = id;
        this.thumbnail = thumbnail;
        this.ad_title = ad_title;
        this.top_ad = top_ad;
        this.category = category;
        this.sub_category = sub_category;
        this.location = location;
        this.sub_location = sub_location;
        this.price = price;
        this.created = created;
        this.bid_count = bid_count;
        this.is_bid = is_bid;
        this.is_job = is_job;
    }

    public FavouriteAdDetails() {
    }

    public int getId() {
        return id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getAd_title() {
        return ad_title;
    }

    public boolean isTop_ad() {
        return top_ad;
    }

    public String getCategory() {
        return category;
    }

    public String getSub_category() {
        return sub_category;
    }

    public String getLocation() {
        return location;
    }

    public String getSub_location() {
        return sub_location;
    }

    public int getPrice() {
        return price;
    }

    public String getCreated() {
        return created;
    }

    public int getBid_count() {
        return bid_count;
    }

    public boolean isIs_bid() {
        return is_bid;
    }

    public boolean isIs_job() {
        return is_job;
    }
}
