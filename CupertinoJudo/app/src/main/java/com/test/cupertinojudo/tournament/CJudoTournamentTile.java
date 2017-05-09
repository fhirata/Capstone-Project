package com.test.cupertinojudo.tournament;

import android.graphics.drawable.Drawable;

/**
 * Created by fabiohh on 4/30/17.
 */

public class CJudoTournamentTile {
    private String mDescription;
    private Drawable mIcon;
    private int mId;

    public CJudoTournamentTile(String description, Drawable icon, int id) {
        mDescription = description;
        mIcon = icon;
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public Drawable getDrawable() {
        return mIcon;
    }

    public int getId() {
        return mId;
    }
}
