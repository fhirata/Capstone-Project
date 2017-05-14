package com.test.cupertinojudo.data.source;

import android.database.Cursor;
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
    private CJTDataSource mLocalDataSource;
    private CJTDataSource mRemoteDataSource;

    public static CJTRepository getInstance(@NonNull CJTDataSource localRepository, @NonNull CJTDataSource remoteRepository) {
        if (null == sInstance) {
            sInstance = new CJTRepository(localRepository, remoteRepository);
        }
        return sInstance;
    }

    private CJTRepository(@NonNull CJTDataSource localRepository, @NonNull CJTDataSource remoteRepository) {
        mLocalDataSource = localRepository;
        mRemoteDataSource = remoteRepository;
    }

    @Override
    public void getParticipant(@NonNull int participantId, @NonNull final GetParticipantCallback callback) {
        mLocalDataSource.getParticipant(participantId, new GetParticipantCallback() {
            @Override
            public void onParticipantLoaded(@NonNull Participant participant) {
                callback.onParticipantLoaded(participant);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }

    @Override
    public int insertParticipants(@NonNull int year, List<Participant> participants) {
        // no-op
        return 0;
    }

    @Override
    public int updateParticipants(@NonNull int year, List<Participant> participants) {
        // no-op
        return 0;
    }

    @Override
    public void getCategories(@NonNull int year, @NonNull final GetCategoryCallback callback) {
        mLocalDataSource.getCategories(year, new GetCategoryCallback() {
            @Override
            public void onCategoriesLoaded(List<TournamentCategory> categories) {
                callback.onCategoriesLoaded(categories);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }

    @Override
    public void getParticipants(@NonNull final int year, @NonNull final CJTDataSource.GetParticipantsCallback callback) {
        mRemoteDataSource.getParticipants(year, new GetParticipantsCallback() {
            @Override
            public void onParticipantsLoaded(@NonNull List<Participant> participants) {
                refreshLocalDataSource(year, participants);
                callback.onParticipantsLoaded(null);

            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }

    @Override
    public void getPools(@NonNull int year, @NonNull String category, @NonNull final GetPoolsCallback callback) {
        mLocalDataSource.getPools(year, category, new GetPoolsCallback() {
            @Override
            public void onPoolsLoaded(List<TournamentPool> pools) {
                callback.onPoolsLoaded(pools);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }

    @Override
    public void getPool(@NonNull int year, @NonNull String category, @NonNull String poolName, @NonNull final GetPoolCallback callback) {
        mLocalDataSource.getPool(year, category, poolName, new GetPoolCallback() {
            @Override
            public void onPoolLoaded(@NonNull TournamentPool pool) {
                callback.onPoolLoaded(pool);
            }

            @Override
            public void onDataNotAvailable(String errorMessage) {
                callback.onDataNotAvailable(errorMessage);
            }
        });
    }

    public interface LoadDataCallback {
        void onDataLoaded(Cursor data);

        void onDataEmpty();

        void onDataNotAvailable();

        void onDataReset();
    }

    private void refreshLocalDataSource(@NonNull final int year, List<Participant> participants) {
        mLocalDataSource.updateParticipants(year, participants);
    }
}
