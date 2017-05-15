package com.cupertinojudo.android.data.source;

import android.support.annotation.NonNull;

import com.cupertinojudo.android.data.models.News;
import com.cupertinojudo.android.data.models.Notification;

import java.util.List;

/**
 * Created by fabiohh on 5/15/17.
 */

public class CJudoClubRepository implements CJudoClubDataSource {
    private static CJudoClubRepository sInstance = null;
    private CJudoClubDataSource mLocalDataSource;
    private CJudoClubDataSource mRemoteDataSource;

    public static CJudoClubRepository getInstance(@NonNull CJudoClubDataSource remoteRepository) {
        if (null == sInstance) {
            sInstance = new CJudoClubRepository(remoteRepository);
        }
        return sInstance;
    }

    private CJudoClubRepository(@NonNull CJudoClubDataSource remoteRepository) {
        mRemoteDataSource = remoteRepository;
    }

    @Override
    public void getNotifications(@NonNull final GetNotificationsCallback callback) {
        mRemoteDataSource.getNotifications(new GetNotificationsCallback() {
            @Override
            public void onNotificationsLoaded(@NonNull List<Notification> notifications) {
                callback.onNotificationsLoaded(notifications);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }

    @Override
    public void getNews(@NonNull final GetNewsCallback callback) {
        mRemoteDataSource.getNews(new GetNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<News> news) {
                callback.onNewsLoaded(news);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }
}
