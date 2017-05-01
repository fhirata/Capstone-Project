package com.test.cupertinojudo.tournament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.test.cupertinojudo.R;

/**
 * Created by fabiohh on 4/23/17.
 */

public class CJudoTournamentFragment extends Fragment implements CJudoTournamentContract.ViewInterface {
    private CJudoTournamentGridViewAdapter mGridviewAdapter;
    private GridView mGridView;

    public static CJudoTournamentFragment newInstance() {
        return new CJudoTournamentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_tournament, container, false);

        mGridView = (GridView) view.findViewById(R.id.gridview_tournament);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridviewAdapter = new CJudoTournamentGridViewAdapter(getContext());

        mGridView.setAdapter(mGridviewAdapter);
    }
}
