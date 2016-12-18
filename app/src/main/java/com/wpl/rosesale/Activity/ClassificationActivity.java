package com.wpl.rosesale.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.wpl.rosesale.R;

/**
 * Created by 培龙 on 2016/11/27.
 */

public class ClassificationActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classification);
        initUI();
    }

    private void initUI() {
        back = (LinearLayout) findViewById(R.id.classification_back);

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.classification_back:  //返回
                finish();
                break;
            default:
                break;
        }
    }
}
