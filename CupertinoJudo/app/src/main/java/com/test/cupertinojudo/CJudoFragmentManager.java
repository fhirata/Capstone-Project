package com.test.cupertinojudo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by fabiohh on 5/8/17.
 */

public class CJudoFragmentManager {
    public static void replaceFragment(FragmentActivity fragmentActivity, int fragmentContainer, Fragment fragment) {
        FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.bottom_up, R.anim.bottom_down);
        transaction.addToBackStack(null);
        transaction.replace(fragmentContainer, fragment);
        transaction.commit();
    }
}
