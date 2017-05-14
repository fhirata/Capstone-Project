package com.test.cupertinojudo.data.source;

import android.support.annotation.NonNull;

import com.test.cupertinojudo.data.models.Participant;
import com.test.cupertinojudo.data.models.TournamentCategory;
import com.test.cupertinojudo.data.models.TournamentPool;

import java.util.List;

/**
 * Created by fabiohh on 5/9/17.
 */

public interface CJTDataSource {

    interface GetParticipantsCallback {
        void onParticipantsLoaded(@NonNull List<Participant> participants);
        void onDataNotAvailable(String errorMessage);
    }

    interface GetCategoryCallback {
        void onCategoriesLoaded(List<TournamentCategory> categories);
        void onDataNotAvailable(String errorMessage);
    }

    interface GetPoolsCallback {
        void onPoolsLoaded(List<TournamentPool> pools);
        void onDataNotAvailable(String errorMessage);
    }

    interface GetPoolCallback {
        void onPoolLoaded(@NonNull TournamentPool pool);
        void onDataNotAvailable(String errorMessage);
    }

    interface GetParticipantCallback {
        void onParticipantLoaded(@NonNull Participant participant);
        void onDataNotAvailable(String errorMessage);
    }

    void getParticipant(@NonNull int participantId, @NonNull GetParticipantCallback callback);

    int insertParticipants(@NonNull final int year, List<Participant> participants);

    int updateParticipants(@NonNull final int year, List<Participant> participants);

    void getCategories(@NonNull int year, @NonNull GetCategoryCallback callback);

    void getParticipants(@NonNull int year, @NonNull GetParticipantsCallback callback);

    void getPools(@NonNull int year, @NonNull String category, @NonNull GetPoolsCallback callback);

    void getPool(@NonNull int year, @NonNull String category, @NonNull String poolName, @NonNull GetPoolCallback callback);
}
