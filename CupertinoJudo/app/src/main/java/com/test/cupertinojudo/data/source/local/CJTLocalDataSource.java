package com.test.cupertinojudo.data.source.local;

import android.support.annotation.NonNull;

import com.test.cupertinojudo.data.source.CJTDataSource;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fabiohh on 5/9/17.
 */

public class CJTLocalDataSource implements CJTDataSource {

    private Date today = new Date();
    private static SimpleDateFormat currentYearDateFormat = new SimpleDateFormat("yyyy");
//    int year = CJTPersistenceContract.CJudoParticipantEntry.getYearFromUri(uri);


    public static CJTLocalDataSource getInstance() {
        return new CJTLocalDataSource();
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
