package com.cupertinojudo.android.notifications;

import android.support.annotation.NonNull;

import com.cupertinojudo.android.data.models.Notification;
import com.cupertinojudo.android.data.source.CJudoClubDataSource;
import com.cupertinojudo.android.data.source.CJudoClubRepository;

import java.util.List;

/**
 * Created by fabiohh on 5/15/17.
 */

public class CJudoNotificationPresenter implements CJudoNotificationContract.Presenter {

    private CJudoNotificationContract.ViewInterface mViewInterface;
    private CJudoNotificationContract.ActivityInterface mActivityInterface;

    @NonNull
    private final CJudoClubRepository mClubRepository;

    public CJudoNotificationPresenter(CJudoNotificationContract.ActivityInterface activityInterface,
                            CJudoNotificationContract.ViewInterface viewInterface,
                            CJudoClubRepository repository) {
        mActivityInterface = activityInterface;
        mViewInterface = viewInterface;
        mClubRepository = repository;
    }


    @Override
    public void start() {
        loadNotifications();
    }

    private void loadNotifications() {
        // Load from server, do dedupping with local database later
        mViewInterface.setLoadingIndicator(true);
        mClubRepository.getNotifications(new CJudoClubDataSource.GetNotificationsCallback() {
            @Override
            public void onNotificationsLoaded(@NonNull List<Notification> notifications) {
                mViewInterface.updateNotifications(notifications);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                // Error message
            }
        });
    }
}
