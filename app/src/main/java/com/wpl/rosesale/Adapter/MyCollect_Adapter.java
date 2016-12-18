package com.wpl.rosesale.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wpl.rosesale.Bean.collect;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.VolleyImageLoader;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 培龙 on 2016/11/1.
 */
public class MyCollect_Adapter extends BaseAdapter implements View.OnClickListener {

    // 屏幕宽度,由于我们用的是HorizontalScrollView,所以按钮选项应该在屏幕外
    private int screenWidth;
    private View view;


    private List<Map<String, String>> list;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public MyCollect_Adapter(Context context, int screenWidth, List<Map<String, String>> list, int resource) {
        this.context = context;
        this.list = list;
        this.resource = resource;
        this.screenWidth = screenWidth;
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

    private boolean isClose = true;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
            // 初始化holder
            holder = new ViewHolder();
            holder.hSView = (HorizontalScrollView) convertView.findViewById(R.id.myCollectItem_item);
            holder.action = convertView.findViewById(R.id.myCollectItem_rightLayout);
            holder.btOne = (Button) convertView.findViewById(R.id.myCollectItem_cancelLike);
            holder._img = (ImageView) convertView.findViewById(R.id.myCollectItem_img);
            holder._title = (TextView) convertView.findViewById(R.id.myCollectItem_title);
            holder._type = (TextView) convertView.findViewById(R.id.myCollectItem_type);
            holder._price = (TextView) convertView.findViewById(R.id.myCollectItem_price);
            // 设置内容view的大小为屏幕宽度,这样按钮就正好被挤出屏幕外
            holder.content = convertView.findViewById(R.id.myCollectItem_content);
            ViewGroup.LayoutParams lp = holder.content.getLayoutParams();
            lp.width = screenWidth;
            convertView.setTag(holder);
        } else {
            // 有直接获得ViewHolder
            holder = (ViewHolder) convertView.getTag();
        }
        // 把位置放到view中,这样点击事件就可以知道点击的是哪一条item
        holder.btOne.setTag(position);
        // 设置监听事件
        convertView.setOnTouchListener(new View.OnTouchListener() {
            private int x, y;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (view != null) {
                            ViewHolder viewHolder1 = (ViewHolder) view.getTag();
                            viewHolder1.hSView.smoothScrollTo(0, 0);
                        }
                        x = (int) event.getX();
                        y = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int x3 = (int) event.getX();
                        int y3 = (int) event.getY();
                        int dY = Math.abs(y - y3);
                        int dx = Math.abs(x - x3);
                        break;
                    case MotionEvent.ACTION_UP:
                        // 获得ViewHolder
                        ViewHolder viewHolder = (ViewHolder) v.getTag();
                        view = v;
                        // 获得HorizontalScrollView滑动的水平方向值.
                        int scrollX = viewHolder.hSView.getScrollX();
                        // 获得操作区域的长度
                        int actionW = viewHolder.action.getWidth();
                        // 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
                        // 如果水平方向的移动值<操作区域的长度的一半,就复原
                        if (isClose) {
                            if (scrollX < (actionW / 5)) {
                                isClose = true;
                                viewHolder.hSView.smoothScrollTo(0, 0);
                            } else {// 否则的话显示操作区域
                                isClose = false;
                                viewHolder.hSView.smoothScrollTo(actionW, 0);
                            }
                        } else {
                            if (scrollX < (actionW * 4.0 / 5.0)) {
                                isClose = true;
                                viewHolder.hSView.smoothScrollTo(0, 0);
                            } else {// 否则的话显示操作区域
                                isClose = false;
                                viewHolder.hSView.smoothScrollTo(actionW, 0);
                            }
                        }
                        return true;
                }
                return false;
            }
        });

        // 这里防止删除一条item后,ListView处于操作状态,直接还原
        if (holder.hSView.getScrollX() != 0) {
            holder.hSView.scrollTo(0, 0);
        }


        Map<String, String> map = list.get(position);

        VolleyImageLoader.setBitmap(context, map.get("pSquare_Image"), holder._img);
        holder._title.setText(map.get("pTitle"));
        holder._type.setText(map.get("pType"));
        holder._price.setText(map.get("pPrice"));

        holder.btOne.setOnClickListener(this);

        return convertView;
    }

    /**
     * ViewHolder 主要是避免了不断的view获取初始化
     */
    class ViewHolder {
        public HorizontalScrollView hSView;

        public View content;
        public View action;
        public Button btOne;

        public ImageView _img;
        public TextView _title, _type, _price;
    }

    @Override
    public void onClick(View v) {
        final int position = (Integer) v.getTag();
        Map<String, String> map = list.get(position);
        switch (v.getId()) {
            case R.id.myCollectItem_cancelLike:
                collect c = new collect();
                c.setObjectId(map.get("cObjId"));
                c.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            list.remove(position);
                            Toast.makeText(context, "已取消收藏", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "失败，请重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
