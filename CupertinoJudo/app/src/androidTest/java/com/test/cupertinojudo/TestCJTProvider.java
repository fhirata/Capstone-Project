package com.test.cupertinojudo;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.test.cupertinojudo.data.source.local.CJTDbHelper;
import com.test.cupertinojudo.data.source.local.CJTPersistenceContract;
import com.test.cupertinojudo.data.source.local.CJTProvider;

/**
 * Created by fabiohh on 5/10/17.
 */

public class TestCJTProvider extends AndroidTestCase {

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI,
                null,
                null
        );

        Cursor cursor = mContext.getContentResolver().query(
                CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from Tournament table during delete: ", 0, cursor.getCount());
    }

    private void deleteAllRecordsFromDB() {
        CJTDbHelper dbHelper = new CJTDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME, null, null);
    }

    public void deleteAllRecords() {
        deleteAllRecordsFromDB();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    /**
     * Test that the content provider is registered correctly.
     */
    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                CJTProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: CJTProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + CJTPersistenceContract.CONTENT_AUTHORITY,
                    providerInfo.authority, CJTPersistenceContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: CJTProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    public void testBasicParticipantsQuery() {
        CJTDbHelper dbHelper = new CJTDbHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long locationRowId = TestUtility.insertParticipantValues(mContext);
        assertTrue("Unable to Insert ParticipantValues into the database", locationRowId != -1);

        ContentValues participantValues = TestUtility.createParticipantValues();

        long participantRowId = db.insert(CJTPersistenceContract.CJudoParticipantEntry.TABLE_NAME, null, participantValues);
        assertTrue("Unable to Insert ParticipantEntry into the database", participantRowId != -1);

        Cursor participantCursor = mContext.getContentResolver().query(
                CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        TestUtility.validateCursor("Error reading cursor", participantCursor, participantValues);
    }

    static private final int BULK_INSERT_RECORDS_TO_INSERT = 10;

    static ContentValues[] createBulkInsertParticipantValues(long locationRowId) {
        long currentTestDate = TestUtility.TEST_DATE;
        long millisecondsInADay = 1000 * 60 * 60 * 24;
        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++, currentTestDate += millisecondsInADay) {
            ContentValues participantValues = new ContentValues();
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_TOURNAMENT_YEAR, locationRowId);
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_FIRST, "Jack_" + i);
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_LAST, "London_" + i);
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CLUB, "Stanford_" + i);
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_BELT, "White_" + i);
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_WEIGHT, 75 + i);
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_DOB, currentTestDate);
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_CATEGORY, "Senior " + i);
            participantValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_POOL, String.valueOf((char) (64 + i)));
            returnContentValues[i] = participantValues;
        }
        return returnContentValues;
    }

    // Test that we can delete after adding/updating commands
    public void testInsertReadProvider() {
        ContentValues testValues = TestUtility.createParticipantValues();

        TestUtility.TestContentObserver tco = TestUtility.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI, true, tco);
        Uri participantUri = mContext.getContentResolver().insert(CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI, testValues);

        // Did our content observer get called?  Note: If this fails, your insert method
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        long participantRowId = ContentUris.parseId(participantUri);

        // Verify we got a row back
        assertTrue(participantRowId != -1);

        // In theory, data is inserted.  Read back to verify
        Cursor cursor = mContext.getContentResolver().query(
                CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        TestUtility.validateCursor("testInsertReadProvider: error validating participant entry",
                cursor, testValues);
    }

    public void testDeleteRecords() {
        testInsertReadProvider();

        // Register content observer for our participant delete.
        TestUtility.TestContentObserver participantObserver = TestUtility.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI, true, participantObserver);

        deleteAllRecordsFromProvider();

        // If this fails, we are probably not calling
        // getContext().getContentResolver().notifyChange(uri, null); in the ContentProvider
        // delete.  (only if the insertReadProvider is succeeding)
        participantObserver.waitForNotificationOrFail();

        mContext.getContentResolver().unregisterContentObserver(participantObserver);
    }

    public void testUpdateParticipants() {
        ContentValues values = TestUtility.createParticipantValues();

        Uri participantUri = mContext.getContentResolver().insert(
                CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI, values);

        long participantRowId = ContentUris.parseId(participantUri);

        // Verify row is valid
        assertTrue(participantRowId != -1);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(CJTPersistenceContract.CJudoParticipantEntry._ID, participantRowId);
        updatedValues.put(CJTPersistenceContract.CJudoParticipantEntry.COLUMN_NAME_BELT, "Black");

        // Create a cursor with observer to make sure that the content provider is notifying
        // the observers as expected
        Cursor participantCursor = mContext.getContentResolver().query(CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI, null, null, null, null);

        TestUtility.TestContentObserver tco = TestUtility.getTestContentObserver();
        participantCursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(
                CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI, updatedValues, CJTPersistenceContract.CJudoParticipantEntry._ID + " = ?",
                new String[]{ Long.toString(participantRowId) }
        );

        assertEquals(count, 1);

        // Test to make sure our observer is called.  If not, we throw an assertion.
        //
        // If code is failing here, it means that your content provider
        // isn't calling getContext().getContentResolver().notifyChange(uri, null);
        tco.waitForNotificationOrFail();

        Cursor cursor = mContext.getContentResolver().query(
                CJTPersistenceContract.CJudoParticipantEntry.CONTENT_URI,
                null,
                CJTPersistenceContract.CJudoParticipantEntry._ID + " = " + participantRowId,
                null,
                null
        );

        TestUtility.validateCursor("update Test: error validating update function", cursor, updatedValues);
    }
}
