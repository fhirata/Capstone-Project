package com.cupertinojudo.android.tournament.categories;

import android.database.Cursor;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;

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
