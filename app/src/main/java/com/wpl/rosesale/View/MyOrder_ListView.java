package com.wpl.rosesale.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 培龙 on 2016/10/21.
 */

public class MyOrder_ListView extends ListView {
    public MyOrder_ListView(Context context) {
        super(context);
    }

    public MyOrder_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyOrder_ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
