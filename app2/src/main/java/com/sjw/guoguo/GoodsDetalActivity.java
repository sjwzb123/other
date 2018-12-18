package com.sjw.guoguo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kuli.commlib.BaseActivity;
import com.sjw.guoguo.bean.CartItem;
import com.sjw.guoguo.bean.GoodPic;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.db.dao.CartDao;
import com.sjw.guoguo.http.HttpManager;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.ToastUtil;

/**
 * Created by sjw on 2018/3/13.
 */

public class GoodsDetalActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = "GoodsDetalActivity";
    public static final String INTENT_KEY = "GOODS";
    private Banner mBanner;
    private Button mBtnAddToCart;
    private CartDao mCartDao;
    private Goods mGoods;
    private TextView mTvGoodsName;
    private TextView mTvPrice;
    private TextView mTvDis;
    private String mId;
    private ImageView mIvBack;

    @Override
    public int getContentView() {
        return R.layout.activity_goods_detail;
    }

    public static void startActivity(Context context, String objId) {
        Intent intent = new Intent(context, GoodsDetalActivity.class);
        intent.putExtra(INTENT_KEY, objId);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBanner = (Banner) findViewById(R.id.banner);
        mBtnAddToCart = (Button) findViewById(R.id.btn_add_cart);
        mBtnAddToCart.setOnClickListener(this);
        mTvGoodsName = (TextView) findViewById(R.id.tv_name);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvDis = (TextView) findViewById(R.id.tv_good_dis);
        //initBannerView();
        initData();
    }

    private void initData() {
        mCartDao = new CartDao(this);
        mId = getIntent().getStringExtra(INTENT_KEY);
        if (!TextUtils.isEmpty(mId)) {
            HttpManager.getGoodsById(mId, new HttpManager.HttpCallBack<Goods>() {
                @Override
                public void onSuccesSingle(Goods goods) {
                    mGoods = goods;
                    bindDataToView();
                }

                @Override
                public void onSuccess(List<Goods> list) {

                }

                @Override
                public void onError(String msg) {

                }
            });
        }

    }

    private void bindDataToView() {
        if (mGoods != null) {
            mTvGoodsName.setText(mGoods.getGoodName());
            mTvDis.setText(mGoods.getGoodDes());
            mTvPrice.setText(String.valueOf(mGoods.getGoodPrice()));
            initBannerView();
        }
    }

    private void initBannerView() {
        List<String> list_path = new ArrayList<>();
        for (GoodPic pic : mGoods.getGoodPics()) {
            list_path.add(pic.getPicUrl());
        }
        // 设置内置样式，共有六种可以点入方法内逐一体验使用。
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        // 设置图片加载器，图片加载器在下方
        mBanner.setImageLoader(new MyLoader());
        // 设置图片网址或地址的集合
        mBanner.setImages(list_path);
        // 设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        mBanner.setBannerAnimation(Transformer.Default);
        mBanner.setBannerTitles(list_path);
        // 设置轮播间隔时间
        mBanner.setDelayTime(3000);
        // 设置是否为自动轮播，默认是“是”。
        mBanner.isAutoPlay(true);
        // 设置指示器的位置，小点点，左中右。
        mBanner.setIndicatorGravity(BannerConfig.CENTER)
                // 必须最后调用的方法，启动轮播图。
                .start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_cart:
                addToCart();
                break;
        }
    }

    private void addToCart() {
        CartItem bean = new CartItem();
        bean.setItemId(mGoods.getObjectId());
        DebugLog.d(TAG, "itemId" + mGoods.getObjectId());
        bean.setGoodsName(mGoods.getGoodName());
        List<GoodPic> list = mGoods.getGoodPics();
        if (list != null) {
            bean.setGoodsPicUrl(mGoods.getGoodPics().get(0).getPicUrl());
        }
        bean.setGoodsPrice(mGoods.getGoodPrice());
        mCartDao.addToCart(bean);
        ToastUtil.showToast(this, "加入购物车成功");
    }

    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }
}
