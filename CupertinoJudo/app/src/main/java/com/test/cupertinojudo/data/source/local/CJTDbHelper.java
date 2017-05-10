package com.test.cupertinojudo.data.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fabiohh on 5/9/17.
 */

public class CJTDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "Tournament.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String INTEGER_TYPE = " INTEGER";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME + " (" +
                    CJTPersistenceContract.CJudoParticipantEntry._ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR + INTEGER_TYPE + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_FIRST    + TEXT_TYPE + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_LAST     + TEXT_TYPE + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CLUB     + TEXT_TYPE + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_BELT     + TEXT_TYPE + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_WEIGHT   + TEXT_TYPE + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_DOB      + TEXT_TYPE + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY + TEXT_TYPE + COMMA_SEP +
                    CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL     + TEXT_TYPE +
                    " )";

    public CJTDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Not required for version 1
    }
}
