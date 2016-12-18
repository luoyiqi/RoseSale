package com.wpl.rosesale.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by 培龙 on 2016/11/27.
 */

public class find extends BmobObject {
    private String img;
    private String username;
    private String title;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
