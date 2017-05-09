package com.test.cupertinojudo.tournament.pools;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 5/9/17.
 */

public interface CJudoTournamentCategoryContract {
    interface Presenter extends BasePresenterInterface {
        void handlePoolsItemClick(int category);
    }

    interface ViewInterface extends BaseViewInterface<Presenter> {

    }

    interface ActivityInterface {
        void handlePoolsItemClick(int categoryId);
    }
}
