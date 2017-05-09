package com.test.cupertinojudo;

/**
 * Created by fabiohh on 5/8/17.
 */

public interface BaseViewInterface<V extends BasePresenterInterface> {

    void setPresenter(V presenter);
}
