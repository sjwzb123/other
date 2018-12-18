package com.sjw.guoguo.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dingmouren.layoutmanagergroup.viewpager.OnViewPagerListener;
import com.dingmouren.layoutmanagergroup.viewpager.ViewPagerLayoutManager;
import com.kuli.commlib.BaseFragment;
import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.SPManager;
import com.kuli.commlib.Utils.ToastUtil;
import com.kuli.commlib.video.MediaPlayManager;
import com.kuli.commlib.view.LoadingView;
import com.kuli.commlib.view.MediaPlayView;
import com.kuli.commlib.wx.WXShareManager;
import com.sjw.guoguo.GGApp;
import com.sjw.guoguo.R;
import com.sjw.guoguo.adapter.ActAdapter;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.http.HttpManager;
import com.tencent.mm.opensdk.utils.Log;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.ushareit.common.appertizers.Logger;
import com.ushareit.common.lang.ObjectStore;

import java.util.List;

/**
 * Created by sjw on 2018/2/7.
 * 资讯
 */

public class ActionFragment extends BaseFragment implements OnViewPagerListener, WXShareManager.ShareListener {
    private RecyclerView mRlv;
    private ActAdapter mAdapter;
    private String TAG = "ActionFragment";
    private ViewPagerLayoutManager mLayoutManager;
    private SPManager spManager;
    private int mSharePosition;
    private LoadingView mLoadingView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spManager = new SPManager(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_act, null);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mAdapter != null) {
                mLoadingView.show();
                getNetData();
            }
        } else {
            MediaPlayManager.getInstance(ObjectStore.getContext()).stopPlay();
        }
    }


    private void initView(View view) {
        mLoadingView = new LoadingView(getContext());
        mRlv = (RecyclerView) view.findViewById(R.id.rl_act);
        mLayoutManager = new ViewPagerLayoutManager(getContext(), ViewPagerLayoutManager.VERTICAL);
        mLayoutManager.setOnViewPagerListener(this);
        mRlv.setLayoutManager(mLayoutManager);
        mAdapter = new ActAdapter(getContext());
        mRlv.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_add_cart:
                        HttpManager.addGoodsToCart(getContext(), mAdapter.getItem(position), new HttpManager.HttpCallBack<String>() {
                            @Override
                            public void onSuccesSingle(String s) {
                                ToastUtil.showToast(getContext(), s);
                            }

                            @Override
                            public void onSuccess(List<String> list) {

                            }

                            @Override
                            public void onError(String msg) {
                                ToastUtil.showToast(getContext(), msg);
                            }
                        });

                        break;
                    case R.id.btn_share:
                        mSharePosition = position;
                        ObjectStore.add(WXShareManager.SHARE_ITEM_KEY, mAdapter.getItem(position));
                        WXShareManager wsm = WXShareManager.getInstance(getContext());
                        wsm.shareByWeixin(wsm.new ShareContentWebpage("我在这里买的水果好便宜啊，最快38分钟到达", "我在这里买的水果好便宜啊，最快38分钟到达", "http://xiaoershengxian.bmob.site/", R.drawable.share),
                                WXShareManager.WEIXIN_SHARE_TYPE_FRENDS);
                        wsm.addShareListener(ActionFragment.this);
                        break;
                }
            }
        });

    }


    private void getNetData() {
        HttpManager.getAllGoods(new HttpManager.HttpCallBack<Goods>() {
            @Override
            public void onSuccesSingle(Goods goods) {

            }

            @Override
            public void onSuccess(List<Goods> list) {
                mLoadingView.dismiss();
                mAdapter.addMore(list);
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToast(getContext(), "数据获取失败请稍后再试");
                mLoadingView.dismiss();

            }
        }, 0);
    }

    @Override
    public void onInitComplete() {
        DebugLog.d(TAG, "onInitComplete--------");
        View itemView = mRlv.getChildAt(0);
        MediaPlayView videoView = (MediaPlayView) itemView.findViewById(R.id.play_view);
        videoView.startPlay(ObjectStore.getContext(), mAdapter.getItem(0).getPlayUrl());
    }

    @Override
    public void onPageRelease(boolean isNext, int position) {
        int index = 0;
        if (isNext) {
            index = 0;
        } else {
            index = 1;
        }
        View itemView = mRlv.getChildAt(index);
        MediaPlayView videoView = (MediaPlayView) itemView.findViewById(R.id.play_view);
        videoView.stopPlay(ObjectStore.getContext());
        DebugLog.d(TAG, "onPageRelease--------" + position + "  " + isNext);
    }

    @Override
    public void onPageSelected(int position, boolean isBottom) {
        View itemView = mRlv.getChildAt(0);
        String playurl = mAdapter.getItem(position).getPlayUrl();
        MediaPlayView videoView = (MediaPlayView) itemView.findViewById(R.id.play_view);
        videoView.startPlay(ObjectStore.getContext(), playurl);
        DebugLog.d(TAG, "onPageSelected--------" + position + isBottom);
    }

    @Override
    public void onShareSucc() {
        Goods goods = (Goods) ObjectStore.get(WXShareManager.SHARE_ITEM_KEY);
        spManager.put(goods.getObjectId(), true);
        mAdapter.notifyItemChanged(mSharePosition);
    }

    @Override
    public void onShareFail(String msg) {
        Goods goods = (Goods) ObjectStore.get(WXShareManager.SHARE_ITEM_KEY);
        spManager.put(goods.getObjectId(), false);
    }
}
