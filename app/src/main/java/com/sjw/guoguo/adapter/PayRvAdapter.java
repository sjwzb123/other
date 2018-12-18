package com.sjw.guoguo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.CartItem;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by sjw on 2018/3/20.
 */

public class PayRvAdapter extends BaseQuickAdapter<CartItem, BaseViewHolder> {
    public PayRvAdapter(Context context) {
        super(R.layout.item_rv);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartItem item) {
        ImageView iv = helper.getView(R.id.iv_goods);
        String url = item.getGoodsPicUrl();
        if (!TextUtils.isEmpty(url)) {
            Glide.with(mContext).load(item.getGoodsPicUrl()).into(iv);
        }

    }

    public void addMore(List<CartItem> list) {
        if (list != null) {
            getData().addAll(list);
            notifyDataSetChanged();
        }
    }
}
