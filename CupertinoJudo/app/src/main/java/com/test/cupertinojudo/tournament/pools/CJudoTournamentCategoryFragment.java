package com.test.cupertinojudo.tournament.pools;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
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

public class CJudoTournamentCategoryFragment extends Fragment implements CJudoTournamentCategoryContract.ViewInterface {
    CJudoTournamentCategoryContract.Presenter mPresenterInterface;
    RecyclerView mCategoryRecyclerview;
    CJudoTournamentCategoryAdapter mTournamentCategoryAdapter;

    public static CJudoTournamentCategoryFragment newInstance() {
        return new CJudoTournamentCategoryFragment();
    }

    protected PoolsItemListener mPoolsItemListener = new PoolsItemListener() {
        @Override
        public void onPoolsItemClick(int category) {
            mPresenterInterface.handlePoolsItemClick(category);
        }
    };

    @Override
    public void setPresenter(CJudoTournamentCategoryContract.Presenter presenter) {
        mPresenterInterface = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTournamentCategoryAdapter = new CJudoTournamentCategoryAdapter(getContext(), mPoolsItemListener);
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

    public interface PoolsItemListener {
        void onPoolsItemClick(int categoryId);
    }

    private static class CJudoTournamentCategoryAdapter extends CursorAdapter {
        private PoolsItemListener mPoolsItemListener;

        public CJudoTournamentCategoryAdapter(Context context, PoolsItemListener poolsItemListener) {
            super(context, null, 0);
            mPoolsItemListener = poolsItemListener;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.pool_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);

            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();

            final TournamentPool tournamentPool = TournamentPool.from(cursor);
            viewHolder.mTitle.setText(tournamentPool.getPoolName());
            viewHolder.mRowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPoolsItemListener.onPoolsItemClick(tournamentPool.getCategoryId());
                }
            });
        }

        public static class ViewHolder {
            public final View mRowView;
            public final TextView mTitle;

            public ViewHolder(View view) {
                mRowView = view;
                mTitle = (TextView) view.findViewById(R.id.pools_textview);
            }
        }
    }
}
