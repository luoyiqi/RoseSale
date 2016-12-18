package com.wpl.rosesale.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.MVP.Presenter.GetLocation_Presenter;
import com.wpl.rosesale.MVP.View.GetLocation_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.IsNetWorkStatus;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 培龙 on 2016/10/24.
 */

public class UpMyCityActivity extends AppCompatActivity implements View.OnClickListener, GetLocation_View {
    private LinearLayout back, save;
    private EditText inputCity;
    private Button getCity;
    private GetLocation_Presenter presenter;
    private Handler handler;
    private String objId, city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_city);

        initUI();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        objId = bundle.getString("objId");
        city = bundle.getString("city");

        back = (LinearLayout) findViewById(R.id.upCity_back);
        save = (LinearLayout) findViewById(R.id.upCity_save);
        inputCity = (EditText) findViewById(R.id.upCity_inputCity);
        getCity = (Button) findViewById(R.id.upCity_getCity);

        back.setOnClickListener(this);
        save.setOnClickListener(this);
        getCity.setOnClickListener(this);
        if (city != null) {
            inputCity.setText("" + city);
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                if (map.get("getStatus").equals("ok")) {
                    inputCity.setText(map.get("city"));
                } else {
                    Toast.makeText(UpMyCityActivity.this, "获取位置失败", Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upCity_back:  //返回
                finish();
                break;
            case R.id.upCity_save:  //保存
                if (IsNetWorkStatus.getActiveNetwork(UpMyCityActivity.this) != null) {
                    saveCity();
                } else {
                    Toast.makeText(this, "请检查您的手机网络", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.upCity_getCity:   //获取当前城市
                if (IsNetWorkStatus.getActiveNetwork(UpMyCityActivity.this) != null) {
                    presenter = new GetLocation_Presenter(this);
                    presenter.loadLocation(this);
                } else {
                    Toast.makeText(this, "请检查您的手机网络", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 修改远程城市数据
     */
    private void saveCity() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                usersTab ut = new usersTab();
                ut.setuCity(inputCity.getText().toString());
                ut.update(objId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(UpMyCityActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpMyCityActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
                            Log.e("修改城市", "失败" + e.getErrorCode() + "，" + e.getMessage());
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 获取到的当前位置信息
     *
     * @param list
     */
    @Override
    public void getLocation(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message msg = new Message();
            msg.obj = map;
            handler.sendMessage(msg);
        }
    }
}
