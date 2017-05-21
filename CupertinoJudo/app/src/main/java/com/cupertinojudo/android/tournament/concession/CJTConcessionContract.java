package com.cupertinojudo.android.tournament.concession;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;

interface   CJTConcessionContract {
    interface ViewInterface extends BaseViewInterface<Presenter> {
        void loadConcession(String url);
    }

    interface Presenter extends BasePresenterInterface {

    }
}
