package com.wpl.rosesale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.VolleyImageLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/1.
 */

public class SY_ListViewAdapter extends BaseAdapter {
    private List<Map<String, String>> list;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public SY_ListViewAdapter(Context context, List<Map<String, String>> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }
        bindView(position, view);
        return view;
    }

    private void bindView(int position, View view) {
        Map<String, String> map = list.get(position);
        if (map == null) {
            return;
        }
        ImageView pImage = (ImageView) view.findViewById(R.id.sy_listView_image);
        TextView pTitle = (TextView) view.findViewById(R.id.sy_listView_title);
        TextView pInfo = (TextView) view.findViewById(R.id.sy_listView_info);
        TextView pPrice = (TextView) view.findViewById(R.id.sy_listView_price);

        pTitle.setText(map.get("pTitle"));
        pInfo.setText(map.get("pInfo"));
        pPrice.setText(map.get("pPrice"));
        VolleyImageLoader.setBitmap(context, map.get("pSquare_Image"), pImage);
    }
}
