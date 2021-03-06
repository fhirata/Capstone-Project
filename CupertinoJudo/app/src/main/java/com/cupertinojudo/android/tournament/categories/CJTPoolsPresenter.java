package com.cupertinojudo.android.tournament.categories;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.TournamentPool;
import com.cupertinojudo.android.data.source.CJTDataSource;
import com.cupertinojudo.android.data.source.CJTLoaderProvider;
import com.cupertinojudo.android.data.source.CJTRepository;

import java.util.List;

/**
 *
 */

public class CJTPoolsPresenter implements CJTPoolsContract.Presenter,
        CJTRepository.LoadDataCallback, CJTDataSource.GetPoolsCallback,
        LoaderManager.LoaderCallbacks<Cursor> {

    public final static int POOLS_LOADER = 2;

    @NonNull
    private final LoaderManager mLoaderManager;
    private CJTPoolsContract.ViewInterface mViewInterface;
    private CJTPoolsContract.ActivityInterface mActivityInterface;

    @NonNull
    private final CJTRepository mTournamentRepository;

    @NonNull
    private final CJTLoaderProvider mLoaderProvider;

    String mCategory;
    int mTournamentYear;

    public CJTPoolsPresenter(CJTPoolsContract.ActivityInterface activityInterface,
                                  CJTLoaderProvider loaderProvider,
                                  LoaderManager loaderManager,
                                  CJTPoolsContract.ViewInterface viewInterface,
                                  CJTRepository repository, String category, int tournamentYear) {
        mActivityInterface = activityInterface;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
        mViewInterface = viewInterface;
        mTournamentRepository = repository;
        mCategory = category;
        if (tournamentYear != 0) {
            mTournamentYear = tournamentYear;
        }
    }


    @Override
    public void start() {
        mViewInterface.setLoadingIndicator(true);
        mTournamentRepository.getPools(mTournamentYear, mCategory, this);
    }

    @Override
    public void handlePoolItemClick(String category, String poolName) {
        mActivityInterface.handlePoolItemClick(category, poolName);
    }

    @Override
    public String getCategory() {
        return mCategory;
    }

    @Override
    public int getTournamentYear() {
        return mTournamentYear;
    }

    /**
     * LoaderManager.LoaderCallbacks implementations
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createPoolsLoader(mTournamentYear, mCategory);
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
    public void onPoolsLoaded(List<TournamentPool> pools) {
        // We don't care about the result since the CursorLoader will load the data for us
        if (mLoaderManager.getLoader(POOLS_LOADER) == null) {
            mLoaderManager.initLoader(POOLS_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(POOLS_LOADER, null, this);
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
        mViewInterface.loadPools(data);
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
