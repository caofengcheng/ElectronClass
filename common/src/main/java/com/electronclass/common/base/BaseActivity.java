package com.electronclass.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.electronclass.common.util.Tools;

import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseActivity<T extends BasePresenterInterface> extends AppCompatActivity implements BaseView {
    public    Logger              logger      = LoggerFactory.getLogger(getClass());
    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected T                   mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * 需要子类手动调用init方法
     */
    protected void init() {
        mPresenter = getPresenter();
        mPresenter.setView(this);
        initView();
        mPresenter.subscribe();
        initData();
        showData();
    }

    @NotNull
    protected abstract T getPresenter();

    /**
     * 初始化view 包含findViewById等
     */
    protected abstract void initView();

    /**
     * 获取数据 初始化presenter等
     */
    protected abstract void initData();

    /**
     * 展示数据 显示已知数据到界面上 显示fragment 调用presenter接口等
     */
    protected abstract void showData();

    /**
     * 释放数据 释放presenter等
     */
    protected abstract void releaseData();

    @Override
    public void onError(String errorMessage) {
        logger.debug(errorMessage);
        Tools.displayToast(errorMessage);
    }

    @Override
    public void onDestroy() {
        releaseData();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }
}

