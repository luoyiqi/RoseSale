package com.wpl.rosesale.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.Dialog.WaitAnimDialog;
import com.wpl.rosesale.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 培龙 on 2016/10/23.
 */

public class ResetPass extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout back, save;
    private EditText newPass, newPassOK;
    private String phone;
    private WaitAnimDialog wad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        initUI();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        phone = bundle.getString("phone");

        wad = new WaitAnimDialog(ResetPass.this, "请稍后...", R.drawable.wait_anim, R.style.loading_dialog);

        back = (LinearLayout) findViewById(R.id.resetPass_back);
        save = (LinearLayout) findViewById(R.id.resetPass_save);

        newPass = (EditText) findViewById(R.id.resetPass_newPass);
        newPassOK = (EditText) findViewById(R.id.resetPass_newPassOK);

        back.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.resetPass_back:   //返回
                finish();
                break;
            case R.id.resetPass_save:   //保存
                resetPassword();
                break;
        }
    }

    /**
     * 重置密码
     */
    private void resetPassword() {
        wad.show();
        String newPassword = newPass.getText().toString();
        final String newPasswordOK = newPassOK.getText().toString();
        if (newPassword.length() < 6) {
            Toast.makeText(this, "新密码不能少于6位数", Toast.LENGTH_SHORT).show();
            wad.dismiss();
        } else {
            if (newPasswordOK.equals(newPassword)) {
                //保存密码到服务器
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        BmobQuery<usersTab> query = new BmobQuery<usersTab>();
                        query.addWhereEqualTo("uId", phone);
                        query.setLimit(1);
                        query.findObjects(new FindListener<usersTab>() {
                            @Override
                            public void done(List<usersTab> list, BmobException e) {
                                if (e == null) {
                                    for (usersTab ut : list) {
                                        usersTab uT = new usersTab();
                                        uT.setuPassword(newPasswordOK);
                                        uT.update(ut.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    wad.dismiss();
                                                    Toast.makeText(ResetPass.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                } else {
                                                    wad.dismiss();
                                                    Toast.makeText(ResetPass.this, "密码修改失败，请重试", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                } else {
                                    wad.dismiss();
                                    Toast.makeText(ResetPass.this, "密码修改失败，请重试", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();

            } else {
                wad.dismiss();
                Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
