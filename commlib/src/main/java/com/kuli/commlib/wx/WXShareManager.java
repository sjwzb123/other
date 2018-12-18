package com.kuli.commlib.wx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ushareit.common.utils.TaskHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;


/**
 * 实现微信分享功能的核心类
 *
 * @author Administrator
 */
public class WXShareManager {
    public static final String SHARE_ITEM_KEY = "shareItem";
    private static final int THUMB_SIZE = 150;
    /**
     * 文字
     */
    public static final int WEIXIN_SHARE_WAY_TEXT = 1;
    /**
     * 图片
     */
    public static final int WEIXIN_SHARE_WAY_PIC = 2;
    /**
     * 链接
     */
    public static final int WEIXIN_SHARE_WAY_WEBPAGE = 3;
    /**
     * 会话
     */
    public static final int WEIXIN_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;
    /**
     * 朋友圈
     */
    public static final int WEIXIN_SHARE_TYPE_FRENDS = SendMessageToWX.Req.WXSceneTimeline;
    private static WXShareManager instance;
    private static String weixinAppId;
    private IWXAPI wxApi;
    private Context context;
    //private List<ShareListener> mList = new ArrayList<>();
    private ShareListener mShareListener;

    private WXShareManager(Context context) {
        this.context = context;
        //初始化数据
        weixinAppId = WeixiShareUtil.getWeixinAppId(context);
        //初始化微信分享代码
        if (weixinAppId != null) {
            initWeixinShare(context);
        }
    }

    public void addShareListener(ShareListener listner) {
        this.mShareListener = listner;
    }

    public void notifyShare(int code) {
        switch (code) {
            case BaseResp.ErrCode.ERR_OK:
                if (mShareListener != null) {
                    mShareListener.onShareSucc();
                }

                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_UNSUPPORT:
            default:
                if (mShareListener != null) {
                    mShareListener.onShareFail("分享失败");
                }
                break;
        }
    }

    /**
     * 获取WeixinShareManager实例
     * 非线程安全，请在UI线程中操作
     *
     * @return
     */
    public static WXShareManager getInstance(Context context) {
        if (instance == null) {
            instance = new WXShareManager(context);
        }
        return instance;
    }

    private void initWeixinShare(Context context) {
        wxApi = WXAPIFactory.createWXAPI(context, weixinAppId, true);
        wxApi.registerApp(weixinAppId);
    }

    /**
     * 通过微信分享
     *
     * @param shareType 分享的方式（文本、图片、链接）
     * @param shareType 分享的类型（朋友圈，会话）
     */
    public void shareByWeixin(ShareContent shareContent, int shareType) {
        switch (shareContent.getShareWay()) {
            case WEIXIN_SHARE_WAY_TEXT:
                shareText(shareType, shareContent);
                break;
            case WEIXIN_SHARE_WAY_PIC:
                sharePicture(shareType, shareContent);
                break;
            case WEIXIN_SHARE_WAY_WEBPAGE:
                shareWebPage(shareType, shareContent);
                break;
        }
    }

    private abstract class ShareContent {
        protected abstract int getShareWay();

        protected abstract String getContent();

        protected abstract String getTitle();

        protected abstract String getURL();

        protected abstract int getPicResource();

    }

    /**
     * 设置分享文字的内容
     *
     * @author Administrator
     */
    public class ShareContentText extends ShareContent {
        private String content;

        /**
         * 构造分享文字类
         *
         * @param content 分享的文字内容
         */
        public ShareContentText(String content) {
            this.content = content;
        }

        @Override
        protected String getContent() {

            return content;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return null;
        }

        @Override
        protected int getPicResource() {
            return -1;
        }

        @Override
        protected int getShareWay() {
            return WEIXIN_SHARE_WAY_TEXT;
        }

    }

    /**
     * 设置分享图片的内容
     *
     * @author Administrator
     */
    public class ShareContentPic extends ShareContent {
        private int picResource;

        public ShareContentPic(int picResource) {
            this.picResource = picResource;
        }

        @Override
        protected String getContent() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected String getURL() {
            return null;
        }

        @Override
        protected int getPicResource() {
            return picResource;
        }

        @Override
        protected int getShareWay() {
            return WEIXIN_SHARE_WAY_PIC;
        }
    }

    /**
     * 设置分享链接的内容
     *
     * @author Administrator
     */
    public class ShareContentWebpage extends ShareContent {
        private String title;
        private String content;
        private String url;
        private int picResource;

        public ShareContentWebpage(String title, String content,
                                   String url, int picResource) {
            this.title = title;
            this.content = content;
            this.url = url;
            this.picResource = picResource;
        }

        @Override
        protected String getContent() {
            return content;
        }

        @Override
        protected String getTitle() {
            return title;
        }

        @Override
        protected String getURL() {
            return url;
        }

        @Override
        protected int getPicResource() {
            return picResource;
        }

        @Override
        protected int getShareWay() {
            return WEIXIN_SHARE_WAY_WEBPAGE;
        }

    }

    /*
     * 分享文字
     */
    private void shareText(int shareType, ShareContent shareContent) {
        String text = shareContent.getContent();
        //初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        //用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求
        req.transaction = buildTransaction("textshare");
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = shareType;
        boolean b = wxApi.sendReq(req);
        Log.d("share", String.valueOf(b));
    }

    /*
     * 分享图片
     */
    private void sharePicture(int shareType, ShareContent shareContent) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), shareContent.getPicResource());
        WXImageObject imgObj = new WXImageObject(bmp);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = WeixiShareUtil.bmpToByteArray(thumbBmp, true);  //设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("imgshareappdata");
        req.message = msg;
        req.scene = shareType;
        wxApi.sendReq(req);
    }

    /*
     * 分享链接
     */
    private void shareWebPage(int shareType, ShareContent shareContent) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareContent.getURL();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();

        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), shareContent.getPicResource());
        if (thumb == null) {
            Toast.makeText(context, "图片不能为空", Toast.LENGTH_SHORT).show();
        } else {
            msg.thumbData = WeixiShareUtil.bmpToByteArray(thumb, true);
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = shareType;
        wxApi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    public interface ShareListener {
        void onShareSucc();

        void onShareFail(String msg);
    }

    public void weixinLogin() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        wxApi.sendReq(req);
    }
}
