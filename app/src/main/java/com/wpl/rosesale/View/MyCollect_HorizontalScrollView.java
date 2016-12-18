package com.wpl.rosesale.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * Created by 培龙 on 2016/11/1.
 */

public class MyCollect_HorizontalScrollView extends HorizontalScrollView {

    public MyCollect_HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 滑动事件(让滑动的速度变为原来的1/2)
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / 2);
    }

}
