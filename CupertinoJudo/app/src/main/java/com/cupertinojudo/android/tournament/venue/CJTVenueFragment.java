package com.cupertinojudo.android.tournament.venue;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.cupertinojudo.android.R;

/**
 *
 */

public class CJTVenueFragment extends Fragment implements CJTVenueContract.ViewInterface {
    private WebView mWebView;
    private CJTVenueContract.PresenterInterface mPresenterInterface;

    public static CJTVenueFragment newInstance() {
        return new CJTVenueFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterInterface.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tournament_venue, container, false);

        mWebView = (WebView) viewGroup.findViewById(R.id.tournament_venue_webview);

        Toolbar toolbar = (Toolbar) viewGroup.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return viewGroup;
    }

    public void loadVenue(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public void setPresenter(CJTVenueContract.PresenterInterface presenterInterface) {
        mPresenterInterface = presenterInterface;
    }
}
