package com.cupertinojudo.android.club;

import android.support.annotation.NonNull;

import com.cupertinojudo.android.data.models.News;
import com.cupertinojudo.android.data.models.Practice;
import com.cupertinojudo.android.data.source.CJudoClubDataSource;
import com.cupertinojudo.android.data.source.CJudoClubRepository;

import java.util.List;

/**
 *
 */

public class CJClubPresenter implements CJClubContract.PresenterInterface, CJudoClubDataSource.GetNewsCallback, CJudoClubDataSource.GetPracticesCallback {

    private CJClubContract.ViewInterface mViewInterface;
    private CJClubContract.ActivityInterface mActivityInterface;

    @NonNull
    private final CJudoClubRepository mClubRepository;


    public CJClubPresenter(CJClubContract.ActivityInterface activityInterface,
                           CJClubContract.ViewInterface viewInterface, CJudoClubRepository repository) {
        mActivityInterface = activityInterface;
        mViewInterface = viewInterface;
        mClubRepository = repository;

        mViewInterface.setPresenter(this);
    }


    @Override
    public void loadNews() {
        mViewInterface.setLoadingIndicator(true);
        mClubRepository.getNews(new CJudoClubDataSource.GetNewsCallback() {
            @Override
            public void onNewsLoaded(@NonNull List<News> newsList) {
                mViewInterface.onNewsLoaded(newsList);
            }

            @Override
            public void onNewsDataNotAvailable(String errorMessage) {
                // Error message
                mActivityInterface.showError(errorMessage);
            }
        });
    }

    @Override
    public void loadPractices() {
        mViewInterface.setLoadingIndicator(true);
        mClubRepository.getPractices(new CJudoClubDataSource.GetPracticesCallback() {
            @Override
            public void onPracticesLoaded(@NonNull List<Practice> practiceList) {
                mViewInterface.onPracticeLoaded(practiceList);
            }

            @Override
            public void onPracticeDataNotAvailable(String errorMessage) {
                // Error message
                mActivityInterface.showError(errorMessage);
            }
        });
    }

    /**
     *
     * @param notifications
     */
    @Override
    public void onNewsLoaded(@NonNull List<News> notifications) {

    }

    @Override
    public void onNewsDataNotAvailable(String errorMessage) {

    }

    /**
     *
     * @param practiceList
     */
    @Override
    public void onPracticesLoaded(@NonNull List<Practice> practiceList) {

    }

    @Override
    public void onPracticeDataNotAvailable(String errorMessage) {

    }

    @Override
    public void start() {
        loadNews();
    }
}
