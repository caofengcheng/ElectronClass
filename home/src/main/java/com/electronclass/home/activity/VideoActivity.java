package com.electronclass.home.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;

import com.electronclass.home.R;
import com.electronclass.home.databinding.ActivityVideoBinding;

public class VideoActivity extends AppCompatActivity {


    private ActivityVideoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this,R.layout.activity_video );
        getUrl();
        back();
    }


    private void getUrl(){
       String           url                  = (String) getIntent().getExtras().get( "Video" );
        MediaController localMediaController = new MediaController(this);
        binding.video.setMediaController(localMediaController);
        binding.video.setVideoPath(url);
        binding.video.start();
    }

    private void back(){
        binding.back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.video.stopPlayback();
    }
}
