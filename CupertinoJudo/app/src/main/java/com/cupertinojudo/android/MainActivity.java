package com.cupertinojudo.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.cupertinojudo.android.club.CJClubContract;
import com.cupertinojudo.android.club.CJClubFragment;
import com.cupertinojudo.android.club.CJClubPresenter;
import com.cupertinojudo.android.data.source.CJTLoaderProvider;
import com.cupertinojudo.android.data.source.CJudoClubRepository;
import com.cupertinojudo.android.data.source.remote.CJudoClubRemoteDataSource;
import com.cupertinojudo.android.notifications.CJudoNotificationContract;
import com.cupertinojudo.android.notifications.CJudoNotificationPresenter;
import com.cupertinojudo.android.notifications.CJudoNotificationsFragment;
import com.cupertinojudo.android.sync.CJudoSyncAdapter;
import com.cupertinojudo.android.tournament.CJTContract;
import com.cupertinojudo.android.tournament.CJTFragment;
import com.cupertinojudo.android.tournament.CJTPresenter;
import com.cupertinojudo.android.tournament.categories.CJTCategoriesActivity;
import com.cupertinojudo.android.tournament.concession.CJTConcessionFragment;
import com.cupertinojudo.android.tournament.concession.CJTConcessionPresenter;
import com.cupertinojudo.android.tournament.schedule.CJTScheduleFragment;
import com.cupertinojudo.android.tournament.schedule.CJTSchedulePresenter;
import com.cupertinojudo.android.tournament.venue.CJTVenueFragment;
import com.cupertinojudo.android.tournament.venue.CJTVenuePresenter;
import com.cupertinojudo.android.widget.WidgetUpdateService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements CJTContract.ActivityInterface, CJudoNotificationContract.ActivityInterface, CJClubContract.ActivityInterface {
    public static final String NOTIFICATION_IDENTIFIER = "Notification";
    public static final String NOTIFICATION_OBJECT = "notification_object";

    protected CoordinatorLayout mSnackbarLayout;
    private BottomNavigationView mBottomNavigationView;
    private Vector<Fragment> mFragments;
    private final int MENU_ITEMS = 3;
    private final int TOURNAMENT = 0;
    private final int CLUB = 1;
    private final int NOTIFICATIONS = 2;
    private static final String MAP_INTENT_PKG = "com.google.android.apps.maps";
    private static final String LYNBROOK_HS_ADDRESS = "Lynbrook High School, 1280 Johnson Ave, San Jose, CA 95129";
    private CJTPresenter mTournamentPresenter;
    private CJudoNotificationPresenter mNotificationPresenter;
    private CJClubPresenter mClubPresenter;

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

        // Snackbar status
        mSnackbarLayout = (CoordinatorLayout) findViewById(R.id.snackbar_layout);

        mFragments = new Vector<>(MENU_ITEMS);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Tournament fragment init
        CJTFragment tournamentFragment = CJTFragment.newInstance();
        mTournamentPresenter = new CJTPresenter(tournamentFragment, this);

        mFragments.add(TOURNAMENT, tournamentFragment);

        // Club fragment init
        CJClubFragment clubFragment = CJClubFragment.newInstance();
        mClubPresenter = new CJClubPresenter(this, clubFragment, CJudoClubRepository.getInstance(CJudoClubRemoteDataSource.getInstance()));

        mFragments.add(CLUB, clubFragment);

        // Notifications fragment init
        CJudoNotificationsFragment notificationsFragment = CJudoNotificationsFragment.newInstance();
        CJTLoaderProvider loaderProvider = new CJTLoaderProvider(this);
        mNotificationPresenter = new CJudoNotificationPresenter(this,
                notificationsFragment,
                Injection.provideClubRepository(getApplicationContext()));

        mFragments.add(NOTIFICATIONS, notificationsFragment);

        if (null == savedInstanceState) {
            swapFragment(TOURNAMENT);
        }

        CJudoSyncAdapter.initializeSyncAdapter(this);
        WidgetUpdateService.startActionUpdatePlayersWidgets(this);
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
        fragmentTransaction.replace(R.id.content, mFragments.get(position), mFragments.get(position).getTag());
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void handleScheduleClick() {
        CJTScheduleFragment tournamentScheduleFragment = CJTScheduleFragment.newInstance();

        new CJTSchedulePresenter(tournamentScheduleFragment, this);

        CJudoFragmentManager.replaceFragment(this, R.id.content, tournamentScheduleFragment);
    }

    @Override
    public void handleVenueClick() {
        CJTVenueFragment venueFragment = CJTVenueFragment.newInstance();

        new CJTVenuePresenter(venueFragment, this);

        CJudoFragmentManager.replaceFragment(this, R.id.content, venueFragment);
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
        CJTConcessionFragment tournamentConcessionFragment = CJTConcessionFragment.newInstance();

        new CJTConcessionPresenter(tournamentConcessionFragment, this);

        CJudoFragmentManager.replaceFragment(this, R.id.content, tournamentConcessionFragment);
    }


    @Override
    public void handleStatsClick() {

    }

    @Override
    public void showError(int messageId) {
        Snackbar snackbar = Snackbar.make(mSnackbarLayout, messageId, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
        snackbar.show();
    }

    @Override
    public void showSuccess(int messageId) {
        Snackbar snackbar = Snackbar.make(mSnackbarLayout, messageId, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlueGreen));
        snackbar.show();
    }

    @Override
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(mSnackbarLayout, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
        snackbar.show();
    }
}
