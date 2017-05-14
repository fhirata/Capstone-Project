package com.test.cupertinojudo.tournament.categories;

import android.database.Cursor;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 5/13/17.
 */

public interface CJTPoolsContract {
    interface Presenter extends BasePresenterInterface {
        void handlePoolItemClick(int year, String Category, String pool);
    }

    interface ViewInterface extends BaseViewInterface<Presenter> {
        void loadPools(Cursor cursor);
        void setLoadingIndicator(boolean enable);

    }

    interface ActivityInterface {
        void handlePoolItemClick(int year, String Category, String pool);
        void showError(int messageId);
    }
}
