package com.test.cupertinojudo.tournament.categories;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.cupertinojudo.R;

/**
 * Created by fabiohh on 5/13/17.
 */

public class CJTDetailParticipantFragment extends Fragment implements CJTDetailParticipantContract.ViewInterface {
    CJTDetailParticipantContract.Presenter mPresenterInterface;
    public static String PARTICIPANT = "participant";

    public static CJTDetailParticipantFragment newInstance() {
        return new CJTDetailParticipantFragment();
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
        View view = inflater.inflate(R.layout.fragment_participant, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.pools_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }

    @Override
    public void loadParticipant(Cursor participant) {

    }

    @Override
    public void setLoadingIndicator(boolean enable) {

    }

    @Override
    public void setPresenter(CJTDetailParticipantContract.Presenter presenter) {
        mPresenterInterface = presenter;
    }

    public interface PoolsItemListener {
        void onPoolsItemClick(String category);
    }
}
