package com.cupertinojudo.android.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cupertinojudo.android.BuildConfig;
import com.cupertinojudo.android.data.models.Club;
import com.cupertinojudo.android.data.models.News;
import com.cupertinojudo.android.data.models.Notification;
import com.cupertinojudo.android.data.source.CJudoClubDataSource;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */

public class CJudoClubRemoteDataSource implements CJudoClubDataSource {
    private static final String BASE_URL = BuildConfig.NEWS_NOTIFICATIONS_BASE_URL;
    private static final String TOURNAMENT_URI = BuildConfig.NEWS_NOTIFICATIONS_URI;
    private static CJudoClubRemoteDataSource sRemoteDataSource;

    private CJTServerInterface mCJudoInterface;

    public static CJudoClubDataSource getInstance() {
        return new CJudoClubRemoteDataSource();
    }

    private CJudoClubRemoteDataSource() {
        HttpLoggingInterceptor bodyInterceptor = new HttpLoggingInterceptor();
        bodyInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(bodyInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mCJudoInterface = retrofit.create(CJTServerInterface.class);
    }

    @Override
    public void getNotifications(@NonNull final GetNotificationsCallback callback) {
        Call<Club> call = mCJudoInterface.getClubData();
        call.enqueue(new Callback<Club>() {
            @Override
            public void onResponse(Call<Club> call, Response<Club> response) {
                if (response.isSuccessful()) {
                    List<Notification> notificationList = response.body().getNotifications();
                    callback.onNotificationsLoaded(notificationList);
                } else {
                    // Log error
                }
            }

            @Override
            public void onFailure(Call<Club> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getNews(@NonNull final GetNewsCallback callback) {
        Call<Club> call = mCJudoInterface.getClubData();
        call.enqueue(new Callback<Club>() {
            @Override
            public void onResponse(Call<Club> call, Response<Club> response) {
                if (response.isSuccessful()) {
                    List<News> newsList = response.body().getNews();

                    callback.onNewsLoaded(newsList);
                } else {
                    // Log error
                }
            }

            @Override
            public void onFailure(Call<Club> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
            }
        });
    }
}
