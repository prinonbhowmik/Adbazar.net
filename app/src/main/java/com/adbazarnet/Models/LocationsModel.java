package com.adbazarnet.Models;

import java.util.List;

public class LocationsModel {

    private int id;
    private String name;
    private String ad_count;
    private String slug;
    private List<SubLocationsModel> sub_locations;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAd_count() {
        return ad_count;
    }

    public String getSlug() {
        return slug;
    }

    public List<SubLocationsModel> getSub_locations() {
        return sub_locations;
    }
}
