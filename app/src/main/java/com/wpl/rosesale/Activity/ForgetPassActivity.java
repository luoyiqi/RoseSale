package com.wpl.rosesale.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.IsNetWorkStatus;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

/**
 * Created by 培龙 on 2016/10/23.
 */

public class ForgetPassActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout back, next;
    private EditText phoneEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        back = (LinearLayout) findViewById(R.id.forgetPass_back);
        next = (LinearLayout) findViewById(R.id.forgetPass_next);
        phoneEdit = (EditText) findViewById(R.id.forgetPass_edit);

        back.setOnClickListener(this);
        next.setOnClickListener(this);

        phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 1) {
                    int length = s.toString().length();
                    if (length == 3 || length == 8) {
                        phoneEdit.setText(s + " ");
                        phoneEdit.setSelection(phoneEdit.getText().toString().length());
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
            case R.id.forgetPass_back:  //返回
                finish();
                break;
            case R.id.forgetPass_next:  //下一步
                final String inputPhone = (phoneEdit.getText()).toString().replaceAll(" ", "");
                Log.e("phone", "" + inputPhone.length());
                if (inputPhone.length() == 11) {
                    if (IsNetWorkStatus.getActiveNetwork(this) != null) {

                        BmobQuery<usersTab> query = new BmobQuery<>();
                        query.addWhereEqualTo("uId", inputPhone);
                        query.count(usersTab.class, new CountListener() {
                            @Override
                            public void done(Integer integer, BmobException e) {
                                if (e == null) {
                                    Log.e("FrogetPassActivity", "查询用户数量：" + integer);
                                    if (integer == 1) {//用户存在
                                        next.setEnabled(false);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    Thread.sleep(1000);
                                                    next.setEnabled(true);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }).start();
                                        //跳转到下一步
                                        Bundle bundle = new Bundle();
                                        bundle.putString("phone", inputPhone);
                                        Intent intent = new Intent().setClass(ForgetPassActivity.this, ForgetPassCheckActivity.class);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                        ForgetPassActivity.this.finish();
                                    } else {
                                        Toast.makeText(ForgetPassActivity.this, "用户未注册，请用短信验证码登录以注册", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ForgetPassActivity.this, "出错啦，请重试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(ForgetPassActivity.this, "请检查您的手机网络", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgetPassActivity.this, "请输入13位正确的手机号码", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
