package com.test.cupertinojudo.tournament.categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.test.cupertinojudo.Injection;
import com.test.cupertinojudo.R;
import com.test.cupertinojudo.data.source.CJTLoaderProvider;

/**
 *
 */

public class CJTCategoriesActivity extends AppCompatActivity implements CJTCategoriesContract.ActivityInterface,
CJTPoolsContract.ActivityInterface, CJTPoolContract.ActivityInterface, CJTDetailParticipantContract.ActivityInterface {
    private CJTCategoriesFragment mCategoryFragment;
    private CJTCategoriesPresenter mCategoryPresenter;
    private static final int TOURNAMENT_YEAR = 2016;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pools);

        mCategoryFragment = CJTCategoriesFragment.newInstance();

        CJTLoaderProvider loaderProvider = new CJTLoaderProvider(this);
        mCategoryPresenter = new CJTCategoriesPresenter(
                this,
                loaderProvider,
                getSupportLoaderManager(),
                mCategoryFragment,
                Injection.provideTournamentRepository(this),
                TOURNAMENT_YEAR
                );

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.pools_container, mCategoryFragment);
        transaction.commit();

        mCategoryFragment.setPresenter(mCategoryPresenter);
    }

    @Override
    public void handlePoolsItemClick(String category) {
        CJTPoolsFragment poolsFragment = CJTPoolsFragment.newInstance();
        CJTPoolsPresenter poolsPresenter = new CJTPoolsPresenter(
                this,
                new CJTLoaderProvider(this),
                getSupportLoaderManager(),
                poolsFragment,
                Injection.provideTournamentRepository(this),
                category,
                TOURNAMENT_YEAR
        );

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.pools_container, poolsFragment);
        transaction.commit();

        poolsFragment.setPresenter(poolsPresenter);

    }

    @Override
    public void handlePoolItemClick(String category, String pool) {
        CJTPoolFragment poolFragment = CJTPoolFragment.newInstance(category, pool);
        CJTPoolPresenter poolPresenter = new CJTPoolPresenter(
                this,
                new CJTLoaderProvider(this),
                getSupportLoaderManager(),
                poolFragment,
                Injection.provideTournamentRepository(this),
                category,
                pool,
                TOURNAMENT_YEAR
        );

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.pools_container, poolFragment);
        transaction.commit();

        poolFragment.setPresenter(poolPresenter);
    }

    @Override
    public void handleParticipantItemClick(int participantId) {
        CJTDetailParticipantFragment participantFragment = CJTDetailParticipantFragment.newInstance();
        CJTDetailParticipantPresenter participantPresenter = new CJTDetailParticipantPresenter(
                this,
                new CJTLoaderProvider(this),
                getSupportLoaderManager(),
                participantFragment,
                Injection.provideTournamentRepository(this),
                participantId
        );

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.pools_container, participantFragment);
        transaction.commit();

        participantFragment.setPresenter(participantPresenter);
    }

    @Override
    public void showError(int messageId) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
