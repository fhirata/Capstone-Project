package com.cupertinojudo.android.tournament.categories;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;
import com.cupertinojudo.android.data.models.Participant;

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
