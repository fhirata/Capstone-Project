package com.cupertinojudo.android.tournament.stats;

/**
 * Created by fabiohh on 6/3/17.
 */

public class CJTStatsPresenter implements CJTStatsContract.PresenterInterface {
    CJTStatsContract.ViewInterface mViewInterface;
    CJTStatsContract.ActivityInterface mActivityInterface;

    public CJTStatsPresenter(CJTStatsContract.ViewInterface viewInterface, CJTStatsContract.ActivityInterface activityInterface) {
        mActivityInterface = activityInterface;
        mViewInterface = viewInterface;

        mViewInterface.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
