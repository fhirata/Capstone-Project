package com.cupertinojudo.android.tournament.stats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cupertinojudo.android.R;

/**
 * Created by fabiohh on 6/3/17.
 */

public class CJTStatsFragment extends Fragment implements CJTStatsContract.ViewInterface {
    CJTStatsContract.PresenterInterface mPresenterInterface;
    RecyclerView mStatsRecyclerview;

    public static CJTStatsFragment newInstance() {
        return new CJTStatsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenterInterface.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.pools_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }

    @Override
    public void setLoadingIndicator(boolean enable) {

    }

    @Override
    public void setPresenter(CJTStatsContract.PresenterInterface presenter) {
        mPresenterInterface = presenter;
    }
}
