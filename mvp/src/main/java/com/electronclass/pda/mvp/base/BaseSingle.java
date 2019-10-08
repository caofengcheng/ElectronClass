package com.electronclass.pda.mvp.base;



import android.support.annotation.NonNull;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseSingle<T> implements SingleObserver<T> {
    private CompositeDisposable compositeDisposable;


    protected BaseSingle(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void onError(@NonNull Throwable e) {
//        Tools.displayToast(HttpExceptionUtil.exceptionHandler(e));
        onFailure(e, HttpExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        compositeDisposable.add(d);
    }

    public abstract void onFailure(Throwable e, String errorMsg);
}
