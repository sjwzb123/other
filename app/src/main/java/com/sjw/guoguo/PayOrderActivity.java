package com.sjw.guoguo;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.base.bj.trpayjar.domain.TrPayResult;
import com.base.bj.trpayjar.listener.PayResultListener;
import com.base.bj.trpayjar.utils.TrPay;
import com.kuli.commlib.BaseActivity;

import java.util.UUID;

public class PayOrderActivity extends BaseActivity {
    private String PAY_KEY="289ddbd1b7814e649ee58c0396ed9b46";
    @Override
    public int getContentView() {
        return R.layout.activity_pay_order;
    }

    @Override
    public void initView() {
        TrPay.getInstance(this).initPaySdk(PAY_KEY,"xiaomi");
        findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });
    }
    private void pay(){
//发起支付所需参数
        String userid = "trpay@52yszd.com";//商户系统用户ID(如：trpay@52yszd.com，商户系统内唯一)
        String outtradeno = UUID.randomUUID() + "";//商户系统订单号(为便于演示，此处利用UUID生成模拟订单号，商户系统内唯一)
        String tradename = "kkkkkk";//商品名称
        String backparams = "name=2&age=22";//商户系统回调参数
        Long amount=100L;
        String notifyurl = "http://101.200.13.92/notify/alipayTestNotify";//商户系统回调地址
        if (TextUtils.isEmpty(tradename)) {
            Toast.makeText(this, "请输入商品名称！", Toast.LENGTH_SHORT).show();
            return;
        }
            /**
             * 发起快捷支付调用
             */
            TrPay.getInstance(this).callPay(tradename, outtradeno, amount, backparams, notifyurl, userid, new PayResultListener() {
                /**
                 * 支付完成回调
                 * @param context      上下文
                 * @param outtradeno   商户系统订单号
                 * @param resultCode   支付状态(RESULT_CODE_SUCC：支付成功、RESULT_CODE_FAIL：支付失败)
                 * @param resultString 支付结果
                 * @param payType      支付类型（1：支付宝 2：微信）
                 * @param amount       支付金额
                 * @param tradename    商品名称
                 */
                @Override
                public void onPayFinish(Context context, String outtradeno, int resultCode, String resultString, int payType, Long amount, String tradename) {
                    if (resultCode == TrPayResult.RESULT_CODE_SUCC.getId()) {//1：支付成功回调
                        TrPay.getInstance((Activity) context).closePayView();//关闭快捷支付页面
                        Toast.makeText(PayOrderActivity.this, resultString, Toast.LENGTH_LONG).show();
                        //支付成功逻辑处理
                    } else if (resultCode == TrPayResult.RESULT_CODE_FAIL.getId()) {//2：支付失败回调
                        Toast.makeText(PayOrderActivity.this, resultString, Toast.LENGTH_LONG).show();
                        //支付失败逻辑处理
                    }
                }
            });

    }
}
