package com.electronclass.application.monitor;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.electronclass.application.R;
import com.electronclass.application.databinding.ActivityMonitorBinding;
import com.electronclass.application.monitor.contract.MonitorContract;
import com.electronclass.application.monitor.presenter.MonitorPresenter;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.database.GlobalParam;
import com.yhd.mediaplayer.MediaPlayerHelper;

import org.jetbrains.annotations.NotNull;


/**
 * 海康教室监控
 */
public class MonitorActivity extends BaseActivity<MonitorContract.Presenter> implements MonitorContract.View {
    private ActivityMonitorBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_monitor);
        init();
    }

    @NotNull
    @Override
    protected MonitorContract.Presenter getPresenter() {
        return new MonitorPresenter();
    }

    @Override
    protected void initView() {
        MediaPlayerHelper.getInstance().setSurfaceView(binding.surfaceView)
                         .playUrl(this,"http://10.99.211.2:8080/video?type=Play&id="+ GlobalParam.getJKIP());
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }
}
