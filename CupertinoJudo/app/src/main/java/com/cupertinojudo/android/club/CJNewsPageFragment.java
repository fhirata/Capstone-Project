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
 * Created by fabiohh on 5/21/17.
 */

public class CJNewsPageFragment extends Fragment {
    private static final String KEY_TITLE = "news";
    private TextView mTextView;

    public static Fragment newInstance(String sTitle) {
        CJNewsPageFragment mFragment = new CJNewsPageFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, sTitle);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_news, container, false);
        mTextView = (TextView) mView.findViewById(R.id.textView);
        mTextView.setText(getTitle());

        return mView;
    }

    public String getTitle() {
        return(getArguments().getString(KEY_TITLE));
    }
}
