package com.cupertinojudo.android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.test.AndroidTestCase;

import com.cupertinojudo.android.data.source.local.CJTDbHelper;
import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;

import java.util.HashSet;

/**
 * Created by fabiohh on 5/10/17.
 */

public class TestCJTDb extends AndroidTestCase {
    Context mContext;

    void deleteDb() {
        mContext.deleteDatabase(CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME);
    }

    public void setUp() {
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        deleteDb();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME);

        mContext.deleteDatabase(CJTDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new CJTDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        assertTrue("Error: This means that the database has not been created correctly", c.moveToFirst());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> participantsColumnHashSet = new HashSet<>();
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry._ID);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_FIRST);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_LAST);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CLUB);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_BELT);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_WEIGHT);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_DOB);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY);
        participantsColumnHashSet.add(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            participantsColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                participantsColumnHashSet.isEmpty());
        db.close();
    }
}
