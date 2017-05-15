package com.cupertinojudo.android.tournament.categories;

import android.database.Cursor;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;

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
