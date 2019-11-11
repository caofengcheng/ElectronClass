package com.electronclass.set.login.presenter;

import android.content.Context;

import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.pda.mvp.entity.TeacherInfo;
import com.electronclass.set.login.contract.LoginContract;
import com.electronclass.set.login.model.LoginModel;

import java.util.List;

/**
 * @author caofengcheng on 2019-10-31
 */
public class LoginPresenter extends BasePresenter<LoginContract.Model,LoginContract.View> implements LoginContract.Presenter {


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
    public void onlogin(boolean str, TeacherInfo teacherInfo) {
        mView.onlogin( str,teacherInfo );
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
    public void bound(String departId, Context context) {
        mModel.bound( departId,context );
    }

    @Override
    public void onBound(String msg) {
        mView.onBound( msg );
    }
}
