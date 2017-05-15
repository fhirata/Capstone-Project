package com.cupertinojudo.android.data.models;

import android.database.Cursor;

import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;

import java.util.List;

/**
 *
 */

public class TournamentPool {
    private String mPoolName;
    private String mCategoryName;
    private int mMinWeight;
    private int mMaxWeight;
    private List<Participant> mParticipants;

    public TournamentPool(String category, String poolName, int minWeight, int maxWeight) {
        mCategoryName = category;
        mPoolName = poolName;
        mMinWeight = minWeight;
        mMaxWeight = maxWeight;
    }

    public String getPoolName() {
        return mPoolName;
    }

    public int getMinWeight() {
        return mMinWeight;
    }

    public int getMaxWeight() {
        return mMaxWeight;
    }

    public String getCategory() {
        return mCategoryName;
    }

    public static TournamentPool from(Cursor cursor) {
        // Assign cursor fields
        String category = cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY));
        String pool = cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL));
        return new TournamentPool(category, pool,  75, 85);
    }

    public List<Participant> getParticipants() {
        return mParticipants;
    }
}
