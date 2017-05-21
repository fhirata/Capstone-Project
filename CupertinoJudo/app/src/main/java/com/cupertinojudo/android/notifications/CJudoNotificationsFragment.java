package com.cupertinojudo.android.notifications;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.Notification;

import java.util.List;

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
        Log.i("HERE", "start called.");
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_notifications, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.notifications_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.title_notifications);

        mNotificationsRecyclerView = (RecyclerView) view.findViewById(R.id.notifications_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mNotificationsRecyclerView.setLayoutManager(layoutManager);
        mNotificationsRecyclerView.setAdapter(mNotificationAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mNotificationsRecyclerView.getContext(),
                layoutManager.getOrientation());
        mNotificationsRecyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    @Override
    public void setPresenter(CJudoNotificationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void updateNotifications(List<Notification> notifications) {
        mNotificationAdapter = new CJudoNotificationAdapter(notifications);
    }

    private static class CJudoNotificationAdapter extends RecyclerView.Adapter<CJudoNotificationAdapter.ViewHolder> {
        List<Notification> mNotificationList;

        public CJudoNotificationAdapter(List<Notification> notificationList) {
            mNotificationList = notificationList;
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
            holder.mBody.setText(mNotificationList.get(position).getBody());
            holder.mDate.setText(mNotificationList.get(position).getDate());
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
            public TextView mBody;
            public TextView mDate;

            public ViewHolder(View itemView) {
                super(itemView);

                mTitle = (TextView) itemView.findViewById(R.id.notification_title);
                mBody = (TextView) itemView.findViewById(R.id.notification_body);
                mDate = (TextView) itemView.findViewById(R.id.notification_time);
            }
        }
    }
}
