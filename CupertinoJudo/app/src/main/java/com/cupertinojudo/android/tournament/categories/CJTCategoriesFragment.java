package com.cupertinojudo.android.tournament.categories;

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

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.TournamentCategory;

/**
 * For a given category, list all the available pools
 */

public class CJTCategoriesFragment extends Fragment implements CJTCategoriesContract.ViewInterface {
    CJTCategoriesContract.Presenter mPresenterInterface;
    RecyclerView mCategoryRecyclerview;
    CJudoTournamentCategoriesAdapter mTournamentCategoryAdapter;

    public static CJTCategoriesFragment newInstance() {
        return new CJTCategoriesFragment();
    }

    protected PoolsItemListener mPoolsItemListener = new PoolsItemListener() {
        @Override
        public void onPoolsItemClick(String category) {
            mPresenterInterface.handlePoolsItemClick(category);
        }
    };

    @Override
    public void setPresenter(CJTCategoriesContract.Presenter presenter) {
        mPresenterInterface = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onResume() {
        super.onResume();
        if (null != mPresenterInterface) {
            mPresenterInterface.start();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.pools_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTournamentCategoryAdapter = new CJudoTournamentCategoriesAdapter(getActivity(), null, mPoolsItemListener);

        mCategoryRecyclerview = (RecyclerView) view.findViewById(R.id.pools_recyclerview);
        mCategoryRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCategoryRecyclerview.setAdapter(mTournamentCategoryAdapter);

        return view;
    }

    @Override
    public void loadCategories(Cursor categories) {
        mTournamentCategoryAdapter.swapCursor(categories);
    }

    @Override
    public void setLoadingIndicator(boolean enable) {

    }

    public interface PoolsItemListener {
        void onPoolsItemClick(String category);
    }

    private static class CJudoTournamentCategoriesAdapter extends CursorRecyclerAdapter<CJudoTournamentCategoriesAdapter.ViewHolder> {
        private PoolsItemListener mPoolsItemListener;

        public CJudoTournamentCategoriesAdapter(Context context, Cursor cursor, PoolsItemListener poolsItemListener) {
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
