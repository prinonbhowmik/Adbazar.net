package com.adbazarnet.Models;

public class FavouriteAds {
    private int id;
    private FavouriteAdDetails ad_detail;
    private int user;
    private int ad;

    public FavouriteAds(int id, FavouriteAdDetails ad_detail, int user, int ad) {
        this.id = id;
        this.ad_detail = ad_detail;
        this.user = user;
        this.ad = ad;
    }

    public int getId() {
        return id;
    }

    public FavouriteAdDetails getAd_detail() {
        return ad_detail;
    }

    public int getUser() {
        return user;
    }

    public int getAd() {
        return ad;
    }
}
