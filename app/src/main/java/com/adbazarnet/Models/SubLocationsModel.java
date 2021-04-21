package com.adbazarnet.Models;

public class SubLocationsModel {
    private int id;
    private String name;
    private int ad_count;
    private String slug;
    private String location_name;

    public SubLocationsModel(int id, String name, int ad_count, String slug, String location_name) {
        this.id = id;
        this.name = name;
        this.ad_count = ad_count;
        this.slug = slug;
        this.location_name = location_name;
    }

    public SubLocationsModel() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAd_count() {
        return ad_count;
    }

    public String getSlug() {
        return slug;
    }

    public String getLocation_name() {
        return location_name;
    }
}
