package com.test.cupertinojudo.data.models;

import android.database.Cursor;

import com.test.cupertinojudo.data.source.local.CJTPersistenceContract;

import java.util.List;

/**
 * Created by fabiohh on 5/9/17.
 */

public class TournamentPool {
    String mPoolName;
    int mCategoryId;
    int mMinWeight;
    int mMaxWeight;
    List<Participant> mParticipants;

    public TournamentPool(String poolName, int minWeight, int maxWeight) {
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

    public int getmMaxWeight() {
        return mMaxWeight;
    }

    public int getCategoryId() {
        return mCategoryId;
    }

    public static TournamentPool from(Cursor cursor) {
        // Assign cursor fields
        String category = cursor.getString(cursor.getColumnIndex(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY));
        return new TournamentPool("Junior Males", 75, 85);
    }

    public List<Participant> getParticipants() {
        return mParticipants;
    }
}
