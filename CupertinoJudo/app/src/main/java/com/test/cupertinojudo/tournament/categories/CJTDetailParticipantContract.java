package com.test.cupertinojudo.tournament.categories;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;
import com.test.cupertinojudo.data.models.Participant;

/**
 * Created by fabiohh on 5/13/17.
 */

public interface CJTDetailParticipantContract {
    interface Presenter extends BasePresenterInterface {
    }

    interface ViewInterface extends BaseViewInterface<Presenter> {
        void loadParticipant(Participant participant);
        void setLoadingIndicator(boolean enable);

    }

    interface ActivityInterface {
        void showError(int messageId);
    }
}
