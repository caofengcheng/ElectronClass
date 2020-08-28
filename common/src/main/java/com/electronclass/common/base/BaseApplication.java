package com.electronclass.common.base;


import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.electronclass.common.database.MacAddress;
import com.electronclass.common.util.InitUtil;
import com.electronclass.common.util.Tools;
import com.electronclass.pda.mvp.GlobalParameter;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.rest.RestManager;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.disposables.CompositeDisposable;


public abstract class BaseApplication<T extends BasePresenterInterface> extends Application implements BaseView {
    private static BaseApplication     mInstance;
    private static Logger              logger      = LoggerFactory.getLogger( BaseApplication.class );
    protected      CompositeDisposable mDisposable = new CompositeDisposable();
    protected      T                   mPresenter;
    public static  Context             context;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initInstance();
        printBootLog();
        InitUtil.init();
        initRest();
        MacAddress.getMacAddress( this );
        init();
    }

    /**
     * 需要子类手动调用init方法
     */
    protected void init() {
        mPresenter = getPresenter();
        mPresenter.setView( this );
        mPresenter.subscribe();
    }

    private void initInstance() {
        mInstance = this;
    }

    private void printBootLog() {
        logger.debug( "application boot" );
    }

    private static void initRest() {
        RestManager.getInstance().initRest( GlobalParameter.getJcAddress() );

    }

    @NotNull
    protected abstract T getPresenter();

    @Override
    public void onError(String errorMessage) {
        logger.debug( errorMessage );
        Tools.displayToast( errorMessage );
    }


}
