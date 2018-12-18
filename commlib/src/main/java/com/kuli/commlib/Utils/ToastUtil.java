package com.kuli.commlib.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sjw on 2018/2/26.
 */

public class ToastUtil {
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
