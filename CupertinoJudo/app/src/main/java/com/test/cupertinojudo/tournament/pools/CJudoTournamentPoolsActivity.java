package com.test.cupertinojudo.tournament.pools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.test.cupertinojudo.R;

/**
 * Created by fabiohh on 5/9/17.
 */

public class CJudoTournamentPoolsActivity extends AppCompatActivity implements CJudoTournamentCategoryContract.ActivityInterface {
    private CJudoTournamentCategoryFragment mCategoryFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pools);

        CJudoTournamentCategoryPresenter categoryPresenter = new CJudoTournamentCategoryPresenter(this);

        mCategoryFragment = CJudoTournamentCategoryFragment.newInstance();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.pools_container, mCategoryFragment);

        mCategoryFragment.setPresenter(categoryPresenter);
    }

    @Override
    public void handlePoolsItemClick(int categoryId) {

    }

}
