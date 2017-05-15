package com.cupertinojudo.android.data.source.remote;

import com.cupertinojudo.android.data.models.Club;
import com.cupertinojudo.android.data.models.Tournament;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fabiohh on 5/10/17.
 */

public interface CJTServerInterface {

    @GET("/bins/1cfvl1")
    Call<Tournament> getTournamentParticipants();

    @GET("/bins/1c4ipl")
    Call<Club> getClubData();
}
