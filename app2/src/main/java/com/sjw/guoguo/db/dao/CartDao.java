package com.sjw.guoguo.db.dao;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sjw.guoguo.bean.CartItem;
import com.sjw.guoguo.db.DBHelper;

import java.sql.SQLException;
import java.util.List;

import com.kuli.commlib.Utils.DebugLog;

/**
 * Created by sjw on 2018/2/27.
 */

public class CartDao {
    private String TAG = "CartDao";
    private Dao<CartItem, Integer> mDao;
    private DBHelper mDbHelper;

    public CartDao(Context context) {
        mDbHelper = DBHelper.getInstance(context);
        try {
            mDao = mDbHelper.getDao(CartItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToCart(CartItem bean) {
        try {
            CartItem item = queryForId(bean.getItemId());
            if (item != null) {
                item.setGoodsCount(item.getGoodsCount() + 1);
                item.setItemMoney(item.getGoodsCount() * item.getGoodsPrice());
                mDao.update(item);
            } else {
                bean.setGoodsCount(bean.getGoodsCount() + 1);
                bean.setItemMoney(bean.getGoodsCount() * bean.getGoodsPrice());
                int i = mDao.create(bean);
                DebugLog.d(TAG, "addToCart" + i);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<CartItem> queryAll() {
        try {
            List<CartItem> list = mDao.queryForAll();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int update(CartItem item) {
        try {
            return mDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public CartItem queryForId(String itemId) {
        try {
            List<CartItem> list = mDao.queryForEq("itemId", itemId);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getTotalMoney() {

        try {
            QueryBuilder<CartItem, Integer> qb = mDao.queryBuilder();
            // select 2 aggregate functions as the return
            qb.selectRaw("SUM(itemMoney)");
            GenericRawResults<String[]> results = mDao.queryRaw(qb.prepareStatementString());
            String[] values = results.getFirstResult();
            if (values != null && values.length > 0) {
                String v = values[0];
                if (TextUtils.isEmpty(v)) {
                    return 0;
                }
                return Float.valueOf(v);
            }
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public void deleCartItemList(List<CartItem> list){
        try {
            mDao.delete(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized int deleCartItem(CartItem item) {
        try {
            int reInt = mDao.delete(item);
            return reInt;
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }
}
