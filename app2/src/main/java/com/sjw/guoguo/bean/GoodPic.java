package com.sjw.guoguo.bean;

/**
 * Created by sjw on 2018/1/31.
 */

public class GoodPic extends BaseBean{
    private String picUrl;
    private int picWidth;
    private int picHeight;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }
}
