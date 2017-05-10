package com.test.cupertinojudo.tournament.pools;

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
import android.widget.TextView;

import com.test.cupertinojudo.R;
import com.test.cupertinojudo.data.models.TournamentPool;

/**
 * For a given category, list all the available pools
 */

public class CJTCategoryFragment extends Fragment implements CJTCategoryContract.ViewInterface {
    CJTCategoryContract.Presenter mPresenterInterface;
    RecyclerView mCategoryRecyclerview;
    CJudoTournamentCategoryAdapter mTournamentCategoryAdapter;

    public static CJTCategoryFragment newInstance() {
        return new CJTCategoryFragment();
    }

    protected PoolsItemListener mPoolsItemListener = new PoolsItemListener() {
        @Override
        public void onPoolsItemClick(int category) {
            mPresenterInterface.handlePoolsItemClick(category);
        }
    };

    @Override
    public void setPresenter(CJTCategoryContract.Presenter presenter) {
        mPresenterInterface = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTournamentCategoryAdapter = new CJudoTournamentCategoryAdapter(getContext(), null, mPoolsItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pools, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.pools_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCategoryRecyclerview = (RecyclerView) view.findViewById(R.id.pools_recyclerview);
        mCategoryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCategoryRecyclerview.setAdapter(mTournamentCategoryAdapter);

        return view;
    }

    @Override
    public void showPools(Cursor poolsCursor) {
        mTournamentCategoryAdapter.swapCursor(poolsCursor);
    }

    public interface PoolsItemListener {
        void onPoolsItemClick(int categoryId);
    }

    private static class CJudoTournamentCategoryAdapter extends CursorRecyclerViewAdapter<CJudoTournamentCategoryAdapter.ViewHolder> {
        private PoolsItemListener mPoolsItemListener;
        Cursor mCursor;

        public CJudoTournamentCategoryAdapter(Context context, Cursor cursor, PoolsItemListener poolsItemListener) {
            super(context, cursor);
            mPoolsItemListener = poolsItemListener;
            mCursor = cursor;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pool_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, Cursor cursor) {
            final TournamentPool tournamentPool = TournamentPool.from(cursor);
            holder.mTitle.setText(tournamentPool.getPoolName());
            holder.mRowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPoolsItemListener.onPoolsItemClick(tournamentPool.getCategoryId());
                }
            });
        }

        @Override
        public int getItemCount() {
            if (null == mCursor) {
                return 0;
            }

            return mCursor.getCount();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            public final View mRowView;
            public final TextView mTitle;

            public ViewHolder(View view) {
                super(view);
                mRowView = view;
                mTitle = (TextView) view.findViewById(R.id.pools_textview);
            }
        }
    }
}
