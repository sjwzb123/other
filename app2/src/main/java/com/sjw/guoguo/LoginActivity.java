package com.sjw.guoguo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kuli.commlib.BaseActivity;
import com.sjw.guoguo.adapter.PayRvAdapter;
import com.sjw.guoguo.bean.UserBean;

import com.kuli.commlib.Utils.ToastUtil;

import cn.bmob.v3.exception.BmobException;
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
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                loginOrSignUp();
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
        if (!TextUtils.isEmpty(phone)) {
            final UserBean user = new UserBean();
            user.setUsername(phone);
            user.setPassword(pwd);
            user.setBalance(100);
            user.signUp(new SaveListener<UserBean>() {
                @Override
                public void done(UserBean userBean, BmobException e) {

                    if (e == null) {
                      //  mLoadView.dismiss();
//                        ToastUtil.showToast(mContext, "登录成功");
//                        MainActivity.startActivity(mContext);
                        login(user);
                    } else {
                        if (e.getErrorCode() == SIGNUP_202) {
                            login(user);
                        } else {
                            ToastUtil.showToast(mContext, e.getMessage());
                        }


                    }
                }
            });
        }
    }

    private void login(UserBean user) {
        user.login(new SaveListener<UserBean>() {

            @Override
            public void done(UserBean userBean, BmobException e) {
                mLoadView.dismiss();
                if (e == null) {
                    ToastUtil.showToast(mContext, "登录成功");
                    MainActivity.startActivity(mContext);
                } else {
                    if (e.getErrorCode() == LOGIN_ERROR_101) {
                        ToastUtil.showToast(mContext, "登录失败，账户或密码错误");
                    }

                }

            }
        });
    }
}
