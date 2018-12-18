package com.sjw.guoguo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuli.commlib.BaseActivity;
import com.sjw.guoguo.adapter.CartAdapter;
import com.sjw.guoguo.adapter.OrderAdapter;
import com.sjw.guoguo.bean.OrderBean;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.http.HttpManager;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by sjw on 2018/3/19.
 */

public class MyOrderActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRv;
    private OrderAdapter mOrderAdapter;
    private UserBean mUser;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MyOrderActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {

        mRv = (RecyclerView) findViewById(R.id.rv_order);
        mRv.setLayoutManager(new LinearLayoutManager(mContext));
        mOrderAdapter = new OrderAdapter(this);
        mRv.setAdapter(mOrderAdapter);
        mOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_pay:
                        Intent intent = new Intent(MyOrderActivity.this, PayOrderActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
            findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrders();
    }

    private void getOrders() {
        mUser = HttpManager.getUserInfo(this);
        HttpManager.getOrdersByUserId(mUser.getUserId(), new HttpManager.HttpCallBack<OrderBean>() {
            @Override
            public void onSuccesSingle(OrderBean orderBean) {

            }

            @Override
            public void onSuccess(List<OrderBean> list) {
                mOrderAdapter.addMore(list);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
