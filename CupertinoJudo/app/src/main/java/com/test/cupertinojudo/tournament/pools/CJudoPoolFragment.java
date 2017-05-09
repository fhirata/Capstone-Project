package com.test.cupertinojudo.tournament.pools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Lists the participats of a single pool
 */

public class CJudoPoolFragment extends Fragment implements CJudoPoolContract.ViewInterface {
    private CJudoPoolContract.Presenter mPresenter;

    public static CJudoPoolFragment newInstance() {
        return new CJudoPoolFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setPresenter(CJudoPoolContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
