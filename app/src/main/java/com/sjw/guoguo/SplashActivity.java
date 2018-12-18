package com.sjw.guoguo;

import android.widget.ImageView;

import com.kuli.commlib.BaseActivity;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.http.HttpManager;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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
        HttpManager.fetchUser();
    }

    private void start() {
        MainActivity.startActivity(mContext);
        finish();
    }


}
