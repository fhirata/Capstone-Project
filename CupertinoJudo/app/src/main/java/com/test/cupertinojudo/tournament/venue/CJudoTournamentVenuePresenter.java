package com.test.cupertinojudo.tournament.venue;

import com.test.cupertinojudo.tournament.CJudoTournamentContract;

/**
 * Created by fabiohh on 5/8/17.
 */


public class CJudoTournamentVenuePresenter implements CJudoTournamentVenueContract.PresenterInterface {
    CJudoTournamentVenueFragment mViewInterface;
    CJudoTournamentContract.ActivityInterface mActivityInterface;
    String mFile = "file:///android_asset/lynbrook%20school%20map.png";

    public CJudoTournamentVenuePresenter(CJudoTournamentVenueFragment viewInterface, CJudoTournamentContract.ActivityInterface activityInterface) {
        mViewInterface = viewInterface;
        mActivityInterface = activityInterface;

        viewInterface.setPresenter(this);
    }

    @Override
    public void start() {
        loadVenue();
    }

    private void loadVenue() {
        mViewInterface.loadVenue(mFile);
    }
}
