package com.sjw.guoguo.db;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sjw.guoguo.bean.CartItem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by sjw on 2018/2/26.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "cart.db";
    private static final int DB_VERSOIN = 1;

    // 用来存放 Dao 的键值对集合
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private static DBHelper instance;

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper(context);
                }
            }
        }
        return instance;
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSOIN);
    }

    /**
     * 创建表的操作
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, CartItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里进行更新表操作
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, CartItem.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过类来获得指定的 dao
     * @param clazz
     * @return
     * @throws SQLException
     */
    /*
     * public synchronized Dao getDao(Class clazz) throws SQLException {
     * Dao dao = null;
     * String className = clazz.getSimpleName();
     * if(daos.containsKey(className)){
     * dao = super.getDao(clazz);
     * daos.put(className,dao);
     * }
     * return dao;
     * }
     */

    /**
     * 释放资源
     */
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

}
