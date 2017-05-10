package com.test.cupertinojudo;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
     *  Test that the content provider is registered correctly.
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

}
