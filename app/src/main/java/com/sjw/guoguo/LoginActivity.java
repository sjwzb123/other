package com.sjw.guoguo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kuli.commlib.BaseActivity;
import com.kuli.commlib.wx.WXShareManager;
import com.sjw.guoguo.adapter.PayRvAdapter;
import com.sjw.guoguo.bean.UserBean;

import com.kuli.commlib.Utils.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by sjw on 2018/3/19.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtPhone;
    private Button mBtnLogin;
    private EditText mEtPwd;
    private final int SIGNUP_202 = 202;
    private final int LOGIN_ERROR_101 = 101;
    private Button mBtnCode;
    private long lastCodeTime;
    private static final long CODE_WAITE_TIME = 60 * 1000;
    private Button mBtnWeiXinLogin;

    public static final void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mBtnWeiXinLogin = (Button) findViewById(R.id.btn_weixin);
        mBtnWeiXinLogin.setOnClickListener(this);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtnLogin.setOnClickListener(this);
        mBtnCode = (Button) findViewById(R.id.btn_code);
        mBtnCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginOrSignUp();
                break;
            case R.id.btn_code:
                long currTime = System.currentTimeMillis();
                if (currTime - lastCodeTime >= CODE_WAITE_TIME) {
                    lastCodeTime = currTime;
                    getCode();
                } else {
                    ToastUtil.showToast(this, "您操作太频繁请稍后再试");
                }
                break;
            case R.id.btn_weixin:
                weixinLogin();
                break;
        }
    }

    private void loginOrSignUp() {
        String phone = mEtPhone.getText().toString().replaceAll(" ", "");
        String pwd = mEtPwd.getText().toString().replaceAll(" ", "");
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pwd)) {
            ToastUtil.showToast(this, "手机号或密码不能为空");
            return;
        }
        mLoadView.show();
        mLoadView.setMessage("正在登录...");
        ToastUtil.showToast(mContext, "登录成功");
        mLoadView.dismiss();
        finish();
//        BmobUser.signOrLoginByMobilePhone(phone, pwd, new LogInListener<UserBean>() {
//            @Override
//            public void done(UserBean userBean, BmobException e) {
//                mLoadView.dismiss();
//                if (e == null) {
//                    ToastUtil.showToast(mContext, "登录成功");
//                    finish();
//                } else {
//                    ToastUtil.showToast(mContext, "登录失败" + e.toString());
//                }
//            }
//        });
    }

    private void getCode() {
        String phoneNum = mEtPhone.getText().toString();
        BmobSMS.requestSMSCode(phoneNum, "小二生鲜", new QueryListener<Integer>() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    ToastUtil.showToast(mContext, "短信验证码发送成功");
                } else {
                    ToastUtil.showToast(mContext, "短信验证码发送失败，请稍后再试");
                    //71851607
                }
            }
        });
    }

    private void weixinLogin() {
        WXShareManager.getInstance(this).weixinLogin();
    }

}
