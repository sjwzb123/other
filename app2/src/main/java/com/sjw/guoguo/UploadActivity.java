package com.sjw.guoguo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.ImageSelectorActivity;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.kuli.commlib.BaseActivity;
import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.ToastUtil;
import com.kuli.commlib.view.LoadingView;
import com.sjw.guoguo.bean.GoodPic;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.http.HttpManager;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UploadActivity extends BaseActivity {
    private EditText mEtName, mEtPrice, mEtNorms, mEtDis;
    private LinearLayout mLlPic;
    private Button mBtnPic;
    private ArrayList<String> picList = new ArrayList<>();
    private int REQUEST_CAMERA_CODE = 0;
    private Button mBtnUpload;
    private List<String> mPicUrllist;
    private LoadingView mLoadView;
    private String TAG = "UploadActivity";

    @Override
    public int getContentView() {
        return R.layout.activity_upload;
    }

    @Override
    public void initView() {
        mLoadView = new LoadingView(this);
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtDis = (EditText) findViewById(R.id.et_dis);
        mEtPrice = (EditText) findViewById(R.id.et_price);
        mEtNorms = (EditText) findViewById(R.id.et_norms);
        mLlPic = (LinearLayout) findViewById(R.id.ll_pic);
        mBtnPic = (Button) findViewById(R.id.btn_add_pic);
        mBtnUpload = (Button) findViewById(R.id.btn_upload);
        mBtnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectPic();
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadPicToServer();
            }
        });


    }

    private void upload() {
        String name = mEtName.getText().toString();
        String pric = mEtPrice.getText().toString();
        String normos = mEtNorms.getText().toString();
        String dis = mEtDis.getText().toString();
        Goods goods = new Goods();
        goods.setGoodName(name);
        goods.setGoodDes(dis);
        goods.setGoodPrice(Float.valueOf(pric));
        goods.setNormos(normos);
        ArrayList<GoodPic> list = new ArrayList();
        for (String picUrl : mPicUrllist) {
            GoodPic pic = new GoodPic();
            pic.setPicUrl(picUrl);
            pic.setPicHeight(100);
            pic.setPicWidth(100);
            list.add(pic);
            goods.setGoodPics(list);
        }
        goods.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                mLoadView.dismiss();
                if (e == null) {
                    ToastUtil.showToast(mContext, "上传成功");
                } else {
                    ToastUtil.showToast(mContext, "上传失败");
                }
            }
        });


    }

    private void startSelectPic() {
//        PhotoPickerIntent intent = new PhotoPickerIntent(this);
//        intent.setSelectModel(SelectModel.MULTI);
//        intent.setShowCarema(true); // 是否显示拍照， 默认false
//        intent.setMaxTotal(9); // 最多选择照片数量，默认为9
//        intent.setSelectedPaths(picList); // 已选中的照片地址， 用于回显选中状态
//// intent.setImageConfig(config);
//        startActivityForResult(intent, REQUEST_CAMERA_CODE);
        openPhoto(this, REQUEST_CAMERA_CODE, false, 3);
    }

    /**
     * 打开相册，选择图片,可多选,限制最大的选择数量。
     *
     * @param activity
     * @param requestCode
     * @param isSingle       是否单选
     * @param maxSelectCount 图片的最大选择数量，小于等于0时，不限数量，isSingle为false时才有用。
     */
    public static void openPhoto(Activity activity, int requestCode,
                                 boolean isSingle, int maxSelectCount) {
        ImageSelectorActivity.openActivity(activity, requestCode, isSingle, maxSelectCount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_CODE && data != null) {
            if (picList != null) {
                picList.clear();
            }
            //获取选择器返回的数据
            picList = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            showSelectImage(picList);
        }
    }


    private void showSelectImage(ArrayList<String> list) {
        for (String path : list) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
            mLlPic.addView(imageView);
            Glide.with(this).load(path).into(imageView);
        }


    }

    private void uploadPicToServer() {
        mLoadView.show();
        String[] picStrs = new String[picList.size()];
        picList.toArray(picStrs);
        HttpManager.uploadPic(picStrs, new HttpManager.HttpUploadFileCallBack() {
            @Override
            public void onSucces(List<String> list) {
                mPicUrllist = list;
                upload();
            }

            @Override
            public void onError(String msg) {
                DebugLog.d(TAG, "msg");
            }
        });
    }

    private void uploadToServer() {

    }

}
