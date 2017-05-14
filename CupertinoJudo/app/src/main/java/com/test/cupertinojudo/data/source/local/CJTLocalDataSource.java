package com.test.cupertinojudo.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.test.cupertinojudo.data.models.Participant;
import com.test.cupertinojudo.data.source.CJTDataSource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by fabiohh on 5/9/17.
 */

public class CJTLocalDataSource implements CJTDataSource {
    private static CJTLocalDataSource sInstance;
    private ContentResolver mContentResolver;
    private Date today = new Date();
    private static SimpleDateFormat tournamentYearDateFormat = new SimpleDateFormat("yyyy");
    private static SimpleDateFormat dobDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//    int year = CJTPersistenceContract.CJudoParticipantEntry.getYearFromUri(uri);

    public static CJTLocalDataSource getInstance(@NonNull ContentResolver contentResolver) {
        if (null == sInstance) {
            sInstance = new CJTLocalDataSource(contentResolver);
        }
        return sInstance;
    }

    private CJTLocalDataSource(@NonNull ContentResolver contentResolver) {
        checkNotNull(contentResolver);
        mContentResolver = contentResolver;
    }

    @Override
    public void getCategories(@NonNull int year, @NonNull GetCategoryCallback callback) {
        // no-op as data is loaded via cursor loader
   }

    @Override
    public void getParticipants(@NonNull int year, @NonNull GetParticipantsCallback callback) {
        // no-op as data is loaded via cursor loader
    }

    @Override
    public void getParticipant(@NonNull int participantId, @NonNull GetParticipantCallback callback) {
        // no-op as data is loaded via cursor loader
    }

    @Override
    public void getPools(@NonNull int year, @NonNull String category, @NonNull GetPoolsCallback callback) {
        // no-op as data is loaded via cursor loader
    }

    @Override
    public void getPool(@NonNull int year, @NonNull String category, @NonNull String poolName, @NonNull GetPoolCallback callback) {
        // no-op as data is loaded via cursor loader
    }

    @Override
    public int updateParticipants(@NonNull int year, List<Participant> participants) {
        if (hasParticipants(year)) {
            deleteParticipants(year);
        }
        ContentValues[] values = new ContentValues[participants.size()];
        for (int i = 0; i < participants.size(); i++) {
            values[i] = Participant.from(participants.get(i));
        }

        int insertCount = mContentResolver.bulkInsert(CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI, values);

        if (insertCount == 0) {
            // Log error inserting
        }
        return insertCount;
    }

    private boolean hasParticipants(@NonNull int year) {
        Cursor cursor = mContentResolver.query(CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI,
                null,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ?",
                new String[]{String.valueOf(year)},
                null);

        return (cursor.getCount() > 0);
    }

    private int deleteParticipants(@NonNull int year) {
        int deleteCount = mContentResolver.delete(CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI,
                CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + " = ?",
                new String[]{String.valueOf(year)});

        return deleteCount;
    }

    @Override
    public int insertParticipants(@NonNull int year, List<Participant> participants) {
        ContentValues[] values = new ContentValues[participants.size()];
        for (int i = 0; i < participants.size(); i++) {
            values[i] = Participant.from(participants.get(i));
        }

        int insertCount = mContentResolver.bulkInsert(CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI, values);

        if (insertCount == 0) {
            // Log error inserting
        }
        return insertCount;
    }
}
