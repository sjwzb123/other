package com.sjw.guoguo.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import cn.bmob.v3.BmobUser;

/**
 * Created by sjw on 2018/3/14.
 */
@DatabaseTable(tableName = UserBean.TABLE_NAME)
public class UserBean extends BmobUser {
    public static final String TABLE_NAME = "t_user";
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "userId")
    private String userId;
    @DatabaseField(columnName = "headUrl")
    private String headUrl;
    private String nickName;
    @DatabaseField(columnName = "addr")
    private String addr;
    @DatabaseField(columnName = "phone")
    private String phone;
    private boolean isVip;
    private long vipStartTime;
    private int vipType;
    private boolean isAdmin;
    private long upTime;

    public long getUpTime() {
        return upTime;
    }

    public void setUpTime(long upTime) {
        this.upTime = upTime;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getVipType() {
        return vipType;
    }

    public void setVipType(int vipType) {
        this.vipType = vipType;
    }

    public long getVipStartTime() {
        return vipStartTime;
    }

    public void setVipStartTime(long vipStartTime) {
        this.vipStartTime = vipStartTime;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    private int balance; // 余额

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private int integral;

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }
}
