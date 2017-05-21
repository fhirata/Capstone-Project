package com.cupertinojudo.android;

import android.content.Context;
import android.support.annotation.NonNull;

import com.cupertinojudo.android.data.source.CJTDataSource;
import com.cupertinojudo.android.data.source.CJTRepository;
import com.cupertinojudo.android.data.source.CJudoClubDataSource;
import com.cupertinojudo.android.data.source.CJudoClubRepository;
import com.cupertinojudo.android.data.source.local.CJTLocalDataSource;
import com.cupertinojudo.android.data.source.remote.CJTRemoteDataSource;
import com.cupertinojudo.android.data.source.remote.CJudoClubRemoteDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 *
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

    public static CJudoClubRepository provideClubRepository(@NonNull Context context) {
        checkNotNull(context);
        return CJudoClubRepository.getInstance(provideClubRemoteDataSource());
    }

    public static CJudoClubDataSource provideClubRemoteDataSource() {
        return CJudoClubRemoteDataSource.getInstance();
    }
}
