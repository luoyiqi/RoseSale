package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.MVP.Listener.GetMeData_Listener;
import com.wpl.rosesale.MVP.Model.GetMeData_Model;
import com.wpl.rosesale.Utils.IsHaveFile;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 培龙 on 2016/9/12.
 */
public class GetMeData_ModelImpl implements GetMeData_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();
    private final String txPath = Environment.getExternalStorageDirectory() + "/小香香的花店/头像缓存";

    @Override
    public void getMeData(final String phoneNumber, final Context context, final GetMeData_Listener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /**
                 * 以手机号码为参数，获取用户资料
                 * 将获取到的资料存到SharedPreferences中、
                 * 如果网络正常，则下载头像到SD卡中，如头像已存在该目录，则不做任何操作
                 */
                BmobQuery<usersTab> query = new BmobQuery<usersTab>();
                query.addWhereEqualTo("uId", phoneNumber);
                query.findObjects(new FindListener<usersTab>() {
                    @Override
                    public void done(List<usersTab> list, BmobException e) {
                        if (e == null) {
                            for (final usersTab ut : list) {
                                final Map<String, String> map = new HashMap<String, String>();
                                map.put("objId", ut.getObjectId());
                                map.put("nickName", ut.getuNickname());
                                map.put("image", ut.getuImage());
                                map.put("id", ut.getuId());
                                map.put("password", ut.getuPassword());
                                map.put("email", ut.getuEmail());
                                map.put("sex", ut.getuSex());
                                map.put("age", ut.getuAge());
                                map.put("city", ut.getuCity());
                                map.put("address", ut.getuAddress());
                                map.put("phoneNumber", ut.getuPhoneNumber());
                                map.put("birthdayYear", ut.getuBirthdayYear());
                                map.put("birthdayMonth", ut.getuBirthdayMonth());
                                map.put("birthdayDay", ut.getuBirthdayDay());
                                map.put("likeRoseType", ut.getuLikeRoseType());
                                map.put("isLogin", ut.getuIslogin());
                                map.put("deviceIMEI", ut.getuDeviceIMEI());
                                map.put("deviceModel", ut.getuDeviceModel());

                                SharedPreferences spf = context.getSharedPreferences("meInfo", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = spf.edit();
                                editor.putString("objId", ut.getObjectId());
                                editor.putString("nickName", ut.getuNickname());
                                editor.putString("id", ut.getuId());
                                editor.putString("password", ut.getuPassword());
                                editor.putString("email", ut.getuEmail());
                                editor.putString("sex", ut.getuSex());
                                editor.putString("age", ut.getuAge());
                                editor.putString("city", ut.getuCity());
                                editor.putString("address", ut.getuAddress());
                                editor.putString("phoneNumber", ut.getuPhoneNumber());
                                editor.putString("birthdayYear", ut.getuBirthdayYear());
                                editor.putString("birthdayMonth", ut.getuBirthdayMonth());
                                editor.putString("birthdayDay", ut.getuBirthdayDay());
                                editor.putString("likeRoseType", ut.getuLikeRoseType());
                                editor.putString("isLogin", ut.getuIslogin());
                                editor.putString("deviceIMEI", ut.getuDeviceIMEI());
                                editor.putString("deviceModel", ut.getuDeviceModel());
                                editor.commit();

                                if (ut.getuImage().equals("")) {
                                    //uImage为空
                                } else {
                                    if (IsHaveFile.fileIsExists(txPath + "/" + phoneNumber + ".jpg") == true) {
                                        //文件存在
                                    } else {
                                        //文件不存在
                                        BmobFile bmobFile = new BmobFile("" + phoneNumber + ".jpg", "", ut.getuImage());
                                        File saveFile = new File(txPath, bmobFile.getFilename());
                                        bmobFile.download(saveFile, new DownloadFileListener() {
                                            @Override
                                            public void done(String s, BmobException e) {
                                                if (e == null) {
                                                    Log.e("头像下载", "成功：" + s);
                                                } else {
                                                    Log.e("头像下载", "失败：" + e.getErrorCode());
                                                }
                                            }

                                            @Override
                                            public void onProgress(Integer integer, long l) {

                                            }
                                        });
                                    }
                                }
                                map.put("imagePath", txPath + "/" + phoneNumber + ".jpg");
                                listAll.add(map);
                                listener.setMeData(listAll);
                            }

                        } else {
                            //获取个人资料失败
                            Map<String, String> map = new HashMap<String, String>();

                            SharedPreferences getspf = context.getSharedPreferences("meInfo", Context.MODE_PRIVATE);
                            map.put("objId", getspf.getString("objId", ""));
                            map.put("nickName", getspf.getString("nickName", "Rose_"));
                            map.put("id", getspf.getString("id", ""));
                            map.put("password", getspf.getString("password", ""));
                            map.put("email", getspf.getString("email", ""));
                            map.put("sex", getspf.getString("sex", ""));
                            map.put("age", getspf.getString("age", ""));
                            map.put("city", getspf.getString("city", ""));
                            map.put("address", getspf.getString("address", ""));
                            map.put("phoneNumber", getspf.getString("phoneNumber", ""));
                            map.put("birthdayYear", getspf.getString("birthdayYear", ""));
                            map.put("birthdayMonth", getspf.getString("birthdayMonth", ""));
                            map.put("birthdayDay", getspf.getString("birthdayDay", ""));
                            map.put("likeRoseType", getspf.getString("likeRoseType", ""));
                            map.put("isLogin", getspf.getString("isLogin", ""));
                            map.put("deviceIMEI", getspf.getString("deviceIMEI", ""));
                            map.put("deviceModel", getspf.getString("deviceModel", ""));
                            map.put("imagePath", txPath + "/" + phoneNumber + ".jpg");

                            listAll.add(map);
                            listener.setMeData(listAll);
                        }
                    }
                });
            }
        }).start();
    }
}
