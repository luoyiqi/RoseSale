package com.wpl.rosesale.Dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.wpl.rosesale.R;

/**
 * Created by 培龙 on 2016/9/8.
 */
public class WaitAnimDialog extends ProgressDialog {

    private AnimationDrawable mAnimation;
    private Context context;
    private String mLoadingTip;
    private int mResid;
    private ImageView loadingIv;
    private TextView loadingTv;

    /**
     * @param context    上下文
     * @param message    文字信息
     * @param drawableId 动画文件
     * @param styleId    style文件
     */
    public WaitAnimDialog(Context context, String message, int drawableId, int styleId) {
        super(context, styleId);
        this.context = context;
        this.mLoadingTip = message;
        this.mResid = drawableId;
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wait_anim);
        this.setCanceledOnTouchOutside(false);
        loadingIv = (ImageView) findViewById(R.id.loadingIv);
        loadingTv = (TextView) findViewById(R.id.loadingTv);
        loadingIv.setBackgroundResource(mResid);
        mAnimation = (AnimationDrawable) loadingIv.getBackground();
        loadingIv.post(new Runnable() {
            @Override
            public void run() {
                mAnimation.start();
            }
        });
        loadingTv.setText(mLoadingTip);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        mAnimation.start();
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void setProgressStyle(int style) {
        super.setProgressStyle(style);
    }
}
