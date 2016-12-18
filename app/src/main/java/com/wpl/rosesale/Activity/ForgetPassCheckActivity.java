package com.wpl.rosesale.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wpl.rosesale.MVP.Presenter.CheckSMSCode_Presenter;
import com.wpl.rosesale.MVP.Presenter.LoginSendCode_Presenter;
import com.wpl.rosesale.MVP.View.CheckSMSCode_View;
import com.wpl.rosesale.MVP.View.LoginSendCode_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.IsNetWorkStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/10/23.
 */

public class ForgetPassCheckActivity extends AppCompatActivity
        implements View.OnClickListener, LoginSendCode_View, CheckSMSCode_View {
    private LinearLayout back, next, reSend;
    private TextView reSendText, displayPhone;
    private EditText inputCode;
    private String phone;
    private LoginSendCode_Presenter sendCode_presenter;
    private CheckSMSCode_Presenter checkSMSCode_presenter;
    private Handler handlerSendCode, handlerCheckCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass_check);

        initUI();
        send_Code();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");


        back = (LinearLayout) findViewById(R.id.forgetPassCheck_back);
        next = (LinearLayout) findViewById(R.id.forgetPassCheck_next);
        reSend = (LinearLayout) findViewById(R.id.forgetPassCheck_reSend);  //重新发送
        displayPhone = (TextView) findViewById(R.id.forgetPassCheck_displayPhone);
        inputCode = (EditText) findViewById(R.id.forgetPassCheck_code); //输入的验证码
        reSendText = (TextView) findViewById(R.id.forgetPassCheck_reSendText);  //重新发送文字

        back.setOnClickListener(this);
        next.setOnClickListener(this);
        reSend.setOnClickListener(this);
        reSend.setEnabled(false);
        displayPhone.setText(phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgetPassCheck_back: //返回
                finish();
                break;
            case R.id.forgetPassCheck_next: //下一步
                check_Code();
                break;
            case R.id.forgetPassCheck_reSend:   //重新发送
                send_Code();
                break;
            default:
                break;
        }
    }

    /**
     * 发送短信验证码
     */
    private void send_Code() {
        if (IsNetWorkStatus.getActiveNetwork(this) != null) {
            sendCode_presenter = new LoginSendCode_Presenter(phone, this);
            sendCode_presenter.sendCode(this);
        }
        handlerSendCode = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //验证码发送成功
                Toast.makeText(ForgetPassCheckActivity.this, "请注意接收短信", Toast.LENGTH_SHORT).show();
                new CountDownTimer(60 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        reSendText.setText(millisUntilFinished / 1000 + "秒");
                    }

                    @Override
                    public void onFinish() {
                        reSendText.setText("重新获取");
                        reSend.setEnabled(true);
                    }
                }.start();
                super.handleMessage(msg);
            }
        };

    }

    /**
     * 验证短信验证码
     */
    private void check_Code() {
        checkSMSCode_presenter = new CheckSMSCode_Presenter(phone, inputCode.getText().toString(), this);
        checkSMSCode_presenter.checkCode(this);
        handlerCheckCode = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                if (map.get("CheckCodeStatus").equals("ok")) {
                    //验证成功
                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phone);
                    Intent intent = new Intent().setClass(ForgetPassCheckActivity.this, ResetPass.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    ForgetPassCheckActivity.this.finish();
                } else {
                    Toast.makeText(ForgetPassCheckActivity.this, "短信验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 接收发送短信验证码的结果
     *
     * @param list
     */
    @Override
    public void sendCode(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message msg = new Message();
            msg.obj = map.get("sendCodeStatus");
            handlerSendCode.sendMessage(msg);
        }
    }

    /**
     * 接收验证短信验证码的结果
     *
     * @param list
     */
    @Override
    public void checkCode(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }

            Message msg = new Message();
            msg.obj = list.get(0);
            handlerCheckCode.sendMessage(msg);
        }
    }
}
