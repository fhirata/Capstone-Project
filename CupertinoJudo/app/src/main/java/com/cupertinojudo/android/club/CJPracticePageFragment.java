package com.cupertinojudo.android.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cupertinojudo.android.R;

/**
 *
 */

public class CJPracticePageFragment extends Fragment {
    private static final String KEY_TITLE = "practices";
    private TextView mTextView;
    private CJClubContract.PresenterInterface mPresenterInterface;

    public static CJPracticePageFragment newInstance(String sTitle) {
        CJPracticePageFragment mFragment = new CJPracticePageFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, sTitle);
        mFragment.setArguments(args);

        return mFragment;
    }

    public void setPresenter(CJClubContract.PresenterInterface presenterInterface) {
        mPresenterInterface = presenterInterface;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_practices, container, false);
        mTextView = (TextView) mView.findViewById(R.id.practice_textview);
        mTextView.setText(getTitle());

        return mView;
    }

    public String getTitle() {
        return (getArguments().getString(KEY_TITLE));
    }
}