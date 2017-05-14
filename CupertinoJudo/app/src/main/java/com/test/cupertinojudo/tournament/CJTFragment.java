package com.test.cupertinojudo.tournament;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.test.cupertinojudo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabiohh on 4/23/17.
 */

public class CJTFragment extends Fragment implements CJTContract.ViewInterface {
    public static final String FRAGMENT_TAG = "CJTFragment";
    private CJTGridViewAdapter mGridviewAdapter;
    private GridView mGridView;
    private CJTContract.Presenter mPresenter;

    public static CJTFragment newInstance() {
        return new CJTFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_tournament, container, false);

        mGridView = (GridView) view.findViewById(R.id.gridview_tournament);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<CJTTile> tournamentTiles = new ArrayList<>();
        try {
            TypedArray icons = getResources().obtainTypedArray(R.array.icons);
            String[] titles = getResources().getStringArray(R.array.title);
            int[] ids = getResources().getIntArray(R.array.tile_ids);

            for (int i = 0; i < icons.length(); i++) {
                tournamentTiles.add(new CJTTile(titles[i], icons.getDrawable(i), ids[i]));
            }
        } catch (Resources.NotFoundException ResNotFoundException) {
            Log.e("Adapter", ResNotFoundException.getMessage() );
        }

        mGridviewAdapter = new CJTGridViewAdapter(mPresenter, tournamentTiles);

        mGridView.setAdapter(mGridviewAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mPresenter) {
            mPresenter.start();
        }
    }

    @Override
    public void setPresenter(CJTContract.Presenter presenter) {
        mPresenter = presenter;
    }

}
