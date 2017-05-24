package com.cupertinojudo.android.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Club {

    @SerializedName("news")
    @Expose
    private List<News> news = null;
    @SerializedName("practice")
    @Expose
    private List<Practice> practice = null;
    @SerializedName("notifications")
    @Expose
    private List<Notification> notifications = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Club() {
    }

    /**
     *
     * @param notifications
     * @param news
     * @param practice
     */
    public Club(List<News> news, List<Practice> practice, List<Notification> notifications) {
        super();
        this.news = news;
        this.practice = practice;
        this.notifications = notifications;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Practice> getPractice() {
        return practice;
    }

    public void setPractice(List<Practice> practice) {
        this.practice = practice;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

}
