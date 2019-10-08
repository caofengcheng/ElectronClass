package com.electronclass.pda.mvp.base;


import android.support.annotation.NonNull;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {
    private CompositeDisposable compositeDisposable;

    protected BaseObserver() {

    }

    protected BaseObserver(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public final void onNext(@NonNull T result) {
        onSuccess(result);
    }

    @Override
    public void onError(@NonNull Throwable e) {
//        Tools.displayToast(HttpExceptionUtil.exceptionHandler(e));
        onFailure(e, HttpExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        compositeDisposable.add(d);
    }

    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable e, String errorMsg);
}
