package com.wpl.rosesale.Bean;

import cn.bmob.v3.BmobObject;

/**
 * 用户信息表
 * Created by 培龙 on 2016/9/9.
 */
public class usersTab extends BmobObject {

    private String uId;                  //用户名--登录标示
    private String uPassword;            //密码
    private String uNickname;            //昵称
    private String uSex;                 //性别
    private String uAge;                 //年龄
    private String uCity;                //城市
    private String uAddress;             //详细地址
    private String uPhoneNumber;         //手机号码
    private String uBirthdayMonth;       //出生月份
    private String uBirthdayDay;         //出生日
    private String uLikeRoseType;        //喜欢的花类型
    private String uIslogin;              //保存是否登录
    private String uDeviceIMEI;           //最后一次登录的设备IMEI串号
    private String uDeviceModel;          //最后一次登录的设备型号
    private String uImage;                //用户头像
    private String uEmail;                //邮箱
    private String uBirthdayYear;         //出生年

    public String getuBirthdayYear() {
        return uBirthdayYear;
    }

    public void setuBirthdayYear(String uBirthdayYear) {
        this.uBirthdayYear = uBirthdayYear;
    }

    public String getuAddress() {
        return uAddress;
    }

    public void setuAddress(String uAddress) {
        this.uAddress = uAddress;
    }

    public String getuAge() {
        return uAge;
    }

    public void setuAge(String uAge) {
        this.uAge = uAge;
    }

    public String getuBirthdayDay() {
        return uBirthdayDay;
    }

    public void setuBirthdayDay(String uBirthdayDay) {
        this.uBirthdayDay = uBirthdayDay;
    }

    public String getuBirthdayMonth() {
        return uBirthdayMonth;
    }

    public void setuBirthdayMonth(String uBirthdayMonth) {
        this.uBirthdayMonth = uBirthdayMonth;
    }

    public String getuCity() {
        return uCity;
    }

    public void setuCity(String uCity) {
        this.uCity = uCity;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuLikeRoseType() {
        return uLikeRoseType;
    }

    public void setuLikeRoseType(String uLikeRoseType) {
        this.uLikeRoseType = uLikeRoseType;
    }

    public String getuNickname() {
        return uNickname;
    }

    public void setuNickname(String uNickname) {
        this.uNickname = uNickname;
    }

    public String getuPassword() {
        return uPassword;
    }

    public void setuPassword(String uPassword) {
        this.uPassword = uPassword;
    }

    public String getuPhoneNumber() {
        return uPhoneNumber;
    }

    public void setuPhoneNumber(String uPhoneNumber) {
        this.uPhoneNumber = uPhoneNumber;
    }

    public String getuSex() {
        return uSex;
    }

    public void setuSex(String uSex) {
        this.uSex = uSex;
    }

    public String getuDeviceIMEI() {
        return uDeviceIMEI;
    }

    public void setuDeviceIMEI(String uDeviceIMEI) {
        this.uDeviceIMEI = uDeviceIMEI;
    }

    public String getuDeviceModel() {
        return uDeviceModel;
    }

    public void setuDeviceModel(String uDeviceModel) {
        this.uDeviceModel = uDeviceModel;
    }

    public String getuIslogin() {
        return uIslogin;
    }

    public void setuIslogin(String uIslogin) {
        this.uIslogin = uIslogin;
    }

    public String getuImage() {
        return uImage;
    }

    public void setuImage(String uImage) {
        this.uImage = uImage;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }
}
