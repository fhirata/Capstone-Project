package com.test.cupertinojudo.tournament.pools;

/**
 * Created by fabiohh on 5/9/17.
 */

public class CJudoTournamentCategoryPresenter implements CJudoTournamentCategoryContract.Presenter {
    CJudoTournamentCategoryContract.ViewInterface mViewInterface;
    CJudoTournamentCategoryContract.ActivityInterface mActivityInterface;

    public CJudoTournamentCategoryPresenter(CJudoTournamentCategoryContract.ActivityInterface activityInterface) {
        mActivityInterface = activityInterface;
    }



    @Override
    public void start() {

    }

    @Override
    public void handlePoolsItemClick(int category) {
        mActivityInterface.handlePoolsItemClick(category);
    }
}
