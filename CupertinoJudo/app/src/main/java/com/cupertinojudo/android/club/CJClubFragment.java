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

public class CJClubFragment extends Fragment {
    public static final String FRAGMENT_TAG = "CJClubFragment";
    public static CJClubFragment newInstance() {
        return new CJClubFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_club, container, false);

        TextView textView;
        textView = (TextView) view.findViewById(R.id.message);
        textView.setText(R.string.title_club);

        return view;
    }
}
