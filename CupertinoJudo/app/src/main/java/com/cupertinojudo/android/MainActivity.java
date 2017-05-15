package com.cupertinojudo.android;

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

import com.cupertinojudo.android.club.CJClubFragment;
import com.cupertinojudo.android.notifications.CJudoNotificationContract;
import com.cupertinojudo.android.notifications.CJudoNotificationPresenter;
import com.cupertinojudo.android.notifications.CJudoNotificationsFragment;
import com.cupertinojudo.android.tournament.CJTContract;
import com.cupertinojudo.android.tournament.CJTFragment;
import com.cupertinojudo.android.tournament.CJTPresenter;
import com.cupertinojudo.android.tournament.categories.CJTCategoriesActivity;
import com.cupertinojudo.android.tournament.schedule.CJTScheduleFragment;
import com.cupertinojudo.android.tournament.schedule.CJTSchedulePresenter;
import com.cupertinojudo.android.tournament.venue.CJTVenueFragment;
import com.cupertinojudo.android.tournament.venue.CJTVenuePresenter;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements CJTContract.ActivityInterface, CJudoNotificationContract.ActivityInterface {
    public static final String NOTIFICATION_IDENTIFIER = "Notification";
    public static final String NOTIFICATION_OBJECT = "notification_object";

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

        // Firebase notification topic subscription
        FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.notification_topic));

        mFragments = new Vector<>(MENU_ITEMS);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Tournament fragment init
        CJTFragment tournamentFragment = (CJTFragment) getSupportFragmentManager().findFragmentByTag(CJTFragment.FRAGMENT_TAG);
        if (null == tournamentFragment) {
            tournamentFragment = CJTFragment.newInstance();
        }
        new CJTPresenter(tournamentFragment, this);
        mFragments.add(TOURNAMENT, tournamentFragment);

        // Club fragment init
        CJClubFragment clubFragment = (CJClubFragment) getSupportFragmentManager().findFragmentByTag(CJClubFragment.FRAGMENT_TAG);
        if (null == clubFragment) {
            clubFragment = CJClubFragment.newInstance();
        }
        mFragments.add(CLUB, clubFragment);

        // Notifications fragment init
        CJudoNotificationsFragment notificationsFragment = (CJudoNotificationsFragment) getSupportFragmentManager().findFragmentByTag(CJudoNotificationsFragment.FRAGMENT_TAG);
        if (null == notificationsFragment) {
            notificationsFragment = CJudoNotificationsFragment.newInstance();
        }
        new CJudoNotificationPresenter(this, notificationsFragment, Injection.provideClubRepository(getApplicationContext()));
        mFragments.add(NOTIFICATIONS, notificationsFragment);

        if (null == savedInstanceState) {
            swapFragment(TOURNAMENT);
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
        CJTScheduleFragment tournamentScheduleFragment = CJTScheduleFragment.newInstance();

        CJudoFragmentManager.replaceFragment(this, R.id.content, tournamentScheduleFragment);

        new CJTSchedulePresenter(tournamentScheduleFragment, this);
    }

    @Override
    public void handleVenueClick() {
        CJTVenueFragment venueFragment = CJTVenueFragment.newInstance();
        CJudoFragmentManager.replaceFragment(this, R.id.content, venueFragment);

        new CJTVenuePresenter(venueFragment, this);
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
        Intent intent = new Intent(this, CJTCategoriesActivity.class);
        startActivity(intent);
    }

    @Override
    public void handleConcessionClick() {

    }


    @Override
    public void handleStatsClick() {

    }
}
