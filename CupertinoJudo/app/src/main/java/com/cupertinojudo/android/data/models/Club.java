package com.cupertinojudo.android.data.models;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class Club {

    @SerializedName("news")
    @Expose
    private List<News> news = null;
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
     */
    public Club(List<News> news, List<Notification> notifications) {
        super();
        this.news = news;
        this.notifications = notifications;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

}
