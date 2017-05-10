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
        void onParticipantsLoaded(List<Participant> participants);
        void onDataNotAvailable();
    }

    interface GetCategoryCallback {
        void onCategoriesLoaded(List<TournamentCategory> categories);
        void onDataNotAvailable();
    }

    interface GetPoolsCallback {
        void onPoolsLoaded(List<TournamentPool> pools);
        void onDataNotAvailable();
    }

    interface GetPoolCallback {
        void onPoolLoaded(@NonNull TournamentPool pool);
        void onDataNotAvailable();
    }

    void getCategories(@NonNull int year, @NonNull GetCategoryCallback callback);

    void getParticipants(@NonNull int year, @NonNull GetParticipantsCallback callback);

    void getPools(@NonNull int year, @NonNull String category, @NonNull GetPoolsCallback callback);

    void getPool(@NonNull int year, @NonNull String category, @NonNull String poolName, @NonNull GetPoolCallback callback);
}
