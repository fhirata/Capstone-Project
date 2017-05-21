package com.cupertinojudo.android.tournament.concession;

import com.cupertinojudo.android.tournament.CJTContract;

/**
 * 
 */

public class CJTConcessionPresenter implements CJTConcessionContract.Presenter {
    CJTConcessionContract.ViewInterface mViewInterface;
    CJTContract.ActivityInterface mActivityInterface;
    String mFile = "file:///android_asset/concession.html";

    public CJTConcessionPresenter(CJTConcessionContract.ViewInterface viewInterface, CJTContract.ActivityInterface activityInterface) {
        mViewInterface = viewInterface;
        mActivityInterface = activityInterface;

        mViewInterface.setPresenter(this);
    }

    @Override
    public void start() {
        loadConcession();
    }

    private void loadConcession() {
        mViewInterface.loadConcession(mFile);
    }
}
