package com.sjw.guoguo.Fragment;

import com.kuli.commlib.BaseFragment;
import com.kuli.commlib.Utils.SPManager;
import com.sjw.guoguo.LoginActivity;
import com.sjw.guoguo.MyOrderActivity;
import com.sjw.guoguo.R;
import com.sjw.guoguo.UpdateAddrActivity;
import com.sjw.guoguo.UploadActivity;
import com.sjw.guoguo.VipActivity;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.http.HttpManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.b.V;

/**
 * Created by sjw on 2018/3/14.
 */

public class MyFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mIvHead;
    private TextView mTvCode;
    private TextView mTvIntegrate;
    private RelativeLayout mRlOrder;
    private RelativeLayout mRlAddr;
    private UserBean mUser;
    private TextView mTvLogin;
    private LinearLayout mLlContent;
    private TextView mTvMember;
    private RelativeLayout mRlVip;
    private RelativeLayout mRlAdmin;
    private RelativeLayout mRlLoginOut;
    private SPManager spManager;
    private LinearLayout mLlTop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spManager = new SPManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initView(View view) {
        mLlTop = (LinearLayout) view.findViewById(R.id.ll_top);
        mRlLoginOut = (RelativeLayout) view.findViewById(R.id.rl_loginout);
        mRlLoginOut.setOnClickListener(this);
        mRlAdmin = (RelativeLayout) view.findViewById(R.id.rl_admin);
        mRlAdmin.setOnClickListener(this);
        mRlVip = (RelativeLayout) view.findViewById(R.id.rl_vip);
        mRlVip.setOnClickListener(this);
        mTvLogin = (TextView) view.findViewById(R.id.tv_login);
        mTvLogin.setOnClickListener(this);
        mLlContent = (LinearLayout) view.findViewById(R.id.ll_content);
        mTvIntegrate = (TextView) view.findViewById(R.id.tv_integrate);
        mIvHead = (ImageView) view.findViewById(R.id.iv_head);
        mTvCode = (TextView) view.findViewById(R.id.tv_code);
        mRlOrder = (RelativeLayout) view.findViewById(R.id.rl_order);
        mRlOrder.setOnClickListener(this);
        mRlAddr = (RelativeLayout) view.findViewById(R.id.rl_addr);
        mRlAddr.setOnClickListener(this);
        mTvMember = (TextView) view.findViewById(R.id.tv_member);

    }

    private void initData() {
        //mUser = HttpManager.getUserInfo(getContext());
        // HttpManager.fetchUser();
        mUser = BmobUser.getCurrentUser(UserBean.class);
        mUser=new UserBean();
        mUser.setObjectId("123456");
        mUser.setPhone("13164232910");
        if (mUser != null) {
            mLlTop.setVisibility(View.VISIBLE);
            mLlContent.setVisibility(View.VISIBLE);
            mTvLogin.setVisibility(View.GONE);
            bindDataToView();
        } else {
            mLlTop.setVisibility(View.GONE);
            mLlContent.setVisibility(View.GONE);
            mTvLogin.setVisibility(View.VISIBLE);
        }

    }

    private void bindDataToView() {
        if (mUser != null) {
            mTvCode.setText(mUser.getMobilePhoneNumber());
            mTvIntegrate.setText(String.valueOf(mUser.getIntegral()) + "分");
            if (!mUser.isVip()) {
                mTvMember.setText("普通用户");
            } else {
                mTvMember.setText("VIP会员，可享受会员价");
            }
            if (mUser.isAdmin()) {
                mRlAdmin.setVisibility(View.VISIBLE);
            } else {
                mRlAdmin.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_order:
                MyOrderActivity.startActivity(getContext());
                break;
            case R.id.rl_addr:
                UpdateAddrActivity.startActivtiy(getContext());
                break;
            case R.id.tv_login:
                LoginActivity.startActivity(getContext());
                break;
            case R.id.rl_vip:
                VipActivity.startVipActivity(getContext());
                break;
            case R.id.rl_admin:
                UploadActivity.startActivity(getContext());
                break;
            case R.id.rl_loginout:
                spManager.clear();
                HttpManager.loginOut(); //推出登录
                initData();
                break;
        }
    }
}
