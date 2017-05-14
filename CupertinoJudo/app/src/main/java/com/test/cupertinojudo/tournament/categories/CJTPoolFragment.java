package com.test.cupertinojudo.tournament.categories;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.test.cupertinojudo.R;
import com.test.cupertinojudo.data.models.Participant;

/**
 * Lists the participats of a single pool
 */

public class CJTPoolFragment extends Fragment implements CJTPoolContract.ViewInterface {

    private CJudoTournamentPoolAdapter mPoolAdapter;
    private CJTPoolContract.Presenter mPresenter;
    RecyclerView mPoolRecyclerview;
    protected PoolItemListener mPoolItemListener = new PoolItemListener() {
        @Override
        public void onPoolItemClick(int participantId) {
            mPresenter.handleParticipantItemClick(participantId);
        }
    };

    public interface PoolItemListener {
        void onPoolItemClick(int participantId);
    }

    public static CJTPoolFragment newInstance(String category, String pool) {
        CJTPoolFragment poolFragment = new CJTPoolFragment();
        return poolFragment;
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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.pools_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(mPresenter.getPoolName());

        mPoolAdapter = new CJudoTournamentPoolAdapter(getActivity(), null, mPoolItemListener);

        mPoolRecyclerview = (RecyclerView) view.findViewById(R.id.pools_recyclerview);
        mPoolRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPoolRecyclerview.setAdapter(mPoolAdapter);

        return view;
    }

    @Override
    public void setPresenter(CJTPoolContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void loadPool(Cursor cursor) {
        mPoolAdapter.swapCursor(cursor);
    }

    @Override
    public void setLoadingIndicator(boolean enable) {

    }

    private static class CJudoTournamentPoolAdapter extends CursorRecyclerAdapter<CJudoTournamentPoolAdapter.ViewHolder> {
        private PoolItemListener mPoolItemListener;

        public CJudoTournamentPoolAdapter(Context context, Cursor cursor, PoolItemListener poolItemListener) {
            super(cursor);
            mPoolItemListener = poolItemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolderCursor(ViewHolder holder, Cursor cursor) {
            final Participant participant = Participant.from(cursor);
            holder.mFullName.setText(participant.getFullName());
            holder.mClub.setText((participant.getClub()));
            holder.mRowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPoolItemListener.onPoolItemClick(participant.getId());
                }
            });
            if (cursor.isFirst()) {
                holder.mAvatarFrameLayout.setBackground(holder.itemView.getContext().getDrawable(R.drawable.ic_avatar_connector_last));
            } else if (cursor.isLast()) {
                holder.mAvatarFrameLayout.setBackground(holder.itemView.getContext().getDrawable(R.drawable.ic_avatar_connector_first));
            }
        }

        static class ViewHolder extends RecyclerView.ViewHolder{
            public final View mRowView;
            public final TextView mFullName;
            public final TextView mClub;
            public final FrameLayout mAvatarFrameLayout;

            public ViewHolder(View view) {
                super(view);

                mRowView = view;
                mAvatarFrameLayout = (FrameLayout) view.findViewById(R.id.avatar_framelayout);
                mFullName = (TextView) view.findViewById(R.id.fullname_textview);
                mClub = (TextView) view.findViewById(R.id.club_textview);
            }
        }
    }
}
