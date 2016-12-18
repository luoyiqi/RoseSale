package com.wpl.rosesale.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.VolleyImageLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/21.
 */
public class MyOrder_ListViewAdapter extends BaseAdapter {

    private List<Map<String, String>> list;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public MyOrder_ListViewAdapter(Context context, List<Map<String, String>> list, int resource) {
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
        final Map<String, String> map = list.get(position);
        if (map == null) {
            return;
        }

        ImageView orderAll_img = (ImageView) view.findViewById(R.id.orderAll_img);
        TextView orderAll_pName = (TextView) view.findViewById(R.id.orderAll_pName);
        TextView orderAll_pType = (TextView) view.findViewById(R.id.orderAll_pType);
        TextView orderAll_pPrice = (TextView) view.findViewById(R.id.orderAll_pPrice);
        TextView orderAll_pNumber = (TextView) view.findViewById(R.id.orderAll_pNumber);
        TextView orderAll_pStatus = (TextView) view.findViewById(R.id.orderAll_pStatus);

        TextView orderAll_rePayBtn_text = (TextView) view.findViewById(R.id.orderAll_rePayBtn_text);
        LinearLayout orderAll_rePayBtn = (LinearLayout) view.findViewById(R.id.orderAll_rePayBtn);

        VolleyImageLoader.setBitmap(context, map.get("pSquare_Image"), orderAll_img);
        orderAll_pName.setText(map.get("pTitle"));
        orderAll_pType.setText(map.get("pType"));
        orderAll_pPrice.setText(map.get("pPrice"));
        orderAll_pNumber.setText(map.get("oGoods_number"));
        orderAll_pStatus.setText(map.get("order_status"));

        if (map.get("order_status").toString().equals("待付款")) {
            orderAll_rePayBtn_text.setText("去付款");
            orderAll_rePayBtn_text.setTextColor(Color.parseColor("#FFFFFF"));
            orderAll_rePayBtn.setBackgroundResource(R.drawable.pay_btn);
        }

        if (map.get("order_status").toString().equals("待收货")) {
            orderAll_rePayBtn_text.setText("查看物流");
        }

        orderAll_rePayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+map.get("pTitle"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
