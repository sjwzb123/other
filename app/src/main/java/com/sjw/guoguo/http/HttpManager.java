package com.sjw.guoguo.http;

import android.content.Context;

import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.Utils.DisplayUtils;
import com.sjw.guoguo.bean.ActionBean;
import com.sjw.guoguo.bean.CartItem;
import com.sjw.guoguo.bean.GoodPic;
import com.sjw.guoguo.bean.Goods;
import com.sjw.guoguo.bean.OrderBean;
import com.sjw.guoguo.bean.RankBean;
import com.sjw.guoguo.bean.UserBean;
import com.sjw.guoguo.db.dao.CartDao;
import com.sjw.guoguo.db.dao.UserDao;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadBatchListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sjw on 2018/3/16.
 */

public class HttpManager {
    private static final long YEAR_TIME = 24 * 60 * 60 * 1000 * 360;
    private static final long MONTH_TIME = 24 * 60 * 60 * 1000 * 30;
    private static final String TAG = "HttpManager";

    public static void getAllGoods(final HttpCallBack<Goods> callBack, int type) {
        BmobQuery<Goods> query = new BmobQuery<>();
        query.addWhereEqualTo("type", type);
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e == null) {
                    callBack.onSuccess(list);
                } else {
                    callBack.onError(e.getMessage());
                }
            }
        });
    }

    public static void getGoodsById(String id, final HttpCallBack<Goods> callBack) {
        BmobQuery<Goods> query = new BmobQuery<>();
        query.getObject(id, new QueryListener<Goods>() {
            @Override
            public void done(Goods goods, BmobException e) {
                if (e == null) {
                    callBack.onSuccesSingle(goods);
                } else {
                    callBack.onError(e.getMessage());
                }
            }
        });
    }

    public static void getAllActs(final HttpCallBack<ActionBean> callBack) {
        BmobQuery<ActionBean> query = new BmobQuery<>();
        query.findObjects(new FindListener<ActionBean>() {
            @Override
            public void done(List<ActionBean> list, BmobException e) {
                if (e == null) {
                    callBack.onSuccess(list);
                } else {
                    callBack.onError(e.getMessage());
                }
            }
        });
    }

    public static final UserBean getUserInfo(Context context) {
        UserDao userDao = new UserDao(context);
        return userDao.queryForId(DisplayUtils.getUUID(context));
    }

    public static void uploadPic(final String[] filePaths, final HttpUploadFileCallBack callBack) {

        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {

            @Override
            public void onSuccess(List<BmobFile> files, List<String> urls) {
                //1、files-上传完成后的BmobFile集合，是为了方便大家对其上传后的数据进行操作，例如你可以将该文件保存到表中
                //2、urls-上传文件的完整url地址
                if (urls.size() == filePaths.length) {//如果数量相等，则代表文件全部上传完成
                    //do something
                    if (callBack != null) {
                        callBack.onSucces(urls);
                    }
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {

            }

            @Override
            public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                //1、curIndex--表示当前第几个文件正在上传
                //2、curPercent--表示当前上传文件的进度值（百分比）
                //3、total--表示总的上传文件数
                //4、totalPercent--表示总的上传进度（百分比）
            }
        });
    }

    public void getMyOrders(String userId, final HttpCallBack<OrderBean> callBack) {
        BmobQuery<OrderBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", userId);
        query.findObjects(new FindListener<OrderBean>() {
            @Override
            public void done(List<OrderBean> list, BmobException e) {
                if (e == null) {
                    callBack.onSuccess(list);
                } else {
                    callBack.onError(e.getMessage());
                }
            }
        });
    }

    public static void subOrder(OrderBean orderBean, final HttpCallBack<OrderBean> callBack) {
        if (orderBean != null) {
            orderBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        callBack.onSuccess(null);
                    } else {
                        callBack.onError(s);
                    }
                }
            });
        }
    }

    public static void getOrdersByUserId(String userId, final HttpCallBack<OrderBean> callBack) {
        BmobQuery<OrderBean> query = new BmobQuery<>();
        query.addWhereEqualTo("userId", userId);
        query.order("-orderTime");
        query.findObjects(new FindListener<OrderBean>() {
            @Override
            public void done(List<OrderBean> list, BmobException e) {
                if (e == null) {
                    callBack.onSuccess(list);
                } else {
                    callBack.onError("数据返回错误，请稍后再试");
                }
            }
        });
    }

    public static void getRankList(final HttpCallBack<RankBean> callBack, int skip) {
        BmobQuery<RankBean> query = new BmobQuery<>();
        query.order("rankGold");
        query.setSkip(skip);
        query.findObjects(new FindListener<RankBean>() {
            @Override
            public void done(List<RankBean> list, BmobException e) {
                if (e == null) {
                    callBack.onSuccess(list);
                } else {
                    callBack.onError("数据返回错误，请稍后再试");
                }
            }
        });
    }

    public interface HttpCallBack<T> {
        void onSuccesSingle(T t);

        void onSuccess(List<T> list);

        void onError(String msg);

    }

    public interface HttpUploadFileCallBack {
        void onSucces(List<String> list);

        void onError(String msg);
    }

    ;
    private static CartDao mCartDao;

    public static void addGoodsToCart(Context context, Goods goods, HttpCallBack<String> callBack) {
        UserBean userBean = getCurrUser();
        userBean = new UserBean();
        userBean.setObjectId("123456");
        userBean.setPhone("13164232910");
        if (mCartDao == null) {
            mCartDao = new CartDao(context);
        }
        CartItem bean = new CartItem();
        bean.setItemId(goods.getObjectId());
        bean.setGoodsName(goods.getGoodName());
        List<GoodPic> list = goods.getGoodPics();
        if (list != null) {
            bean.setGoodsPicUrl(goods.getGoodPics().get(0).getPicUrl());
        }
        if (goods.isShred() || userBean.isVip()) {
            bean.setGoodsPrice(goods.getGoodCutPrice());
        } else {
            bean.setGoodsPrice(goods.getGoodPrice());
        }

        int code = mCartDao.addToCart(bean);
        if (code > 0) {
            callBack.onSuccesSingle("加入购物车成功");
        } else {
            callBack.onError("加入购物车失败");
        }
    }

    public static void loginOut() {
        BmobUser.logOut();
    }

    public static UserBean getCurrUser() {
        return BmobUser.getCurrentUser(UserBean.class);
    }

    public static void fetchUser() {
        UserBean userBean = getCurrUser();
        if (userBean != null) {
            userBean.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    DebugLog.d("fetchUser", "err  " + e.toString() + "  msg  " + s);
                }
            });
        }
        updateVip();

    }

    public static void updateVip() {
        UserBean userBean = getCurrUser();
        if (userBean != null) {
            long currTime = System.currentTimeMillis();
            long vipStartTime = userBean.getVipStartTime();
            long diffTime = currTime - vipStartTime;
            if (userBean.getVipType() == 1) {
                if (diffTime > MONTH_TIME) {
                    userBean.setVip(false);
                }
                ;
                if (diffTime > YEAR_TIME) {
                    userBean.setVip(false);
                }
            }
            userBean.update();
        }

    }

    public static void getWXAccessToken(String appid,String secret,String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code=" + code + "&grant_type=authorization_code";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                DebugLog.d(TAG, "response " + response.body().string());
            }
        });
    }
}
