package com.test.cupertinojudo.tournament;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.cupertinojudo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabiohh on 4/30/17.
 */

public class CJudoTournamentGridViewAdapter extends BaseAdapter {
    List<CJudoTournamentTile> mTournamentTiles;

    public CJudoTournamentGridViewAdapter(Context context) {
        mTournamentTiles = new ArrayList<>();
        try {
            TypedArray icons = context.getResources().obtainTypedArray(R.array.icons);
            TypedArray titles = context.getResources().obtainTypedArray(R.array.title);

            for (int i = 0; i < icons.length(); i++) {
                mTournamentTiles.add(new CJudoTournamentTile(titles.getString(i), icons.getDrawable(i)));
            }
        } catch (Resources.NotFoundException ResNotFoundException) {
            Log.e("Adapter", ResNotFoundException.getMessage() );
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View gridViewItem =  inflater.inflate(R.layout.gridview_item, parent, false);

        TextView description = (TextView) gridViewItem.findViewById(R.id.tile_description);
        description.setText(mTournamentTiles.get(position).getDescription());

        ImageView imageView = (ImageView) gridViewItem.findViewById(R.id.tile_image);
        Glide.with(parent.getContext())
                .load(mTournamentTiles.get(position).getDrawable())
                .asBitmap()
                .placeholder(mTournamentTiles.get(position).getDrawable())
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(imageView);

        return gridViewItem;
    }
}
