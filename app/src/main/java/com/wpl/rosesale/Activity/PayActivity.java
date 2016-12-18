package com.wpl.rosesale.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wpl.rosesale.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 培龙 on 2016/10/20.
 */

public class PayActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout back;
    private String objId, price, type, name;//商品ID、价格
    private TextView min, sec;  //倒计时分、秒
    private TextView priceAll;  //总金额
    private Button payBtn;  //支付按钮

    private TextView pay_price, pay_priceTotal;
    private TextView pay_pName, pay_pType;

    private int minLen = 14;
    private int recLen = 60;
    private Timer timer = new Timer();
    private Handler handlerCountDown;

    private RelativeLayout pay_zfb, pay_wx; //支付宝、微信
    private ImageView zfb_img, wx_img;

    private String payType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        initUI();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        objId = bundle.getString("objId");
        price = bundle.getString("price");
        type = bundle.getString("type");
        name = bundle.getString("name");

        back = (LinearLayout) findViewById(R.id.pay_back);
        min = (TextView) findViewById(R.id.pay_min);
        sec = (TextView) findViewById(R.id.pay_sec);
        payBtn = (Button) findViewById(R.id.pay_payBtn);
        priceAll = (TextView) findViewById(R.id.pay_priceAll);

        pay_zfb = (RelativeLayout) findViewById(R.id.pay_zfb);
        pay_wx = (RelativeLayout) findViewById(R.id.pay_wx);
        zfb_img = (ImageView) findViewById(R.id.pay_zfb_img);
        wx_img = (ImageView) findViewById(R.id.pay_wx_img);
        pay_price = (TextView) findViewById(R.id.pay_price);
        pay_priceTotal = (TextView) findViewById(R.id.pay_priceTotal);
        pay_pName = (TextView) findViewById(R.id.pay_pName);
        pay_pType = (TextView) findViewById(R.id.pay_pType);

        initCountDown();
        back.setOnClickListener(this);
        payBtn.setOnClickListener(this);
        pay_zfb.setOnClickListener(this);
        pay_wx.setOnClickListener(this);

        pay_price.setText("" + price);
        pay_priceTotal.setText("" + price);
        priceAll.setText("" + price);
        pay_pName.setText("" + name);
        pay_pType.setText("" + type);
        zfb_img.setImageResource(R.mipmap.abc_btn_check_to_on_mtrl_015);
        payType = "支付宝";

    }

    /**
     * 倒计时
     */
    private void initCountDown() {
        handlerCountDown = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        min.setText("" + minLen);
                        sec.setText("" + recLen);
                        if (recLen <= 0) {
                            if (minLen <= 0) {
                                timer.cancel();
                                PayActivity.this.finish();
                            } else {
                                recLen = 60;
                                minLen--;
                            }
                        }
                }
                super.handleMessage(msg);
            }
        };
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                recLen--;
                Message msg = new Message();
                msg.what = 1;
                handlerCountDown.sendMessage(msg);
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_back:     //返回
                showExitDialog();
                break;
            case R.id.pay_payBtn:   //去支付
                if (payType.toString().equals("微信")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "微信");
                    bundle.putString("price", price);
                    Intent intent = new Intent().setClass(PayActivity.this, QRcode_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                if (payType.toString().equals("支付宝")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", "支付宝");
                    bundle.putString("price", price);
                    Intent intent = new Intent().setClass(PayActivity.this, QRcode_Activity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;
            case R.id.pay_zfb:  //支付宝
                zfb_img.setImageResource(R.mipmap.abc_btn_check_to_on_mtrl_015);
                wx_img.setImageResource(R.mipmap.abc_btn_check_to_on_mtrl_000);
                payType = "支付宝";
                break;
            case R.id.pay_wx:   //微信
                zfb_img.setImageResource(R.mipmap.abc_btn_check_to_on_mtrl_000);
                wx_img.setImageResource(R.mipmap.abc_btn_check_to_on_mtrl_015);
                payType = "微信";
                break;
            default:
                break;
        }
    }


    /**
     * 监听返回键
     *
     * @param keyCode 键值
     * @param event   点击值
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 弹出确认返回对话框
     */
    private void showExitDialog() {
        AlertDialog.Builder builderBack = new AlertDialog.Builder(PayActivity.this);
        builderBack.setMessage("当前订单还未付款，确认退出？");
        builderBack.setTitle("提示");
        builderBack.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderBack.show();
    }
}
