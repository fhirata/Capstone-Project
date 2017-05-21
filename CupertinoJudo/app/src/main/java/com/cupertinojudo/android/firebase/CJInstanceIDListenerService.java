package com.cupertinojudo.android.firebase;

import android.util.Log;

import com.cupertinojudo.android.TokenStorage;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.google.android.gms.internal.zzt.TAG;

/**
 *
 */

public class CJInstanceIDListenerService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        Log.d(TAG, "FCM registration token refresh: " + FirebaseInstanceId.getInstance().getToken());
        String fcmToken = FirebaseInstanceId.getInstance().getToken();
        TokenStorage tokenStorage = new TokenStorage(getApplicationContext());
        tokenStorage.setFcmToken(fcmToken);

    }
}
