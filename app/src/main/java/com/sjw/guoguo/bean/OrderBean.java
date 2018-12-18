package com.sjw.guoguo.bean;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by sjw on 2018/3/16.
 */

public class OrderBean extends BaseBean{
    public enum OrderState{
        Unpaid,Distribution,Arrive,
    }
    private String userId;
    private long orderTime;
    private String orderPicUrl;
    private float orderMoney;
    private String orderAddr;
    private OrderState state;
    private List<CartItem> list;
    private String phone;
    private int dif;
    private String storeAddr;
    private String egent;

    public String getEgent() {
        return egent;
    }

    public void setEgent(String egent) {
        this.egent = egent;
    }

    public String getStoreAddr() {
        if (TextUtils.isEmpty(storeAddr)){
            storeAddr="新鲜果业—泰安街店";
        }
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public int getDif() {
        return dif;
    }

    public void setDif(int dif) {
        this.dif = dif;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<CartItem> getList() {
        return list;
    }

    public float getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
    }

    public void setList(List<CartItem> list) {
        this.list = list;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderAddr() {
        return orderAddr;
    }

    public void setOrderAddr(String orderAddr) {
        this.orderAddr = orderAddr;
    }



    public String getOrderPicUrl() {
        return orderPicUrl;
    }

    public void setOrderPicUrl(String orderPicUrl) {
        this.orderPicUrl = orderPicUrl;
    }
}
