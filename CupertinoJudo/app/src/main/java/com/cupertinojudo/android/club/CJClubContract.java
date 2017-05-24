package com.cupertinojudo.android.club;

import com.cupertinojudo.android.BasePresenterInterface;
import com.cupertinojudo.android.BaseViewInterface;
import com.cupertinojudo.android.data.models.News;
import com.cupertinojudo.android.data.models.Practice;

import java.util.List;

/**
 *
 */

public interface CJClubContract {
    interface PresenterInterface extends BasePresenterInterface {
        public void loadNews();

        public void loadPractices();
    }

    interface ViewInterface extends BaseViewInterface<PresenterInterface> {
        public void onNewsLoaded(List<News> newsList);

        public void onPracticeLoaded(List<Practice> practiceList);

        public void setLoadingIndicator(boolean enable);

    }
    interface ActivityInterface {
        void showError(int messageId);
        void showError(String message);
        void showSuccess(int messageId);
    }
}
