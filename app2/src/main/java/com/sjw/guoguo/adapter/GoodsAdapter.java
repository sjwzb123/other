package com.sjw.guoguo.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.GoodPic;
import com.sjw.guoguo.bean.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjw on 2018/1/31.
 */

public class GoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> implements View.OnClickListener {
    private Context mContext;

    public GoodsAdapter(Context context) {
        super(R.layout.item_good);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        helper.setText(R.id.tv_name, item.getGoodName()).setText(R.id.tv_price, String.valueOf(item.getGoodPrice() + "元/斤"));
        helper.addOnClickListener(R.id.iv_add);
        ImageView imageView = helper.getView(R.id.iv_goods);
        List<GoodPic> list=item.getGoodPics();
        if (list!=null){
            Glide.with(mContext).load(item.getGoodPics().get(0).getPicUrl()).into(imageView);
        }

    }

    public void addMoreItems(List<Goods> goodsList) {
        if (goodsList != null) {
            mData.addAll(goodsList);

        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
