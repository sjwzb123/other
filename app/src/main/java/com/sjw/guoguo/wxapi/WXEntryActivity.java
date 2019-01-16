package com.sjw.guoguo.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuli.commlib.BaseActivity;
import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.ToastUtil;
import com.kuli.commlib.wx.WXShareManager;
import com.kuli.commlib.wx.WeixiShareUtil;
import com.sjw.guoguo.GGApp;
import com.sjw.guoguo.R;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.http.HttpManager;
import com.sjw.guoguo.util.ObjectStore;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import java.util.List;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI mWXAPI;
    private String TAG = "wxshare";
    private ImageView ivImg;
    private TextView mTvPrice;
    private Button mBtnAdd;
    private Goods mShareGoods;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
    }

    public void initView() {
        mShareGoods = (Goods) ObjectStore.get(WXShareManager.SHARE_ITEM_KEY);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivImg = (ImageView) findViewById(R.id.iv_goods);
        mBtnAdd = (Button) findViewById(R.id.btn_add_cart);
        mTvPrice = (TextView) findViewById(R.id.tv_share_price);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpManager.addGoodsToCart(WXEntryActivity.this, mShareGoods, new HttpManager.HttpCallBack<String>() {
                    @Override
                    public void onSuccesSingle(String s) {
                        ToastUtil.showToast(WXEntryActivity.this, s);
                    }

                    @Override
                    public void onSuccess(List<String> list) {

                    }

                    @Override
                    public void onError(String msg) {
                        ToastUtil.showToast(WXEntryActivity.this, msg);
                    }
                });
            }
        });
        mWXAPI = WXAPIFactory.createWXAPI(this, GGApp.WXAPPID, false);
        mWXAPI.registerApp(GGApp.WXAPPID);
        mWXAPI.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        DebugLog.d(TAG, "wx msg  = " + resp.errStr + "code = " + resp.errCode + "code ==" + ((SendAuth.Resp) resp).code);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) resp).code;
               // HttpManager.getWXAccessToken(WeixiShareUtil.getWeixinAppId(this),W code);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mShareGoods.setShred(true);
//                        if (mShareGoods.isShred()) {
//                            mTvPrice.setText(String.format("%s %s", mShareGoods.getGoodCutPrice(), "/" + mShareGoods.getNormos()));
//                        } else {
//                            mTvPrice.setText(String.format("%s %s", mShareGoods.getGoodPrice(), "/" + mShareGoods.getNormos()));
//                        }
//                    }
//                });

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_UNSUPPORT:
        }
        WXShareManager.getInstance(this).notifyShare(resp.errCode);

    }


}
