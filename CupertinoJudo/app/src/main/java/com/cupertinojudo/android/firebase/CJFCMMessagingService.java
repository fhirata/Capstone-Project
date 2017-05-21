package com.cupertinojudo.android.firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.cupertinojudo.android.MainActivity;
import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.CJudoNotification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Date;
import java.util.Map;

/**
 *
 */

public class CJFCMMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        CJudoNotification notification = parseReceivedFCMPayload(remoteMessage);

        sendNotification(notification);
    }

    private CJudoNotification parseReceivedFCMPayload(RemoteMessage remoteMessage) {
        Map<String, String> messageData = remoteMessage.getData();
        return new CJudoNotification(messageData.get("title"), messageData.get("body"), new Date());
    }

    private void sendNotification(CJudoNotification notification) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (prefs.getBoolean(getString(R.string.notification_prefs), true)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(MainActivity.NOTIFICATION_IDENTIFIER, true);
            intent.putExtra(MainActivity.NOTIFICATION_OBJECT, notification);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_stat)
                    .setContentTitle(notification.getTitle())
                    .setContentText(notification.getBody())
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }
    }
}
