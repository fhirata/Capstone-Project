package com.test.cupertinojudo.data.source;

import android.support.annotation.NonNull;

import com.test.cupertinojudo.data.models.Participant;
import com.test.cupertinojudo.data.models.TournamentCategory;
import com.test.cupertinojudo.data.models.TournamentPool;

import java.util.List;

/**
 * Created by fabiohh on 5/9/17.
 */

public class CJTRepository implements CJTDataSource {
    private static CJTRepository sInstance = null;
    private CJTDataSource mLocalRepository;
    private CJTDataSource mRemoteRepository;

    public static CJTRepository getInstance(@NonNull CJTDataSource localRepository, @NonNull CJTDataSource remoteRepository) {
        if (null == sInstance) {
            sInstance = new CJTRepository(localRepository, remoteRepository);
        }
        return sInstance;
    }

    private CJTRepository(@NonNull CJTDataSource localRepository, @NonNull CJTDataSource remoteRepository) {
        mLocalRepository = localRepository;
        mRemoteRepository = remoteRepository;
    }

    @Override
    public void getCategories(@NonNull int year, @NonNull final GetCategoryCallback callback) {
        mLocalRepository.getCategories(year, new GetCategoryCallback() {
            @Override
            public void onCategoriesLoaded(List<TournamentCategory> categories) {
                callback.onCategoriesLoaded(categories);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getParticipants(@NonNull int year, @NonNull final GetParticipantsCallback callback) {
        mLocalRepository.getParticipants(year, new GetParticipantsCallback() {
            @Override
            public void onParticipantsLoaded(List<Participant> participants) {
                callback.onParticipantsLoaded(participants);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPools(@NonNull int year, @NonNull String category, @NonNull final GetPoolsCallback callback) {
        mLocalRepository.getPools(year, category, new GetPoolsCallback() {
            @Override
            public void onPoolsLoaded(List<TournamentPool> pools) {
                callback.onPoolsLoaded(pools);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getPool(@NonNull int year, @NonNull String category, @NonNull String poolName, @NonNull final GetPoolCallback callback) {
        mLocalRepository.getPool(year, category, poolName, new GetPoolCallback() {
            @Override
            public void onPoolLoaded(@NonNull TournamentPool pool) {
                callback.onPoolLoaded(pool);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
}
