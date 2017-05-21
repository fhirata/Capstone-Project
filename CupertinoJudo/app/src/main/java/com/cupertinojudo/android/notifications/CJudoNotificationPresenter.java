package com.cupertinojudo.android.notifications;

import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.Notification;
import com.cupertinojudo.android.data.source.CJTLoaderProvider;
import com.cupertinojudo.android.data.source.CJudoClubDataSource;
import com.cupertinojudo.android.data.source.CJudoClubRepository;

import java.util.List;

/**
 *
 */

public class CJudoNotificationPresenter implements CJudoNotificationContract.Presenter,
        CJudoClubDataSource.GetNotificationsCallback {

    private CJudoNotificationContract.ViewInterface mViewInterface;
    private CJudoNotificationContract.ActivityInterface mActivityInterface;

    @NonNull
    private final LoaderManager mLoaderManager;

    @NonNull
    private final CJudoClubRepository mClubRepository;

    @NonNull
    private final CJTLoaderProvider mLoaderProvider;

    public CJudoNotificationPresenter(CJudoNotificationContract.ActivityInterface activityInterface,
                                      CJTLoaderProvider loaderProvider,
                                      LoaderManager loaderManager,
                                      CJudoNotificationContract.ViewInterface viewInterface,
                                      CJudoClubRepository repository) {
        mActivityInterface = activityInterface;
        mViewInterface = viewInterface;
        mClubRepository = repository;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;

        mViewInterface.setPresenter(this);
    }


    @Override
    public void start() {
        loadNotifications();
    }

    private void loadNotifications() {
        // Load from server, dedup with local database later
        mViewInterface.setLoadingIndicator(true);
        mClubRepository.getNotifications(new CJudoClubDataSource.GetNotificationsCallback() {
            @Override
            public void onNotificationsLoaded(@NonNull List<Notification> notifications) {
                mViewInterface.updateNotifications(notifications);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                // Error message
                mActivityInterface.showError(errorMessage);
            }
        });
    }

    @Override
        public void onNotificationsLoaded(@NonNull List<Notification> notifications) {
        mViewInterface.setLoadingIndicator(false);
        mViewInterface.updateNotifications(notifications);
    }

    @Override
    public void onDataNotAvailable(String errorMessage) {
        mViewInterface.setLoadingIndicator(false);
        mActivityInterface.showError(R.string.failed_to_load_data);
    }

}
