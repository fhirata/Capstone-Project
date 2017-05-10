package com.test.cupertinojudo.tournament.pools;

import com.test.cupertinojudo.BasePresenterInterface;
import com.test.cupertinojudo.BaseViewInterface;

/**
 * Created by fabiohh on 5/9/17.
 */

public interface CJTPoolContract {
    interface ViewInterface extends BaseViewInterface<Presenter> {
    }

    interface Presenter extends BasePresenterInterface {

    }
}
