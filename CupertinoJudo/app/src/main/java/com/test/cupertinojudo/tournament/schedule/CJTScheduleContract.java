package com.test.cupertinojudo.tournament.schedule;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 5/8/17.
 */

interface CJTScheduleContract {
    interface ViewInterface extends BaseViewInterface<Presenter> {
        void loadSchedule(String url);
    }

    interface Presenter extends BasePresenterInterface {

    }
}
