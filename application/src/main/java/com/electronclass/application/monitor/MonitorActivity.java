package com.electronclass.application.monitor;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.electronclass.application.R;
import com.electronclass.application.databinding.ActivityMonitorBinding;
import com.electronclass.application.monitor.contract.MonitorContract;
import com.electronclass.application.monitor.presenter.MonitorPresenter;
import com.electronclass.common.base.BaseActivity;
import com.yhd.mediaplayer.MediaPlayerHelper;

import org.jetbrains.annotations.NotNull;

public class MonitorActivity extends BaseActivity<MonitorContract.Presenter> implements MonitorContract.View {
    private ActivityMonitorBinding  binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_monitor);
    }

    @NotNull
    @Override
    protected MonitorContract.Presenter getPresenter() {
        return new MonitorPresenter();
    }

    @Override
    protected void initView() {
        MediaPlayerHelper.getInstance().setSurfaceView(binding.surfaceView).playUrl(this,"");
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
