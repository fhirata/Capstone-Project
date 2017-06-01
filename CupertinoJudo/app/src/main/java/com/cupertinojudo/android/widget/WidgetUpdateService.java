package com.cupertinojudo.android.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;
import com.cupertinojudo.android.sync.CJudoSyncAdapter;

/**
 * Created by fabiohh on 5/29/17.
 */

public class WidgetUpdateService extends IntentService {

    public static final String ACTION_UPDATE_PLAYERS = "com.cupertinojudoclub.android.ACTION_UPDATE_PLAYERS";
    public static final String ACTION_UPDATE_PLAYER_WIDGETS = "com.cupertinojudoclub.android.ACTION_UPDATE_PLAYER_WIDGET";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    /**
     * Starts this service to perform UpdatePlayersWidgets action with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdatePlayersWidgets(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_PLAYER_WIDGETS);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (null != intent) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_PLAYERS.equals(action)) {
                handleActionUpdatePlayers();
            } else if (ACTION_UPDATE_PLAYER_WIDGETS.equals(action)) {
                handleActionUpdatePlayerWidgets();
            }
        }
    }

    private void handleActionUpdatePlayers() {
        CJudoSyncAdapter.syncImmediately(this);
    }

    private void handleActionUpdatePlayerWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CJudoAppWidgetProvider.class));

        int tournamentYear = 2016;
        Uri participantsUri = CJTPersistenceContract.CJudoParticipantEntry.buildParticipantsUri(tournamentYear);

        // Query our contentProvider for participants count
        Cursor cursor = getContentResolver().query(participantsUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            int count = cursor.getCount();
            cursor.close();

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String lastNotificationKey = getString(R.string.pref_last_notification);
            long lastSync = prefs.getLong(lastNotificationKey, 0);

            for(int appWidgetId : appWidgetIds) {
                CJudoAppWidgetProvider.updateAppWidget(this, appWidgetManager, count, lastSync, tournamentYear, appWidgetId);
            }
        }
    }
}
