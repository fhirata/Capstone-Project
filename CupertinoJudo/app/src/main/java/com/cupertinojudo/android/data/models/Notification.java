package com.cupertinojudo.android.data.models;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    private static transient SimpleDateFormat sServerTimeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");
    private static transient Date sTimeStamp;

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

    public String getRawDate() {
        return date;
    }

    public Date getDate() {
        try {
            return sServerTimeFormat.parse(date);
        } catch (ParseException parseException) {
            Log.e("HERE", parseException.getMessage());
        }
        return null;
    }

    public void setDate(String date) {
        this.date = date;
    }

}