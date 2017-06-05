package com.cupertinojudo.android.data.models;

import com.google.firebase.crash.FirebaseCrash;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class News {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("bg_image")
    @Expose
    private String bgImage;
    @SerializedName("date")
    @Expose
    private String date;

    private static transient SimpleDateFormat sServerTimeFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZ yyyy");
    private static transient Date sTimeStamp;

    /**
     * No args constructor for use in serialization
     *
     */
    public News() {
    }

    /**
     *
     * @param body
     * @param title
     * @param bgImage
     * @param date
     */
    public News(String title, String body, String bgImage, String date) {
        super();
        this.title = title;
        this.body = body;
        this.bgImage = bgImage;
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

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }

    public String getRawDate() {
        return date;
    }

    public Date getDate() {
        try {
            return sServerTimeFormat.parse(date);
        } catch (ParseException parseException) {
            FirebaseCrash.report(parseException);
        }
        return null;
    }


    public void setDate(String date) {
        this.date = date;
    }

}