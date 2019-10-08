package com.electronclass.aclass.activity.presenter;

import com.electronclass.aclass.activity.contract.UpdateDutyContract;
import com.electronclass.aclass.activity.model.UpdateDutyModel;
import com.electronclass.pda.mvp.base.BasePresenter;


public class UpdateDutyPresenter extends BasePresenter<UpdateDutyContract.Model, UpdateDutyContract.View> implements UpdateDutyContract.Presenter {

    @Override
    protected void initModel() {
        mModel = new UpdateDutyModel();
        mModel.setPresenter( this );
    }

    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
    }

    @Override
    public void addOrUpdateDuty(String id, String studentCardNo, String task, String name, String eventDate) {
        mModel.addOrUpdateDuty( id, studentCardNo, task, name, eventDate );
    }

    @Override
    public void onSuccress(String msg) {
        mView.onSuccress( msg );
    }

    @Override
    public void deleteDuty(String id) {
        mModel.deleteDuty( id );
    }

    @Override
    public void onDeleteDuty(String msg) {
        mView.onDeleteDuty( msg );
    }
}