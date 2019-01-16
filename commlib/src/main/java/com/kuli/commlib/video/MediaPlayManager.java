package com.kuli.commlib.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;



public class MediaPlayManager {
    private String TAG = "MediaPlayManager";
    private static MediaPlayManager manager;
    private TXVodPlayer mPlayer;
    private PlayListener mListener;

    public void setPlayListener(PlayListener listener) {
        this.mListener = listener;
    }

    private MediaPlayManager(Context context) {
        initTXMediaPlayer(context);
    }

    public static MediaPlayManager getInstance(Context context) {
        if (manager != null) {
            return manager;
        } else {
            manager = new MediaPlayManager(context);
        }
        return manager;
    }

    private void initTXMediaPlayer(Context context) {
        if (mPlayer == null) {
            final TXVodPlayConfig mConfig = new TXVodPlayConfig();
            mConfig.setCacheFolderPath(
                    Environment.getExternalStorageDirectory().getPath() + "/txcache");
//指定本地最多缓存多少文件，避免缓存太多数据
            mConfig.setMaxCacheItems(10);
            mPlayer = new TXVodPlayer(context);
            mPlayer.setConfig(mConfig);
            mPlayer.setVodListener(new ITXVodPlayListener() {
                @Override
                public void onPlayEvent(TXVodPlayer txVodPlayer, int i, Bundle bundle) {
                    Log.d(TAG, "onPlayEvent " + i);
                    if (i == TXLiveConstants.PLAY_EVT_PLAY_END) {
                        mPlayer.resume();
                        if (mListener != null) {
                            mListener.onPlayComplete();
                        }
                    }
                    if (i == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
                        if (mListener != null) {
                            mListener.onPlayBegin();
                        }
                    }
                    if (i == TXLiveConstants.PLAY_EVT_RTMP_STREAM_BEGIN) {
                        if (mListener != null) {
                            mListener.onPlayBegin();
                        }
                    }
                }

                @Override
                public void onNetStatus(TXVodPlayer txVodPlayer, Bundle bundle) {

                }
            });
        }
    }

    public void setSurfaceView(TXCloudVideoView videoView) {
        if (videoView != null) {
            mPlayer.setPlayerView(videoView);
        }
    }

    public void stopPlay() {
        if (mPlayer != null) {
            mPlayer.stopPlay(true);
        }
    }

    public void startPlay(String url) {
        if (!TextUtils.isEmpty(url)) {
            mPlayer.startPlay(url);
        }
    }

    public interface PlayListener {
        void onPlayComplete();

        void onPlayBegin();
    }
}
