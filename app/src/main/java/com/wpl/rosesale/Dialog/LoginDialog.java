package com.wpl.rosesale.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wpl.rosesale.Activity.MoreLoginActivity;
import com.wpl.rosesale.MVP.Presenter.LoginSendCode_Presenter;
import com.wpl.rosesale.MVP.View.LoginSendCode_View;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.DensityUtil;

import java.util.List;
import java.util.Map;

/**
 * 手机号码登录
 * Created by 培龙 on 2016/9/10.
 */
public class LoginDialog extends Dialog implements LoginSendCode_View {
    private Context context;
    private AutoCompleteTextView phoneNumber;//输入的手机号码
    private ImageView close;//关闭Dialog
    private Button next;//下一步
    private LinearLayout moreLoginType;//更多登录方式
    private ArrayAdapter<String> adapter;//自动提示Adapter
    private LoginSendCode_Presenter presenter;
    private Handler getSendCodeStatus;//用于获取登录状态
    private WaitAnimDialog wad;

    public LoginDialog(Context context, int i) {
        super(context, i);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_login);
        this.setCanceledOnTouchOutside(false);//点击周围空白处，Dialog不消失
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        wad = new WaitAnimDialog(getContext(), "正在发送验证码...", R.drawable.wait_anim, R.style.loading_dialog);
        phoneNumber = (AutoCompleteTextView) findViewById(R.id.login_dialog_phoneNumber);
        close = (ImageView) findViewById(R.id.login_dialog_close);
        next = (Button) findViewById(R.id.login_dialog_next);
        moreLoginType = (LinearLayout) findViewById(R.id.login_dialog_moreLoginType);
        phoneNumber.setDropDownBackgroundResource(R.drawable.login_button_no_style);
        phoneNumber.setDropDownVerticalOffset(DensityUtil.dip2px(getContext(), 10));

        moreLoginType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent().setClass(getContext(), MoreLoginActivity.class));
                LoginDialog.this.dismiss();
            }
        });

        /**
         * 读取spf中存的历史记录以自动提示
         */
        SharedPreferences spf = getContext().getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        String[] arr = {spf.getString("phoneNumber", "")};
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, arr);
        phoneNumber.setAdapter(adapter);

        /**
         * 监听EditText是否有内容
         */
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 13) {
                    //输入了完整的手机号码
                    next.setEnabled(true);
                    next.setBackgroundResource(R.drawable.login_button_pre_style);
                } else {
                    //手机号码不完整
                    next.setEnabled(false);
                    next.setBackgroundResource(R.drawable.login_button_no_style);
                }

                /**
                 * 手机号码自动添加空格
                 */
                if (count == 1) {
                    int length = s.toString().length();
                    if (length == 3 || length == 8) {
                        phoneNumber.setText(s + " ");
                        phoneNumber.setSelection(phoneNumber.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /**
         * 关闭
         */
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialog.this.dismiss();
            }
        });

        /**
         * 下一步
         */
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setEnabled(false);
                if ((phoneNumber.getText().toString().replaceAll(" ", "")).length() == 11) {
                    wad.show();
                    /**
                     * 点击下一步 -- 保存手机号码到历史记录
                     */
                    SharedPreferences spf = getContext().getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spf.edit();
                    editor.putString("phoneNumber", phoneNumber.getText().toString());
                    editor.commit();

                    presenter = new LoginSendCode_Presenter(phoneNumber.getText().toString().replaceAll(" ", ""), LoginDialog.this);
                    presenter.sendCode(LoginDialog.this.context);

                    /**
                     * 等待子线程返回短信验证码发送结果，再进行后续操作
                     */
                    getSendCodeStatus = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            //验证码发送成功
                            wad.dismiss();
                            Toast.makeText(getContext(), "请注意接收短信", Toast.LENGTH_SHORT).show();
                            Dialog dialog = new CheckUpCode(getContext(), R.style.loading_dialog);
                            dialog.show();
                            LoginDialog.this.dismiss();
                            super.handleMessage(msg);
                        }
                    };
                } else {
                    Toast.makeText(getContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void sendCode(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message msg = new Message();
            msg.obj = map.get("sendCodeStatus");
            getSendCodeStatus.sendMessage(msg);
        }
    }
}
