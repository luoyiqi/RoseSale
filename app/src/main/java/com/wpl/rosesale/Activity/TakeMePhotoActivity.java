package com.wpl.rosesale.Activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.wpl.rosesale.R;

/**
 * Created by 培龙 on 2016/9/13.
 */

public class TakeMePhotoActivity extends Activity implements View.OnClickListener {

    private LinearLayout takeMePhoto_popLayout, takeMePhoto_pz, takeMePhoto_xc;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_me_photo);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        intent = getIntent();
        takeMePhoto_popLayout = (LinearLayout) findViewById(R.id.takeMePhoto_popLayout);
        takeMePhoto_pz = (LinearLayout) findViewById(R.id.takeMePhoto_pz);
        takeMePhoto_xc = (LinearLayout) findViewById(R.id.takeMePhoto_xc);

        //添加点击事件监听
        takeMePhoto_popLayout.setOnClickListener(this);
        takeMePhoto_pz.setOnClickListener(this);
        takeMePhoto_xc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.takeMePhoto_popLayout:    //窗口
                break;
            case R.id.takeMePhoto_pz:   //拍照
                /*
                 * 拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE
                 * 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                 */
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.takeMePhoto_xc:   //相册
                try {
                    /*
                     * 选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
                     * 有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                     */
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 2);
                } catch (ActivityNotFoundException e) {
                }
                break;
            default:
                break;
        }
    }

    // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        //选择完或者拍完照后会在这里处理，然后我们继续使用setResult返回Intent以便可以传递数据和调用
        if (data.getExtras() != null) {
            intent.putExtras(data.getExtras());
        }
        if (data.getData() != null) {
            intent.setData(data.getData());
        }
        setResult(1, intent);
        finish();
    }
}
