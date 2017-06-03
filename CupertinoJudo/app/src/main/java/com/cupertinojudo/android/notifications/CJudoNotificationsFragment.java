package com.cupertinojudo.android.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cupertinojudo.android.DateFormatterUtil;
import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.Notification;
import com.cupertinojudo.android.settings.CJudoSettingsActivity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */

public class CJudoNotificationsFragment extends Fragment implements CJudoNotificationContract.ViewInterface {
    public static final String FRAGMENT_TAG = "CJudoNotificationsFragment";

    private CJudoNotificationContract.Presenter mPresenter;
    private RecyclerView mNotificationsRecyclerView;
    private CJudoNotificationAdapter mNotificationAdapter;

    public static CJudoNotificationsFragment newInstance() {
        return new CJudoNotificationsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_notifications, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.notifications_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setTitle(R.string.title_notifications);

        setHasOptionsMenu(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNotificationsRecyclerView = (RecyclerView) view.findViewById(R.id.notifications_recyclerview);
        mNotificationAdapter = new CJudoNotificationAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mNotificationsRecyclerView.getContext(),
                layoutManager.getOrientation());
        mNotificationsRecyclerView.addItemDecoration(dividerItemDecoration);
        mNotificationsRecyclerView.setAdapter(mNotificationAdapter);
        mNotificationsRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(getActivity(), CJudoSettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.settings, menu);
    }

    @Override
    public void setPresenter(CJudoNotificationContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void updateNotifications(List<Notification> notifications) {
        mNotificationAdapter.updateData(notifications);
    }

    private static class CJudoNotificationAdapter extends RecyclerView.Adapter<CJudoNotificationAdapter.ViewHolder> {
        List<Notification> mNotificationList;

        public CJudoNotificationAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTitle.setText(mNotificationList.get(position).getTitle());
            Log.i("HERE", "Here: " + mNotificationList.get(position).getDate());
            holder.mDate.setText(DateFormatterUtil.formatTimestamp(mNotificationList.get(position).getDate(), holder.itemView.getContext()));
        }

        public void updateData(List<Notification> notifications) {
            mNotificationList = notifications;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (null != mNotificationList) {
                return mNotificationList.size();
            }
            return 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTitle;
            public TextView mDate;

            public ViewHolder(View itemView) {
                super(itemView);

                mTitle = (TextView) itemView.findViewById(R.id.notification_title);
                mDate = (TextView) itemView.findViewById(R.id.notification_time);
            }
        }
    }
}
