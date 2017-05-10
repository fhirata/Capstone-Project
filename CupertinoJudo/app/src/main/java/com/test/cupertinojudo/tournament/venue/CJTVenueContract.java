package com.test.cupertinojudo.tournament.venue;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 5/8/17.
 */

public interface CJTVenueContract {
    interface ViewInterface extends BaseViewInterface<PresenterInterface> {
        void loadVenue(String url);
    }

    interface PresenterInterface extends BasePresenterInterface {

    }
}
