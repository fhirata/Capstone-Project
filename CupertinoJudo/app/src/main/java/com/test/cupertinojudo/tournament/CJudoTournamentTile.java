package com.test.cupertinojudo.tournament;

import android.graphics.drawable.Drawable;

/**
 * Created by fabiohh on 4/30/17.
 */

public class CJudoTournamentTile {
    private String mDescription;
    private Drawable mIcon;

    public CJudoTournamentTile(String description, Drawable icon) {
        mDescription = description;
        mIcon = icon;
    }

    public String getDescription() {
        return mDescription;
    }

    public Drawable getDrawable() {
        return mIcon;
    }
}
