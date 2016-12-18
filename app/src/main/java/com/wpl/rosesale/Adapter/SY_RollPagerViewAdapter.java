package com.wpl.rosesale.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.wpl.rosesale.R;

/**
 * Created by 培龙 on 2016/9/26.
 */

public class SY_RollPagerViewAdapter extends StaticPagerAdapter {
    private int[] imgs = {
            R.mipmap.rv_1,
            R.mipmap.rv_2,
            R.mipmap.rv_3,
            R.mipmap.rv_4,
    };


    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        return view;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }
}
