package com.test.cupertinojudo.data.models;

import java.util.List;

/**
 * Created by fabiohh on 5/9/17.
 */

public class TournamentCategory {
    private String mName;
    private List<TournamentPool> mPools;

    public TournamentCategory(String name, List<TournamentPool> pools) {
        mName = name;
        mPools = pools;
    }

    public String getName() {
        return mName;
    }

    public List<TournamentPool> getPools() {
        return mPools;
    }
}
