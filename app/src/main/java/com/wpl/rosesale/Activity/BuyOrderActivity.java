package com.wpl.rosesale.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wpl.rosesale.Bean.orderTab;
import com.wpl.rosesale.Dialog.WaitAnimDialog;
import com.wpl.rosesale.MVP.Presenter.GetPD_Presenter;
import com.wpl.rosesale.MVP.View.GetPD_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.IsNetWorkStatus;
import com.wpl.rosesale.Utils.VolleyImageLoader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 培龙 on 2016/10/9.
 */

public class BuyOrderActivity extends AppCompatActivity implements View.OnClickListener, GetPD_View {

    private String objId, userPhone;
    private LinearLayout back, date, buyOrder_PD, commitBtn;
    private DatePickerDialog chooseDate;
    private Handler handlerPD, handlerSelectAddressResult;
    private ImageView pImage;
    private TextView date_text, pTitle, pType, pPrice, pPriceAll,
            addressAll_name, addressAll_phone, addressAll_address;
    private EditText buyOrder_buyInfo;
    private RelativeLayout addressAll;
    private WaitAnimDialog wad;//加载动画
    private Map<String, String> addressMap;
    private Map<String, String> pdMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyorder);
        initUI();
    }

    private void initUI() {
        wad = new WaitAnimDialog(this, "请稍后...", R.drawable.wait_anim, R.style.loading_dialog);
        SharedPreferences spf = getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        userPhone = (spf.getString("phoneNumber", "")).replaceAll(" ", "");
        back = (LinearLayout) findViewById(R.id.buyOrder_back);
        date = (LinearLayout) findViewById(R.id.bo_date);
        date_text = (TextView) findViewById(R.id.bo_date_text);
        buyOrder_buyInfo = (EditText) findViewById(R.id.buyOrder_buyInfo);
        buyOrder_PD = (LinearLayout) findViewById(R.id.buyOrder_PD);//鲜花详情
        commitBtn = (LinearLayout) findViewById(R.id.buyOrder_commitBtn);

        pImage = (ImageView) findViewById(R.id.buyOrder_pImage);
        pTitle = (TextView) findViewById(R.id.buyOrder_pTitle);
        pType = (TextView) findViewById(R.id.buyOrder_pType);
        pPrice = (TextView) findViewById(R.id.buyOrder_pPrice);
        pPriceAll = (TextView) findViewById(R.id.buyOrder_pPriceAll);

        //地址栏中的信息
        addressAll = (RelativeLayout) findViewById(R.id.buyOrder_addressAll);
        addressAll_name = (TextView) findViewById(R.id.buyOrder_addressAll_name);
        addressAll_phone = (TextView) findViewById(R.id.buyOrder_addressAll_phone);
        addressAll_address = (TextView) findViewById(R.id.buyOrder_addressAll_address);

        Bundle bundle = getIntent().getExtras();
        objId = bundle.getString("objId");

        back.setOnClickListener(this);
        date.setOnClickListener(this);
        commitBtn.setOnClickListener(this);
        buyOrder_PD.setOnClickListener(this);
        addressAll.setOnClickListener(this);

        initData();
    }

    /**
     * 获取数据
     */
    private void initData() {
        if (IsNetWorkStatus.getActiveNetwork(BuyOrderActivity.this) != null) {//有网
            GetPD_Presenter presenter = new GetPD_Presenter(objId, this);
            presenter.loadPD(BuyOrderActivity.this);
        } else {//无网络
            Toast.makeText(this, "请检查手机网络", Toast.LENGTH_SHORT).show();
        }

        /**
         * 更新商品信息UI
         */
        handlerPD = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Map<String, String> map = ((List<Map<String, String>>) msg.obj).get(0);
                pdMap = map;
                VolleyImageLoader.setBitmap(BuyOrderActivity.this, map.get("pSquare_Image"), pImage);
                pTitle.setText(map.get("pTitle"));
                pType.setText(map.get("pType"));
                pPrice.setText(map.get("pPrice"));
                pPriceAll.setText(map.get("pPrice"));
                super.handleMessage(msg);
            }
        };

        /**
         * 更新选择的地址UI
         */
        handlerSelectAddressResult = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                addressAll_name.setText(map.get("uAM_name"));
                addressAll_phone.setText(map.get("uAM_phone"));
                String address = map.get("uAM_province") + map.get("uAM_city")
                        + map.get("uAM_county") + map.get("uAM_detailAddress");
                addressAll_address.setText(address);
                super.handleMessage(msg);
            }
        };

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buyOrder_back:    //返回
                finish();
                break;
            case R.id.bo_date:  //日期
                ChooseDate();
                break;
            case R.id.buyOrder_PD:  //产品详情
                break;
            case R.id.buyOrder_addressAll:  //地址栏
                startActivityForResult(new Intent().setClass(BuyOrderActivity.this, SelectAddress.class), 1);//返回结果
                break;
            case R.id.buyOrder_commitBtn:   //提交订单
                if ((date_text.getText().toString().equals("请选择送达日期"))) {
                    Toast.makeText(this, "请选择送达日期", Toast.LENGTH_SHORT).show();
                } else {
                    if ((addressAll_name.getText().toString().equals("收货人"))) {
                        Toast.makeText(this, "请选择收货地址", Toast.LENGTH_SHORT).show();
                    } else {
                        AlertDialog.Builder builderBack = new AlertDialog.Builder(BuyOrderActivity.this);
                        builderBack.setMessage("订单一旦提交后，不可修改");
                        builderBack.setTitle("确认提交？");
                        builderBack.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                commitOrder();
                            }
                        });
                        builderBack.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderBack.show();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 提交订单
     */
    private void commitOrder() {
        commitBtn.setClickable(false);
        wad.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取服务器时间
                Bmob.getServerTime(new QueryListener<Long>() {
                    @Override
                    public void done(Long aLong, BmobException e) {
                        if (e == null) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            String times = format.format(new Date(aLong * 1000L));
                            orderTab ot = new orderTab();
                            ot.setoBelongTo(userPhone);
                            ot.setoIncome_name("" + addressMap.get("uAM_name"));
                            ot.setoIncome_phone("" + addressMap.get("uAM_phone"));
                            ot.setoIncome_post("" + addressMap.get("uAM_post"));
                            ot.setoIncome_province("" + addressMap.get("uAM_province"));
                            ot.setoIncome_city("" + addressMap.get("uAM_city"));
                            ot.setoIncome_county("" + addressMap.get("uAM_county"));
                            ot.setoIncome_address("" + addressMap.get("uAM_detailAddress"));
                            ot.setoGoods_objId(objId);
                            ot.setoGoods_number("1");
                            ot.setoGoods_price(pPriceAll.getText().toString());
                            ot.setoTime_commit(times);
                            ot.setOrder_status("待付款");
                            ot.setoBuyInfo("" + buyOrder_buyInfo.getText().toString());
                            ot.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        commitBtn.setClickable(true);
                                        wad.dismiss();
                                        Log.e("提交订单", "成功" + s);
                                        //此时跳转到支付页面，当前Activity finish掉
                                        pay(s, pPriceAll.getText().toString(),pdMap.get("pType"), pdMap.get("pTitle"));
                                    } else {
                                        commitBtn.setClickable(true);
                                        wad.dismiss();
                                        Log.e("提交订单", "失败" + e.getErrorCode() + "," + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            commitBtn.setClickable(true);
                            Toast.makeText(BuyOrderActivity.this, "订单提交失败，请重试", Toast.LENGTH_SHORT).show();
                            Log.e("提交订单", "获取服务器时间失败" + e.getErrorCode() + "，" + e.getMessage());
                            wad.dismiss();
                        }
                    }
                });
            }
        }).start();
    }

    /**
     * 支付
     */
    private void pay(String objId, String price, String type, String name) {
        Bundle bundle = new Bundle();
        bundle.putString("objId", objId);
        bundle.putString("price", price);
        bundle.putString("type", type);
        bundle.putString("name", name);
        Intent intent = new Intent(BuyOrderActivity.this, PayActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        BuyOrderActivity.this.finish();
    }

    /**
     * 日期选择器
     */
    private void ChooseDate() {
        final Calendar objDate = Calendar.getInstance();
        chooseDate = new DatePickerDialog(BuyOrderActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date_text.setText("送达日期：" + String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
            }
        }, objDate.get(Calendar.YEAR), objDate.get(Calendar.MONTH), objDate.get(Calendar.DAY_OF_MONTH));

        Date date = new Date();//当前时间
        long time = date.getTime();//当前日期的毫秒
        chooseDate.getDatePicker().setMinDate(time + 86400000 + 43200000);//设置开始日期为一天半后
        chooseDate.show();
    }

    /**
     * 获取到的远程商品信息数据
     *
     * @param list
     */
    @Override
    public void getPD(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Message msg = new Message();
            msg.obj = list;
            handlerPD.sendMessage(msg);
        }
    }

    /**
     * 接收Activity返回的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 2) {
            if (requestCode == 1) {
                //接收返回数据
                Map<String, String> map = new HashMap<>();
                map.put("objId", data.getStringExtra("objId"));
                map.put("uAM_name", data.getStringExtra("uAM_name"));
                map.put("uAM_phone", data.getStringExtra("uAM_phone"));
                map.put("uAM_post", data.getStringExtra("uAM_post"));
                map.put("uAM_province", data.getStringExtra("uAM_province"));
                map.put("uAM_city", data.getStringExtra("uAM_city"));
                map.put("uAM_county", data.getStringExtra("uAM_county"));
                map.put("uAM_detailAddress", data.getStringExtra("uAM_detailAddress"));
                addressMap = map;
                Message msg = new Message();
                msg.obj = map;
                handlerSelectAddressResult.sendMessage(msg);
            }
        }

    }

}
