package com.electronclass.home.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;

import com.bumptech.glide.Glide;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.home.R;
import com.electronclass.home.databinding.ActivityImageBinding;

public class ImageActivity extends AppCompatActivity {

    private ActivityImageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this,R.layout.activity_image );
        back();
        getUrl();
    }

    private void getUrl(){
        String           url                  = (String) getIntent().getExtras().get( "Image" );
        Glide.with( this ).load( url ).into( binding.image );
    }

    private void back(){
        binding.back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }
}
