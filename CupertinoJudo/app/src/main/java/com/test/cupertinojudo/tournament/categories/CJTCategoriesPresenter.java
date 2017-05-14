package com.test.cupertinojudo.tournament.categories;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.test.cupertinojudo.R;
import com.test.cupertinojudo.data.models.Participant;
import com.test.cupertinojudo.data.source.CJTDataSource;
import com.test.cupertinojudo.data.source.CJTLoaderProvider;
import com.test.cupertinojudo.data.source.CJTRepository;

import java.util.List;

/**
 *
 */

public class CJTCategoriesPresenter implements CJTCategoriesContract.Presenter,
        CJTRepository.LoadDataCallback, CJTDataSource.GetParticipantsCallback,
        LoaderManager.LoaderCallbacks<Cursor> {

    public final static int CATEGORIES_LOADER = 1;

    @NonNull
    private final LoaderManager mLoaderManager;

    private static final int TOURNAMENT_YEAR = 2016;
    private CJTCategoriesContract.ViewInterface mViewInterface;
    private CJTCategoriesContract.ActivityInterface mActivityInterface;

    @NonNull
    private final CJTRepository mTournamentRepository;

    @NonNull
    private final CJTLoaderProvider mLoaderProvider;

    public CJTCategoriesPresenter(CJTCategoriesContract.ActivityInterface activityInterface,
                                  CJTLoaderProvider loaderProvider,
                                  LoaderManager loaderManager,
                                  CJTCategoriesContract.ViewInterface viewInterface,
                                  CJTRepository repository) {
        mActivityInterface = activityInterface;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
        mViewInterface = viewInterface;
        mTournamentRepository = repository;
    }


    @Override
    public void start() {
        loadParticipants();
    }

    private void loadParticipants() {
        mViewInterface.setLoadingIndicator(true);
        mTournamentRepository.getParticipants(TOURNAMENT_YEAR, this);
    }

    @Override
    public void handlePoolsItemClick(String category) {
        mActivityInterface.handlePoolsItemClick(category);
    }

    private void loadCategories(Cursor data) {
        mViewInterface.loadCategories(data);
    }

    /**
     * LoaderManager.LoaderCallbacks implementations
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createTournamentCategoriesLoader(TOURNAMENT_YEAR);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (null != data) {
            if (data.moveToLast()) {
                onDataLoaded(data);
            } else {
                onDataEmpty();
            }
        } else {
            onDataNotAvailable();;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        onDataReset();
    }

    /**
     * CJTDataSource.GetParticipantsCallback implementations
     */

    @Override
    public void onParticipantsLoaded(@NonNull List<Participant> participants) {
        // We don't care about the result since the CursorLoader will load the data for us
        if (mLoaderManager.getLoader(CATEGORIES_LOADER) == null) {
            mLoaderManager.initLoader(CATEGORIES_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(CATEGORIES_LOADER, null, this);
        }
    }

    @Override
    public void onDataNotAvailable(String errorMessage) {
        mViewInterface.setLoadingIndicator(false);
        mActivityInterface.showError(R.string.failed_to_load_data);
    }

    /**
     * CJTRepository.LoadDataCallback implementations
     */

    @Override
    public void onDataLoaded(Cursor data) {
        mViewInterface.setLoadingIndicator(false);
        mViewInterface.loadCategories(data);
    }

    @Override
    public void onDataEmpty() {

    }

    @Override
    public void onDataNotAvailable() {

    }

    @Override
    public void onDataReset() {

    }
}
