package com.test.cupertinojudo.tournament;

/**
 * Created by fabiohh on 4/26/17.
 */

public class CJudoTournamentPresenter implements CJudoTournamentContract.Presenter {
    private CJudoTournamentContract.ActivityInterface mActivityInterface;
    private CJudoTournamentContract.ViewInterface mViewInterface;

    public CJudoTournamentPresenter(CJudoTournamentContract.ViewInterface tournamentFragment, CJudoTournamentContract.ActivityInterface activityInterface) {
        mActivityInterface = activityInterface;
        mViewInterface = tournamentFragment;

        mViewInterface.setPresenter(this);
    }

    @Override
    public void start() {
        //no-op
    }

    @Override
    public void handleTileClick(CJudoTournamentTile tournamentTile) {
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
