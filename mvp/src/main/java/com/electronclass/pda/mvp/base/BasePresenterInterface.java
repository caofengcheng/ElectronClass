package com.electronclass.pda.mvp.base;


public interface BasePresenterInterface<T extends BaseView> {
    void onError(String errorMessage);

    void subscribe();

    void unsubscribe();

    void setView(T view);

}
