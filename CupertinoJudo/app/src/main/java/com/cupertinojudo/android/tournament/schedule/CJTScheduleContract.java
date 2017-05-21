package com.cupertinojudo.android.tournament.schedule;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;

/**
 *
 */

interface CJTScheduleContract {
    interface ViewInterface extends BaseViewInterface<Presenter> {
        void loadSchedule(String url);
    }

    interface Presenter extends BasePresenterInterface {

    }
}
