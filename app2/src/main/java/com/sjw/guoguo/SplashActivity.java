package com.sjw.guoguo;

import android.os.UserManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuli.commlib.BaseActivity;
import com.sjw.guoguo.bean.UserBean;

import cn.bmob.v3.BmobUser;

/**
 * Created by sjw on 2018/3/19.
 */

public class SplashActivity extends BaseActivity {
    private ImageView mTv;

    @Override
    public int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        mTv = (ImageView) findViewById(R.id.iv_bg);
        mTv.postDelayed(new Runnable() {
            @Override
            public void run() {
                start();
            }
        }, 2000);
    }

    private void start() {
//        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
////        if (userBean == null) {
////            LoginActivity.startActivity(mContext);
////        } else {
////            MainActivity.startActivity(mContext);
////        }
        MainActivity.startActivity(mContext);
        finish();
    }
}
