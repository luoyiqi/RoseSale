package com.wpl.rosesale.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wpl.rosesale.Bean.collect;
import com.wpl.rosesale.Dialog.LoginDialog;
import com.wpl.rosesale.MVP.Presenter.GetPD_Presenter;
import com.wpl.rosesale.MVP.View.GetPD_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.ACache;
import com.wpl.rosesale.Utils.IsNetWorkStatus;
import com.wpl.rosesale.Utils.VolleyImageLoader;
import com.wpl.rosesale.View.MyScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 培龙 on 2016/10/5.
 */

public class ProductDetailsActicity extends AppCompatActivity implements View.OnClickListener, GetPD_View {

    private String objId, pTitle, userPhone, cId;    //产品ID，产品标题，用户账号, 收藏ID
    private RelativeLayout productDetails_title;
    private MyScrollView productDetails_scrollView;
    private LinearLayout productDetails_back, kf, sc, buy;
    private TextView title, englishTitle, info, price;
    private ImageView productDetails_pSquareImage, sc_img;
    private ImageView pImage1, pImage2, pImage3, pImage4, pImage5,
            pImage6, pImage7, pImage8, pImage9, pImage10;
    private GetPD_Presenter getPD_presenter;
    private Handler handlerLoadPD;
    private List<Map<String, String>> list;
    private ACache aCache;
    private Boolean isC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initUI();
        initData();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        isC = false;
        SharedPreferences spf = getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        userPhone = (spf.getString("phoneNumber", "")).replaceAll(" ", "");

        Bundle bundle = getIntent().getExtras();
        objId = bundle.getString("objId");
        pTitle = bundle.getString("pTitle");
        aCache = ACache.get(this);

        productDetails_title = (RelativeLayout) findViewById(R.id.productDetails_title);
        productDetails_scrollView = (MyScrollView) findViewById(R.id.productDetails_scrollView);
        productDetails_back = (LinearLayout) findViewById(R.id.productDetails_back);
        title = (TextView) findViewById(R.id.productDetails_pTitle);
        englishTitle = (TextView) findViewById(R.id.productDetails_pEnglishTitle);
        info = (TextView) findViewById(R.id.productDetails_pInfo);
        price = (TextView) findViewById(R.id.productDetails_pPrice);
        productDetails_pSquareImage = (ImageView) findViewById(R.id.productDetails_pSquareImage);
        kf = (LinearLayout) findViewById(R.id.productDetails_kf);
        sc = (LinearLayout) findViewById(R.id.productDetails_sc);
        buy = (LinearLayout) findViewById(R.id.productDetails_buy);
        sc_img = (ImageView) findViewById(R.id.productDetails_sc_img);
        pImage1 = (ImageView) findViewById(R.id.productDetails_pDetailImage_1);
        pImage2 = (ImageView) findViewById(R.id.productDetails_pDetailImage_2);
        pImage3 = (ImageView) findViewById(R.id.productDetails_pDetailImage_3);
        pImage4 = (ImageView) findViewById(R.id.productDetails_pDetailImage_4);
        pImage5 = (ImageView) findViewById(R.id.productDetails_pDetailImage_5);
        pImage6 = (ImageView) findViewById(R.id.productDetails_pDetailImage_6);
        pImage7 = (ImageView) findViewById(R.id.productDetails_pDetailImage_7);
        pImage8 = (ImageView) findViewById(R.id.productDetails_pDetailImage_8);
        pImage9 = (ImageView) findViewById(R.id.productDetails_pDetailImage_9);
        pImage10 = (ImageView) findViewById(R.id.productDetails_pDetailImage_10);


        sc_img.setImageResource(R.mipmap.product_detail_favourite_like_white_24dp);

        productDetails_back.setOnClickListener(this);
        kf.setOnClickListener(this);
        sc.setOnClickListener(this);
        buy.setOnClickListener(this);

        /**
         * ScrollView滑动渐渐显示Title
         */
        productDetails_title.setAlpha(0);
        productDetails_scrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(View scrollView, int x, int y, int oldx, int oldy) {
                if (y < 800) {
                    float alpha = ((float) y) / 800;
                    productDetails_title.setAlpha(alpha);
                }
            }
        });

        /**
         * 更新UI层数据
         */
        handlerLoadPD = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                List<Map<String, String>> list_getPD = (List<Map<String, String>>) msg.obj;
                Map<String, String> pdResult = list_getPD.get(0);

                title.setText(pdResult.get("pTitle"));
                englishTitle.setText(pdResult.get("pEnglishTitle"));
                info.setText(pdResult.get("pInfo"));
                price.setText(pdResult.get("pPrice"));
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pSquare_Image"), productDetails_pSquareImage);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_1"), pImage1);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_2"), pImage2);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_3"), pImage3);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_4"), pImage4);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_5"), pImage5);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_6"), pImage6);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_7"), pImage7);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_8"), pImage8);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_9"), pImage9);
                VolleyImageLoader.setBitmap(ProductDetailsActicity.this, pdResult.get("pDetailImage_10"), pImage10);
                super.handleMessage(msg);
            }
        };
    }


    /**
     * 初始化数据
     */
    private void initData() {
        if (IsNetWorkStatus.getActiveNetwork(this) != null) {//有网络
            getPD_presenter = new GetPD_Presenter(objId, this);
            getPD_presenter.loadPD(this);
        } else {//无网络
            Toast.makeText(this, "请检查您的手机网络", Toast.LENGTH_SHORT).show();
            if (aCache.getAsObject("loadPD") != null) {
                ArrayList arrayList = (ArrayList) aCache.getAsObject("loadPD");
                Message message = new Message();
                message.obj = arrayList;
                handlerLoadPD.sendMessage(message);
            }
        }
    }

    /**
     * 查询是否收藏
     */
    private void isCollect() {
        BmobQuery<collect> query = new BmobQuery<>();
        query.addWhereEqualTo("uBlongToUserPhone", userPhone);
        query.addWhereEqualTo("uBlongToProductId", objId);
        query.findObjects(new FindListener<collect>() {
            @Override
            public void done(List<collect> list, BmobException e) {
                if (e == null) {
                    if (list.size() > 0) {//已收藏
                        isC = true;
                        sc_img.setImageResource(R.mipmap.product_detail_favourite_liked_24dp);
                        for (collect c : list) {
                            cId = c.getObjectId();
                        }
                    } else {//未收藏
                        isC = false;
                        sc_img.setImageResource(R.mipmap.product_detail_favourite_like_white_24dp);
                    }
                }
            }
        });
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.productDetails_back: //返回
                finish();
                break;
            case R.id.productDetails_kf: //客服
                break;
            case R.id.productDetails_sc: //收藏
                SharedPreferences spf = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                if (spf.getBoolean("login_status", false)) {
                    updateCollectStatus();
                } else {
                    Toast.makeText(this, "请先登录账号", Toast.LENGTH_SHORT).show();
                    (new LoginDialog(this, R.style.loading_dialog)).show();
                }
                break;
            case R.id.productDetails_buy: //立即购买
                goBuy();
                break;
        }
    }

    /**
     * 更新收藏状态
     */
    private void updateCollectStatus() {
        sc.setEnabled(false);
        if (isC) {//已收藏--取消收藏
            collect c = new collect();
            c.setObjectId(cId);
            c.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {//取消收藏成功
                        sc.setEnabled(true);
                        isC = false;
                        sc_img.setImageResource(R.mipmap.product_detail_favourite_like_white_24dp);
                        Toast.makeText(ProductDetailsActicity.this, "已取消收藏", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("ProductDetailsActivity", "取消收藏失败：" + e.getErrorCode() + "," + e.getMessage());
                        Toast.makeText(ProductDetailsActicity.this, "失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {//未收藏--去收藏
            collect c = new collect();
            c.setuBlongToProductId(objId);
            c.setuBlongToUserPhone(userPhone);
            c.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {//收藏成功
                        sc.setEnabled(true);
                        isC = true;
                        sc_img.setImageResource(R.mipmap.product_detail_favourite_liked_24dp);
                        cId = s;
                    } else {
                        Log.e("ProductDetailsActivity", "收藏失败：" + e.getErrorCode() + "," + e.getMessage());
                        Toast.makeText(ProductDetailsActicity.this, "失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 去购买
     */
    private void goBuy() {
        SharedPreferences spf = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        if (spf.getBoolean("login_status", false) == false) {//未登录
            Toast.makeText(this, "请先登录账号", Toast.LENGTH_SHORT).show();
            (new LoginDialog(this, R.style.loading_dialog)).show();
        } else {//已登录状态
            if (IsNetWorkStatus.getActiveNetwork(ProductDetailsActicity.this) != null) {
                Map<String, String> map = list.get(0);
                Bundle bundle = new Bundle();
                bundle.putString("objId", map.get("objId"));
                bundle.putString("pTitle", map.get("pTitle"));
                Intent intent = new Intent(ProductDetailsActicity.this, BuyOrderActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Toast.makeText(this, "请检查您的手机网络", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 接受到的远程数据
     *
     * @param list 产品信息
     */
    @Override
    public void getPD(List<Map<String, String>> list) {
        this.list = list;
        if (list instanceof ArrayList) {
            ArrayList arrayList = (ArrayList) list;
            aCache.put("getPD", arrayList);
        }

        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message msg = new Message();
            msg.obj = list;
            handlerLoadPD.sendMessage(msg);
        }
    }

    @Override
    protected void onResume() {
        SharedPreferences spf = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        if (spf.getBoolean("login_status", false) == false) {
            sc_img.setImageResource(R.mipmap.product_detail_favourite_like_white_24dp);
        } else {
            isCollect();
        }
        super.onResume();
    }
}
