package com.electronclass.application.login.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;

public interface LoginContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void sendSms(String phoneNum);
        void login(String phoneNum, String smsCode);
    }

    interface View extends BaseView {
        void onSms(boolean isSuccess);
        void onlogin(boolean str);
    }

    interface Presenter extends BasePresenterInterface<LoginContract.View> {
        void sendSms(String phoneNum);
        void onSms(boolean isSuccess);

        void login(String phoneNum, String smsCode);
        void onlogin(boolean str);
    }
}