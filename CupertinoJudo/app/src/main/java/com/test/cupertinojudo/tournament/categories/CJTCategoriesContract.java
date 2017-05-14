package com.test.cupertinojudo.tournament.categories;

import android.database.Cursor;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 5/9/17.
 */

public interface CJTCategoriesContract {
    interface Presenter extends BasePresenterInterface {
        void handlePoolsItemClick(String category);
    }

    interface ViewInterface extends BaseViewInterface<Presenter> {
        void loadCategories(Cursor categories);
        void setLoadingIndicator(boolean enable);

    }

    interface ActivityInterface {
        void handlePoolsItemClick(String category);
        void showError(int messageId);
    }
}
