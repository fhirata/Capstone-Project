package com.cupertinojudo.android.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

import com.cupertinojudo.android.DateFormatterUtil;
import com.cupertinojudo.android.MainActivity;
import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;

import java.util.Date;

import static com.cupertinojudo.android.sync.CJudoSyncAdapter.ACTION_UPDATE_PLAYERS_WIDGETS;

/**
 * Created by fabiohh on 5/29/17.
 */

public class WidgetUpdateService extends IntentService {

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
        intent.setAction(ACTION_UPDATE_PLAYERS_WIDGETS);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (null != intent) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_PLAYERS_WIDGETS.equals(action)) {
                handleActionUpdatePlayerWidgets();
            }
        }
    }

    private void handleActionUpdatePlayerWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CJudoAppWidget.class));

        int tournamentYear = 2016;
        Uri participantsUri = CJTPersistenceContract.CJudoParticipantEntry.buildParticipantsUri(tournamentYear);

        // we'll query our contentProvider, as always
        Cursor cursor = getContentResolver().query(participantsUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getCount();
            cursor.close();

            for (int appWidgetId : appWidgetIds) {

                RemoteViews views = new RemoteViews(
                        getBaseContext().getPackageName(), R.layout.widget);

                // intent to launch app
                Intent openWidgetIntent = new Intent(getBaseContext(), MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, openWidgetIntent, 0);
                views.setOnClickPendingIntent(R.id.widget, pendingIntent);

                // set widget dynamic information
                views.setTextViewText(R.id.widget_participants_count, String.format(getString(R.string.participants_count), tournamentYear, count));
                views.setTextViewText(R.id.widget_updated_at, DateFormatterUtil.formatTimestamp(new Date(), this));

                appWidgetManager.updateAppWidget(appWidgetId, views);
            }
        }
    }
}
