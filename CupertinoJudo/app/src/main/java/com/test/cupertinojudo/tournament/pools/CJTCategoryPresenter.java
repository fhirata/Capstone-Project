package com.test.cupertinojudo.tournament.pools;

import com.test.cupertinojudo.data.source.CJTRepository;

/**
 *
 */

public class CJTCategoryPresenter implements CJTCategoryContract.Presenter {
    CJTCategoryContract.ViewInterface mViewInterface;
    CJTCategoryContract.ActivityInterface mActivityInterface;

    private final CJTRepository mTournamentRepository;

    public CJTCategoryPresenter(CJTCategoryContract.ActivityInterface activityInterface,
                                CJTCategoryContract.ViewInterface viewInterface,
                                CJTRepository repository) {
        mActivityInterface = activityInterface;
        mViewInterface = viewInterface;
        mTournamentRepository = repository;
    }



    @Override
    public void start() {

    }

    @Override
    public void handlePoolsItemClick(int category) {
        mActivityInterface.handlePoolsItemClick(category);
    }
}
