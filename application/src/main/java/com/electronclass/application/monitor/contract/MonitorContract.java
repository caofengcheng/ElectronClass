package com.electronclass.application.monitor.contract;

import com.electronclass.application.monitor.presenter.MonitorPresenter;
import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;

public interface MonitorContract {
    interface Model extends BaseModelInterface {
        void setPresenter(MonitorPresenter mainPresenter);
    }

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenterInterface<View> {
        void setView(View view);
    }
}
