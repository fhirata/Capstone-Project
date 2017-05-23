package com.cupertinojudo.android.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Practice {

    @SerializedName("regular")
    @Expose
    private List<Regular> regular = null;
    @SerializedName("kangeiko")
    @Expose
    private List<Kangeiko> kangeiko = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Practice() {
    }

    /**
     *
     * @param kangeiko
     * @param regular
     */
    public Practice(List<Regular> regular, List<Kangeiko> kangeiko) {
        super();
        this.regular = regular;
        this.kangeiko = kangeiko;
    }

    public List<Regular> getRegular() {
        return regular;
    }

    public void setRegular(List<Regular> regular) {
        this.regular = regular;
    }

    public List<Kangeiko> getKangeiko() {
        return kangeiko;
    }

    public void setKangeiko(List<Kangeiko> kangeiko) {
        this.kangeiko = kangeiko;
    }

}