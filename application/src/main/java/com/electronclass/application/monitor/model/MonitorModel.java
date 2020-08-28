package com.electronclass.application.monitor.model;

import com.electronclass.application.monitor.contract.MonitorContract;
import com.electronclass.application.monitor.presenter.MonitorPresenter;
import com.electronclass.pda.mvp.base.BaseModel;

public class MonitorModel extends BaseModel implements MonitorContract.Model {
    private MonitorContract.Presenter mPresenter;

    @Override
    public void setPresenter(MonitorPresenter mainPresenter) {
          this.mPresenter = mainPresenter;
    }
}
