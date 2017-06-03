package com.cupertinojudo.android.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.Practice;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */

public class CJPracticePageFragment extends Fragment {
    private static final String KEY_TITLE = "practices";
    private TextView mTextView;
    private CJClubContract.PresenterInterface mPresenterInterface;
    private RecyclerView mPracticeRecyclerView;
    private CJudoPracticeAdapter mPracticeAdapter;


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPracticeAdapter = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    public static CJPracticePageFragment newInstance(String sTitle) {
        CJPracticePageFragment mFragment = new CJPracticePageFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, sTitle);
        mFragment.setArguments(args);

        return mFragment;
    }

    public void setPresenter(CJClubContract.PresenterInterface presenterInterface) {
        mPresenterInterface = checkNotNull(presenterInterface);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterInterface.loadPractices();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_practices, container, false);

        mPracticeRecyclerView = (RecyclerView) view.findViewById(R.id.practice_recyclerview);

        mPracticeAdapter = new CJudoPracticeAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mPracticeRecyclerView.setAdapter(mPracticeAdapter);
        mPracticeRecyclerView.setLayoutManager(layoutManager);
        return view;
    }

    public String getTitle() {
        return (getArguments().getString(KEY_TITLE));
    }

    public void loadPractice(List<Practice> practiceList) {
        mPracticeAdapter.updateData(practiceList);
    }

    private static class CJudoPracticeAdapter extends RecyclerView.Adapter<CJudoPracticeAdapter.ViewHolder> {
        List<Practice> mPracticeList;

        public CJudoPracticeAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.practice_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position < mPracticeList.size()) {
                holder.mTitle.setText(mPracticeList.get(position).getDay());
                if (!TextUtils.isEmpty(mPracticeList.get(position).getTime1()))
                    holder.mTime1.setText(mPracticeList.get(position).getTime1());
                else {
                    holder.mTime1.setVisibility(View.GONE);
                }

                if (!TextUtils.isEmpty(mPracticeList.get(position).getTime2())) {
                    holder.mTime2.setText(mPracticeList.get(position).getTime2());
                } else {
                    holder.mTime2.setVisibility(View.GONE);
                }
            }
        }

        public void updateData(List<Practice> practice) {
            mPracticeList = practice;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (null != mPracticeList) {
                return mPracticeList.size();
            }
            return 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTitle;
            public TextView mTime1;
            public TextView mTime2;

            public ViewHolder(View itemView) {
                super(itemView);

                mTitle = (TextView) itemView.findViewById(R.id.practice_title_textview);
                mTime1 = (TextView) itemView.findViewById(R.id.practice_time1_textview);
                mTime2 = (TextView) itemView.findViewById(R.id.practice_time2_textview);
            }
        }
    }
}
