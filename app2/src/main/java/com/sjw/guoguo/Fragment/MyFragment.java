package com.sjw.guoguo.Fragment;

import com.kuli.commlib.BaseFragment;
import com.sjw.guoguo.MyOrderActivity;
import com.sjw.guoguo.R;
import com.sjw.guoguo.UpdateAddrActivity;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.http.HttpManager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mTvIntegrate = (TextView)view.findViewById(R.id.tv_integrate);
        mIvHead = (ImageView)view.findViewById(R.id.iv_head);
        mTvCode = (TextView)view.findViewById(R.id.tv_code);
        mRlOrder = (RelativeLayout)view.findViewById(R.id.rl_order);
        mRlOrder.setOnClickListener(this);
        mRlAddr = (RelativeLayout)view.findViewById(R.id.rl_addr);
        mRlAddr.setOnClickListener(this);

    }

    private void initData() {
        mUser = HttpManager.getUserInfo();
        bindDataToView();
    }

    private void bindDataToView() {
        mTvCode.setText(mUser.getUsername());
        mTvIntegrate.setText(String.valueOf(mUser.getIntegral()) + "分");
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
        }
    }
}
