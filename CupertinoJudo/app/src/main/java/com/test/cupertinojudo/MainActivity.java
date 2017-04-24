package com.test.cupertinojudo;

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
import com.test.cupertinojudo.tournament.CJudoTournamentFragment;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    private Vector<Fragment> mFragments;
    private final int MENU_ITEMS = 3;
    private final int TOURNAMENT = 0;
    private final int CLUB = 1;
    private final int NOTIFICATIONS = 2;

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

        mFragments = new Vector<>(MENU_ITEMS);

        setContentView(R.layout.activity_main);

        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CJudoTournamentFragment tournamentFragment = CJudoTournamentFragment.newInstance();
        mFragments.add(TOURNAMENT, tournamentFragment);

        CJudoClubFragment clubFragment = CJudoClubFragment.newInstance();
        mFragments.add(CLUB, clubFragment);

        CJudoNotificationsFragment notificationsFragment = CJudoNotificationsFragment.newInstance();
        mFragments.add(NOTIFICATIONS, notificationsFragment);
    }

    private void swapFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, mFragments.get(position));
        fragmentTransaction.commit();
    }
}
