package com.test.cupertinojudo.data.source.remote;

import com.test.cupertinojudo.data.models.Tournament;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fabiohh on 5/10/17.
 */

public interface CJTServerInterface {

    @GET("/bins/1cfvl1")
    Call<Tournament> getTournamentParticipants();
}
