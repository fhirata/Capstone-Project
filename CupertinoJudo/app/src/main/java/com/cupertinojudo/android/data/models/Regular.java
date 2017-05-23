package com.cupertinojudo.android.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Regular {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("time1")
    @Expose
    private String time1;
    @SerializedName("time2")
    @Expose
    private String time2;

    /**
     * No args constructor for use in serialization
     *
     */
    public Regular() {
    }

    /**
     *
     * @param time2
     * @param time1
     * @param day
     */
    public Regular(String day, String time1, String time2) {
        super();
        this.day = day;
        this.time1 = time1;
        this.time2 = time2;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

}