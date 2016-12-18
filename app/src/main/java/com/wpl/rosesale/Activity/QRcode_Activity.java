package com.wpl.rosesale.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpl.rosesale.Bean.payType;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.VolleyImageLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 培龙 on 2016/10/21.
 */

public class QRcode_Activity extends AppCompatActivity {
    private String type, price;
    private ImageView qrCode_img;
    private TextView qrCode_info, qrCode_price;
    private Handler handler;
    private LinearLayout qrCode_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initUI();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        price = bundle.getString("price");

        qrCode_img = (ImageView) findViewById(R.id.qrCode_img);
        qrCode_info = (TextView) findViewById(R.id.qrCode_info);
        qrCode_price = (TextView) findViewById(R.id.qrCode_price);
        qrCode_back = (LinearLayout) findViewById(R.id.qrCode_back);
        qrCode_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                VolleyImageLoader.setBitmap(QRcode_Activity.this, map.get("qr_code"), qrCode_img);
                qrCode_info.setText("" + map.get("info"));
                super.handleMessage(msg);
            }
        };
        qrCode_price.setText("" + price);
        initData();

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BmobQuery<payType> query = new BmobQuery<payType>();
                query.addWhereEqualTo("type", type);
                query.setLimit(1);
                query.findObjects(new FindListener<payType>() {
                    @Override
                    public void done(List<payType> list, BmobException e) {
                        if (e == null) {
                            for (payType pt : list) {
                                Map<String, String> map = new HashMap<String, String>();
                                map.put("qr_code", pt.getQr_code());
                                map.put("info", pt.getInfo());
                                Message msg = new Message();
                                msg.obj = map;
                                handler.sendMessage(msg);
                            }
                        } else {

                        }
                    }
                });

            }
        }).start();
    }
}
