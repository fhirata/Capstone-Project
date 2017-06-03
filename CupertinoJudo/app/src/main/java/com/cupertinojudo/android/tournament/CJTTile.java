package com.cupertinojudo.android.tournament;

import android.graphics.drawable.Drawable;

/**
 *
 */

public class CJTTile {
    private String mTitle;
    private Drawable mIcon;
    private int mId;
    private String mContentDescriptions;

    public CJTTile(String title, Drawable icon, String contentDescriptions, int id) {
        mTitle = title;
        mIcon = icon;
        mId = id;
        mContentDescriptions = contentDescriptions;
    }

    public String getTitle() {
        return mTitle;
    }

    public Drawable getDrawable() {
        return mIcon;
    }

    public int getId() {
        return mId;
    }

    public String getDescription() {
        return mContentDescriptions;
    }
}
