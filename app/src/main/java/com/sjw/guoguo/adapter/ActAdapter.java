package com.sjw.guoguo.adapter;

import android.content.Context;
import android.telephony.SmsManager;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuli.commlib.Utils.SPManager;
import com.kuli.commlib.wx.WXShareManager;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.http.HttpManager;

import java.util.List;

public class ActAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    private SPManager spManager;
    private UserBean user;
    public ActAdapter(Context context) {
        super(R.layout.item_act);
        spManager = new SPManager(context);
        user= HttpManager.getCurrUser();
        user=new UserBean();
        user.setObjectId("123456");
        user.setPhone("13164232910");
        mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        helper.setText(R.id.tv_name, item.getGoodName()).setText(R.id.tv_des, item.getGoodDes());
        helper.setText(R.id.tv_price, String.valueOf(String.format("%s %s", item.getGoodPrice(), "/" + item.getNormos())));
        helper.setText(R.id.tv_act_dis, item.getShareDis());
        helper.setText(R.id.tv_act_price, String.valueOf(String.format("%s %s", item.getGoodCutPrice(), "/" + item.getNormos())));
        helper.addOnClickListener(R.id.btn_add_cart);
        helper.addOnClickListener(R.id.btn_share);
        boolean isShare = (boolean) spManager.getSharedPreference(item.getObjectId(), false);
        item.setShred(isShare);
        Button btnShare=helper.getView(R.id.btn_share);
        if (item.isShred()) {
            helper.setText(R.id.btn_share, "已经分享");
            btnShare.setBackgroundColor(mContext.getResources().getColor(R.color.color_a3a3a3));
            btnShare.setEnabled(false);
        } else {
            helper.setText(R.id.btn_share, "分享朋友圈");
            btnShare.setBackgroundColor(mContext.getResources().getColor(R.color.color_ff62ac));
            btnShare.setEnabled(true);
        }
    }

    public void addMore(List<Goods> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void refData(List<Goods> list) {
        mData.clear();
        if (list != null) {
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }

}
