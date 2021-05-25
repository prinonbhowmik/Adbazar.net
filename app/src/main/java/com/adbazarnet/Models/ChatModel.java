package com.adbazarnet.Models;

public class ChatModel {
    private int id;
    private SenderDetailsModel sender_details;
    private ReceiverDetailsModel receiver_details;
    private ChatAdDetails ad_details;
    private int sender_unread;
    private int receiver_unread;
    private String created;
    private String updated;
    private int ad;
    private int sender;
    private int receiver;

    public ChatModel(int id, SenderDetailsModel sender_details, ReceiverDetailsModel receiver_details, ChatAdDetails ad_details, int sender_unread, int receiver_unread, String created, String updated, int ad, int sender, int receiver) {
        this.id = id;
        this.sender_details = sender_details;
        this.receiver_details = receiver_details;
        this.ad_details = ad_details;
        this.sender_unread = sender_unread;
        this.receiver_unread = receiver_unread;
        this.created = created;
        this.updated = updated;
        this.ad = ad;
        this.sender = sender;
        this.receiver = receiver;
    }

    public int getId() {
        return id;
    }

    public SenderDetailsModel getSender_details() {
        return sender_details;
    }

    public ReceiverDetailsModel getReceiver_details() {
        return receiver_details;
    }

    public ChatAdDetails getAd_details() {
        return ad_details;
    }

    public int getSender_unread() {
        return sender_unread;
    }

    public int getReceiver_unread() {
        return receiver_unread;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public int getAd() {
        return ad;
    }

    public int getSender() {
        return sender;
    }

    public int getReceiver() {
        return receiver;
    }
}
