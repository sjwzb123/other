package com.sjw.guoguo.db.dao;

import android.content.Context;
import android.text.TextUtils;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.QueryBuilder;
import com.kuli.commlib.Utils.DebugLog;
import com.sjw.guoguo.bean.CartItem;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.db.DBHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sjw on 2018/2/27.
 */

public class UserDao {
    private String TAG = "UserDao";
    private Dao<UserBean, Integer> mDao;
    private DBHelper mDbHelper;

    public UserDao(Context context) {
        mDbHelper = DBHelper.getInstance(context);
        try {
            mDao = mDbHelper.getDao(UserBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUserToDB(UserBean bean) {
        try {
            mDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int update(UserBean item) {
        try {
            return mDao.update(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public UserBean queryForId(String itemId) {
        try {
            List<UserBean> list = mDao.queryForEq("userId", itemId);
            if (list != null && list.size() > 0) {
                return list.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
