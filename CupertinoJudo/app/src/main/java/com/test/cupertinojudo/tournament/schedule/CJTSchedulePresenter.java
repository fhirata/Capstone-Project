package com.test.cupertinojudo.tournament.schedule;

import com.test.cupertinojudo.tournament.CJTContract;

/**
 * Created by fabiohh on 5/8/17.
 */

public class CJTSchedulePresenter implements CJTScheduleContract.Presenter {
    CJTScheduleContract.ViewInterface mViewInterface;
    CJTContract.ActivityInterface mActivityInterface;
    String mFile = "file:///android_asset/tournament_schedule.html";

    public CJTSchedulePresenter(CJTScheduleFragment viewInterface, CJTContract.ActivityInterface activityInterface) {
        mViewInterface = viewInterface;
        mActivityInterface = activityInterface;

        mViewInterface.setPresenter(this);
    }

    @Override
    public void start() {
        loadSchedule();
    }

    private void loadSchedule() {
        mViewInterface.loadSchedule(mFile);
    }
}
