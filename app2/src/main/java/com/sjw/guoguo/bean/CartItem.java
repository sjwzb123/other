package com.sjw.guoguo.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by sjw on 2018/2/26.
 */
@DatabaseTable(tableName = CartItem.TABLE_NAME)
public class CartItem extends BaseBean {
    public static final String TABLE_NAME = "t_cart";
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "itemId")
    private String itemId;
    @DatabaseField(columnName = "goodsName")
    private String goodsName;
    @DatabaseField(columnName = "goodsDis")
    private String goodsDis;
    @DatabaseField(columnName = "goodsPrice")
    private float goodsPrice;
    @DatabaseField(columnName = "goodsPicUrl")
    private String goodsPicUrl;
    @DatabaseField(columnName = "goodsCount")
    private int goodsCount;
    @DatabaseField(columnName = "itemMoney")
    private float itemMoney;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public float getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(float goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public float getItemMoney() {
        return itemMoney;
    }

    public void setItemMoney(float itemMoney) {
        this.itemMoney = itemMoney;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsDis() {
        return goodsDis;
    }

    public void setGoodsDis(String goodsDis) {
        this.goodsDis = goodsDis;
    }

    public String getGoodsPicUrl() {
        return goodsPicUrl;
    }

    public void setGoodsPicUrl(String goodsPicUrl) {
        this.goodsPicUrl = goodsPicUrl;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }
}
