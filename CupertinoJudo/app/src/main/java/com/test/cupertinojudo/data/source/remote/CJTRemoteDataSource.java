package com.test.cupertinojudo.data.source.remote;

import android.support.annotation.NonNull;

import com.test.cupertinojudo.BuildConfig;
import com.test.cupertinojudo.data.models.Participant;
import com.test.cupertinojudo.data.models.Tournament;
import com.test.cupertinojudo.data.source.CJTDataSource;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fabiohh on 5/9/17.
 */

public class CJTRemoteDataSource implements CJTDataSource {
    private static final String BASE_URL = BuildConfig.CUPERTINO_BASE_URL;
    private static final String TOURNAMENT_URI = BuildConfig.CUPERTINO_TOURNAMENT_URI;
    private static CJTRemoteDataSource sRemoteDataSource;

    private CJTServerInterface mTournamentInterface;

    public static CJTDataSource getInstance() {
        return new CJTRemoteDataSource();
    }

    private CJTRemoteDataSource() {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTournamentInterface = retrofit.create(CJTServerInterface.class);
    }

    @Override
    public void getCategories(@NonNull int year, @NonNull GetCategoryCallback callback) {

    }

    @Override
    public void getParticipants(@NonNull final int year, @NonNull final GetParticipantsCallback participantsCallback) {
        Call<Tournament> call = mTournamentInterface.getTournamentParticipants();
        call.enqueue(new Callback<Tournament>() {
            @Override
            public void onResponse(Call<Tournament> call, Response<Tournament> response) {
                if (response.isSuccessful()) {
                    List<Participant> participantList = response.body().getParticipants();
                    for( Participant participant : participantList) {
                        participant.setTournamentYear(year);
                        participant.setDateDOB(participant.getDOB());
                    }
                    participantsCallback.onParticipantsLoaded(participantList);
                } else {
                    // Log error
                }
            }

            @Override
            public void onFailure(Call<Tournament> call, Throwable t) {
                participantsCallback.onDataNotAvailable(t.getMessage());
            }
        });
    }

    @Override
    public void getPools(@NonNull int year, @NonNull String category, @NonNull GetPoolsCallback callback) {

    }

    @Override
    public void getPool(@NonNull int year, @NonNull String category, @NonNull String poolName, @NonNull GetPoolCallback callback) {

    }

    @Override
    public void getParticipant(@NonNull int participantId, @NonNull GetParticipantCallback callback) {

    }

    @Override
    public int insertParticipants(@NonNull int year, List<Participant> participants) {
        // no-op
        return 0;
    }

    @Override
    public int updateParticipants(@NonNull final int year, List<Participant> participants) {
        return 0;
    }

}
