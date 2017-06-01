package com.cupertinojudo.android.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.cupertinojudo.android.Injection;

/**
 *
 */

public class CJudoSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static CJudoSyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new CJudoSyncAdapter(getApplicationContext(), Injection.provideTournamentRepository(getApplicationContext()), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}