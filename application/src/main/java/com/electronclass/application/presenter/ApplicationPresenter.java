package com.electronclass.application.presenter;

import com.electronclass.application.contract.ApplicationContract;
import com.electronclass.application.model.ApplicationModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassItem;

import java.util.List;

public class ApplicationPresenter extends BasePresenter<ApplicationContract.Model, ApplicationContract.View> implements ApplicationContract.Presenter {

    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
    }

    @Override
    protected void initModel() {
        mModel = new ApplicationModel();
        mModel.setPresenter( this );
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

    @Override
    public void getClassList(String departId, String userId) {
        mModel.getClassList( departId, userId );
    }


    @Override
    public void onClassList(List<ClassItem> classItems) {
        mView.onClassList( classItems );
    }

    @Override
    public void onGradeList(List<ClassItem> classItems) {
        mView.onGradeList( classItems );
    }

    @Override
    public void onSchoolList(List<ClassItem> classItems) {
        mView.onSchoolList( classItems );
    }

    @Override
    public void bound(String departCode) {
        mModel.bound( departCode );
    }

    @Override
    public void onBound(String msg) {
        mView.onBound( msg );
    }


}
