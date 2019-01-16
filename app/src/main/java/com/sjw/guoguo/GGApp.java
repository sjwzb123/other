package com.sjw.guoguo;

import android.app.Application;
import android.content.Context;

import com.kuli.commlib.Utils.DisplayUtils;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.db.dao.UserDao;
import com.sjw.guoguo.util.ObjectStore;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

/**
 * Created by sjw on 2018/3/19.
 */

public class GGApp extends Application {
    public static String APPID = "c71e5fb9ab2e9f0a5db6d340ca4cd36b";
    public static String WXAPPID = "wx1e7e3c280955224d";
    public static Context Context;

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectStore.setContext(this);
        Bmob.initialize(getApplicationContext(), APPID);
        UserDao userDao = new UserDao(this);
        UserBean userBean = userDao.queryForId(DisplayUtils.getUUID(this));
        if (null == userBean) {
            userBean = new UserBean();
            userBean.setUserId(DisplayUtils.getUUID(this));
            userDao.saveUserToDB(userBean);
        }
        registWeiXin();

    }

    private void registWeiXin() {
        WXAPIFactory.createWXAPI(this, WXAPPID, true).registerApp(WXAPPID);
    }
}
