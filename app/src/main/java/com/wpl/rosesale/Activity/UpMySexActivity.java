package com.wpl.rosesale.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 培龙 on 2016/9/21.
 */

public class UpMySexActivity extends Activity implements View.OnClickListener {

    private LinearLayout upSex_popLayout, upSex_man, upSex_woman;
    private String objId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_sex);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        Bundle bundle = getIntent().getExtras();
        objId = bundle.getString("objId");

        upSex_popLayout = (LinearLayout) findViewById(R.id.upSex_popLayout);
        upSex_man = (LinearLayout) findViewById(R.id.upSex_man);
        upSex_woman = (LinearLayout) findViewById(R.id.upSex_woman);

        upSex_popLayout.setOnClickListener(this);
        upSex_man.setOnClickListener(this);
        upSex_woman.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upSex_popLayout: //窗口
                break;
            case R.id.upSex_man: //男
                upDataMyCity("男");
                break;
            case R.id.upSex_woman: //女
                upDataMyCity("女");
                break;
            default:
                break;
        }
    }

    private void upDataMyCity(String s) {
        usersTab ut = new usersTab();
        ut.setuSex(s);
        ut.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    finish();
                } else {
                    Toast.makeText(UpMySexActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.e("修改昵称", "失败：" + e.getErrorCode() + "," + e.getMessage());
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
