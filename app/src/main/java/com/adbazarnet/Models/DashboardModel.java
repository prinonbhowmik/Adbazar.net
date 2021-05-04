package com.adbazarnet.Models;

public class DashboardModel {
    private Integer total_ads;
    private Integer live_ads;
    private Integer pending_ads;
    private Integer expired_ads;
    private String expires_at;
    private String membership;
    private Integer total_credit;
    private Integer used_credit;
    private String membership_type;
    private Integer free_remaining;
    private Integer paid_remaining;

    public DashboardModel(Integer total_ads, Integer live_ads, Integer pending_ads, Integer expired_ads, String expires_at, String membership, Integer total_credit, Integer used_credit, String membership_type, Integer free_remaining, Integer paid_remaining) {
        this.total_ads = total_ads;
        this.live_ads = live_ads;
        this.pending_ads = pending_ads;
        this.expired_ads = expired_ads;
        this.expires_at = expires_at;
        this.membership = membership;
        this.total_credit = total_credit;
        this.used_credit = used_credit;
        this.membership_type = membership_type;
        this.free_remaining = free_remaining;
        this.paid_remaining = paid_remaining;
    }

    public DashboardModel() {
    }

    public Integer getTotal_ads() {
        return total_ads;
    }

    public Integer getLive_ads() {
        return live_ads;
    }

    public Integer getPending_ads() {
        return pending_ads;
    }

    public Integer getExpired_ads() {
        return expired_ads;
    }

    public String getExpires_at() {
        return expires_at;
    }

    public String getMembership() {
        return membership;
    }

    public Integer getTotal_credit() {
        return total_credit;
    }

    public Integer getUsed_credit() {
        return used_credit;
    }

    public String getMembership_type() {
        return membership_type;
    }

    public Integer getFree_remaining() {
        return free_remaining;
    }

    public Integer getPaid_remaining() {
        return paid_remaining;
    }
}
