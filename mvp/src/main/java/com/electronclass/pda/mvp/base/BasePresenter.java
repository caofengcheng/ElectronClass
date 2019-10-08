package com.electronclass.pda.mvp.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class BasePresenter<T extends BaseModelInterface, V extends BaseView> implements BasePresenterInterface<V> {
    protected Logger mLogger = (Logger) LoggerFactory.getLogger(getClass());
    protected T      mModel;
    protected V      mView;

    @Override
    public final void setView(V view) {
        mView = view;
        initModel();
    }

    protected abstract void initModel();

    @Override
    public void subscribe() {
        mModel.subscribe();
    }

    @Override
    public void unsubscribe() {
        mModel.unsubscribe();
    }

}
