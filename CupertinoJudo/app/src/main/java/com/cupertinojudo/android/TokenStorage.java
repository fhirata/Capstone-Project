package com.cupertinojudo.android;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by fabiohh on 5/14/17.
 */

public class TokenStorage {
    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private static final String FCM_TOKEN = "fcm_token";
    private static final String TOKEN_STORE_PREFERENCE = "token_store_preference";

    public TokenStorage(Context context) {
        mSharedPreferences = context.getSharedPreferences(TOKEN_STORE_PREFERENCE, Context.MODE_PRIVATE);
        mContext = context;
    }

    public String getFcmToken() {
        return mSharedPreferences.getString(FCM_TOKEN, null);
    }

    public void setFcmToken(String fcmToken) {
        mSharedPreferences.edit().putString(FCM_TOKEN, fcmToken).apply();
    }
}
