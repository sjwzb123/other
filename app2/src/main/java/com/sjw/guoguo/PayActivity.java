package com.sjw.guoguo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kuli.commlib.BaseActivity;
import com.kuli.commlib.view.LoadingView;
import com.sjw.guoguo.adapter.PayRvAdapter;
import com.sjw.guoguo.bean.OrderBean;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.db.dao.CartDao;
import com.sjw.guoguo.http.HttpManager;

import java.util.List;

import com.kuli.commlib.Utils.ToastUtil;

import cn.bmob.v3.BmobUser;

import static com.sjw.guoguo.R.id.rb_ps;
import static com.sjw.guoguo.R.id.rb_ziti;
import static com.sjw.guoguo.R.id.tv_addr;

/**
 * Created by sjw on 2018/3/19.
 */

public class PayActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String INTENT_KEY = "ORDER";
    private TextView mTvPhone;
    private RecyclerView mRvGoods;
    private TextView mTvTotal;
    private RadioButton mRbZiTi;
    private RadioButton mRbPS;
    private TextView mTvAddr;
    private RadioButton mRbWX;
    private RadioButton mRbToPay;
    private TextView mTvBtotal;
    private Button mBtnYes;
    private OrderBean mOrderBean;
    private UserBean mUser;
    private PayRvAdapter mAdpter;
    private ImageView mIvBack;
    private LinearLayout mLladdr;
    private CartDao mCartDao;
    private TextView mTvGold;
    private LinearLayout mLlGold;
    private RadioButton mRbTA, mRbHC, mRbAX;
    private RadioGroup mRgStoreAddr;
    private LoadingView mLoading;

    public static final void startActivity(Context context, OrderBean orderBean) {
        Intent intent = new Intent(context, PayActivity.class);
        intent.putExtra(INTENT_KEY, orderBean);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_pay;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mUser = BmobUser.getCurrentUser(UserBean.class);
        mTvAddr.setText(mUser.getAddr());
        mTvPhone.setText(mUser.getPhone());
    }

    @Override
    public void initView() {
        mLoading = new LoadingView(this, R.style.CustomDialog);
        mRbTA = (RadioButton) findViewById(R.id.rb_TA);
        mTvPhone = (TextView) findViewById(R.id.tv_phone);
        mRvGoods = (RecyclerView) findViewById(R.id.pay_rl_goods);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvGoods.setLayoutManager(manager);
        mTvTotal = (TextView) findViewById(R.id.tv_total);
        mRbZiTi = (RadioButton) findViewById(rb_ziti);
        mRbPS = (RadioButton) findViewById(rb_ps);
        mTvAddr = (TextView) findViewById(tv_addr);
        mTvAddr.setOnClickListener(this);
        mRbWX = (RadioButton) findViewById(R.id.rb_wexin);
        mRbToPay = (RadioButton) findViewById(R.id.rb_to_pay);
        mTvBtotal = (TextView) findViewById(R.id.tv_btotal);
        mBtnYes = (Button) findViewById(R.id.btn_yes);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mLladdr = (LinearLayout) findViewById(R.id.ll_addr);
        mLladdr.setOnClickListener(this);
        mBtnYes = (Button) findViewById(R.id.btn_yes);
        mBtnYes.setOnClickListener(this);
        mTvGold = (TextView) findViewById(R.id.tv_gold);
        mLlGold = (LinearLayout) findViewById(R.id.ll_gold);
        mRgStoreAddr = (RadioGroup) findViewById(R.id.rg_addr);
        mRgStoreAddr.setOnCheckedChangeListener(this);
        initData();
    }

    private void initData() {
        mUser = BmobUser.getCurrentUser(UserBean.class);
        mOrderBean = (OrderBean) getIntent().getSerializableExtra(INTENT_KEY);
        bindDataToView();
        mCartDao = new CartDao(mContext);
    }

    private void bindDataToView() {
        mAdpter = new PayRvAdapter(this);
        mRvGoods.setAdapter(mAdpter);
        mAdpter.addMore(mOrderBean.getList());
        mTvTotal.setText(String.valueOf(mOrderBean.getOrderMoney()));
        //int needGold = (int) (mOrderBean.getOrderMoney() / 10) * 10;
//        int dif = needGold / 10;
//        if (needGold > mUser.getBalance() && mOrderBean.getOrderMoney() > 10) {
//            mLlGold.setVisibility(View.GONE);
//        } else {
//            mOrderBean.setDif(dif);
//            mLlGold.setVisibility(View.VISIBLE);
//            mTvGold.setText(String.valueOf(dif));
//        }
        mTvBtotal.setText(String.valueOf(mOrderBean.getOrderMoney()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_addr:

                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.ll_addr:
                UpdateAddrActivity.startActivtiy(this);
                break;
            case R.id.btn_yes:
                subOrder();
                break;
        }
    }

    private void subOrder() {

        String addr = mTvAddr.getText().toString();
        String phone = mTvPhone.getText().toString();
        if (TextUtils.isEmpty(addr) || TextUtils.isEmpty(phone)) {
            ToastUtil.showToast(this, "请填写联系方式和收货地址");
            return;
        }
        if (mLoading != null && !mLoading.isShowing()) {
            mLoading.show();
        }
        mLoading.show();
        mOrderBean.setOrderTime(System.currentTimeMillis());
        mOrderBean.setUserId(mUser.getObjectId());
        mOrderBean.setPhone(phone);
        mOrderBean.setOrderAddr(addr);
        mOrderBean.setState(OrderBean.OrderState.Unpaid);
        HttpManager.subOrder(mOrderBean, new HttpManager.HttpCallBack<OrderBean>() {
            @Override
            public void onSuccesSingle(OrderBean orderBean) {

            }

            @Override
            public void onSuccess(List<OrderBean> list) {
                mLoading.dismiss();
                ToastUtil.showToast(mContext, "订单提交成功");
                MyOrderActivity.startActivity(mContext);
                mCartDao.deleCartItemList(mOrderBean.getList());
                finish();
            }

            @Override
            public void onError(String msg) {
                mLoading.dismiss();
                ToastUtil.showToast(mContext, "订单提交失败，请稍后再试");
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (mOrderBean == null) {
            return;
        }
        switch (checkedId) {

            case R.id.rb_TA:
                mOrderBean.setOrderAddr("新鲜果业—泰安街店");
                break;
            case R.id.rb_HC:
                mOrderBean.setOrderAddr("新鲜果业—荟萃路店");
                break;
            case R.id.rb_AX:
                mOrderBean.setOrderAddr("新鲜果业—爱乡路店");
                break;
        }
    }
}
