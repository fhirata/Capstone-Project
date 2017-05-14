package com.test.cupertinojudo.tournament.categories;

import android.database.Cursor;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 5/13/17.
 */

public interface CJTDetailParticipantContract {
    interface Presenter extends BasePresenterInterface {

    }

    interface ViewInterface extends BaseViewInterface<Presenter> {
        void loadParticipant(Cursor participant);
        void setLoadingIndicator(boolean enable);

    }

    interface ActivityInterface {
        void showError(int messageId);
    }
}
