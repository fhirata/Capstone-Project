package com.cupertinojudo.android.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.cupertinojudo.android.MainActivity;
import com.cupertinojudo.android.PreferenceUtil;
import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.Participant;
import com.cupertinojudo.android.data.source.CJTDataSource;
import com.cupertinojudo.android.data.source.CJTRepository;
import com.cupertinojudo.android.data.source.local.CJTPersistenceContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.cupertinojudo.android.widget.WidgetUpdateService.ACTION_UPDATE_PLAYER_WIDGETS;
import static java.lang.System.currentTimeMillis;

/**
 *
 */

public class CJudoSyncAdapter extends AbstractThreadedSyncAdapter {
    // Interval at which to sync with the weather, in milliseconds.
    // 60 seconds (1 minute)  180 = 3 hours
    private static final int SYNC_INTERVAL = 180;
    private static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static SimpleDateFormat sYearFormat = new SimpleDateFormat("yyyy");

    private final String LOG_TAG = CJudoSyncAdapter.class.getSimpleName();

    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24 * 7;
    private static final int JUDO_NOTIFICATION_ID = 3004;

    private final CJTRepository mTournamentRepository;
    int mTournamentYear = 2016;
    public CJudoSyncAdapter(Context context, CJTRepository repository, boolean autoInitialize) {
        super(context, autoInitialize);
        mTournamentRepository = repository;
    }

    private void notifyParticipantCount() {
        Context context = getContext();

        // Checking the last update and notify if it's changed
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String lastNotificationKey = context.getString(R.string.pref_last_notification);

        long lastSync = prefs.getLong(lastNotificationKey, 0);

        if (!PreferenceUtil.getNotificationEnabled(context)) {
            return;
        }

        if (currentTimeMillis() - lastSync >= DAY_IN_MILLIS) {
            // Last sync was more than 1 week ago, let's send a notification with the news.
            Date year = new Date();
            Cursor cursor;

            try {
                Date currentYear = sYearFormat.parse(year.toString());
                Uri participantsUri = CJTPersistenceContract.CJudoParticipantEntry.buildParticipantsUri(Integer.valueOf(currentYear.toString()));
                cursor = context.getContentResolver().query(participantsUri, null, null, null, null);
            } catch (ParseException e) {
                cursor = null;
            }

            int count = PreferenceUtil.getParticipantsCount(getContext());

            if (cursor != null && cursor.moveToFirst() && count != cursor.getCount()) {

                String title = context.getString(R.string.app_name);

                // Define text
                String contentText = String.format(context.getString(R.string.format_notification), cursor.getCount());

                // Build your notification here.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addNextIntent(new Intent(context, MainActivity.class));

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_stat)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PRIVATE);

                notificationBuilder.setContentIntent(pendingIntent);
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notificationBuilder.build());

                //refreshing last sync
                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong(lastNotificationKey, currentTimeMillis());
                editor.apply();
            }
        }
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d(LOG_TAG, "onPerformSync Called.");
        // Fetch participants from remote and store into database
        mTournamentRepository.getParticipants(mTournamentYear, new CJTDataSource.GetParticipantsCallback() {
            @Override
            public void onParticipantsLoaded(@NonNull List<Participant> participants) {
                notifyParticipantCount();

                // Send broadcast to update widget
                Intent dataUpdated = new Intent(ACTION_UPDATE_PLAYER_WIDGETS);
                getContext().sendBroadcast(dataUpdated);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {

            }
        });
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    private static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }


    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        CJudoSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }


    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    private static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }
}