package com.test.cupertinojudo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.test.cupertinojudo.club.CJudoClubFragment;
import com.test.cupertinojudo.notifications.CJudoNotificationsFragment;
import com.test.cupertinojudo.tournament.CJudoTournamentContract;
import com.test.cupertinojudo.tournament.CJudoTournamentFragment;
import com.test.cupertinojudo.tournament.CJudoTournamentPresenter;
import com.test.cupertinojudo.tournament.schedule.CJudoTournamentScheduleFragment;
import com.test.cupertinojudo.tournament.schedule.CJudoTournamentSchedulePresenter;
import com.test.cupertinojudo.tournament.venue.CJudoTournamentVenueFragment;
import com.test.cupertinojudo.tournament.venue.CJudoTournamentVenuePresenter;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements CJudoTournamentContract.ActivityInterface {
    private BottomNavigationView mBottomNavigationView;
    private Vector<Fragment> mFragments;
    private final int MENU_ITEMS = 3;
    private final int TOURNAMENT = 0;
    private final int CLUB = 1;
    private final int NOTIFICATIONS = 2;
    private static final String MAP_INTENT_PKG = "com.google.android.apps.maps";
    private static final String LYNBROOK_HS_ADDRESS = "Lynbrook High School, 1280 Johnson Ave, San Jose, CA 95129";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_tournament:
                    swapFragment(TOURNAMENT);
                    return true;
                case R.id.navigation_club:
                    swapFragment(CLUB);
                    return true;
                case R.id.navigation_notifications:
                    swapFragment(NOTIFICATIONS);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mFragments = new Vector<>(MENU_ITEMS);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Tournament fragment init
        CJudoTournamentFragment tournamentFragment = (CJudoTournamentFragment) getSupportFragmentManager().findFragmentByTag(CJudoTournamentFragment.FRAGMENT_TAG);
        if (null == tournamentFragment) {
            tournamentFragment = CJudoTournamentFragment.newInstance();
        }
        new CJudoTournamentPresenter(tournamentFragment, this);
        mFragments.add(TOURNAMENT, tournamentFragment);

        // Club fragment init
        CJudoClubFragment clubFragment = (CJudoClubFragment) getSupportFragmentManager().findFragmentByTag(CJudoClubFragment.FRAGMENT_TAG);
        if (null == clubFragment) {
            clubFragment = CJudoClubFragment.newInstance();
        }
        mFragments.add(CLUB, clubFragment);

        // Notifications fragment init
        CJudoNotificationsFragment notificationsFragment = (CJudoNotificationsFragment) getSupportFragmentManager().findFragmentByTag(CJudoNotificationsFragment.FRAGMENT_TAG);
        if (null == notificationsFragment) {
            CJudoNotificationsFragment.newInstance();
        }
        mFragments.add(NOTIFICATIONS, notificationsFragment);

        if (null == savedInstanceState) {
            swapFragment(TOURNAMENT);
        } else {

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void swapFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, mFragments.get(position));
        fragmentTransaction.commit();
    }

    @Override
    public void handleScheduleClick() {
        CJudoTournamentScheduleFragment tournamentScheduleFragment = CJudoTournamentScheduleFragment.newInstance();

        CJudoFragmentManager.replaceFragment(this, R.id.content, tournamentScheduleFragment);

        new CJudoTournamentSchedulePresenter(tournamentScheduleFragment, this);
    }

    @Override
    public void handleVenueClick() {
        CJudoTournamentVenueFragment venueFragment = CJudoTournamentVenueFragment.newInstance();
        CJudoFragmentManager.replaceFragment(this, R.id.content, venueFragment);

        new CJudoTournamentVenuePresenter(venueFragment, this);
    }

    @Override
    public void handleDirectionsClick() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + LYNBROOK_HS_ADDRESS);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(MAP_INTENT_PKG);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public void handlePoolsClick() {

    }

    @Override
    public void handleConcessionClick() {

    }


    @Override
    public void handleStatsClick() {

    }
}
