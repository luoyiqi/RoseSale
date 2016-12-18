package com.wpl.rosesale.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wpl.rosesale.MVP.Presenter.CheckSMSCode_Presenter;
import com.wpl.rosesale.MVP.Presenter.RegistUser_Presenter;
import com.wpl.rosesale.MVP.View.CheckSMSCode_View;
import com.wpl.rosesale.MVP.View.RegistUser_View;
import com.wpl.rosesale.R;

import java.util.List;
import java.util.Map;

/**
 * 输入验证码
 * Created by 培龙 on 2016/9/10.
 */
public class CheckUpCode extends Dialog implements CheckSMSCode_View, RegistUser_View {
    private Context context;

    private ImageView back, close;
    private TextView notCode, time;
    private EditText inputCode;//验证码
    private String phoneNumber;//请求验证的手机号
    private Boolean isLoginStatus;//登录状态
    private CheckSMSCode_Presenter checkCodePresenter;
    private RegistUser_Presenter registUserPresenter;
    private Handler getCheckCodeStatus;
    private final WaitAnimDialog wad = new WaitAnimDialog(getContext(), "请稍后...", R.drawable.wait_anim, R.style.loading_dialog);
    //动画消失
    private Handler dismissAnim = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            wad.dismiss();
            super.handleMessage(msg);
        }
    };

    //显示动画
    private Handler showAnim = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            wad.show();
            super.handleMessage(msg);
        }
    };

    public CheckUpCode(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_check_up_code);
        this.setCanceledOnTouchOutside(false);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        SharedPreferences spf = getContext().getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        phoneNumber = spf.getString("phoneNumber", "");
        back = (ImageView) findViewById(R.id.check_up_code_back);
        close = (ImageView) findViewById(R.id.check_up_code_close);
        notCode = (TextView) findViewById(R.id.check_up_code_notCode);
        inputCode = (EditText) findViewById(R.id.check_up_code_code);
        time = (TextView) findViewById(R.id.check_up_code_time);

        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(millisUntilFinished / 1000 + "秒后");
                notCode.setEnabled(false);
            }

            @Override
            public void onFinish() {
                time.setText("");
                notCode.setEnabled(true);
            }
        }.start();

        /**
         * 监听回车键
         */
        inputCode.setImeOptions(EditorInfo.IME_ACTION_SEND);
        inputCode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                check_Code(inputCode.getText());
                return false;
            }
        });

        /**
         * 返回
         */
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUpCode.this.dismiss();
                LoginDialog loginDialog = new LoginDialog(getContext(), R.style.loading_dialog);
                loginDialog.show();
            }
        });

        /**
         * 关闭
         */
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUpCode.this.dismiss();
            }
        });

        /**
         * 未收到验证码
         */
        notCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUpCode.this.dismiss();
                LoginDialog loginDialog = new LoginDialog(getContext(), R.style.loading_dialog);
                loginDialog.show();
                wad.dismiss();
            }
        });

        /**
         * 监听键盘
         */
        inputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                check_Code(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 验证短信验证码
     *
     * @param s
     */
    private void check_Code(CharSequence s) {
        if (s.length() == 6) {
            showAnim.sendMessage(new Message());
            checkCodePresenter = new CheckSMSCode_Presenter(phoneNumber.toString().replaceAll(" ", ""), inputCode.getText().toString(), CheckUpCode.this);
            checkCodePresenter.checkCode(CheckUpCode.this.context);
            getCheckCodeStatus = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Map<String, String> map = (Map<String, String>) msg.obj;
                    if (map.get("CheckCodeStatus").equals("ok")) {
                        //验证成功
                        registUserPresenter = new RegistUser_Presenter(phoneNumber.toString().replaceAll(" ", ""), CheckUpCode.this);
                        registUserPresenter.registUser(CheckUpCode.this.context);
                        SharedPreferences spf = getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = spf.edit();
                        editor.putBoolean("login_status", true);
                        editor.commit();
                        dismissAnim.sendMessage(new Message());
                        CheckUpCode.this.dismiss();
                        Toast.makeText(getContext(), "验证成功", Toast.LENGTH_SHORT).show();
                    } else {
                        //验证失败
                        SharedPreferences spf = getContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = spf.edit();
                        editor.putBoolean("login_status", false);
                        editor.commit();
                        dismissAnim.sendMessage(new Message());
                        Toast.makeText(getContext(), "验证失败：" + map.get("ErrorCode"), Toast.LENGTH_SHORT).show();
                    }
                    super.handleMessage(msg);
                }
            };

        }
    }

    /**
     * 验证验证码结果
     *
     * @param list
     */
    @Override
    public void checkCode(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }

            Message msg = new Message();
            msg.obj = list.get(0);
            getCheckCodeStatus.sendMessage(msg);
        }
    }

    /**
     * 注册用户
     * 此操作为用户手机登录 -- 注册操作
     * 如检测到用户为注册，将该条信息插入到数据库
     * 会保存 uId, uPhoneNumber, uNickname
     * uId: 用该手机号码作为uId；
     * uPhoneNumber: 用该手机号码作为uPhoneNumber；
     * uNickname: "Rose_" + 随机生成6位数字 作为昵称
     *
     * @param list
     */
    @Override
    public void registUser(List<Map<String, String>> list) {
        /**
         * 此操作不返回结果
         */
    }
}
