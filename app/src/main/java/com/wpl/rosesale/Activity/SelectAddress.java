package com.wpl.rosesale.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.roger.catloadinglibrary.CatLoadingView;
import com.wpl.rosesale.Adapter.AM_ListViewAdapter;
import com.wpl.rosesale.MVP.Presenter.AddressManager_Presenter;
import com.wpl.rosesale.MVP.View.AddressManager_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.View.AddressManger_ListView;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/18.
 */

public class SelectAddress extends AppCompatActivity implements View.OnClickListener, AddressManager_View {
    private LinearLayout back, add, notAdd;
    private String userPhone;
    private AddressManager_Presenter presenter;
    private AddressManger_ListView listView;
    private AM_ListViewAdapter adapter;
    private Handler handler;
    private CatLoadingView catLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);

        initUI();
    }

    private void initUI() {
        catLoadingView = new CatLoadingView();

        SharedPreferences spf = getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        userPhone = (spf.getString("phoneNumber", "")).replaceAll(" ", "");

        listView = (AddressManger_ListView) findViewById(R.id.selectAddress_listView);

        back = (LinearLayout) findViewById(R.id.selectAddress_back);
        add = (LinearLayout) findViewById(R.id.selectAddress_add);
        notAdd = (LinearLayout) findViewById(R.id.selectAddress_notAdd);

        back.setOnClickListener(this);
        add.setOnClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
                if (list.size() == 0) {
                    notAdd.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                } else {
                    notAdd.setVisibility(View.GONE);

                    adapter = new AM_ListViewAdapter(SelectAddress.this, list, R.layout.listview_item_address_manager);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (list.size() != 0) {
                                Map<String, String> map = list.get(position);
                                //返回结果给上个Activity
                                Intent intent = new Intent();
                                intent.putExtra("objId", map.get("objId"));
                                intent.putExtra("uAM_name", map.get("uAM_name"));
                                intent.putExtra("uAM_phone", map.get("uAM_phone"));
                                intent.putExtra("uAM_post", map.get("uAM_post"));
                                intent.putExtra("uAM_province", map.get("uAM_province"));
                                intent.putExtra("uAM_city", map.get("uAM_city"));
                                intent.putExtra("uAM_county", map.get("uAM_county"));
                                intent.putExtra("uAM_detailAddress", map.get("uAM_detailAddress"));
                                setResult(2, intent);
                                SelectAddress.this.finish();
                            }
                        }
                    });
                }
                catLoadingView.dismiss();
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectAddress_back:   //返回
                finish();
                break;
            case R.id.selectAddress_add:    //添加地址
                startActivity(new Intent().setClass(SelectAddress.this, AddAddressActivity.class));
                break;
        }
    }

    @Override
    public void loadAM(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
        }
        Message msg = new Message();
        msg.obj = list;
        handler.sendMessage(msg);
    }

    @Override
    protected void onResume() {
        catLoadingView.show(getSupportFragmentManager(), "请稍后");
        presenter = new AddressManager_Presenter(this);
        presenter.loadAM(this, userPhone);
        super.onResume();
    }
}
