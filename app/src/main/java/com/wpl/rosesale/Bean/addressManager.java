package com.wpl.rosesale.Bean;

import cn.bmob.v3.BmobObject;

/**
 * 收货地址表
 * Created by 培龙 on 2016/10/17.
 */

public class addressManager extends BmobObject {

    private String uBelongTo;    //该地址属于谁（账号）
    private String uAM_name;    //收货人姓名
    private String uAM_phone;    //收货人电话
    private String uAM_post;        //收货人邮编
    private String uAM_province;    //收货人省
    private String uAM_city;        //收货人城市
    private String uAM_county;        //收货人县
    private String uAM_detailAddress;    //收货人详细地址

    public String getuAM_city() {
        return uAM_city;
    }

    public void setuAM_city(String uAM_city) {
        this.uAM_city = uAM_city;
    }

    public String getuAM_county() {
        return uAM_county;
    }

    public void setuAM_county(String uAM_county) {
        this.uAM_county = uAM_county;
    }

    public String getuAM_detailAddress() {
        return uAM_detailAddress;
    }

    public void setuAM_detailAddress(String uAM_detailAddress) {
        this.uAM_detailAddress = uAM_detailAddress;
    }

    public String getuAM_name() {
        return uAM_name;
    }

    public void setuAM_name(String uAM_name) {
        this.uAM_name = uAM_name;
    }

    public String getuAM_phone() {
        return uAM_phone;
    }

    public void setuAM_phone(String uAM_phone) {
        this.uAM_phone = uAM_phone;
    }

    public String getuAM_post() {
        return uAM_post;
    }

    public void setuAM_post(String uAM_post) {
        this.uAM_post = uAM_post;
    }

    public String getuAM_province() {
        return uAM_province;
    }

    public void setuAM_province(String uAM_province) {
        this.uAM_province = uAM_province;
    }

    public String getuBelongTo() {
        return uBelongTo;
    }

    public void setuBelongTo(String uBelongTo) {
        this.uBelongTo = uBelongTo;
    }
}
