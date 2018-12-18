package com.sjw.guoguo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.sjw.guoguo.bean.GoodPic;
import com.sjw.guoguo.bean.Goods;

import java.util.ArrayList;
import java.util.List;

import com.kuli.commlib.Utils.DebugLog;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by sjw on 2018/3/19.
 */

public class TestActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Goods goods = new Goods();
                goods.setSalesCount(200);
                goods.setGoodPrice(2.5f);
                goods.setGoodName("pingguo");
                goods.setGoodDes("hajjjjhhhhhhhhhh");
                goods.setStockCount(1000);
                goods.setType(Goods.TYPE.SHUIGUO);
                GoodPic pic = new GoodPic();
                pic.setPicUrl("http://bmob-cdn-17722.b0.upaiyun.com/2018/03/30/5628907140c9541a80cce43525d3472d.png");
                List<GoodPic> list = new ArrayList<GoodPic>();
                list.add(pic);
                goods.setGoodPics(list);
                goods.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            DebugLog.d("TestActivity", "save success--------");
                        }
                    }
                });
            }
        });
    }
}
