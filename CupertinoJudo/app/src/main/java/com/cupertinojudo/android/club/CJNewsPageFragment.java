package com.cupertinojudo.android.club;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cupertinojudo.android.DateFormatterUtil;
import com.cupertinojudo.android.R;
import com.cupertinojudo.android.data.models.News;

import java.util.List;

/**
 *
 */

public class CJNewsPageFragment extends Fragment {
    private static final String KEY_TITLE = "news";
    private CJClubContract.PresenterInterface mPresenterInterface;
    private RecyclerView mNewsRecyclerView;
    private CJudoNewsAdapter mNewsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public static CJNewsPageFragment newInstance(String sTitle) {
        CJNewsPageFragment mFragment = new CJNewsPageFragment();

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, sTitle);
        mFragment.setArguments(args);

        return mFragment;
    }

    public void setPresenter(CJClubContract.PresenterInterface presenterInterface) {
        mPresenterInterface = presenterInterface;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenterInterface.loadNews();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mNewsRecyclerView = (RecyclerView) view.findViewById(R.id.news_recyclerview);

        mNewsAdapter = new CJudoNewsAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        mNewsRecyclerView.setAdapter(mNewsAdapter);
        mNewsRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public String getTitle() {
        return (getArguments().getString(KEY_TITLE));
    }

    public void loadNews(List<News> newsList) {
        mNewsAdapter.updateData(newsList);
    }

    private static class CJudoNewsAdapter extends RecyclerView.Adapter<CJudoNewsAdapter.ViewHolder> {
        List<News> mNewsList;

        public CJudoNewsAdapter() {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTitle.setText(mNewsList.get(position).getTitle());
            holder.mDate.setText(DateFormatterUtil.formatTimestamp(mNewsList.get(position).getDate(), holder.itemView.getContext()));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                holder.mBody.setText(Html.fromHtml(mNewsList.get(position).getBody(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                holder.mBody.setText(Html.fromHtml(mNewsList.get(position).getBody()));
            }
            Glide.with(holder.itemView.getContext())
                    .load(mNewsList.get(position).getBgImage())
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .into(holder.mImageView);
        }

        public void updateData(List<News> notifications) {
            mNewsList = notifications;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (null != mNewsList) {
                return mNewsList.size();
            }
            return 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTitle;
            public TextView mDate;
            public TextView mBody;
            public ImageView mImageView;

            public ViewHolder(View itemView) {
                super(itemView);

                mTitle = (TextView) itemView.findViewById(R.id.news_title);
                mBody = (TextView) itemView.findViewById(R.id.news_body);
                mDate = (TextView) itemView.findViewById(R.id.news_time);
                mImageView = (ImageView) itemView.findViewById(R.id.news_icon);
            }
        }
    }
}
