package com.cupertinojudo.android;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cupertinojudo.android.data.source.CJTDataSource;
import com.cupertinojudo.android.data.source.CJTRepository;
import com.cupertinojudo.android.data.source.local.CJTLocalDataSource;
import com.cupertinojudo.android.data.source.remote.CJTRemoteDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by fabiohh on 5/11/17.
 */

public class Injection {
    public static CJTRepository provideTournamentRepository(@NonNull Context context) {
        checkNotNull(context);
        return CJTRepository.getInstance(provideLocalDataSource(context), provideRemoteDataSource());
    }

    public static CJTDataSource provideRemoteDataSource() {
        return CJTRemoteDataSource.getInstance();
    }

    public static CJTLocalDataSource provideLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
        return CJTLocalDataSource.getInstance(context.getContentResolver());
    }
}
