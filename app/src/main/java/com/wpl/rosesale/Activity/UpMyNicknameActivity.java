package com.wpl.rosesale.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 修改昵称
 * Created by 培龙 on 2016/9/21.
 */

public class UpMyNicknameActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout upNickname_back, upNickname_save;
    private EditText upNickname_editNickname;
    private String nickName, objId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_nickname);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        nickName = bundle.getString("nickName");
        objId = bundle.getString("objId");

        upNickname_back = (LinearLayout) findViewById(R.id.upNickname_back);
        upNickname_save = (LinearLayout) findViewById(R.id.upNickname_save);
        upNickname_editNickname = (EditText) findViewById(R.id.upNickname_editNickname);
        upNickname_editNickname.setText(nickName);

        upNickname_back.setOnClickListener(this);
        upNickname_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upNickname_back: //返回
                finish();
                break;
            case R.id.upNickname_save: //保存
                usersTab ut = new usersTab();
                ut.setuNickname(upNickname_editNickname.getText().toString());
                ut.update(objId, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(UpMyNicknameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpMyNicknameActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
                            Log.e("修改昵称", "失败：" + e.getErrorCode() + "," + e.getMessage());
                        }
                    }
                });

                break;
            default:
                break;
        }
    }
}
