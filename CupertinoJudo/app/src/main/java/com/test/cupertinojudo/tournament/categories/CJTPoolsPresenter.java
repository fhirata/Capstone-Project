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

    private static final int TOURNAMENT_YEAR = 2016;
    private CJTPoolsContract.ViewInterface mViewInterface;
    private CJTPoolsContract.ActivityInterface mActivityInterface;

    @NonNull
    private final CJTRepository mTournamentRepository;

    @NonNull
    private final CJTLoaderProvider mLoaderProvider;

    String mCategory;

    public CJTPoolsPresenter(CJTPoolsContract.ActivityInterface activityInterface,
                                  CJTLoaderProvider loaderProvider,
                                  LoaderManager loaderManager,
                                  CJTPoolsContract.ViewInterface viewInterface,
                                  CJTRepository repository, String category) {
        mActivityInterface = activityInterface;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
        mViewInterface = viewInterface;
        mTournamentRepository = repository;
        mCategory = category;
    }


    @Override
    public void start() {
        loadParticipants();
    }

    private void loadParticipants() {
        mViewInterface.setLoadingIndicator(true);
        mTournamentRepository.getPools(TOURNAMENT_YEAR, mCategory, this);
    }

    @Override
    public void handlePoolItemClick(int year, String category, String poolName) {
        mActivityInterface.handlePoolItemClick(TOURNAMENT_YEAR, category, poolName);
    }

    private void loadPools(Cursor data) {
        mViewInterface.loadPools(data);
    }

    /**
     * LoaderManager.LoaderCallbacks implementations
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createTournamentPoolsLoader(TOURNAMENT_YEAR, mCategory);
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
