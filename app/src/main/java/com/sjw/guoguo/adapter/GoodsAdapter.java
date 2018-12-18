package com.sjw.guoguo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuli.commlib.Utils.ImageLoadManager;
import com.kuli.commlib.Utils.SPManager;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.GoodPic;
import com.sjw.guoguo.bean.Goods;

import java.util.List;

/**
 * Created by sjw on 2018/1/31.
 */

public class GoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> implements View.OnClickListener {
    private Context mContext;
    private SPManager mSpManager;

    public GoodsAdapter(Context context) {
        super(R.layout.item_good);
        this.mContext = context;
        mSpManager = new SPManager(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        helper.setText(R.id.tv_name, item.getGoodName()).setText(R.id.tv_price, String.valueOf(item.getGoodPrice() + "元/" + item.getNormos()));
        helper.setText(R.id.tv_share_dis, item.getShareDis());
        helper.addOnClickListener(R.id.btn_share);
        helper.addOnClickListener(R.id.iv_add);
        helper.setText(R.id.tv_scale, "已售：" + String.valueOf(item.getSalesCount()) + "件");
        ImageView imageView = helper.getView(R.id.iv_goods);
        List<GoodPic> list = item.getGoodPics();
        boolean isShared = (boolean) mSpManager.getSharedPreference(item.getObjectId(), false);
        item.setShred(isShared);
        helper.setVisible(R.id.btn_share, !TextUtils.isEmpty(item.getShareDis()));
        helper.setText(R.id.tv_vip_price, String.format("%s %s %s", "会员专享:", item.getGoodCutPrice(), "/" + item.getNormos()));
        Button btnShare = helper.getView(R.id.btn_share);
        if (item.isShred()) {
            helper.setText(R.id.btn_share, "已经分享");
            btnShare.setBackgroundColor(mContext.getResources().getColor(R.color.color_a3a3a3));
            btnShare.setEnabled(false);
        } else {
            helper.setText(R.id.btn_share, "去分享");
            btnShare.setBackgroundResource(R.drawable.shape_btn_bg);
            btnShare.setEnabled(true);
        }

        if (list != null) {
            // Glide.with(mContext).load(item.getGoodPics().get(0).getPicUrl()).into(imageView);
            ImageLoadManager.loadRoundImage(mContext, item.getGoodPics().get(0).getPicUrl(), imageView);
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
