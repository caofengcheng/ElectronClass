package com.electronclass.application.monitor.presenter;


import com.electronclass.application.monitor.contract.MonitorContract;
import com.electronclass.application.monitor.model.MonitorModel;
import com.electronclass.pda.mvp.base.BasePresenter;

public class MonitorPresenter extends BasePresenter<MonitorContract.Model,MonitorContract.View> implements MonitorContract.Presenter {
    public MonitorContract.View mView;

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    protected void initModel() {
        mModel = new MonitorModel();
        mModel.setPresenter( this );
    }
}
