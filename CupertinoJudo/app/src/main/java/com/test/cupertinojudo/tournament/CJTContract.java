package com.test.cupertinojudo.tournament;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 4/26/17.
 */

public interface CJTContract {

    interface Presenter extends BasePresenterInterface {
        void handleTileClick(CJTTile tournamentTile);
    }

    interface ViewInterface extends BaseViewInterface<CJTContract.Presenter> {

    }

    interface ActivityInterface {
        void handleScheduleClick();
        void handleDirectionsClick();
        void handlePoolsClick();
        void handleConcessionClick();
        void handleVenueClick();
        void handleStatsClick();
    }
}
