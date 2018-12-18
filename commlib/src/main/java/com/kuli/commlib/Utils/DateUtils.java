package com.kuli.commlib.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sjw on 2018/4/8.
 */

public class DateUtils {

    public static String getDateTimeFromMillisecond(Long millisecond){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(millisecond);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}
