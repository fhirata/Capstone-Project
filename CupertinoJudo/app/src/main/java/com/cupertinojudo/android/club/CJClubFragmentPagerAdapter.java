package com.cupertinojudo.android.club;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.News;
import com.cupertinojudo.android.data.models.Practice;

import java.util.List;

/**
 *
 */

public class CJClubFragmentPagerAdapter extends FragmentPagerAdapter {
    private final static int NEWS = 0;
    private final static int PRACTICE = 1;
    private final static int ABOUT = 2;
    final int PAGE_COUNT = 3;
    private String[] mTabTitles;
    private CJClubContract.PresenterInterface mPresenterInterface;
    private CJNewsPageFragment mNewsPageFragment;
    private CJPracticePageFragment mPracticePageFragment;
    private CJAboutPageFragment mAboutPageFragment;

    public CJClubFragmentPagerAdapter(FragmentManager fm, CJClubContract.PresenterInterface presenterInterface) {
        super(fm);
        mPresenterInterface = presenterInterface;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case NEWS:
                return CJNewsPageFragment.newInstance("news");
            case PRACTICE:
                return CJPracticePageFragment.newInstance("practice");
            case ABOUT:
                return CJAboutPageFragment.newInstance("about");
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
        switch (position) {
            case NEWS:
                mNewsPageFragment = (CJNewsPageFragment) createdFragment;
                mNewsPageFragment.setPresenter(mPresenterInterface);
                mTabTitles[NEWS] = container.getContext().getString(R.string.news);
                break;
            case PRACTICE:
                mPracticePageFragment = (CJPracticePageFragment) createdFragment;
                mPracticePageFragment.setPresenter(mPresenterInterface);
                mTabTitles[PRACTICE] = container.getContext().getString(R.string.practice);
                break;
            case ABOUT:
                mAboutPageFragment = (CJAboutPageFragment) createdFragment;
                mAboutPageFragment.setPresenter(mPresenterInterface);
                mTabTitles[ABOUT] = container.getContext().getString(R.string.about);

                break;
            default:
                throw new IllegalStateException();
        }
        return createdFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return mTabTitles[position];
    }

    public void loadNews(List<News> newsList) {
        if (null == mNewsPageFragment) {
            return;
        }
        mNewsPageFragment.loadNews(newsList);
    }

    public void loadPractices(List<Practice> practices) {
        if (null == mPracticePageFragment) {
            return;
        }
        mPracticePageFragment.loadPractice(practices);
    }
}
