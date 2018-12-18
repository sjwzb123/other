package com.sjw.guoguo.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuli.commlib.BaseFragment;
import com.kuli.commlib.Utils.SPManager;
import com.kuli.commlib.view.LoadingView;
import com.kuli.commlib.wx.WXShareManager;
import com.sjw.guoguo.GoodsDetalActivity;
import com.sjw.guoguo.LoginActivity;
import com.sjw.guoguo.R;
import com.sjw.guoguo.adapter.GoodsAdapter;
import com.sjw.guoguo.adapter.RecycleViewDivider;
import com.sjw.guoguo.bean.CartItem;
import com.sjw.guoguo.bean.GoodPic;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.db.dao.CartDao;
import com.sjw.guoguo.http.HttpManager;
import com.sjw.guoguo.wxapi.WXEntryActivity;
import com.ushareit.common.lang.ObjectStore;
import com.ushareit.common.utils.TaskHelper;

import java.util.List;

import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.ToastUtil;

import cn.bmob.v3.BmobUser;

/**
 * Created by sjw on 2018/1/30.
 */

public class GoodsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, WXShareManager.ShareListener {
    private RecyclerView mRv;
    private GoodsAdapter mAdapter;
    private CartDao mCartDao;
    private SwipeRefreshLayout mSrl;
    private LoadingView mPd;
    private SPManager spManager;
    private String mGoodsId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartDao = new CartDao(getContext());
        spManager = new SPManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods, null, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mAdapter != null) {
            initData();
        }
        // if (isVisibleToUser) {
        // initData();
        // }
    }

    private void initView(View view) {
        mPd = new LoadingView(getContext(), R.style.CustomDialog);
        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSrl.setRefreshing(true);
        mSrl.setColorSchemeResources(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent);
        mSrl.setOnRefreshListener(this);
        mRv = (RecyclerView) view.findViewById(R.id.rv_goods);
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new GoodsAdapter(getContext());
        mRv.setAdapter(mAdapter);
//        mRv.addItemDecoration(new RecycleViewDivider(
//                getContext(), LinearLayoutManager.HORIZONTAL));
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                GoodsDetalActivity.startActivity(getContext(), mAdapter.getItem(position).getObjectId());
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_add:
                        // ToastUtil.showToast(getActivity(), "add to cart");
//                        if (BmobUser.getCurrentUser() == null) {
//                            LoginActivity.startActivity(getContext());
//                            return;
//                        }
                        Goods goods = mAdapter.getItem(position);
                        addToCart(goods);
                        break;
                    case R.id.btn_share:
                        ObjectStore.add(WXShareManager.SHARE_ITEM_KEY, mAdapter.getItem(position));
                        mGoodsId = mAdapter.getItem(position).getObjectId();
                        WXShareManager wsm = WXShareManager.getInstance(getContext());

                        wsm.shareByWeixin(wsm.new ShareContentPic(R.mipmap.ic_launcher),
                                WXShareManager.WEIXIN_SHARE_TYPE_FRENDS);
                        wsm.addShareListener(GoodsFragment.this);
                        break;

                }
            }
        });

        // initData();

    }

    private void addToCart(final Goods bean) {
        if (!mPd.isShowing()) {
            mPd.show();
        }
        mPd.setMessage("正在加入购物车...");
        HttpManager.addGoodsToCart(getContext(), bean, new HttpManager.HttpCallBack<String>() {
            @Override
            public void onSuccesSingle(String s) {
                ToastUtil.showToast(getContext(), s);
                mPd.dismiss();
            }

            @Override
            public void onSuccess(List<String> list) {

            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToast(getContext(), msg);
                mPd.dismiss();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            if (mAdapter != null) {
                initData();
            }
        }

    }

    private void initData() {
        HttpManager.getAllGoods(new HttpManager.HttpCallBack<Goods>() {
            @Override
            public void onSuccesSingle(Goods goods) {

            }

            @Override
            public void onSuccess(List<Goods> list) {
                mSrl.setRefreshing(false);
                if (list != null) {
                    mAdapter.getData().clear();
                    mAdapter.addMoreItems(list);
                }
            }

            @Override
            public void onError(String msg) {
                mSrl.setRefreshing(false);
            }
        }, 0);

    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onShareSucc() {
        spManager.put(mGoodsId, true);
    }

    @Override
    public void onShareFail(String msg) {
        spManager.put(mGoodsId, false);
    }
}
