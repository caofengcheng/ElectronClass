package com.electronclass.application.login.presenter;

import com.electronclass.application.login.contract.LoginContract;
import com.electronclass.application.login.model.LoginModel;
import com.electronclass.pda.mvp.base.BasePresenter;

public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> implements LoginContract.Presenter {

    @Override
    protected void initModel() {
        mModel = new LoginModel();
        mModel.setPresenter( this );
    }

    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
    }



    @Override
    public void sendSms(String phoneNum) {
        mModel.sendSms( phoneNum );
    }

    @Override
    public void onSms(boolean isSuccess) {
        mView.onSms( isSuccess );
    }

    @Override
    public void login(String phoneNum, String smsCode) {
        mModel.login( phoneNum, smsCode );
    }

    @Override
    public void onlogin(boolean str) {
        mView.onlogin( str );
    }
}