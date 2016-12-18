package com.wpl.rosesale.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wpl.rosesale.Dialog.LoginDialog;
import com.wpl.rosesale.MainFragment.FxFragment;
import com.wpl.rosesale.MainFragment.SyFragment;
import com.wpl.rosesale.MainFragment.WdFragment;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.PermissionsChecker;

import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private boolean isRequireCheck; //是否需要系统权限检测
    static final String[] PERMISSIONS = new String[]{   //危险权限（运行时权限）
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private PermissionsChecker mPermissionsChecker; //检查权限
    private static final int PERMISSION_REQUEST_CODE = 0;   //系统权限返回码
    private static final String PACKAGE_URL_SCHEME = "package:";

    private LinearLayout sy, fx, wd;
    private ImageView sy_img, fx_img, wd_img;
    private TextView sy_text, fx_text, wd_text;
    private Fragment syFragment, ddFragment, wdFragment, currentFragment;
    private long exitTime = 0;
    private Dialog loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        mPermissionsChecker = new PermissionsChecker(this);
        isRequireCheck = true;

        initView();
        initTab();

    }


    /**
     * 初始化组件
     */
    private void initView() {
        sy = (LinearLayout) findViewById(R.id.sy);
        fx = (LinearLayout) findViewById(R.id.fx);
        wd = (LinearLayout) findViewById(R.id.wd);

        sy_img = (ImageView) findViewById(R.id.sy_img);
        fx_img = (ImageView) findViewById(R.id.fx_img);
        wd_img = (ImageView) findViewById(R.id.wd_img);

        sy_text = (TextView) findViewById(R.id.sy_text);
        fx_text = (TextView) findViewById(R.id.fx_text);
        wd_text = (TextView) findViewById(R.id.wd_text);

        loginDialog = new LoginDialog(this, R.style.loading_dialog);

        sy.setOnClickListener(this);
        fx.setOnClickListener(this);
        wd.setOnClickListener(this);
    }

    /**
     * 初始化导航栏
     */
    private void initTab() {
        if (syFragment == null) {
            syFragment = new SyFragment();
        }

        if (!syFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, syFragment).commit();
            currentFragment = syFragment;

            sy_img.setImageResource(R.mipmap.home_pre);
            fx_img.setImageResource(R.mipmap.find_no);
            wd_img.setImageResource(R.mipmap.me_no);

            sy_text.setTextColor(Color.parseColor("#2e2e2e"));
            fx_text.setTextColor(Color.parseColor("#959595"));
            wd_text.setTextColor(Color.parseColor("#959595"));
        }
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sy: //首页
                SY();
                break;
            case R.id.fx: //发现
                DD();
                break;
            case R.id.wd: //我的
                SharedPreferences spf = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                if (spf.getBoolean("login_status", false) == false) {
                    //未登录
                    loginDialog.show();
                } else {
                    WD();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 点击我的
     */
    private void WD() {
        if (wdFragment == null) {
            wdFragment = new WdFragment();
        }
        AddOrShowFragment(getSupportFragmentManager().beginTransaction(), wdFragment);
        sy_img.setImageResource(R.mipmap.home_no);
        fx_img.setImageResource(R.mipmap.find_no);
        wd_img.setImageResource(R.mipmap.me_pre);
        sy_text.setTextColor(Color.parseColor("#959595"));
        fx_text.setTextColor(Color.parseColor("#959595"));
        wd_text.setTextColor(Color.parseColor("#2e2e2e"));
    }

    /**
     * 点击发现
     */
    private void DD() {
        if (ddFragment == null) {
            ddFragment = new FxFragment();
        }
        AddOrShowFragment(getSupportFragmentManager().beginTransaction(), ddFragment);
        sy_img.setImageResource(R.mipmap.home_no);
        fx_img.setImageResource(R.mipmap.find_pre);
        wd_img.setImageResource(R.mipmap.me_no);
        sy_text.setTextColor(Color.parseColor("#959595"));
        fx_text.setTextColor(Color.parseColor("#2e2e2e"));
        wd_text.setTextColor(Color.parseColor("#959595"));
    }

    /**
     * 点击首页
     */
    private void SY() {
        if (syFragment == null) {
            syFragment = new SyFragment();
        }
        AddOrShowFragment(getSupportFragmentManager().beginTransaction(), syFragment);
        sy_img.setImageResource(R.mipmap.home_pre);
        fx_img.setImageResource(R.mipmap.find_no);
        wd_img.setImageResource(R.mipmap.me_no);
        sy_text.setTextColor(Color.parseColor("#2e2e2e"));
        fx_text.setTextColor(Color.parseColor("#959595"));
        wd_text.setTextColor(Color.parseColor("#959595"));
    }

    /**
     * 添加或显示Fragment
     *
     * @param transaction
     * @param fragment
     */
    private void AddOrShowFragment(FragmentTransaction transaction, Fragment fragment) {

        if (currentFragment == fragment) {
            return;
        }

        if (!fragment.isAdded()) {
            transaction.hide(currentFragment).add(R.id.content, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }


    /**
     * 再按一次退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
//                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isRequireCheck) {
            //权限没有授权，进入授权界面
            if (mPermissionsChecker.judgePermissions(PERMISSIONS)) {
                Log.e("MainActivity","没有权限");
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
            }else {
                Log.e("MainActivity","有权限");
                BmobUpdateAgent.setUpdateOnlyWifi(false);
                BmobUpdateAgent.update(this);
            }
        } else {
            isRequireCheck = true;
        }
    }

    /**
     * 用户权限处理，
     * 如果全部获取，则直接过
     * 如果权限缺失，则提示Dialog
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE && hasAllPermissionsGranted(grantResults)) {
            isRequireCheck = true;
        } else {
            isRequireCheck = false;
            showPermissionDialog();
        }
    }

    /**
     * 提示对话框
     */
    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-打开所需权限。");
        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                setResult(PERMISSIONS_DENIED);
                finish();
            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + getPackageName()));
        startActivity(intent);
    }

    /**
     * 含有全部权限
     *
     * @param grantResults
     * @return
     */
    private boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
