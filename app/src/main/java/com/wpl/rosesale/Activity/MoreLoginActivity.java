package com.wpl.rosesale.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;
import com.wpl.rosesale.MVP.Presenter.Login_Presenter;
import com.wpl.rosesale.MVP.View.Login_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.IsNetWorkStatus;

import java.util.List;
import java.util.Map;

/**
 * Created by 培龙 on 2016/9/12.
 */
public class MoreLoginActivity extends AppCompatActivity implements View.OnClickListener, Login_View {
    private LinearLayout back, login;
    private EditText inputPhone, inputPass;
    private TextView forgetPass;
    private Login_Presenter login_presenter;
    private Handler loginHandler;
    private CatLoadingView catLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morelogin);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        catLoadingView = new CatLoadingView();
        back = (LinearLayout) findViewById(R.id.moreLogin_back);
        login = (LinearLayout) findViewById(R.id.moreLogin_loginBtn);
        forgetPass = (TextView) findViewById(R.id.moreLogin_forgetPass);
        inputPhone = (EditText) findViewById(R.id.moreLogin_inputPhone);
        inputPass = (EditText) findViewById(R.id.moreLogin_inputPass);

        back.setOnClickListener(this);
        login.setOnClickListener(this);
        forgetPass.setOnClickListener(this);

        initView();
    }

    private void initView() {
        inputPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /**
                 * 手机号码自动添加空格
                 */
                if (count == 1) {
                    int length = s.toString().length();
                    if (length == 3 || length == 8) {
                        inputPhone.setText(s + " ");
                        inputPhone.setSelection(inputPhone.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moreLogin_back:   //返回
                finish();
                break;
            case R.id.moreLogin_loginBtn:  //登录
                onLogin();
                break;
            case R.id.moreLogin_forgetPass: //忘记密码
                startActivity(new Intent().setClass(MoreLoginActivity.this, ForgetPassActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     */
    private void onLogin() {
        catLoadingView.show(getSupportFragmentManager(), "正在登陆");
        if ((inputPhone.getText().toString()).replaceAll(" ", "").length() == 11) {
            //完整的手机号码
            if (inputPass.getText().toString().equals("")) {
                catLoadingView.dismiss();
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                if (IsNetWorkStatus.getActiveNetwork(MoreLoginActivity.this) != null) {
                    login_presenter = new Login_Presenter(MoreLoginActivity.this, (inputPhone.getText().toString()).replaceAll(" ", ""), inputPass.getText().toString());
                    login_presenter.login(MoreLoginActivity.this);
                    loginHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            Map<String, String> loginInfo = (Map<String, String>) msg.obj;
                            if (loginInfo.get("registStatus").equals("yes")) {
                                if (loginInfo.get("loginStatus").equals("yes")) {//登陆成功
                                    SharedPreferences spf = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = spf.edit();
                                    editor.putBoolean("login_status", true);
                                    editor.commit();

                                    SharedPreferences sp = getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor edito = sp.edit();
                                    edito.putString("phoneNumber", inputPhone.getText().toString());
                                    edito.commit();

                                    catLoadingView.dismiss();
                                    finish();
                                    Toast.makeText(MoreLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    //密码错误
                                    catLoadingView.dismiss();
                                    Toast.makeText(MoreLoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                //未注册
                                Toast.makeText(MoreLoginActivity.this, "您还未注册", Toast.LENGTH_SHORT).show();
                                catLoadingView.dismiss();
                            }
                            super.handleMessage(msg);
                        }
                    };
                } else {
                    Toast.makeText(this, "请检查您的手机网路", Toast.LENGTH_SHORT).show();
                    catLoadingView.dismiss();
                }
            }
        } else {
            catLoadingView.dismiss();
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void login(List<Map<String, String>> list) {
        if (list.size() != 0) {
            Map<String, String> map = list.get(0);
            Message msg = new Message();
            msg.obj = map;
            loginHandler.sendMessage(msg);
        }
    }
}
