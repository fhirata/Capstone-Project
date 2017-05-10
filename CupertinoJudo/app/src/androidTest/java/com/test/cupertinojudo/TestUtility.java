package com.test.cupertinojudo;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;

import com.test.cupertinojudo.data.source.local.CJTDbHelper;
import com.test.cupertinojudo.data.source.local.CJTPersistenceContract;
import com.test.cupertinojudo.utils.PollingCheck;

import java.util.Map;
import java.util.Set;

import static com.test.cupertinojudo.data.source.local.CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME;

/**
 * Created by fabiohh on 5/10/17.
 */

public class TestUtility extends AndroidTestCase {
    static final String TEST_LOCATION = "99705";
    static final long TEST_DATE = 1419033600L;  // December 20th, 2014

    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            String actualValue = valueCursor.getString(idx);

            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, actualValue);
        }
    }

    static ContentValues createParticipantValues() {
        ContentValues participantValues = new ContentValues();
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR, 2017);
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_FIRST, "Joe");
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_LAST, "Smith");
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CLUB, "Stanford");
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_BELT, "Yellow");
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_WEIGHT, 67);
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_DOB, TestUtility.TEST_DATE);
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY, "Junior Males");
        participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL, "A");

        return participantValues;
    }

    static long insertParticipantValues(Context context) {
        // insert our test records into the database
        CJTDbHelper dbHelper = new CJTDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues testValues = TestUtility.createParticipantValues();

        long locationRowId;
        locationRowId = db.insert(TABLE_NAME, null, testValues);

        // Verify we got a row back.
        assertTrue("Error: Failure to insert Participant Values", locationRowId != -1);

        return locationRowId;
    }

    /*
        Students: The functions we provide inside of TestProvider use this utility class to test
        the ContentObserver callbacks using the PollingCheck class that we grabbed from the Android
        CTS tests.

        Note that this only tests that the onChange function is called; it does not test that the
        correct Uri is returned.
     */
    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
