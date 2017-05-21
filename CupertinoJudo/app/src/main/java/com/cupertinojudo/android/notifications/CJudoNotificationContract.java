package com.cupertinojudo.android.notifications;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;
import com.cupertinojudo.android.data.models.Notification;

import java.util.List;

/**
 *
 */

public interface CJudoNotificationContract {

    interface Presenter extends BasePresenterInterface {
    }

    interface ViewInterface extends BaseViewInterface<Presenter> {
        void setLoadingIndicator(boolean active);
        void updateNotifications(List<Notification> notifications);
    }

    interface ActivityInterface {
        void showError(int messageId);
        void showError(String message);
        void showSuccess(int messageId);
    }
}
