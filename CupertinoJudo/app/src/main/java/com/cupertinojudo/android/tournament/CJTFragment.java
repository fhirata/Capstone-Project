package com.cupertinojudo.android.tournament;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.cupertinojudo.android.R;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */

public class CJTFragment extends Fragment implements CJTContract.ViewInterface {
    public static final String FRAGMENT_TAG = "CJTFragment";
    private CJTGridViewAdapter mGridviewAdapter;
    private GridView mGridView;
    private CJTContract.Presenter mPresenter;
    List<CJTTile> mTournamentTiles;

    /**
     * Listener for clicks on GridView.
     */
    GridItemListener mItemListener = new GridItemListener() {
        @Override
        public void onTileClick(CJTTile tileItem) {
            mPresenter.handleTileClick(tileItem);
        }
    };


    public static CJTFragment newInstance() {
        return new CJTFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTournamentTiles = new ArrayList<>();
        try {
            TypedArray icons = getResources().obtainTypedArray(R.array.icons);
            String[] titles = getResources().getStringArray(R.array.title);
            String[] contentDescriptions = getResources().getStringArray(R.array.content_descriptions);

            int[] ids = getResources().getIntArray(R.array.tile_ids);

            for (int i = 0; i < icons.length(); i++) {
                mTournamentTiles.add(new CJTTile(titles[i], icons.getDrawable(i), contentDescriptions[i], ids[i]));
            }
        } catch (Resources.NotFoundException ResNotFoundException) {
            Log.e("Adapter", ResNotFoundException.getMessage() );
        }

        mGridviewAdapter = new CJTGridViewAdapter(mPresenter, mTournamentTiles, mItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_tournament, container, false);

        mGridView = (GridView) view.findViewById(R.id.gridview_tournament);
        mGridView.setAdapter(mGridviewAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(CJTContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    private static class CJTGridViewAdapter extends BaseAdapter {
        List<CJTTile> mTournamentTiles;
        GridItemListener mGridItemListener;

        CJTGridViewAdapter(CJTContract.Presenter presenterInterface, List<CJTTile> tournamentTiles, GridItemListener itemListener) {
            mTournamentTiles = tournamentTiles;
            mGridItemListener = itemListener;
        }

        @Override
        public int getCount() {
            if (mTournamentTiles != null) {
                return mTournamentTiles.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mTournamentTiles.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View gridViewItem =  inflater.inflate(R.layout.gridview_item, parent, false);

            TextView title = (TextView) gridViewItem.findViewById(R.id.tile_title);
            title.setText(mTournamentTiles.get(position).getTitle());
            title.setContentDescription(mTournamentTiles.get(position).getDescription());

            ImageView imageView = (ImageView) gridViewItem.findViewById(R.id.tile_image);
            imageView.setImageDrawable(mTournamentTiles.get(position).getDrawable());

            gridViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CJTTile tournamentTile = mTournamentTiles.get(position);
                    if (null != tournamentTile) {
                        mGridItemListener.onTileClick(tournamentTile);
                    }
                }
            });
            return gridViewItem;
        }
    }

    private interface GridItemListener {
        void onTileClick(CJTTile tileItem);
    }
}
