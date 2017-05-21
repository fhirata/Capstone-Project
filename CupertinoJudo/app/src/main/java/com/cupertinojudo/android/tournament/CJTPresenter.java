package com.cupertinojudo.android.tournament;

/**
 *
 */

public class CJTPresenter implements CJTContract.Presenter {
    private CJTContract.ActivityInterface mActivityInterface;
    private CJTContract.ViewInterface mViewInterface;

    public CJTPresenter(CJTContract.ViewInterface tournamentFragment, CJTContract.ActivityInterface activityInterface) {
        mActivityInterface = activityInterface;
        mViewInterface = tournamentFragment;

        mViewInterface.setPresenter(this);
    }

    @Override
    public void start() {
        //no-op
    }

    @Override
    public void handleTileClick(CJTTile tournamentTile) {
        switch (tournamentTile.getId()) {
            case 0:
                mActivityInterface.handleScheduleClick();
                return;
            case 1:
                mActivityInterface.handleDirectionsClick();
                return;
            case 2:
                mActivityInterface.handlePoolsClick();
                return;
            case 3:
                mActivityInterface.handleConcessionClick();
                return;
            case 4:
                mActivityInterface.handleVenueClick();
                return;
            case 5:
                mActivityInterface.handleStatsClick();
                return;
            default:
                // TODO: Log error
        }
    }
}
