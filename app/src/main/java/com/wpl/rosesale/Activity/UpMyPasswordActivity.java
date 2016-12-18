package com.wpl.rosesale.Activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wpl.rosesale.Dialog.WaitAnimDialog;
import com.wpl.rosesale.MVP.Presenter.UpDateMyData_Presenter;
import com.wpl.rosesale.MVP.View.UpDateMyData_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.IsNetWorkStatus;

import java.util.List;
import java.util.Map;

/**
 * 更新密码
 * Created by 培龙 on 2016/9/15.
 */

public class UpMyPasswordActivity extends AppCompatActivity implements View.OnClickListener, UpDateMyData_View {

    private LinearLayout back, save;
    private EditText upPass_inOldPass, upPass_inNewPass, upPass_OkPass;
    private String objId;
    private Dialog wad;
    private UpDateMyData_Presenter upDateMyData_presenter;
    private Handler toastUpPassResult = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if ((msg.obj).toString().equals("密码修改成功")) {
                dismissAnim.sendMessage(new Message());
                finish();
            }
            dismissAnim.sendMessage(new Message());
            Toast.makeText(UpMyPasswordActivity.this, ""+msg.obj, Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };

    private Handler dismissAnim = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            wad.dismiss();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_password);

        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        wad = new WaitAnimDialog(UpMyPasswordActivity.this, "请稍后...", R.drawable.wait_anim, R.style.loading_dialog);
        objId = getIntent().getExtras().getString("objId");
        back = (LinearLayout) findViewById(R.id.upPass_back);
        save = (LinearLayout) findViewById(R.id.upPass_save);

        upPass_inOldPass = (EditText) findViewById(R.id.upPass_inOldPass);
        upPass_inNewPass = (EditText) findViewById(R.id.upPass_inNewPass);
        upPass_OkPass = (EditText) findViewById(R.id.upPass_OkPass);

        back.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upPass_back: // 返回
                finish();
                break;
            case R.id.upPass_save: // 保存
                wad.show();
                if (IsNetWorkStatus.getActiveNetwork(this) == null) {
                    wad.dismiss();
                    Toast.makeText(this, "请检查你的手机网络", Toast.LENGTH_SHORT).show();
                }
                if (upPass_inOldPass.getText().toString().length() > 0
                        && upPass_inNewPass.getText().toString().length() >= 6
                        && upPass_OkPass.getText().toString().length() >= 6) {
                    if (upPass_inNewPass.getText().toString().equals(upPass_OkPass.getText().toString())) {
                        upDateMyData_presenter = new UpDateMyData_Presenter(this, upPass_inOldPass.getText().toString(), objId, upPass_OkPass.getText().toString());
                        upDateMyData_presenter.upPassword(this);
                    } else {
                        wad.dismiss();
                        Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    wad.dismiss();
                    Toast.makeText(this, "新密码不能低于6位数", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void upDatePassword(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Log.e("更新密码取得结果：", "" + map.get("upPasswordStatus"));
            if (map.get("upPasswordStatus").equals("ok")) {
                Message msg = new Message();
                msg.obj = "密码修改成功";
                toastUpPassResult.sendMessage(msg);
            }
            if (map.get("upPasswordStatus").equals("no")) {
                Message msg = new Message();
                msg.obj = "密码修改失败";
                toastUpPassResult.sendMessage(msg);
            }
            if (map.get("upPasswordStatus").equals("oldPasswordError")) {
                Message msg = new Message();
                msg.obj = "旧密码错误";
                toastUpPassResult.sendMessage(msg);
            }


        }
    }
}
