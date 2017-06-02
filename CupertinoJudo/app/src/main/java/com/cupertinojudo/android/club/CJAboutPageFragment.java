package com.cupertinojudo.android.club;

import android.content.Intent;
import android.net.Uri;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

        final TextView mpChartTextView = (TextView) view.findViewById(R.id.mpchart_value_textview);
        mpChartTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(mpChartTextView.getText().toString());
            }
        });

        final TextView guavaValueTextView = (TextView) view.findViewById(R.id.guava_value_textview);
        guavaValueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(guavaValueTextView.getText().toString());
            }
        });

        final TextView retrofitValueTextView = (TextView) view.findViewById(R.id.retrofit2_value_textview);
        retrofitValueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(retrofitValueTextView.getText().toString());
            }
        });

        final TextView robotiumValueTextView = (TextView) view.findViewById(R.id.robotium_value_textview);
        robotiumValueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(robotiumValueTextView.getText().toString());
            }
        });

        final TextView gsonValueTextView = (TextView) view.findViewById(R.id.gson_value_textview);
        gsonValueTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl(gsonValueTextView.getText().toString());
            }
        });

        return view;
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    public String getTitle() {
        return (getArguments().getString(KEY_TITLE));
    }
}
