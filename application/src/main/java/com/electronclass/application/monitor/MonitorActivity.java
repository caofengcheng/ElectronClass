package com.electronclass.application.monitor;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.electronclass.application.R;
import com.electronclass.application.databinding.ActivityMonitorBinding;
import com.electronclass.common.database.GlobalParam;


import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;


/**
 * 海康教室监控
 */
public class MonitorActivity extends AppCompatActivity {
    private ActivityMonitorBinding binding;

    private static final boolean USE_TEXTURE_VIEW = false;
    private static final boolean ENABLE_SUBTITLES = true;
    MediaPlayer mMediaPlayer;
    LibVLC      mLibVLC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_monitor);

        //返回按钮
        binding.back.setOnClickListener(view -> finish());

        //获取ip地址
        String                  url  = getIntent().getStringExtra(GlobalParam.APPURL);
        final ArrayList<String> args = new ArrayList<>();//VLC参数
        args.add("--rtsp-tcp");//强制rtsp-tcp，加快加载视频速度
        args.add("--live-caching=0");
        args.add("--file-caching=0");
        args.add("--network-caching=0");//增加实时性，延时大概2-3秒
        mLibVLC = new LibVLC(this, args);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        mMediaPlayer.attachViews(binding.vlc, null, ENABLE_SUBTITLES, USE_TEXTURE_VIEW);
        mMediaPlayer.setVideoScale(MediaPlayer.ScaleType.SURFACE_BEST_FIT);
        Uri         uri   = Uri.parse("http://10.99.211.2:8080/video?type=Play&id=" + url);//rtsp流地址或其他流地址
        final Media media = new Media(mLibVLC, uri);
        mMediaPlayer.setMedia(media);
        media.release();
        mMediaPlayer.play();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.getVLCVout().detachViews();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null)
            mMediaPlayer.release();


        if (mLibVLC != null)
            mLibVLC.release();

    }
}
