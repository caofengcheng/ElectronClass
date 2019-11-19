package com.electronclass.application.web;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.electronclass.application.R;
import com.electronclass.application.databinding.ActivityWebBinding;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.event.CardType;
import com.electronclass.common.event.FoodCard;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加载网页
 */
public class WebActivity extends AppCompatActivity {
    protected Logger             logger = LoggerFactory.getLogger( getClass() );
    private   ActivityWebBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_web );
        EventBus.getDefault().register( this );
        GlobalParam.setCardType( GlobalParam.OTHER );
        setWeb();
        setOnClick();
    }

    private void setOnClick() {
        binding.back.setOnClickListener( v -> finish() );
    }

    private void setWeb() {
        String url = getIntent().getStringExtra( GlobalParam.APPURL );
        binding.webView.loadUrl( url );
        binding.webView.setWebViewClient( new WebViewClient() );
        //得到webview设置
        WebSettings webSettings = binding.webView.getSettings();
        //允许使用javascript
        webSettings.setJavaScriptEnabled( true );
        binding.webView.getSettings().setSupportZoom( true );
        binding.webView.getSettings().setBuiltInZoomControls( true );
        binding.webView.setWebViewClient( new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl( url );
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished( view, url );
                GlobalParam.setCardType( GlobalParam.FOODACTIVITY );
            }
        } );

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onFoodCard(FoodCard cardType) {
        if (cardType != null) {
            logger.info( "访问h5接口+卡号：" + cardType.number );
            binding.webView.evaluateJavascript( "javascript:thirdLogin(" + cardType.number + ")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    logger.info( "访问h5接口返回：" + value );
                }
            } );

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlobalParam.setCardType( GlobalParam.MAINACTIVITY );
        EventBus.getDefault().unregister( this );//解除注册
    }
}
