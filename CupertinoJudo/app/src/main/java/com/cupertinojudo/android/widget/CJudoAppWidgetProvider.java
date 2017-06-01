package com.cupertinojudo.android.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.cupertinojudo.android.DateFormatterUtil;
import com.cupertinojudo.android.MainActivity;
import com.cupertinojudo.android.R;

import java.util.Date;

import static com.cupertinojudo.android.widget.WidgetUpdateService.ACTION_UPDATE_PLAYERS;

/**
 * Implementation of App Widget functionality.
 */
public class CJudoAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int participantsCount,
                                long lastSync,
                                int tournamentYear,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        views.setTextViewText(R.id.widget_participants_count, String.format(context.getString(R.string.participants_count), tournamentYear, participantsCount));
        Date dateLastSync;
        if (lastSync != 0) {
            dateLastSync = new Date(lastSync);
            views.setTextViewText(R.id.widget_updated_at, DateFormatterUtil.formatTimestamp(dateLastSync, context));
        } else {
            views.setTextViewText(R.id.widget_updated_at, "");
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
        views.setOnClickPendingIntent(R.id.widget_image, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (ACTION_UPDATE_PLAYERS.equals(intent.getAction())) {
            context.startService(new Intent(context, WidgetUpdateService.class));
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        WidgetUpdateService.startActionUpdatePlayersWidgets(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

