package com.test.cupertinojudo.data.source.remote;

import android.support.annotation.NonNull;

/**
 * Created by fabiohh on 5/9/17.
 */

public class CJTDataSource implements com.test.cupertinojudo.data.source.CJTDataSource {
    public static com.test.cupertinojudo.data.source.remote.CJTDataSource getInstance() {
        return new com.test.cupertinojudo.data.source.remote.CJTDataSource();
    }

    @Override
    public void getCategories(@NonNull int year, @NonNull GetCategoryCallback callback) {

    }

    @Override
    public void getParticipants(@NonNull int year, @NonNull GetParticipantsCallback callback) {

    }

    @Override
    public void getPools(@NonNull int year, @NonNull String category, @NonNull GetPoolsCallback callback) {

    }

    @Override
    public void getPool(@NonNull int year, @NonNull String category, @NonNull String poolName, @NonNull GetPoolCallback callback) {

    }
}
