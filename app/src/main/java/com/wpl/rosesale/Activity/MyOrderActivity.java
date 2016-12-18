package com.wpl.rosesale.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wpl.rosesale.Adapter.FragmentAdapter;
import com.wpl.rosesale.MyOrderFragment.AllFragment;
import com.wpl.rosesale.MyOrderFragment.NotEvaluateFragment;
import com.wpl.rosesale.MyOrderFragment.NotPayFragment;
import com.wpl.rosesale.MyOrderFragment.NotReceiveFragment;
import com.wpl.rosesale.MyOrderFragment.NotSendFragment;
import com.wpl.rosesale.MyOrderFragment.OverFragment;
import com.wpl.rosesale.R;
import com.wpl.rosesale.SourceCode.NoPreloadViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 培龙 on 2016/10/21.
 */

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout back;
    private NoPreloadViewPager myOrder_viewPager;
    private LinearLayout all, notPay, notSend,
            notReceive, notEvaluate, over;
    private TextView all_text, notPay_text, notSend_text,
            notReceive_text, notEvaluate_text, over_text;
    private ImageView tabLine;

    private List<Fragment> list = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;
    private AllFragment allFragment;
    private NotPayFragment notPayFragment;
    private NotSendFragment notSendFragment;
    private NotReceiveFragment notReceiveFragment;
    private NotEvaluateFragment notEvaluateFragment;
    private OverFragment overFragment;
    private int currentIndex, screenWidth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        initUI();
        initTabLineWidth();
    }

    /**
     * 设置滑动条的宽度为屏幕的1/6(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabLine.getLayoutParams();
        lp.width = screenWidth / 6;
        tabLine.setLayoutParams(lp);
    }

    private void initUI() {
        back = (LinearLayout) findViewById(R.id.myOrder_back);
        myOrder_viewPager = (NoPreloadViewPager) findViewById(R.id.myOrder_viewPager);
        all = (LinearLayout) findViewById(R.id.myOrder_all);
        notPay = (LinearLayout) findViewById(R.id.myOrder_notPay);
        notSend = (LinearLayout) findViewById(R.id.myOrder_notSend);
        notReceive = (LinearLayout) findViewById(R.id.myOrder_notReceive);
        notEvaluate = (LinearLayout) findViewById(R.id.myOrder_notEvaluate);
        over = (LinearLayout) findViewById(R.id.myOrder_over);
        all_text = (TextView) findViewById(R.id.myOrder_all_text);
        notPay_text = (TextView) findViewById(R.id.myOrder_notPay_text);
        notSend_text = (TextView) findViewById(R.id.myOrder_notSend_text);
        notReceive_text = (TextView) findViewById(R.id.myOrder_notReceive_text);
        notEvaluate_text = (TextView) findViewById(R.id.myOrder_notEvaluate_text);
        over_text = (TextView) findViewById(R.id.myOrder_over_text);
        tabLine = (ImageView) findViewById(R.id.myOrder_tabLine);

        back.setOnClickListener(this);
        all.setOnClickListener(this);
        notPay.setOnClickListener(this);
        notSend.setOnClickListener(this);
        notReceive.setOnClickListener(this);
        notEvaluate.setOnClickListener(this);
        over.setOnClickListener(this);

        allFragment = new AllFragment();
        notPayFragment = new NotPayFragment();
        notSendFragment = new NotSendFragment();
        notReceiveFragment = new NotReceiveFragment();
        notEvaluateFragment = new NotEvaluateFragment();
        overFragment = new OverFragment();

        list.add(allFragment);
        list.add(notPayFragment);
        list.add(notSendFragment);
        list.add(notReceiveFragment);
        list.add(notEvaluateFragment);
        list.add(overFragment);

        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), list);
        myOrder_viewPager.setAdapter(fragmentAdapter);
        Bundle bundle = getIntent().getExtras();
        switch (bundle.getInt("index")) {
            case 0:
                currentIndex = 0;
                myOrder_viewPager.setCurrentItem(0);
                all_text.setTextColor(Color.parseColor("#333333"));
                break;
            case 1:
                currentIndex = 1;
                myOrder_viewPager.setCurrentItem(1);
                notPay_text.setTextColor(Color.parseColor("#333333"));
                break;
            case 2:
                currentIndex = 2;
                myOrder_viewPager.setCurrentItem(2);
                notSend_text.setTextColor(Color.parseColor("#333333"));
                break;
            case 3:
                currentIndex = 3;
                myOrder_viewPager.setCurrentItem(3);
                notReceive_text.setTextColor(Color.parseColor("#333333"));
                break;
            case 4:
                currentIndex = 4;
                myOrder_viewPager.setCurrentItem(4);
                notEvaluate_text.setTextColor(Color.parseColor("#333333"));
                break;
            default:
                break;
        }
        myOrder_viewPager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            /**
             *
             * @param position  当前页面，及点击滑动的页面
             * @param offset    当前页面偏移的百分比
             * @param offsetPixels  当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来设置下划线的左边距
                 * 滑动场景：
                 *      记6个页面
                 *      从左往右分别为0,1,2,3,4,5
                 *  0->1; 1->0;  1->2; 2->1;  2->3; 3->2;  3->4; 4->3;  4->5; 5->4;
                 */
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabLine.getLayoutParams();
                if (currentIndex == 0 && position == 0) {           // 0->1
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6) + currentIndex * (screenWidth / 6));
                } else if (currentIndex == 1 && position == 0) {    // 1->0
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 6) + currentIndex * (screenWidth / 6));
                } else if (currentIndex == 1 && position == 1) {    // 1->2
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6)) + currentIndex * (screenWidth / 6);
                } else if (currentIndex == 2 && position == 1) {    // 2->1
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 6) + currentIndex * (screenWidth / 6));
                } else if (currentIndex == 2 && position == 2) {    // 2->3
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6)) + currentIndex * (screenWidth / 6);
                } else if (currentIndex == 3 && position == 2) {    // 3->2
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 6) + currentIndex * (screenWidth / 6));
                } else if (currentIndex == 3 && position == 3) {    // 3->4
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6)) + currentIndex * (screenWidth / 6);
                } else if (currentIndex == 4 && position == 3) {    // 4->3
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 6) + currentIndex * (screenWidth / 6));
                } else if (currentIndex == 4 && position == 4) {    // 4->5
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 6)) + currentIndex * (screenWidth / 6);
                } else if (currentIndex == 5 && position == 4) {    // 5->4
                    lp.leftMargin = (int) (-(1 - offset) * (screenWidth * 1.0 / 6) + currentIndex * (screenWidth / 6));
                }
                tabLine.setLayoutParams(lp);

            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        all_text.setTextColor(Color.parseColor("#333333"));
                        break;
                    case 1:
                        notPay_text.setTextColor(Color.parseColor("#333333"));
                        break;
                    case 2:
                        notSend_text.setTextColor(Color.parseColor("#333333"));
                        break;
                    case 3:
                        notReceive_text.setTextColor(Color.parseColor("#333333"));
                        break;
                    case 4:
                        notEvaluate_text.setTextColor(Color.parseColor("#333333"));
                        break;
                    case 5:
                        over_text.setTextColor(Color.parseColor("#333333"));
                        break;
                    default:
                        break;
                }
                currentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 重置Tab字体颜色
     */
    private void resetTextView() {
        all_text.setTextColor(Color.parseColor("#AAAAAA"));
        notPay_text.setTextColor(Color.parseColor("#AAAAAA"));
        notSend_text.setTextColor(Color.parseColor("#AAAAAA"));
        notReceive_text.setTextColor(Color.parseColor("#AAAAAA"));
        notEvaluate_text.setTextColor(Color.parseColor("#AAAAAA"));
        over_text.setTextColor(Color.parseColor("#AAAAAA"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myOrder_back: //返回
                finish();
                break;
            case R.id.myOrder_all:  //全部
                myOrder_viewPager.setCurrentItem(0);
                break;
            case R.id.myOrder_notPay:   //待付款
                myOrder_viewPager.setCurrentItem(1);
                break;
            case R.id.myOrder_notSend:  //待发货
                myOrder_viewPager.setCurrentItem(2);
                break;
            case R.id.myOrder_notReceive:   //待收货
                myOrder_viewPager.setCurrentItem(3);
                break;
            case R.id.myOrder_notEvaluate:  //待评价
                myOrder_viewPager.setCurrentItem(4);
                break;
            case R.id.myOrder_over: //已完成
                myOrder_viewPager.setCurrentItem(5);
            default:
                break;
        }
    }
}
