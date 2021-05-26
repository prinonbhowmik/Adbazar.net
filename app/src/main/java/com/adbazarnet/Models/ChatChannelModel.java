package com.adbazarnet.Models;

public class ChatChannelModel {
    private int id;
    private String message;
    private boolean is_read;
    private String created;
    private int channel;
    private int user;

    public ChatChannelModel(int id, String message, boolean is_read, String created, int channel, int user) {
        this.id = id;
        this.message = message;
        this.is_read = is_read;
        this.created = created;
        this.channel = channel;
        this.user = user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isIs_read() {
        return is_read;
    }

    public String getCreated() {
        return created;
    }

    public int getChannel() {
        return channel;
    }

    public int getUser() {
        return user;
    }
}
