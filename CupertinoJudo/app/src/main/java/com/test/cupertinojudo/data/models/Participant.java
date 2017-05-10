package com.test.cupertinojudo.data.models;

import java.util.Date;

/**
 * Created by fabiohh on 5/9/17.
 */

public class Participant {
    private String mFirst;
    private String mLast;
    private String mFullName;
    private String mPoolName;
    private String mClub;
    private Date mDOB;
    private int mWeight;
    private String mBelt;
    private String mCategory;

    public String getFirst() {
        return mFirst;
    }

    public String getLast() {
        return mLast;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getPoolName() {
        return mPoolName;
    }

    public String getClub() {
        return mClub;
    }

    public Date getDOB() {
        return mDOB;
    }

    public int getWeight() {
        return mWeight;
    }

    public String getBelt() {
        return mBelt;
    }

    public String getCategory() {
        return mCategory;
    }
}
