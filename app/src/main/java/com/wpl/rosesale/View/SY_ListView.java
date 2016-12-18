package com.wpl.rosesale.View;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by 培龙 on 2016/10/1.
 */

public class SY_ListView extends ListView {
    public SY_ListView(Context context) {
        super(context);
    }

    public SY_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SY_ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
