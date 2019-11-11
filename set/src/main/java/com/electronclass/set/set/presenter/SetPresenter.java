package com.electronclass.set.set.presenter;

import android.content.Context;

import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.set.set.contract.SetContract;
import com.electronclass.set.set.model.SetModel;

import java.util.List;

/**
 * @author caofengcheng on 2019-10-31
 */
public class SetPresenter extends BasePresenter<SetContract.Model,SetContract.View> implements SetContract.Presenter {

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
    public void bound(String departId, Context context) {
        mModel.bound( departId,context );
    }

    @Override
    public void onBound(String msg) {
        mView.onBound( msg );
    }
    @Override
    protected void initModel() {
        mModel = new SetModel();
        mModel.setPresenter( this );
    }
}
