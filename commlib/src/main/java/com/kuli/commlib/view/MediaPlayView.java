package com.kuli.commlib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.kuli.commlib.R;
import com.kuli.commlib.Utils.DebugLog;
import com.kuli.commlib.video.MediaPlayManager;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class MediaPlayView extends FrameLayout implements MediaPlayManager.PlayListener {
    private ProgressBar mPbLoading;
    private TXCloudVideoView mVideoView;
    private ImageView mIvCover;
    private String TAG = "MediaPlayView";

    public MediaPlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.play_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void initView() {
        mPbLoading = (ProgressBar) findViewById(R.id.pb);
        mVideoView = (TXCloudVideoView) findViewById(R.id.tx_video);
        mIvCover = (ImageView) findViewById(R.id.iv_cover);

    }

    public void startPlay(Context context, String url) {
        mPbLoading.setVisibility(VISIBLE);
        MediaPlayManager.getInstance(context).setPlayListener(this);
        MediaPlayManager.getInstance(context).setSurfaceView(mVideoView);
        MediaPlayManager.getInstance(context).startPlay(url);
    }

    public void stopPlay(Context context) {
        mPbLoading.setVisibility(GONE);
        MediaPlayManager.getInstance(context).stopPlay();
        mIvCover.setVisibility(VISIBLE);
    }

    @Override
    public void onPlayComplete() {

    }

    @Override
    public void onPlayBegin() {
        mPbLoading.setVisibility(GONE);
        DebugLog.d(TAG, "onPlayBegin------------");
        mIvCover.setVisibility(GONE);
    }
}
