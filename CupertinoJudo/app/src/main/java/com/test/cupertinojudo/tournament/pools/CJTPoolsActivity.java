package com.test.cupertinojudo.tournament.pools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.test.cupertinojudo.R;
import com.test.cupertinojudo.data.source.CJTRepository;
import com.test.cupertinojudo.data.source.local.CJTLocalDataSource;
import com.test.cupertinojudo.data.source.remote.CJTDataSource;

/**
 *
 */

public class CJTPoolsActivity extends AppCompatActivity implements CJTCategoryContract.ActivityInterface {
    private CJTCategoryFragment mCategoryFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pools);

        CJTCategoryPresenter categoryPresenter = new CJTCategoryPresenter(this, mCategoryFragment,
                CJTRepository.getInstance(CJTLocalDataSource.getInstance(), CJTDataSource.getInstance()));

        mCategoryFragment = CJTCategoryFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.pools_container, mCategoryFragment);

        mCategoryFragment.setPresenter(categoryPresenter);
    }

    @Override
    public void handlePoolsItemClick(int categoryId) {

    }

}
