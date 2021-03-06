package com.sjw.guoguo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kuli.commlib.BaseActivity;
import com.sjw.guoguo.bean.UserBean;

import com.kuli.commlib.Utils.ToastUtil;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by sjw on 2018/3/21.
 */

public class UpdateAddrActivity extends BaseActivity implements View.OnClickListener {
    private static final String INTENT_KEY = "ADDR";
    private EditText mEtAddr;
    private Button mBtnAddr;
    private UserBean mUser;
    private EditText mEtPhone;

    public static void startActivtiy(Context context) {
        Intent intent = new Intent(context, UpdateAddrActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_addr;
    }

    @Override
    public void initView() {

        mEtAddr = (EditText)findViewById(R.id.et_addr);
        mBtnAddr = (Button)findViewById(R.id.btn_addr);
        mEtPhone = (EditText)findViewById(R.id.et_phone);
        mBtnAddr.setOnClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initData() {
        mUser = BmobUser.getCurrentUser(UserBean.class);
        String addr = mUser.getAddr();
        String phone = mUser.getPhone();
        mEtPhone.setText(phone);
        mEtAddr.setText(addr);
    }

    private void updateAddrAndPhone() {
        String addr = mEtAddr.getText().toString().replaceAll(" ", "");
        String phone = mEtPhone.getText().toString().replaceAll(" ", "");
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            ToastUtil.showToast(mContext, "请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(addr)) {
            ToastUtil.showToast(mContext, "配送地址不能为空");
            return;
        }
        mUser.setAddr(addr);
        mUser.setPhone(phone);
        mUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtil.showToast(mContext, "设置地址成功");
                    finish();
                } else {
                    ToastUtil.showToast(mContext, "设置地址失败");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addr:
                updateAddrAndPhone();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
