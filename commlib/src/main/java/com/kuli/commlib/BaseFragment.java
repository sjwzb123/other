package com.kuli.commlib;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuli.commlib.view.LoadingView;

/**
 * Created by sjw on 2017/10/17.
 */

public class BaseFragment extends Fragment {
    public LoadingView mPd;
    public String TAG = this.getClass().getName();
    private String title;
    private int iconId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mPd=new LoadingView(getContext(),R.style.CustomDialog);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

}
