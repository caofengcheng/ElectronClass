package com.electronclass.common.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

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

public abstract class BaseFragment<T extends BasePresenterInterface> extends Fragment implements BaseView {
    protected Logger              logger      = LoggerFactory.getLogger(getClass());
    protected CompositeDisposable mDisposable = new CompositeDisposable();
    protected T                   mPresenter;
    protected Activity            aty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object event) {
        //处理逻辑
        logger.info( "base收到event" );
    }

    @Override
    public void onResume() {
        super.onResume();
        setUserVisibleHint(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible()) {
            getData();
        }
    }

    protected void init(View view) {
        aty=getActivity();
        mPresenter = getPresenter();
        mPresenter.setView(this);
        initView(view);
        mPresenter.subscribe();
        initData();
        showData();
    }
    @NotNull
    protected abstract T getPresenter();
    @Override
    public void onError(String errorMessage) {
        logger.debug(errorMessage);
        Tools.displayToast(errorMessage);
    }

    //

    /**
     * 初始化view 包含findViewById等
     *
     * @param view fragment
     */
    protected abstract void initView(View view);

    /**
     * 获取数据 调用presenter接口
     */
    protected abstract void initData();

    /**
     * 展示数据 显示到界面上
     */
    protected abstract void showData();

    /**
     * 释放数据 释放presenter
     */
    protected abstract void releaseData();

    protected abstract void getData();


    @Override
    public void onDestroy() {
        releaseData();
        if (mPresenter != null) {
            mPresenter.unsubscribe();
        }
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
