package com.wpl.rosesale.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wpl.rosesale.Bean.addressManager;
import com.wpl.rosesale.Dialog.WaitAnimDialog;
import com.wpl.rosesale.R;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 培龙 on 2016/10/17.
 */

public class AddAddressActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout back, save;
    private EditText name, phone, post, province, city, county, detailAddress;
    private String phoneNumber;
    private WaitAnimDialog wad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        initUI();
    }

    private void initUI() {
        SharedPreferences sf = getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        phoneNumber = (sf.getString("phoneNumber", "")).replaceAll(" ", "");

        wad = new WaitAnimDialog(this, "请稍后...", R.drawable.wait_anim, R.style.loading_dialog);

        name = (EditText) findViewById(R.id.addAddress_name);
        phone = (EditText) findViewById(R.id.addAddress_phone);
        post = (EditText) findViewById(R.id.addAddress_post);
        province = (EditText) findViewById(R.id.addAddress_province);
        city = (EditText) findViewById(R.id.addAddress_city);
        county = (EditText) findViewById(R.id.addAddress_county);
        detailAddress = (EditText) findViewById(R.id.addAddress_detailAddress);

        back = (LinearLayout) findViewById(R.id.addAddress_back);
        save = (LinearLayout) findViewById(R.id.addAddress_save);

        back.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAddress_back:  //返回
                showExitDialog();
                break;
            case R.id.addAddress_save:  //保存
                if (name.getText().length() != 0 && phone.getText().length() != 0
                        && province.getText().length() != 0 && city.getText().length() != 0
                        && county.getText().length() != 0 && detailAddress.getText().length() != 0) {
                    save.setClickable(false);
                    wad.show();
                    saveData();
                } else {
                    Toast.makeText(this, "请填写完整", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void saveData() {
        addressManager am = new addressManager();
        am.setuBelongTo(phoneNumber);
        am.setuAM_name(name.getText().toString());
        am.setuAM_phone(phone.getText().toString());
        am.setuAM_post(post.getText().toString());
        am.setuAM_province(province.getText().toString());
        am.setuAM_city(city.getText().toString());
        am.setuAM_county(county.getText().toString());
        am.setuAM_detailAddress(detailAddress.getText().toString());
        am.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    wad.dismiss();
                    Toast.makeText(AddAddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent().setClass(AddAddressActivity.this, MeAddressManagerActivity.class));
                    AddAddressActivity.this.finish();
                } else {
                    wad.dismiss();
                    Toast.makeText(AddAddressActivity.this, "保存失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.e("新增地址", "失败：" + e.getErrorCode() + "," + e.getMessage());
                }
            }
        });
    }

    /**
     * 弹出确认退出对话框
     */
    private void showExitDialog() {
        AlertDialog.Builder builderBack = new AlertDialog.Builder(AddAddressActivity.this);
        builderBack.setMessage("确认放弃编辑吗？");
        builderBack.setTitle("提示");
        builderBack.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent().setClass(AddAddressActivity.this, MeAddressManagerActivity.class));
                AddAddressActivity.this.finish();
                dialog.dismiss();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
