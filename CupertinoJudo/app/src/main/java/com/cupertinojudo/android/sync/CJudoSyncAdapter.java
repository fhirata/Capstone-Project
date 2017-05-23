package com.cupertinojudo.android.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.format.Time;
import android.util.Log;

import com.cupertinojudo.android.MainActivity;
import com.cupertinojudo.android.PreferenceUtil;
import com.cupertinojudo.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Vector;

import static java.lang.System.currentTimeMillis;

/**
 *
 */

public class CJudoSyncAdapter extends AbstractThreadedSyncAdapter {
    // Interval at which to sync with the weather, in milliseconds.
    // 60 seconds (1 minute)  180 = 3 hours
    private static final int SYNC_INTERVAL = 180;
    private static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    private final String LOG_TAG = CJudoSyncAdapter.class.getSimpleName();

    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24 * 7;
    private static final int WEATHER_NOTIFICATION_ID = 3004;

    public @interface LocationStatus {}

    public static final int LOCATION_STATUS_OK = 0;
    public static final int LOCATION_STATUS_SERVER_DOWN = 1;
    public static final int LOCATION_STATUS_SERVER_INVALID = 2;
    public static final int LOCATION_STATUS_UNKNOWN = 3;
    public static final int LOCATION_STATUS_SERVER_INVALID_LOCATION = 4;

    public static final String ACTION_DATA_UPDATED = "com.cupertinojudoclub.android.app.ACTION_DATA_UPDATED";

    public  CJudoSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    private void notifyWeather() {
        Context context = getContext();
        //checking the last update and notify if it' the first of the day
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String lastNotificationKey = context.getString(R.string.pref_last_notification);
        long lastSync = prefs.getLong(lastNotificationKey, 0);

        if (!PreferenceUtil.getNotificationEnabled(context)) {
            return;
        }

        if (currentTimeMillis() - lastSync >= DAY_IN_MILLIS) {
            // Last sync was more than 1 week ago, let's send a notification with the news.
            String locationQuery = Utility.getPreferredLocation(context);

            Time dayTime = new Time();
            dayTime.setToNow();

            // we start at the day returned by local time. Otherwise this is a mess.
            int julianStartDay = Time.getJulianDay(currentTimeMillis(), dayTime.gmtoff);

            long dateTime = dayTime.setJulianDay(julianStartDay);

            Uri weatherUri = WeatherContract.WeatherEntry.buildWeatherLocationWithDate(locationQuery, dateTime);

            // we'll query our contentProvider, as always
            Cursor cursor = context.getContentResolver().query(weatherUri, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                Weather weatherEntry = Weather.fromCursor(context, cursor);

                String title = context.getString(R.string.app_name);

                // Define the text of the forecast.
                String contentText = String.format(context.getString(R.string.format_notification),
                        weatherEntry.getDesc(),
                        Utility.formatTemperature(context, weatherEntry.getHigh(), weatherEntry.isMetric()),
                        Utility.formatTemperature(context, weatherEntry.getLow(), weatherEntry.isMetric()));

                //build your notification here.
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                stackBuilder.addNextIntent(new Intent(context, MainActivity.class));

                PendingIntent pendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);

                Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(weatherEntry.getIconId())
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
        String locationQuery = Utility.getPreferredLocation(getContext());

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        String format = "json";
        String units = "metric";
        int numDays = 14;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "http://api.openweathermap.org/data/2.5/forecast/daily?";
            final String QUERY_PARAM = "q";
            final String LAT_PARAM = "lat";
            final String LON_PARAM = "lon";
            final String FORMAT_PARAM = "mode";
            final String UNITS_PARAM = "units";
            final String DAYS_PARAM = "cnt";
            final String APIKEY_PARAM = "appid";

            Uri.Builder uriBuilder = Uri.parse(FORECAST_BASE_URL).buildUpon();

            if (Utility.isLocationLatLonAvailable(this.getContext())) {
                String latitudeQuery = Utility.getLocationLat(this.getContext());
                String longitudeQuery = Utility.getLocationLon(this.getContext());

                uriBuilder.appendQueryParameter(LAT_PARAM, latitudeQuery);
                uriBuilder.appendQueryParameter(LON_PARAM, longitudeQuery);
            } else {
                uriBuilder.appendQueryParameter(QUERY_PARAM, locationQuery);
            }

            uriBuilder.appendQueryParameter(FORMAT_PARAM, format)
                    .appendQueryParameter(UNITS_PARAM, units)
                    .appendQueryParameter(DAYS_PARAM, Integer.toString(numDays))
                    .appendQueryParameter(APIKEY_PARAM, "ce528ad204bac5e4899e547f4ba62323")
                    .build();

            URL url = new URL(uriBuilder.toString());

            Log.d(LOG_TAG, "fetch URL: " + url);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line)
                        .append("\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                setLocationStatus(getContext(), LOCATION_STATUS_SERVER_DOWN);
                return;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            setLocationStatus(getContext(), LOCATION_STATUS_SERVER_DOWN);
            return;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            getWeatherDataFromJson(forecastJsonStr, locationQuery);
            notifyWeather();
            deleteOldData();
            setLocationStatus(getContext(), LOCATION_STATUS_OK);

            // send broadcast to update widget
            Intent dataUpdated = new Intent(ACTION_DATA_UPDATED);
            getContext().sendBroadcast(dataUpdated);


        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
            setLocationStatus(getContext(), LOCATION_STATUS_SERVER_INVALID_LOCATION);
        }

    }

    private static void setLocationStatus(Context context, @SunshineSyncAdapter.LocationStatus int locationStatus) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(context.getString(R.string.pref_location_result), locationStatus);
        editor.commit();
    }


    private void deleteOldData() {
        // we start at the day returned by local time. Otherwise this is a mess.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        Time dayTime = new Time();
        dayTime.setToNow();

        String yesterday = Integer.toString(Time.getJulianDay(cal.getTimeInMillis(), dayTime.gmtoff));
        int row = getContext().getContentResolver().delete(WeatherContract.WeatherEntry.CONTENT_URI,
                WeatherContract.WeatherEntry.COLUMN_DATE + "<?",
                new String[]{yesterday});

        Log.i(LOG_TAG, "Deleted yesterday data (rows deleted): " + row);
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private void getWeatherDataFromJson(String forecastJsonStr,
                                        String locationSetting)
            throws JSONException {

       //Insert into db
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
        SunshineSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

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
     * Helper method to handle insertion of a new location in the weather database.
     *
     * @param locationSetting The location string used to request updates from the server.
     * @param cityName A human-readable city name, e.g "Mountain View"
     * @param lat the latitude of the city
     * @param lon the longitude of the city
     * @return the row ID of the added location.
     */
    private long addLocation(String locationSetting, String cityName, double lat, double lon) {
        // Students: First, check if the location with this city name exists in the db
        // If it exists, return the current ID
        // Otherwise, insert it using the content resolver and the base URI
        Cursor locationCursor = getContext().getContentResolver().query(WeatherContract.LocationEntry.CONTENT_URI,
                new String[]{WeatherContract.LocationEntry._ID},
                WeatherContract.LocationEntry.COLUMN_LOCATION_SETTING + "=?",
                new String[]{locationSetting},
                null,
                null);

        long locationId;

        if (locationCursor != null && locationCursor.moveToFirst()) {
            return locationCursor.getLong(0);
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CITY_NAME, cityName);
            values.put(COLUMN_COORD_LAT, lat);
            values.put(COLUMN_COORD_LONG, lon);
            values.put(COLUMN_LOCATION_SETTING, locationSetting);

            Uri returnUri = getContext().getContentResolver().insert(WeatherContract.LocationEntry.CONTENT_URI, values);
            locationId = ContentUris.parseId(returnUri);
            getContext().getContentResolver().notifyChange(WeatherContract.LocationEntry.CONTENT_URI, null);
        }

        if (locationCursor != null && !locationCursor.isClosed()) {
            locationCursor.close();
        }

        return locationId;

    }
    /**
     * Helper method to have the sync adapter sync immediately
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
        if ( null == accountManager.getPassword(newAccount) ) {

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