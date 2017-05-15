package com.cupertinojudo.android.notifications;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;
import com.cupertinojudo.android.data.models.Notification;

import java.util.List;

/**
 * Created by fabiohh on 5/15/17.
 */

public interface CJudoNotificationContract {

    interface Presenter extends BasePresenterInterface {

    }

    interface ViewInterface extends BaseViewInterface<Presenter> {
        void setLoadingIndicator(boolean active);
        void updateNotifications(List<Notification> notifications);
    }

    interface ActivityInterface {

    }
}
