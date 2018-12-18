package com.kuli.commlib;

import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.kuli.commlib.view.LoadingView;

/**
 * Created by sjw on 2017/10/17.
 */

public abstract class BaseActivity extends FragmentActivity {
    public Context mContext;
    public LoadingView mLoadView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        mContext=this;
        mLoadView=new LoadingView(this,R.style.CustomDialog);
        initView();
    }

    public abstract int getContentView();

    public abstract void initView();
}
