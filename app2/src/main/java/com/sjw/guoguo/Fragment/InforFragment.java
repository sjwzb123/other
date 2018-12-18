package com.sjw.guoguo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuli.commlib.BaseFragment;
import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.ToastUtil;
import com.sjw.guoguo.R;
import com.sjw.guoguo.TestActivity;
import com.sjw.guoguo.adapter.RankingAdapter;
import com.sjw.guoguo.bean.RankBean;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.http.HttpManager;

import java.util.List;
import java.util.logging.Logger;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by sjw on 2018/2/7.
 * 资讯
 */

public class InforFragment extends BaseFragment {
    private RecyclerView mRlv;
    private RankingAdapter mAdapter;
    private String TAG = "InforFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_infor, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }


    private void initView(View view) {
        mRlv = (RecyclerView) view.findViewById(R.id.rl_ranking);
        GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        mRlv.setLayoutManager(manager);
        mAdapter = new RankingAdapter();
        mRlv.setAdapter(mAdapter);
        //testSaveData();
        getNetData();


    }

    private void testSaveData() {
        UserBean userBean = BmobUser.getCurrentUser(UserBean.class);
        for (int i = 0; i < 50; i++) {
            RankBean bean = new RankBean();
            bean.setImgUrl("http://bmob-cdn-17722.b0.upaiyun.com/2018/03/30/5628907140c9541a80cce43525d3472d.png");
            bean.setPlace("第" + i + "名");
            bean.setRankGold(String.valueOf(50 + i));
            bean.setUserId(userBean.getObjectId());
            bean.setUserName(userBean.getUsername());
            bean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    DebugLog.d(TAG, "s");
                    if (e == null) {
                        DebugLog.d(TAG, "s");
                    } else {

                    }
                }
            });

        }

    }

    private void getNetData() {
        HttpManager.getRankList(new HttpManager.HttpCallBack<RankBean>() {
            @Override
            public void onSuccesSingle(RankBean rankBean) {

            }

            @Override
            public void onSuccess(List<RankBean> list) {
                mAdapter.addMore(list);
            }

            @Override
            public void onError(String msg) {
                ToastUtil.showToast(getContext(), msg);
            }
        }, mAdapter.getData().size());
    }
}
