package com.cupertinojudo.android;

/**
 *
 */

public interface BaseViewInterface<V extends BasePresenterInterface> {

    void setPresenter(V presenter);
}
