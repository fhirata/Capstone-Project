package com.cupertinojudo.android.tournament;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;

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
