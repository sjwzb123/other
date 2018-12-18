package com.kuli.commlib.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.kuli.commlib.R;

public class ImageLoadManager {
    //加载圆角图片
    public static void loadRoundImage(Context context, String url, final ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_launcher)
                .error(R.drawable.ic_launcher)
                //.centerCrop() 千万不要加，加了就没有圆角效果了
                .transform(new CenterCrop(context), new GlideRoundTransform(context,5))
                .into(imageView);
    }

}
