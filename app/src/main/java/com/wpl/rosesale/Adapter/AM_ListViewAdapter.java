package com.wpl.rosesale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wpl.rosesale.R;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/17.
 */
public class AM_ListViewAdapter extends BaseAdapter {
    private List<Map<String, String>> list;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public AM_ListViewAdapter(Context context, List<Map<String, String>> list, int resource) {
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
        TextView name = (TextView) view.findViewById(R.id.aMI_name);
        TextView phone = (TextView) view.findViewById(R.id.aMI_phone);
        TextView address = (TextView) view.findViewById(R.id.aMI_address);

        name.setText(map.get("uAM_name"));


        String uAM_phone = map.get("uAM_phone");

        if (uAM_phone == null) {
            phone.setText("" + uAM_phone);
        } else {
            phone.setText(uAM_phone.replace(uAM_phone.substring(3, 7), "****"));
        }


        String detailAddress = (map.get("uAM_province") + ""
                + map.get("uAM_city") + ""
                + map.get("uAM_county") + ""
                + map.get("uAM_detailAddress"));
        address.setText(detailAddress);
    }
}
