package com.cupertinojudo.android.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("date")
    @Expose
    private String date;

    /**
     * No args constructor for use in serialization
     *
     */
    public News() {
    }

    /**
     *
     * @param content
     * @param date
     */
    public News(String content, String date) {
        super();
        this.content = content;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}