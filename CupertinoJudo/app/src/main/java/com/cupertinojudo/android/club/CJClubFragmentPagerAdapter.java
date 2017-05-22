package com.cupertinojudo.android.club;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fabiohh on 5/21/17.
 */

public class CJClubFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "News", "Practice", "About" };
    private Context context;

    public CJClubFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return CJNewsPageFragment.newInstance("news");
            case 1:
                return CJPracticePageFragment.newInstance("practice");
            case 2:
                return CJAboutPageFragment.newInstance("about");
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
