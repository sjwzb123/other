package com.sjw.guoguo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.CartItem;

import java.util.List;

import com.kuli.commlib.Utils.DebugLog;

/**
 * Created by sjw on 2018/3/4.
 */

public class CartAdapter extends BaseQuickAdapter<CartItem, BaseViewHolder> {
    private String TAG = "CartAdapter";

    public CartAdapter(Context context) {
        super(R.layout.adapter_cart_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, CartItem item) {
        helper.setText(R.id.tv_cart_item_name, item.getGoodsName()).setText(R.id.tv_cart_price, String.valueOf(item.getGoodsPrice())).setText(R.id.et_count, String.valueOf(item.getGoodsCount()));
        helper.addOnClickListener(R.id.iv_add).addOnClickListener(R.id.iv_remove);
        ImageView iv = helper.getView(R.id.iv_cart);
        Glide.with(mContext).load(item.getGoodsPicUrl()).into(iv);
    }

    public void addMore(List<CartItem> list) {
        if (list != null) {
            getData().addAll(list);
            DebugLog.d(TAG, "list size " + list.size() + "  " + getData().size());
            notifyDataSetChanged();
        }
    }

    public void refrData(List<CartItem> list) {
        if (list != null) {
            getData().clear();
            getData().addAll(list);
            notifyDataSetChanged();
        }
    }
    public boolean isEmpty(){
        return mData.isEmpty();
    }
}
