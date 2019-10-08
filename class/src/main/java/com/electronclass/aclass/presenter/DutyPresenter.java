package com.electronclass.aclass.presenter;

import com.electronclass.aclass.contract.DutyContract;
import com.electronclass.aclass.model.DutyModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.Duty;

import java.util.List;

public class DutyPresenter extends BasePresenter<DutyContract.Model,DutyContract.View> implements DutyContract.Presenter {

    @Override
    public void onError(String errorMessage) {
     mView.onError( errorMessage );
    }


    @Override
    protected void initModel() {
        mModel = new DutyModel();
        mModel.setPresenter( this );
    }

    @Override
    public void getDuty() {
        mModel.getDuty();
    }

    @Override
    public void onDuty(List<Duty> duties) {
        mView.onDuty( duties );
    }
}
