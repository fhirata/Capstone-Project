package com.test.cupertinojudo.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.cupertinojudo.R;

/**
 * Created by fabiohh on 4/23/17.
 */

public class CJudoClubFragment extends Fragment {

    public static CJudoClubFragment newInstance() {
        return new CJudoClubFragment();
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
