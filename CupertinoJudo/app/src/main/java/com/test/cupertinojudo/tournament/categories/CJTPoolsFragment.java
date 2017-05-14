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
import android.widget.TextView;

import com.test.cupertinojudo.R;
import com.test.cupertinojudo.data.models.TournamentCategory;

import static android.R.attr.category;

/**
 * For a given category, list all the available pools
 */

public class CJTPoolsFragment extends Fragment implements CJTPoolsContract.ViewInterface {
    CJTPoolsContract.Presenter mPresenterInterface;
    RecyclerView mCategoryRecyclerview;
    CJudoTournamentPoolsAdapter mTournamentPoolsAdapter;

    public static CJTPoolsFragment newInstance() {
        return new CJTPoolsFragment();
    }

    protected PoolsItemListener mPoolsItemListener = new PoolsItemListener() {
        @Override
        public void onPoolsItemClick(String poolName) {
            mPresenterInterface.handlePoolItemClick(poolName);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenterInterface.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.pools_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTournamentPoolsAdapter = new CJudoTournamentPoolsAdapter(getActivity(), null, mPoolsItemListener);

        mCategoryRecyclerview = (RecyclerView) view.findViewById(R.id.pools_recyclerview);
        mCategoryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCategoryRecyclerview.setAdapter(mTournamentPoolsAdapter);

        return view;
    }

    @Override
    public void loadPools(Cursor cursor) {
        mTournamentPoolsAdapter.swapCursor(cursor);
    }

    @Override
    public void setLoadingIndicator(boolean enable) {

    }

    @Override
    public void setPresenter(CJTPoolsContract.Presenter presenter) {
        mPresenterInterface = presenter;
    }

    public interface PoolsItemListener {
        void onPoolsItemClick(String category);
    }

    private static class CJudoTournamentPoolsAdapter extends CursorRecyclerAdapter<CJudoTournamentPoolsAdapter.ViewHolder> {
        private PoolsItemListener mPoolsItemListener;

        public CJudoTournamentPoolsAdapter(Context context, Cursor cursor, PoolsItemListener poolsItemListener) {
            super(cursor);
            mPoolsItemListener = poolsItemListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pool_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);

            return viewHolder;
        }

        @Override
        public void onBindViewHolderCursor(ViewHolder holder, Cursor cursor) {
            final TournamentCategory category = TournamentCategory.from(cursor);
            holder.mTitle.setText(category.getName());
            holder.mRowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPoolsItemListener.onPoolsItemClick(category.getName());
                }
            });
        }

        static class ViewHolder extends RecyclerView.ViewHolder{
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
