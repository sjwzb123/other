package com.sjw.guoguo.Fragment;

import android.app.ProgressDialog;
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
import com.kuli.commlib.view.LoadingView;
import com.sjw.guoguo.GoodsDetalActivity;
import com.sjw.guoguo.R;
import com.sjw.guoguo.adapter.GoodsAdapter;
import com.sjw.guoguo.adapter.RecycleViewDivider;
import com.sjw.guoguo.bean.CartItem;
import com.sjw.guoguo.bean.GoodPic;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.db.dao.CartDao;
import com.sjw.guoguo.http.HttpManager;

import java.util.List;

import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.ToastUtil;

/**
 * Created by sjw on 2018/1/30.
 */

public class GoodsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRv;
    private GoodsAdapter mAdapter;
    private CartDao mCartDao;
    private SwipeRefreshLayout mSrl;
    private LoadingView mPd;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCartDao = new CartDao(getContext());
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
        mRv.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.HORIZONTAL));
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
                        Goods goods = mAdapter.getItem(position);
                        CartItem bean = new CartItem();
                        bean.setItemId(goods.getObjectId());
                        DebugLog.d(TAG, "itemId" + goods.getObjectId());
                        bean.setGoodsName(goods.getGoodName());
                        List<GoodPic> list = goods.getGoodPics();
                        if (list != null) {
                            bean.setGoodsPicUrl(goods.getGoodPics().get(0).getPicUrl());
                        }
                        bean.setGoodsPrice(goods.getGoodPrice());
                        addToCart(bean);
                        break;

                }
            }
        });

        // initData();

    }

    private void addToCart(final CartItem bean) {
        if (!mPd.isShowing()) {
            mPd.show();
        }
        mPd.setMessage("正在加入购物车...");
        TaskHelper.exec(new TaskHelper.Task() {
            @Override
            public void execute() throws Exception {
                mCartDao.addToCart(bean);
            }

            @Override
            public void callback(final Exception e) {
                mRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPd.dismiss();
                        if (e == null) {
                            ToastUtil.showToast(getContext(), "加入购物车成功");
                        } else {
                            ToastUtil.showToast(getContext(), "加入购物车失败");
                        }
                    }
                }, 1000);


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
}
