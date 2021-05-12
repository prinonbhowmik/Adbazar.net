package com.adbazarnet.Models;

public class BidModel {
    private int id;
    private User user;
    private String amount;
    private String created;
    private int ad;

    public BidModel(int id, User user, String amount, String created, int ad) {
        this.id = id;
        this.user = user;
        this.amount = amount;
        this.created = created;
        this.ad = ad;
    }

    public BidModel() {
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getAmount() {
        return amount;
    }

    public String getCreated() {
        return created;
    }

    public int getAd() {
        return ad;
    }
}
