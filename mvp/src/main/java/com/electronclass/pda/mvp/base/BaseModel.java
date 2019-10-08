package com.electronclass.pda.mvp.base;


import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.reactivex.disposables.CompositeDisposable;

public  class BaseModel implements BaseModelInterface {
    public    Logger                logger            = LoggerFactory.getLogger(getClass());
    @NonNull
    protected BaseSchedulerProvider schedulerProvider = SchedulerProvider.getInstance();
    protected CompositeDisposable   compositeDisposable;

    public void setSchedulerProvider(@NonNull BaseSchedulerProvider schedulerProvider) {
        this.schedulerProvider = schedulerProvider;
    }


    @Override
    public void subscribe() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.dispose();
        compositeDisposable = null;
    }



}
