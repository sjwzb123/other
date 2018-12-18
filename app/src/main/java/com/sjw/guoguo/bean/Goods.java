package com.sjw.guoguo.bean;

import com.ushareit.common.fs.SFile;

import java.util.List;

/**
 * Created by sjw on 2018/1/31.
 */

public class Goods extends BaseBean {
    public static class TYPE {
        public static final int SHUIGUO = 0;
        public static final int GANGUO = 1;
        public static final int ZHENGJIAN = 2;
    }

    private String goodName;
    private float goodPrice;
    private List<GoodPic> goodPics;
    private String goodDes;
    private int stockCount;
    private boolean isOff;
    private int type;
    private int salesCount;
    private String normos;
    private String videoUrl;
    private float goodCutPrice;
    private String shareDis;
    private String playUrl;
    private boolean isShred;

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public boolean isShred() {
        return isShred;
    }

    public void setShred(boolean shred) {
        isShred = shred;
    }

    public float getGoodCutPrice() {
        return goodCutPrice;
    }

    public void setGoodCutPrice(float goodCutPrice) {
        this.goodCutPrice = goodCutPrice;
    }

    public String getShareDis() {
        return shareDis;
    }

    public void setShareDis(String shareDis) {
        this.shareDis = shareDis;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getNormos() {
        return normos;
    }

    public void setNormos(String normos) {
        this.normos = normos;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public float getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(float goodPrice) {
        this.goodPrice = goodPrice;
    }

    public List<GoodPic> getGoodPics() {
        return goodPics;
    }

    public void setGoodPics(List<GoodPic> goodPics) {
        this.goodPics = goodPics;
    }

    public String getGoodDes() {
        return goodDes;
    }

    public void setGoodDes(String goodDes) {
        this.goodDes = goodDes;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public boolean isOff() {
        return isOff;
    }

    public void setOff(boolean off) {
        isOff = off;
    }
}
