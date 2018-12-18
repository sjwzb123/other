package com.kuli.commlib.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.UUID;

/**
 * Created by sjw on 2017/10/20.
 */

public class DisplayUtils {

    public static int getW(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return width;
    }

    public static int getH(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 得到全局唯一UUID
     */
    public static String getUUID(Context context) {
        SharedPreferences spf = context.getSharedPreferences("xiaoer", 0);
        String uuid = spf.getString("UUID", "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            spf.edit().putString("UUID", uuid).commit();
        }

        return uuid;
    }
}
