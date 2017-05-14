package com.test.cupertinojudo.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tournament {

    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("participants")
    @Expose
    private List<Participant> participants = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Tournament() {
    }

    /**
     *
     * @param year
     * @param participants
     */
    public Tournament(Integer year, List<Participant> participants) {
        super();
        this.year = year;
        this.participants = participants;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

}
