package com.sjw.guoguo.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.CartItem;
import com.sjw.guoguo.bean.OrderBean;

import java.util.List;

import com.kuli.commlib.Utils.DateUtils;

/**
 * Created by sjw on 2018/4/8.
 */

public class OrderAdapter extends BaseQuickAdapter<OrderBean, BaseViewHolder> {
    public OrderAdapter(Context context) {
        super(R.layout.item_order);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setText(R.id.tv_order_id, item.getObjectId());
        StringBuffer buffer = new StringBuffer();
        for (CartItem cartItem : item.getList()) {
            buffer.append(cartItem.getGoodsName()).append("\u3000\u3000").append(cartItem.getGoodsCount()).append(" x ").append("￥ " + cartItem.getGoodsPrice()).append("\n");
        }
        buffer.append("送货门店：" + item.getStoreAddr()).append("\n");
        buffer.append("电 话：" + item.getPhone()).append("\n");
        buffer.append("地 址：" + item.getOrderAddr()).append("\n");
        buffer.append("时 间：" + DateUtils.getDateTimeFromMillisecond(item.getOrderTime())).append("\n");
        buffer.append("优 惠：" + item.getDif());
        helper.addOnClickListener(R.id.btn_pay);
        helper.setText(R.id.tv_order_dis, buffer.toString());
        helper.setText(R.id.tv_total_money, String.valueOf("大概合计：" + (item.getOrderMoney() - item.getDif()) + "元"));

    }

    public void addMore(List<OrderBean> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }
}
