package com.cupertinojudo.android.tournament.stats;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;

/**
 * Created by fabiohh on 6/3/17.
 */

public interface CJTStatsContract {
    interface ViewInterface extends BaseViewInterface<CJTStatsContract.PresenterInterface> {
        void setLoadingIndicator(boolean enable);
    }

    interface PresenterInterface extends BasePresenterInterface {

    }

    interface ActivityInterface {

    }
}
