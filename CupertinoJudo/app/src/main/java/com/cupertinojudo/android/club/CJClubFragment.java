package com.cupertinojudo.android.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.News;
import com.cupertinojudo.android.data.models.Practice;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 *
 */

public class CJClubFragment extends Fragment implements CJClubContract.ViewInterface{
    public static final String FRAGMENT_TAG = "CJClubFragment";
    private CJClubContract.PresenterInterface mPresenterInterface;
    private CJClubFragmentPagerAdapter mFragmentPagerAdapter;
    public static CJClubFragment newInstance() {
        return new CJClubFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentPagerAdapter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterInterface.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_club, container, false);

        mFragmentPagerAdapter = new CJClubFragmentPagerAdapter(getChildFragmentManager(), mPresenterInterface, view.getContext());

        // Change color of appbarlayout when collapsed
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.club_appbarlayout);
        final Toolbar clubToolbar = (Toolbar) view.findViewById(R.id.club_toolbar);

        //Set a listener to know the current visible state of CollapseLayout
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(final AppBarLayout appBarLayout, int verticalOffset) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                //Check if the view is collapsed
                if (scrollRange + verticalOffset == 0) {
                    clubToolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.lightGray));
                    clubToolbar.setTitle(R.string.title_club);
                    clubToolbar.setVisibility(View.VISIBLE);

                } else {
                    clubToolbar.setVisibility(View.GONE);
                }
            }
        });
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(mFragmentPagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.pager_header);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onNewsLoaded(List<News> newsList) {
        mFragmentPagerAdapter.loadNews(newsList);
    }

    @Override
    public void onPracticeLoaded(List<Practice> practiceList) {
        mFragmentPagerAdapter.loadPractices(practiceList);
    }

    @Override
    public void setLoadingIndicator(boolean enable) {

    }

    @Override
    public void setPresenter(CJClubContract.PresenterInterface presenter) {
        mPresenterInterface = checkNotNull(presenter);
    }
}
