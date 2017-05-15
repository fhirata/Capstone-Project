package com.cupertinojudo.android.tournament.categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.UnitsFormatterUtil;
import com.cupertinojudo.android.data.models.Participant;

/**
 * Created by fabiohh on 5/13/17.
 */

public class CJTDetailParticipantFragment extends Fragment implements CJTDetailParticipantContract.ViewInterface {
    CJTDetailParticipantContract.Presenter mPresenterInterface;

    private TextView mNameTextView;
    private ImageView mParticipantImageView;
    private TextView mClubTextView;
    private TextView mBeltTextView;
    private TextView mWeightTextView;

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
        if (null != mPresenterInterface) {
            mPresenterInterface.start();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participant, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.pools_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.participant_screen_title);

        mNameTextView = (TextView) view.findViewById(R.id.name_textview);
        mParticipantImageView = (ImageView) view.findViewById(R.id.avatar_imageview);
        mClubTextView = (TextView) view.findViewById(R.id.club_textview);
        mBeltTextView = (TextView) view.findViewById(R.id.belt_textview);
        mWeightTextView = (TextView) view.findViewById(R.id.weight_textview);

        return view;
    }

    @Override
    public void loadParticipant(Participant participant) {
        mNameTextView.setText(participant.getFullName());
        mClubTextView.setText(participant.getClub());
        mBeltTextView.setText(participant.getBelt());
        mWeightTextView.setText(UnitsFormatterUtil.formatWeight(getContext(), participant.getWeight()));
    }

    @Override
    public void setLoadingIndicator(boolean enable) {

    }

    @Override
    public void setPresenter(CJTDetailParticipantContract.Presenter presenter) {
        mPresenterInterface = presenter;
    }
}
