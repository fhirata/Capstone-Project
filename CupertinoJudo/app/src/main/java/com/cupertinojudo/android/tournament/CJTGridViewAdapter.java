package com.cupertinojudo.android.tournament;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cupertinojudo.android.R;

import java.util.List;

/**
 *
 */

public class CJTGridViewAdapter extends BaseAdapter {
    List<CJTTile> mTournamentTiles;
    CJTContract.Presenter mPresenterInterface;

    CJTGridViewAdapter(CJTContract.Presenter presenterInterface, List<CJTTile> tournamentTiles) {
        mPresenterInterface = presenterInterface;
        mTournamentTiles = tournamentTiles;
    }

    @Override
    public int getCount() {
        return mTournamentTiles.size();
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

        TextView description = (TextView) gridViewItem.findViewById(R.id.tile_description);
        description.setText(mTournamentTiles.get(position).getDescription());

        ImageView imageView = (ImageView) gridViewItem.findViewById(R.id.tile_image);
        imageView.setImageDrawable(mTournamentTiles.get(position).getDrawable());

        gridViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CJTTile tournamentTile = mTournamentTiles.get(position);
                if (null != tournamentTile) {
                    mPresenterInterface.handleTileClick(tournamentTile);
                }
            }
        });
        return gridViewItem;
    }
}
