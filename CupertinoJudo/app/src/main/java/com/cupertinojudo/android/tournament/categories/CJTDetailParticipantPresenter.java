package com.cupertinojudo.android.tournament.categories;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.Participant;
import com.cupertinojudo.android.data.source.CJTDataSource;
import com.cupertinojudo.android.data.source.CJTLoaderProvider;
import com.cupertinojudo.android.data.source.CJTRepository;

/**
 * Created by fabiohh on 5/13/17.
 */

public class CJTDetailParticipantPresenter implements CJTDetailParticipantContract.Presenter,
        CJTRepository.LoadDataCallback, CJTDataSource.GetParticipantCallback,
        LoaderManager.LoaderCallbacks<Cursor> {

    public final static int PARTICIPANT_LOADER = 4;

    @NonNull
    private final LoaderManager mLoaderManager;

    private CJTDetailParticipantContract.ViewInterface mViewInterface;
    private CJTDetailParticipantContract.ActivityInterface mActivityInterface;

    @NonNull
    private final CJTRepository mTournamentRepository;

    @NonNull
    private final CJTLoaderProvider mLoaderProvider;

    private int mParticipantId;

    public CJTDetailParticipantPresenter(CJTDetailParticipantContract.ActivityInterface activityInterface,
                                         CJTLoaderProvider loaderProvider,
                                         LoaderManager loaderManager,
                                         CJTDetailParticipantContract.ViewInterface viewInterface,
                                         CJTRepository repository, int participantId) {
        mActivityInterface = activityInterface;
        mLoaderProvider = loaderProvider;
        mLoaderManager = loaderManager;
        mViewInterface = viewInterface;
        mTournamentRepository = repository;
        mParticipantId = participantId;
    }


    @Override
    public void start() {
        mViewInterface.setLoadingIndicator(true);
        mTournamentRepository.getParticipant(mParticipantId, this);
    }

    /**
     * LoaderManager.LoaderCallbacks implementations
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return mLoaderProvider.createParticipantLoader(mParticipantId);
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
            onDataNotAvailable();
            ;
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
    public void onParticipantLoaded(Participant participant) {
        // We don't care about the result since the CursorLoader will load the data for us
        if (mLoaderManager.getLoader(PARTICIPANT_LOADER) == null) {
            mLoaderManager.initLoader(PARTICIPANT_LOADER, null, this);
        } else {
            mLoaderManager.restartLoader(PARTICIPANT_LOADER, null, this);
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
        Participant participant = Participant.from(data);
        mViewInterface.loadParticipant(participant);
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