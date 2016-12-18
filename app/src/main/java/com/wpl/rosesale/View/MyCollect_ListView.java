package com.wpl.rosesale.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 培龙 on 2016/11/1.
 */

public class MyCollect_ListView extends ListView {

    public MyCollect_ListView(Context context) {
        super(context);
    }

    public MyCollect_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCollect_ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
