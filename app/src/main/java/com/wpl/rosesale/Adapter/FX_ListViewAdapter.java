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
 * Created by 培龙 on 2016/11/27.
 */

public class FX_ListViewAdapter extends BaseAdapter {
    private List<Map<String, String>> list;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public FX_ListViewAdapter(Context context, List<Map<String, String>> list, int resource) {
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

        ImageView img = (ImageView) view.findViewById(R.id.fxItem_img);
        TextView title = (TextView) view.findViewById(R.id.fxItem_title);
        TextView username = (TextView) view.findViewById(R.id.fxItem_username);
        TextView sc = (TextView) view.findViewById(R.id.fxItem_sc);
        TextView ly = (TextView) view.findViewById(R.id.fxItem_ly);

        VolleyImageLoader.setBitmap(context, map.get("img"), img);
        title.setText(map.get("title"));
        username.setText(map.get("username"));

    }
}
