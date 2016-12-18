package com.wpl.rosesale.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;
import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.MVP.Presenter.GetMeData_Presenter;
import com.wpl.rosesale.MVP.Presenter.UpMeImage_Presenter;
import com.wpl.rosesale.MVP.View.GetMeData_View;
import com.wpl.rosesale.MVP.View.UpMeImage_View;
import com.wpl.rosesale.MyApplication.MyApplication;
import com.wpl.rosesale.R;
import com.wpl.rosesale.Utils.DeleteSDFile;
import com.wpl.rosesale.Utils.IsHaveFile;
import com.wpl.rosesale.Utils.IsNetWorkStatus;
import com.wpl.rosesale.Utils.RoundImageView;
import com.wpl.rosesale.Utils.SaveBitmapToSD;
import com.wpl.rosesale.Utils.VolleyImageLoader;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 个人资料
 * Created by 培龙 on 2016/9/12.
 */
public class MeInfoActivity extends AppCompatActivity implements View.OnClickListener, GetMeData_View, UpMeImage_View {
    private LinearLayout meInfo_back, meInfo_exitLoginLL;
    private RelativeLayout meInfo_imageRL, meInfo_emailRL, meInfo_nickNameRL, meInfo_sexRL,
            meInfo_ageRL, meInfo_cityRL, meInfo_brithDayRL, meInfo_phoneRL, meInfo_passwordRL,
            meInfo_addressRL, meInfo_likeRoseTypeRL;
    private RoundImageView meInfo_image;
    private TextView meInfo_email, meInfo_nickName, meInfo_sex, meInfo_age, meInfo_city,
            meInfo_brithDay, meInfo_phone, meInfo_password;
    private GetMeData_Presenter getMeData_presenter;
    private Handler setViewHandler;
    private String phoneNumber, meImageUrl, objId, nickName, city;
    private UpMeImage_Presenter upMeImage_presenter;
    private CatLoadingView catLoadingView;

    private int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;

    //Toast
    final Handler toastMsg = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(MeInfoActivity.this, ""+msg.obj, Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meinfo);
        initUI();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        catLoadingView = new CatLoadingView();
        SharedPreferences sf = getSharedPreferences("historyPhoneNumber", Context.MODE_PRIVATE);
        phoneNumber = (sf.getString("phoneNumber", "")).replaceAll(" ", "");
        meInfo_image = (RoundImageView) findViewById(R.id.meInfo_image);
        meInfo_email = (TextView) findViewById(R.id.meInfo_email);
        meInfo_nickName = (TextView) findViewById(R.id.meInfo_nickName);
        meInfo_sex = (TextView) findViewById(R.id.meInfo_sex);
        meInfo_age = (TextView) findViewById(R.id.meInfo_age);
        meInfo_city = (TextView) findViewById(R.id.meInfo_city);
        meInfo_brithDay = (TextView) findViewById(R.id.meInfo_brithDay);
        meInfo_phone = (TextView) findViewById(R.id.meInfo_phone);
        meInfo_password = (TextView) findViewById(R.id.meInfo_password);
        meInfo_back = (LinearLayout) findViewById(R.id.meInfo_back);
        meInfo_exitLoginLL = (LinearLayout) findViewById(R.id.meInfo_exitLoginLL);
        meInfo_emailRL = (RelativeLayout) findViewById(R.id.meInfo_emailRL);
        meInfo_imageRL = (RelativeLayout) findViewById(R.id.meInfo_imageRL);
        meInfo_nickNameRL = (RelativeLayout) findViewById(R.id.meInfo_nickNameRL);
        meInfo_sexRL = (RelativeLayout) findViewById(R.id.meInfo_sexRL);
        meInfo_ageRL = (RelativeLayout) findViewById(R.id.meInfo_ageRL);
        meInfo_cityRL = (RelativeLayout) findViewById(R.id.meInfo_cityRL);
        meInfo_brithDayRL = (RelativeLayout) findViewById(R.id.meInfo_brithDayRL);
        meInfo_phoneRL = (RelativeLayout) findViewById(R.id.meInfo_phoneRL);
        meInfo_passwordRL = (RelativeLayout) findViewById(R.id.meInfo_passwordRL);
        meInfo_addressRL = (RelativeLayout) findViewById(R.id.meInfo_addressRL);
        meInfo_likeRoseTypeRL = (RelativeLayout) findViewById(R.id.meInfo_likeRoseTypeRL);

        meInfo_back.setOnClickListener(this);
        meInfo_exitLoginLL.setOnClickListener(this);
        meInfo_emailRL.setOnClickListener(this);
        meInfo_imageRL.setOnClickListener(this);
        meInfo_nickNameRL.setOnClickListener(this);
        meInfo_sexRL.setOnClickListener(this);
        meInfo_ageRL.setOnClickListener(this);
        meInfo_cityRL.setOnClickListener(this);
        meInfo_brithDayRL.setOnClickListener(this);
        meInfo_phoneRL.setOnClickListener(this);
        meInfo_passwordRL.setOnClickListener(this);
        meInfo_addressRL.setOnClickListener(this);
        meInfo_likeRoseTypeRL.setOnClickListener(this);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        getMeData_presenter = new GetMeData_Presenter(phoneNumber, this);
        getMeData_presenter.getMeData(this);
        setViewHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Map<String, String> meResult = (Map<String, String>) msg.obj;
                meImageUrl = meResult.get("image");
                objId = meResult.get("objId");
                /**
                 * 判断是否有网络
                 * 有网络：直接加载网络图片
                 * 无网络：读取保存到本地的图片
                 *          本地图片存在：将ImageView设置成本地图片
                 *          本地图片不存在： - -
                 */
                if (IsNetWorkStatus.getActiveNetwork(MeInfoActivity.this) != null) {
                    //网络正常
                    VolleyImageLoader.setImageView(MeInfoActivity.this, meResult.get("image"), meInfo_image);
                } else {
                    //无网络
                    if (meInfo_image.getDrawable().getCurrent().getConstantState()
                            == getResources().getDrawable(R.mipmap.ic_rose_icon_no).getConstantState()) {
                        Toast.makeText(MeInfoActivity.this, "请检查你的手机网络", Toast.LENGTH_SHORT).show();
                        if (IsHaveFile.fileIsExists(meResult.get("imagePath")) == true) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 10;
                            Bitmap bm = BitmapFactory.decodeFile(meResult.get("imagePath"), options);
                            meInfo_image.setImageBitmap(bm);
                        } else {
                            //本地图片不存在
                        }
                    }
                }

                nickName = meResult.get("nickName");
                if (meResult.get("nickName").equals("")) {
                    meInfo_nickName.setText("未设置");
                } else {
                    meInfo_nickName.setText(meResult.get("nickName"));
                }
                if (meResult.get("email").equals("")) {
                    meInfo_email.setText("未设置");
                } else {
                    meInfo_email.setText(meResult.get("email"));
                }
                if (meResult.get("sex").equals("")) {
                    meInfo_sex.setText("未设置");
                } else {
                    meInfo_sex.setText(meResult.get("sex"));
                }
                if (meResult.get("age").equals("")) {
                    meInfo_age.setText("未设置");
                } else {
                    meInfo_age.setText(meResult.get("age"));
                }
                if (meResult.get("city").equals("")) {
                    meInfo_city.setText("未设置");
                } else {
                    meInfo_city.setText(meResult.get("city"));
                    city = meResult.get("city");
                }
                if (meResult.get("birthdayMonth").equals("") || meResult.get("birthdayDay").equals("")) {
                    meInfo_brithDay.setText("未设置");
                } else {
                    meInfo_brithDay.setText(meResult.get("birthdayMonth") + "-" + meResult.get("birthdayDay"));
                }
                if (meResult.get("phoneNumber").equals("")) {
                    meInfo_phone.setText("未设置");
                } else {
                    meInfo_phone.setText(meResult.get("phoneNumber"));
                }
                if (meResult.get("password").equals("")) {
                    meInfo_password.setText("未设置");
                } else {
                    meInfo_password.setText("********");
                }
                catLoadingView.dismiss();
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meInfo_back:  //返回
                finish();
                break;
            case R.id.meInfo_exitLoginLL:  //退出登录
                new AlertDialog.Builder(MeInfoActivity.this).setTitle("提示")
                        .setMessage("是否退出登录").setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        catLoadingView.show(getSupportFragmentManager(), "请稍后");
                        if (IsNetWorkStatus.getActiveNetwork(MeInfoActivity.this) != null) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        SharedPreferences spf = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = spf.edit();
                                        editor.putBoolean("login_status", false);
                                        editor.commit();
                                        MyApplication.mainActivity.finish();
                                        Thread.sleep(2020);
                                        catLoadingView.dismiss();
                                        Message msg = new Message();
                                        msg.obj = "提交成功";
                                        toastMsg.sendMessage(msg);
                                        finish();
                                        startActivity(new Intent().setClass(MeInfoActivity.this, MainActivity.class));
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(1000);
                                        catLoadingView.dismiss();
                                        Message msg = new Message();
                                        msg.obj = "提交失败，请检查网络";
                                        toastMsg.sendMessage(msg);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }
                }).show();
                break;
            case R.id.meInfo_imageRL:  //头像
                //使用startActivityForResult启动TakeMePhotoActivity当返回到此Activity的时候就会调用onActivityResult函数
                startActivityForResult(new Intent(MeInfoActivity.this, TakeMePhotoActivity.class), 1);
                break;
            case R.id.meInfo_emailRL:  //邮箱
                break;
            case R.id.meInfo_nickNameRL:  //昵称
                Bundle bNickname = new Bundle();
                bNickname.putString("nickName", nickName);
                bNickname.putString("objId", objId);
                Intent iNickname = new Intent(MeInfoActivity.this, UpMyNicknameActivity.class);
                iNickname.putExtras(bNickname);
                startActivity(iNickname);
                break;
            case R.id.meInfo_sexRL:  //性别
                Bundle bSex = new Bundle();
                bSex.putString("objId", objId);
                Intent iSex = new Intent(MeInfoActivity.this, UpMySexActivity.class);
                iSex.putExtras(bSex);
                startActivity(iSex);
                break;
            case R.id.meInfo_ageRL:  //年龄
                Toast.makeText(this, "请修改出生日期的年份", Toast.LENGTH_SHORT).show();
                break;
            case R.id.meInfo_cityRL:  //城市
                Bundle bCity = new Bundle();
                bCity.putString("objId", objId);
                bCity.putString("city", city);
                Intent iCity = new Intent(MeInfoActivity.this, UpMyCityActivity.class);
                iCity.putExtras(bCity);
                startActivity(iCity);
                break;
            case R.id.meInfo_brithDayRL:  //出生日期
                if (IsNetWorkStatus.getActiveNetwork(this) != null) {
                    showDialog(DATE_DIALOG);
                } else {
                    Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.meInfo_phoneRL:  //手机号
                break;
            case R.id.meInfo_passwordRL:  //密码
                Intent intentPW = new Intent();
                intentPW.setClass(MeInfoActivity.this, UpMyPasswordActivity.class);
                Bundle b = new Bundle();
                b.putString("objId", objId);
                intentPW.putExtras(b);
                startActivity(intentPW);
                break;
            case R.id.meInfo_addressRL:  //详细地址
                startActivity(new Intent().setClass(MeInfoActivity.this, MeAddressManagerActivity.class));
                break;
            case R.id.meInfo_likeRoseTypeRL:  //喜欢的花种
                break;
            default:
                break;
        }
    }

    @Override
    public void getMeData(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Message msg = new Message();
            msg.obj = map;
            setViewHandler.sendMessage(msg);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    public void display() {
        Calendar c = Calendar.getInstance();
        String s = String.valueOf((c.get(Calendar.YEAR) - mYear));
        meInfo_age.setText(s);
        usersTab ut = new usersTab();
        final String mon = String.valueOf(mMonth + 1);
        ut.setuBirthdayMonth(mon);
        ut.setuBirthdayDay(String.valueOf(mDay));
        ut.setuBirthdayYear(String.valueOf(mYear));
        ut.setuAge(s);
        ut.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    meInfo_brithDay.setText(new StringBuffer().append("" + mon).append(" - ").append("" + mDay));
                } else {
                    Toast.makeText(MeInfoActivity.this, "修改失败，请重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };


    @Override
    protected void onResume() {
        catLoadingView.show(getSupportFragmentManager(), "请稍后");
        getMeData_presenter = new GetMeData_Presenter(phoneNumber, this);
        getMeData_presenter.getMeData(this);
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                if (data != null) {
                    DeleteSDFile.delFile(Environment.getExternalStorageDirectory() + "/小香香的花店/头像缓存/" + phoneNumber + ".jpg");
                    /*
                     * 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                     */
                    Uri mImageCaptureUri = data.getData();
                    if (mImageCaptureUri != null) {
                        try {
                            //这个方法是根据Uri获取Bitmap图片的静态方法
                            Bitmap image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mImageCaptureUri);
                            if (image != null) {
                                if (IsNetWorkStatus.getActiveNetwork(this) != null) {
                                    String imagePath = SaveBitmapToSD.saveBitmapToSD(image, phoneNumber, Environment.getExternalStorageDirectory() + "/小香香的花店/头像缓存");
                                    upMeImage_presenter = new UpMeImage_Presenter(objId, phoneNumber, meImageUrl, imagePath, MeInfoActivity.this);
                                    upMeImage_presenter.upMeImage(MeInfoActivity.this);
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inSampleSize = 10;
                                    Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
                                    meInfo_image.setImageBitmap(bm);
                                    if (image.isRecycled()) {
                                        image.isRecycled();
                                        image = null;
                                    }
                                } else {
                                    Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                            Bitmap image = extras.getParcelable("data");
                            if (image != null) {
                                if (IsNetWorkStatus.getActiveNetwork(this) != null) {
                                    String imagePath = SaveBitmapToSD.saveBitmapToSD(image, phoneNumber, Environment.getExternalStorageDirectory() + "/小香香的花店/头像缓存");
                                    upMeImage_presenter = new UpMeImage_Presenter(objId, phoneNumber, meImageUrl, imagePath, MeInfoActivity.this);
                                    upMeImage_presenter.upMeImage(MeInfoActivity.this);
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inSampleSize = 10;
                                    Bitmap bm = BitmapFactory.decodeFile(imagePath, options);
                                    meInfo_image.setImageBitmap(bm);
                                    if (image.isRecycled()) {
                                        image.isRecycled();
                                        image = null;
                                    }
                                } else {
                                    Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void upMeImage(List<Map<String, String>> list) {
        if (list.size() > 0) {
            Map<String, String> map = list.get(0);
            if (map == null) {
                return;
            }
            Toast.makeText(this, "头像上传成功", Toast.LENGTH_SHORT).show();
        }
    }
}
