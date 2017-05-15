package com.cupertinojudo.android.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * No args constructor for use in serialization
     *
     */
    public Notification() {
    }

    /**
     *
     * @param body
     * @param title
     * @param date
     */
    public Notification(String title, String body, String date) {
        super();
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}