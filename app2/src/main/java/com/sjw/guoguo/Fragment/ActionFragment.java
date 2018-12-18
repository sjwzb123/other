package com.sjw.guoguo.Fragment;

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
import com.sjw.guoguo.adapter.ActAdapter;
import com.sjw.guoguo.adapter.RankingAdapter;
import com.sjw.guoguo.bean.ActionBean;
import com.sjw.guoguo.bean.RankBean;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.http.HttpManager;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by sjw on 2018/2/7.
 * 资讯
 */

public class ActionFragment extends BaseFragment {
    private RecyclerView mRlv;
    private ActAdapter mAdapter;
    private String TAG = "ActionFragment";

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
                getNetData();
            }
        }
    }


    private void initView(View view) {
        mRlv = (RecyclerView) view.findViewById(R.id.rl_act);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRlv.setLayoutManager(manager);
        mAdapter = new ActAdapter();
        mRlv.setAdapter(mAdapter);
        //testSaveData();


    }


    private void getNetData() {
        HttpManager.getAllActs(new HttpManager.HttpCallBack<ActionBean>() {
            @Override
            public void onSuccesSingle(ActionBean actionBean) {

            }

            @Override
            public void onSuccess(List<ActionBean> list) {
                mAdapter.refData(list);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
