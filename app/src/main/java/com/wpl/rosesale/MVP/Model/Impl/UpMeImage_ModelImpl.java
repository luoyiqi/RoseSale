package com.wpl.rosesale.MVP.Model.Impl;

import android.content.Context;

import com.wpl.rosesale.Bean.usersTab;
import com.wpl.rosesale.MVP.Listener.UpMeImage_Listener;
import com.wpl.rosesale.MVP.Model.UpMeImage_Model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 更新用户头像
 * Created by 培龙 on 2016/9/14.
 */

public class UpMeImage_ModelImpl implements UpMeImage_Model {
    private List<Map<String, String>> listAll = new ArrayList<>();
    private String objId, phone, imageUrl, imagePath;
    private Context context;
    private UpMeImage_Listener listener;

    /**
     * 更新用户头像：
     * 1.查询用户头像地址，删除该地址的文件
     * 2.上传用户新的头像到服务器
     * 3.更新用户头像信息
     *
     * @param objId
     * @param phone
     * @param imageUrl
     * @param imagePath
     * @param context
     * @param listener
     */
    @Override
    public void upMeImage(final String objId, final String phone, final String imageUrl, final String imagePath, Context context, final UpMeImage_Listener listener) {

        this.objId = objId;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.context = context;
        this.listener = listener;
        new Thread(new Runnable() {
            @Override
            public void run() {
                queryAndDelete();
            }
        }).start();
    }

    /**
     * 1.查询用户原头像
     * 2.删除头像的网络文件
     */
    private void queryAndDelete() {
        BmobQuery<usersTab> query = new BmobQuery<usersTab>();
        query.getObject(objId, new QueryListener<usersTab>() {
            @Override
            public void done(usersTab ut, BmobException e) {
                if (e == null) {
                    if (ut.getuImage().equals("")) {
                        //原头像为空
                        upLoadImageFile();
                    } else {
                        BmobFile file = new BmobFile();
                        file.setUrl(ut.getuImage());
                        file.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    //删除原头像文件成功
                                    upLoadImageFile();
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    /**
     * 上传文件到服务器
     */
    private void upLoadImageFile() {
        final BmobFile bf = new BmobFile(new File(imagePath));
        bf.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    //上传成功，得到文件的URL
                    updateUserImage(bf.getFileUrl());
                }
            }
        });
    }

    /**
     * 更新用户头像
     *
     * @param fileUrl 用户头像的网络地址
     */
    private void updateUserImage(String fileUrl) {
        usersTab ut = new usersTab();
        ut.setuImage(fileUrl);
        ut.update(objId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Map<String, String> map = new HashMap<String, String>();
                if (e == null) {
                    //用户头像数据更新成功
                    map.put("upMeImage_Status", "ok");
                } else {
                    //失败
                    map.put("upMeImage_Status", "no");
                }
                listAll.add(map);
                listener.resultUpMeImage(listAll);
            }
        });
    }
}
