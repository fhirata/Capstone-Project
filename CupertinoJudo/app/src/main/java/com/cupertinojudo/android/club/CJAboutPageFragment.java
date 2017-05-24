package com.cupertinojudo.android.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cupertinojudo.android.BuildConfig;
import com.cupertinojudo.android.R;

/**
 *
 */

public class CJAboutPageFragment extends Fragment {
        private static final String KEY_TITLE = "about";
        private TextView mTextView;
        private CJClubContract.PresenterInterface mPresenterInterface;

        public static CJAboutPageFragment newInstance(String sTitle) {
            CJAboutPageFragment mFragment = new CJAboutPageFragment();

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
            View view = inflater.inflate(R.layout.fragment_about, container, false);

            TextView appVersionTextview = (TextView) view.findViewById(R.id.version_textview);
            appVersionTextview.setText(String.format(getString(R.string.format_version), BuildConfig.VERSION_NAME));


            return view;
        }

        public String getTitle() {
            return (getArguments().getString(KEY_TITLE));
        }
    }
