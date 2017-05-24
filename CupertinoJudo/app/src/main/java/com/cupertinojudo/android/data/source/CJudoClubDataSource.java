package com.cupertinojudo.android.data.source;

import android.support.annotation.NonNull;

import com.cupertinojudo.android.data.models.News;
import com.cupertinojudo.android.data.models.Notification;
import com.cupertinojudo.android.data.models.Practice;

import java.util.List;

/**
 *
 */

public interface CJudoClubDataSource {

    interface GetNotificationsCallback {
        void onNotificationsLoaded(@NonNull List<Notification> notifications);
        void onDataNotAvailable(String errorMessage);
    }

    interface GetNewsCallback {
        void onNewsLoaded(@NonNull List<News> notifications);
        void onNewsDataNotAvailable(String errorMessage);
    }

    interface GetPracticesCallback {
        void onPracticesLoaded(@NonNull List<Practice> practiceList);
        void onPracticeDataNotAvailable(String errorMessage);
    }

    void getNotifications(@NonNull GetNotificationsCallback callback);

    void getNews(@NonNull GetNewsCallback callback);

    void getPractices(@NonNull GetPracticesCallback callback);
}
