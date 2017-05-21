package com.cupertinojudo.android.tournament.venue;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;

/**
 *
 */

public interface CJTVenueContract {
    interface ViewInterface extends BaseViewInterface<PresenterInterface> {
        void loadVenue(String url);
    }

    interface PresenterInterface extends BasePresenterInterface {

    }
}
