package com.cupertinojudo.android.data.models;

import android.database.Cursor;

import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;

/**
 * Created by fabiohh on 5/9/17.
 */

public class TournamentCategory {
    private String mName;

    public TournamentCategory(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public static TournamentCategory from(Cursor cursor) {
        String category = cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY));
        return new TournamentCategory(category);
    }
}
