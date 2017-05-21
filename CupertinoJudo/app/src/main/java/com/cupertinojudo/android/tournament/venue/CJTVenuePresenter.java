package com.cupertinojudo.android.tournament.venue;

import com.cupertinojudo.android.tournament.CJTContract;

/**
 *
 */


public class CJTVenuePresenter implements CJTVenueContract.PresenterInterface {
    CJTVenueFragment mViewInterface;
    CJTContract.ActivityInterface mActivityInterface;
    String mFile = "file:///android_asset/lynbrook%20school%20map.png";

    public CJTVenuePresenter(CJTVenueFragment viewInterface, CJTContract.ActivityInterface activityInterface) {
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
