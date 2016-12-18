package com.wpl.rosesale.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
 * Created by 培龙 on 2016/10/17.
 */

public class MeAddressManagerActivity extends AppCompatActivity implements View.OnClickListener, AddressManager_View {
    private LinearLayout back, add;
    private String userPhone;//当前用户的账号
    private AddressManger_ListView listView;
    private AddressManager_Presenter presenter;
    private LinearLayout meAddressManager_notAdd;
    private AM_ListViewAdapter adapter;
    private Handler handler;
    private CatLoadingView catLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_address_manager);

        initUI();
    }

    private void initUI() {
        catLoadingView = new CatLoadingView();

        SharedPreferences spf = getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        userPhone = (spf.getString("phoneNumber", "")).replaceAll(" ", "");

        meAddressManager_notAdd = (LinearLayout) findViewById(R.id.meAddressManager_notAdd);
        listView = (AddressManger_ListView) findViewById(R.id.meAddressManager_listView);
        back = (LinearLayout) findViewById(R.id.meAddressManager_back);
        add = (LinearLayout) findViewById(R.id.meAddressManager_add);
        back.setOnClickListener(this);
        add.setOnClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                final List<Map<String, String>> list = (List<Map<String, String>>) msg.obj;
                if (list.size() == 0) {
                    meAddressManager_notAdd.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    catLoadingView.dismiss();
                } else {
                    meAddressManager_notAdd.setVisibility(View.GONE);
                    adapter = new AM_ListViewAdapter(MeAddressManagerActivity.this, list, R.layout.listview_item_address_manager);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (list.size() != 0) {
                                Map<String, String> map = list.get(position);
                                Bundle bundle = new Bundle();
                                bundle.putString("objId", map.get("objId"));
                                bundle.putString("uAM_name", map.get("uAM_name"));
                                bundle.putString("uAM_phone", map.get("uAM_phone"));
                                bundle.putString("uAM_post", map.get("uAM_post"));
                                bundle.putString("uAM_province", map.get("uAM_province"));
                                bundle.putString("uAM_city", map.get("uAM_city"));
                                bundle.putString("uAM_county", map.get("uAM_county"));
                                bundle.putString("uAM_detailAddress", map.get("uAM_detailAddress"));
                                Intent intent = new Intent(MeAddressManagerActivity.this, UpAddressManager.class);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    });
                    catLoadingView.dismiss();
                }
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meAddressManager_back: //返回
                finish();
                break;
            case R.id.meAddressManager_add: //新增地址
                startActivity(new Intent().setClass(MeAddressManagerActivity.this, AddAddressActivity.class));
                MeAddressManagerActivity.this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void loadAM(List<Map<String, String>> list) {
        Log.e("loadAM", "回调loadAM");
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
        presenter = new AddressManager_Presenter(this);
        presenter.loadAM(this, userPhone);
        catLoadingView.show(getSupportFragmentManager(), "请稍后");

        super.onResume();
    }
}
