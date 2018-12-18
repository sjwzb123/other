package com.sjw.guoguo.adapter;

import android.widget.ImageView;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.bean.RankBean;

import java.util.List;

public class RankingAdapter extends BaseQuickAdapter<RankBean, BaseViewHolder> {
    public RankingAdapter() {
        super(R.layout.item_rank);
    }

    @Override
    protected void convert(BaseViewHolder helper, RankBean item) {
        helper.setText(R.id.tv_place, item.getPlace()).setText(R.id.tv_user_name, item.getUserName());
        ImageView imageView = helper.getView(R.id.iv_head);
        Glide.with(mContext).load(item.getImgUrl()).into(imageView);
        helper.addOnClickListener(R.id.btn_help);
    }

    public void addMore(List<RankBean> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }
}
