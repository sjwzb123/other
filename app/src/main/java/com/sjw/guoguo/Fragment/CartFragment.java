package com.sjw.guoguo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuli.commlib.BaseFragment;
import com.sjw.guoguo.PayActivity;
import com.sjw.guoguo.R;
import com.sjw.guoguo.adapter.CartAdapter;
import com.sjw.guoguo.adapter.RecycleViewDivider;
import com.sjw.guoguo.bean.CartItem;
import com.sjw.guoguo.bean.OrderBean;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.db.dao.CartDao;
import com.sjw.guoguo.http.HttpManager;

import java.util.List;

import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.ToastUtil;

/**
 * Created by sjw on 2018/2/26.
 */

public class CartFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView mCartRV;
    private CartDao mCartDao;
    private CartAdapter mCartAdapter;
    private TextView mTotalMoney;
    private TextView mBntPay;
    private UserBean mUserBean;
    private TextView mTvEmpty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartDao = new CartDao(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        // initData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        DebugLog.d(TAG, "setUserVisibleHint " + isVisibleToUser);
        if (isVisibleToUser) {
            if (mCartAdapter != null) {
                initData();
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            if (mCartAdapter != null) {
                initData();

            }

        }
        // initData();
    }

    private void initView(View view) {
        getEmpetyView();
        mCartAdapter = new CartAdapter(getContext());
        mCartAdapter.setEmptyView(mTvEmpty);
        mCartRV = (RecyclerView) view.findViewById(R.id.rv_cart);
        mBntPay = (TextView) view.findViewById(R.id.btn_pay);
        mBntPay.setOnClickListener(this);
        mCartRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mCartRV.setAdapter(mCartAdapter);
        mCartRV.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.HORIZONTAL));
        mCartAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                final CartItem item = mCartAdapter.getItem(position);
                switch (view.getId()) {
                    case R.id.iv_add:
                        changeCount(item, 1);
                        break;
                    case R.id.iv_remove:
                        changeCount(item, -1);
                        break;

                }
                updateTotalMoney();
            }
        });
        mTotalMoney = (TextView) view.findViewById(R.id.tv_total_money);

    }

    private synchronized void changeCount(CartItem item, int offset) {
        int count = item.getGoodsCount();
        item.setGoodsCount(count + offset);
        if (item.getGoodsCount() <= 0) {
            int v = mCartDao.deleCartItem(item);
            if (v > 0) {
                mCartAdapter.getData().remove(item);
                ToastUtil.showToast(getContext(), "已删除该项目");
                mCartAdapter.notifyDataSetChanged();
            }
        } else {
            item.setItemMoney(item.getGoodsPrice() * item.getGoodsCount());
            int res = mCartDao.update(item);
            Log.d(TAG, "res " + res);
            if (res > 0) {
                mCartAdapter.notifyDataSetChanged();
            }
        }

    }

    private float money;

    private void initData() {
        mUserBean = HttpManager.getUserInfo(getContext());
        List<CartItem> list = mCartDao.queryAll();
        DebugLog.d(TAG, "list " + list.size());
        mCartAdapter.refrData(list);
        updateTotalMoney();

    }

    private void updateTotalMoney() {
        money = mCartDao.getTotalMoney();
        mTotalMoney.setText(String.valueOf(money));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                createOrder();
                break;

        }
    }

    private void createOrder() {
        if (mCartAdapter.isEmpty()) {
            ToastUtil.showToast(getContext(), "购物车为空");
            return;
        }
        OrderBean bean = new OrderBean();
        bean.setList(mCartAdapter.getData());
        bean.setOrderAddr(mUserBean.getAddr());
        bean.setOrderMoney(money);
        PayActivity.startActivity(getContext(), bean);
    }

    private void getEmpetyView() {
        mTvEmpty = new TextView(getContext());
        mTvEmpty.setText("购物车还没有东西，亲!");
        mTvEmpty.setTextSize(20);
        mTvEmpty.setGravity(Gravity.CENTER);
        mTvEmpty.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

}
