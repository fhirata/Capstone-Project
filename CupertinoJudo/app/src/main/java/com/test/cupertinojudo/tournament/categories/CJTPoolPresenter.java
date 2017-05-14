package com.test.cupertinojudo.tournament.categories;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.test.cupertinojudo.R;
import com.test.cupertinojudo.data.models.TournamentPool;
import com.test.cupertinojudo.data.source.CJTDataSource;
import com.test.cupertinojudo.data.source.CJTLoaderProvider;
import com.test.cupertinojudo.data.source.CJTRepository;

import static com.test.cupertinojudo.tournament.categories.CJTPoolsPresenter.POOLS_LOADER;

/**
 *
 */

public class CJTPoolPresenter implements CJTPoolContract.Presenter,
        CJTRepository.LoadDataCallback, CJTDataSource.GetPoolCallback,
        LoaderManager.LoaderCallbacks<Cursor> {

    public final static int POOL_LOADER = 3;

    @NonNull
    private final LoaderManager mLoaderManager;

    private CJTPoolContract.ViewInterface mViewInterface;
    private CJTPoolContract.ActivityInterface mActivityInterface;

    @NonNull
    private final CJTRepository mTournamentRepository;

    @NonNull
    private final CJTLoaderProvider mLoaderProvider;

    private String mCategory;
    private String mPoolName;
    private int mTournamentYear;

    public CJTPoolPresenter(CJTPoolContract.ActivityInterface activityInterface,
                             CJTLoaderProvider loaderProvider,
                             LoaderManager loaderManager,
                             CJTPoolContract.ViewInterface viewInterface,
                             CJTRepository repository, String category, String poolName, int tournamentYear) {
        mActivityInterface = activityInterface;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
        mViewInterface = viewInterface;
        mTournamentRepository = repository;
        mCategory = category;
        mPoolName = poolName;
        mTournamentYear = tournamentYear;
    }


    @Override
    public void start() {
        mViewInterface.setLoadingIndicator(true);
        mTournamentRepository.getPool(mTournamentYear, mCategory, mPoolName, this);
    }

    /**
     * LoaderManager.LoaderCallbacks implementations
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createPoolLoader(mTournamentYear, mCategory, mPoolName);
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
    public void onPoolLoaded(TournamentPool pool) {
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
        mViewInterface.loadPool(data);
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

    @Override
    public void handleParticipantItemClick(int participantId) {
        mActivityInterface.handleParticipantItemClick(participantId);
    }

    @Override
    public String getPoolName() {
        return mPoolName;
    }
}
