package com.sjw.guoguo;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kuli.commlib.BaseActivity;
import com.kuli.commlib.Utils.ToastUtil;
import com.sjw.guoguo.bean.UserBean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class VipActivity extends BaseActivity {
    private Button mBtnVip;
    private RadioButton mRbYean;
    private RadioButton mRbMonth;
    private RadioGroup mRgVip;
    private UserBean mUser;
    private ImageView mIvBack;
    private static final int VIP_TYPE_YEAR = 1;
    private static final int VIP_TYPE_MOHTH = 2;
    private String TAG = "VipActivity";

    public static void startVipActivity(Context context) {
        Intent intent = new Intent(context, VipActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_vip;
    }

    @Override
    public void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRbMonth = (RadioButton) findViewById(R.id.rb_vip_moth);
        mRbYean = (RadioButton) findViewById(R.id.rb_vip_year);
        mRgVip = (RadioGroup) findViewById(R.id.rg_vip);
        // mRgVip.setOnCheckedChangeListener(ne);
        mBtnVip = (Button) findViewById(R.id.btn_vip);
        mBtnVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // upUserVip();
            }
        });

    }

    private void upUserVip() {
        mLoadView.show();
        mUser = BmobUser.getCurrentUser(UserBean.class);
        mUser = new UserBean();
        mUser.setObjectId("123456");
        mUser.setPhone("13164232910");
        if (mRbYean.isChecked()) {
            if (mUser != null) {
                mUser.setVip(true);
                mUser.setVipType(VIP_TYPE_YEAR);
                mUser.setVipStartTime(System.currentTimeMillis());
            }
        } else {
            mUser.setVip(true);
            mUser.setVipType(VIP_TYPE_MOHTH);
            mUser.setVipStartTime(System.currentTimeMillis());
        }
        mUser.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                mLoadView.dismiss();
                if (e == null) {
                    ToastUtil.showToast(mContext, "恭喜您，您已经成为我们的会员了");
                } else {
                    ToastUtil.showToast(mContext, "网络问题，请稍后再试");
                }
            }
        });
    }


}
