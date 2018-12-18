package com.sjw.guoguo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.ActionBean;

import java.util.List;

public class ActAdapter extends BaseQuickAdapter<ActionBean, BaseViewHolder> {
    public ActAdapter() {
        super(R.layout.item_act);
    }

    @Override
    protected void convert(BaseViewHolder helper, ActionBean item) {

    }

    public void addMore(List<ActionBean> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void refData(List<ActionBean> list) {
        mData.clear();
        if (list != null) {
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }
}
