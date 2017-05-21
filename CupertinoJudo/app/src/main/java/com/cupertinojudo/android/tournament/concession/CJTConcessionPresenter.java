package com.cupertinojudo.android.tournament.concession;

import com.cupertinojudo.android.tournament.CJTContract;

/**
 * Created by fabiohh on 5/21/17.
 */

public class CJTConcessionPresenter implements CJTConcessionContract.Presenter {
    CJTConcessionContract.ViewInterface mViewInterface;
    CJTContract.ActivityInterface mActivityInterface;
    String mFile = "file:///android_asset/concessio.html";

    public CJTConcessionPresenter(CJTConcessionContract.ViewInterface viewInterface, CJTContract.ActivityInterface activityInterface) {
        mViewInterface = viewInterface;
        mActivityInterface = activityInterface;

        mViewInterface.setPresenter(this);
    }

    @Override
    public void start() {
        loadConcession();
    }

    private void loadSchedule() {
        mViewInterface.loadConcession(mFile);
    }
}
