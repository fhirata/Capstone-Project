package com.cupertinojudo.android.tournament.categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cupertinojudo.android.Injection;
import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.source.CJTLoaderProvider;

/**
 *
 */

public class CJTCategoriesActivity extends AppCompatActivity implements CJTCategoriesContract.ActivityInterface,
        CJTPoolsContract.ActivityInterface, CJTPoolContract.ActivityInterface, CJTDetailParticipantContract.ActivityInterface {

    protected CoordinatorLayout mSnackbarLayout;
    private CJTCategoriesFragment mCategoryFragment;
    private CJTCategoriesPresenter mCategoryPresenter;
    private static final int TOURNAMENT_YEAR = 2016;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pools);

        mCategoryFragment = CJTCategoriesFragment.newInstance();

        // Snackbar status
        mSnackbarLayout = (CoordinatorLayout) findViewById(R.id.snackbar_layout);


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
        Snackbar snackbar = Snackbar.make(mSnackbarLayout, messageId, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(mSnackbarLayout, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
        snackbar.show();

    }
}
