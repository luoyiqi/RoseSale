package com.wpl.rosesale.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;
import com.wpl.rosesale.Adapter.MyCollect_Adapter;
import com.wpl.rosesale.MVP.Presenter.MyCollect_Presenter;
import com.wpl.rosesale.MVP.View.MyCollect_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.ACache;
import com.wpl.rosesale.Utils.IsNetWorkStatus;
import com.wpl.rosesale.View.MyCollect_ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的收藏
 * Created by 培龙 on 2016/11/1.
 */

public class MyCollectActivity extends AppCompatActivity implements View.OnClickListener, MyCollect_View {
    private String userPhone;
    private LinearLayout back, notAdd;
    private MyCollect_Presenter presenter;
    private MyCollect_ListView listView;
    private MyCollect_Adapter adapter;
    private ACache aCache;
    private Handler handler;
    private CatLoadingView catLoadingView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        initUI();
    }

    private void initData() {
        if (IsNetWorkStatus.getActiveNetwork(this) != null) {
            presenter = new MyCollect_Presenter(userPhone, this);
            presenter.loadCollect(this);
        } else {//无网络
            if (aCache.getAsObject("loadCollect") != null) {
                ArrayList arrayList = (ArrayList) aCache.getAsObject("loadCollect");
                Message msg = new Message();
                msg.obj = arrayList;
                handler.sendMessage(msg);
            }
            Toast.makeText(this, "请检查您的手机网络", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        aCache = ACache.get(this);

        catLoadingView = new CatLoadingView();

        SharedPreferences spf = getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        userPhone = (spf.getString("phoneNumber", "")).replaceAll(" ", "");

        back = (LinearLayout) findViewById(R.id.myCollect_back);
        notAdd = (LinearLayout) findViewById(R.id.myCollect_notAdd);
        listView = (MyCollect_ListView) findViewById(R.id.myCollect_listView);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
                if (list.size() == 0) {
                    notAdd.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    catLoadingView.dismiss();
                } else {
                    notAdd.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                    adapter = new MyCollect_Adapter(MyCollectActivity.this, dm.widthPixels, list, R.layout.listview_item_my_collect);
                    listView.setAdapter(adapter);
                    catLoadingView.dismiss();
                }
                super.handleMessage(msg);
            }
        };

        back.setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myCollect_back:   //返回
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取到的远程数据
     *
     * @param list
     */
    @Override
    public void loadCollect(List<Map<String, String>> list) {
        if (list instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) list;
            aCache.put("loadCollect", arrayList);
        }
        Message msg = new Message();
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    protected void onResume() {
        catLoadingView.show(getSupportFragmentManager(), "请稍后");
        initData();
        super.onResume();
    }
}
