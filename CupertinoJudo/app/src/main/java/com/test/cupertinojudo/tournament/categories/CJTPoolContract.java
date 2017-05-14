package com.test.cupertinojudo.tournament.categories;

import android.database.Cursor;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 *
 */

public interface CJTPoolContract {
    interface Presenter extends BasePresenterInterface {
        void handleParticipantItemClick(int participantId);
        String getPoolName();
    }

    interface ViewInterface extends BaseViewInterface<Presenter> {
        void loadPool(Cursor cursor);
        void setLoadingIndicator(boolean enable);

    }

    interface ActivityInterface {
        void handleParticipantItemClick(int participantId);
        void showError(int messageId);
    }
}
