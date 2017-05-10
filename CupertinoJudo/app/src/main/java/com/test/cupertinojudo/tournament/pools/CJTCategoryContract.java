package com.test.cupertinojudo.tournament.pools;

import android.database.Cursor;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 5/9/17.
 */

public interface CJTCategoryContract {
    interface Presenter extends BasePresenterInterface {
        void handlePoolsItemClick(int category);
    }

    interface ViewInterface extends BaseViewInterface<Presenter> {
        void showPools(Cursor poolsCursor);
    }

    interface ActivityInterface {
        void handlePoolsItemClick(int categoryId);
    }
}
