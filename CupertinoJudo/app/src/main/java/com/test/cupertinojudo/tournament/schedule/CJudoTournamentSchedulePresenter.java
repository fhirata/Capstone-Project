package com.test.cupertinojudo.tournament.schedule;

import com.test.cupertinojudo.tournament.CJudoTournamentContract;

/**
 * Created by fabiohh on 5/8/17.
 */

public class CJudoTournamentSchedulePresenter implements CJudoTournamentScheduleContract.Presenter {
    CJudoTournamentScheduleContract.ViewInterface mViewInterface;
    CJudoTournamentContract.ActivityInterface mActivityInterface;
    String mFile = "file:///android_asset/tournament_schedule.html";

    public CJudoTournamentSchedulePresenter(CJudoTournamentScheduleFragment viewInterface, CJudoTournamentContract.ActivityInterface activityInterface) {
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
