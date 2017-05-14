package com.test.cupertinojudo.tournament.categories;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.cupertinojudo.R;
import com.test.cupertinojudo.data.models.Participant;

/**
 * Lists the participats of a single pool
 */

public class CJTPoolFragment extends Fragment implements CJTPoolContract.ViewInterface {
    public static final String CATEGORY = "category";
    public static final String POOL = "pool";

    private CJudoTournamentPoolAdapter mPoolAdapter;
    private CJTPoolContract.Presenter mPresenter;
    private PoolItemListener mPoolItemListener;
    private String mCategory;
    private String mPoolName;

    public interface PoolItemListener {
        void onPoolItemClick(int participantId);
    }

    public static CJTPoolFragment newInstance(String category, String pool) {
        CJTPoolFragment poolFragment = new CJTPoolFragment();

        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY, category);
        bundle.putString(POOL, pool);

        poolFragment.setArguments(bundle);

        return poolFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        Bundle bundle = getArguments();
        mCategory = bundle.getString(CATEGORY);
        mPoolName = bundle.getString(POOL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPoolAdapter = new CJudoTournamentPoolAdapter(getContext(), null, mPoolItemListener);
        return view;
    }

    @Override
    public void setPresenter(CJTPoolContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void loadPool(Cursor cursor) {

    }

    @Override
    public void setLoadingIndicator(boolean enable) {

    }


    private static class CJudoTournamentPoolAdapter extends CursorRecyclerAdapter<CJudoTournamentPoolAdapter.ViewHolder> {
        private CJTPoolFragment.PoolItemListener mPoolItemListener;

        public CJudoTournamentPoolAdapter(Context context, Cursor cursor, CJTPoolFragment.PoolItemListener poolItemListener) {
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
            holder.mRowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPoolItemListener.onPoolItemClick(participant.getId());
                }
            });
        }

        static class ViewHolder extends RecyclerView.ViewHolder{
            public final View mRowView;
            public final TextView mFullName;
            public final TextView mClub;

            public ViewHolder(View view) {
                super(view);
                mRowView = view;
                mFullName = (TextView) view.findViewById(R.id.name_textview);
                mClub = (TextView) view.findViewById(R.id.club_textview);
            }
        }
    }
}
