package com.electronclass.application.set.presenter;

import com.electronclass.application.set.contract.SetContract;
import com.electronclass.application.set.model.SetModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassItem;

import java.util.List;

public class SetPresenter extends BasePresenter<SetContract.Model, SetContract.View> implements SetContract.Presenter {

    @Override
    protected void initModel() {
        mModel = new SetModel();
        mModel.setPresenter( this );
    }

    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
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