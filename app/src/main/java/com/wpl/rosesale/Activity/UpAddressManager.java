package com.wpl.rosesale.Activity;

import android.content.DialogInterface;
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
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 培龙 on 2016/10/17.
 */

public class UpAddressManager extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout back, delete, save;
    private EditText name, phone, post, province, city, county, detailAddress;
    private String objId;
    private WaitAnimDialog wad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_address_manager);

        initUI();
    }

    private void initUI() {
        Bundle bundle = getIntent().getExtras();

        wad = new WaitAnimDialog(this, "请稍后...", R.drawable.wait_anim, R.style.loading_dialog);

        back = (LinearLayout) findViewById(R.id.UpAddressManager_back);
        delete = (LinearLayout) findViewById(R.id.UpAddressManager_delete);
        save = (LinearLayout) findViewById(R.id.UpAddressManager_save);

        name = (EditText) findViewById(R.id.UpAddressManager_name);
        phone = (EditText) findViewById(R.id.UpAddressManager_phone);
        post = (EditText) findViewById(R.id.UpAddressManager_post);
        province = (EditText) findViewById(R.id.UpAddressManager_province);
        city = (EditText) findViewById(R.id.UpAddressManager_city);
        county = (EditText) findViewById(R.id.UpAddressManager_county);
        detailAddress = (EditText) findViewById(R.id.UpAddressManager_detailAddress);

        objId = bundle.getString("objId");
        if (bundle.getString("uAM_name") != null) {
            name.setText(bundle.getString("uAM_name"));
        }
        if (bundle.getString("uAM_phone") != null) {
            phone.setText(bundle.getString("uAM_phone"));
        }
        if (bundle.getString("uAM_post") != null) {
            post.setText(bundle.getString("uAM_post"));
        }
        if (bundle.getString("uAM_province") != null) {
            province.setText(bundle.getString("uAM_province"));
        }
        if (bundle.getString("uAM_city") != null) {
            city.setText(bundle.getString("uAM_city"));
        }
        if (bundle.getString("uAM_county") != null) {
            county.setText(bundle.getString("uAM_county"));
        }
        if (bundle.getString("uAM_detailAddress") != null) {
            detailAddress.setText(bundle.getString("uAM_detailAddress"));
        }


        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.UpAddressManager_back:    //返回
                showExitDialog();
                break;
            case R.id.UpAddressManager_delete:  //删除地址
                showDeleteDialog();
                break;
            case R.id.UpAddressManager_save:    //保存
                if (name.getText().length() != 0 && phone.getText().length() != 0
                        && province.getText().length() != 0 && city.getText().length() != 0
                        && county.getText().length() != 0 && detailAddress.getText().length() != 0) {
                    wad.show();
                    saveData();
                } else {
                    Toast.makeText(this, "请填写完整", Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * 删除远程数据
     */
    private void deleteData() {
        addressManager am = new addressManager();
        am.setObjectId(objId);
        am.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    wad.dismiss();
                    Toast.makeText(UpAddressManager.this, "删除成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    wad.dismiss();
                    Toast.makeText(UpAddressManager.this, "删除失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.e("删除地址", "失败：" + e.getErrorCode() + "," + e.getMessage());
                }
            }
        });
    }

    /**
     * 保存数据到远程
     */
    private void saveData() {
        addressManager am = new addressManager();
        am.setuAM_name(name.getText().toString());
        am.setuAM_phone(phone.getText().toString());
        am.setuAM_post(post.getText().toString());
        am.setuAM_province(province.getText().toString());
        am.setuAM_city(city.getText().toString());
        am.setuAM_county(county.getText().toString());
        am.setuAM_detailAddress(detailAddress.getText().toString());
        am.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    wad.dismiss();
                    Toast.makeText(UpAddressManager.this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    wad.dismiss();
                    Toast.makeText(UpAddressManager.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
                    Log.e("修改地址", "失败：" + e.getErrorCode() + "," + e.getMessage());
                }
            }
        });
    }

    /**
     * 监听返回键
     *
     * @param keyCode 返回值
     * @param event   点击值
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 弹出退出对话框
     */
    private void showExitDialog() {
        AlertDialog.Builder builderBack = new AlertDialog.Builder(UpAddressManager.this);
        builderBack.setMessage("确认放弃编辑吗？");
        builderBack.setTitle("提示");
        builderBack.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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

    /**
     * 弹出确认删除对话框
     */
    private void showDeleteDialog() {
        AlertDialog.Builder builderDelete = new AlertDialog.Builder(UpAddressManager.this);
        builderDelete.setMessage("是否删除地址");
        builderDelete.setTitle("提示");
        builderDelete.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                wad.show();
                deleteData();
            }
        });
        builderDelete.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderDelete.show();
    }

}
