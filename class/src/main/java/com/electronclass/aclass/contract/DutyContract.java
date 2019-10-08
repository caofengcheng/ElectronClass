package com.electronclass.aclass.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.Duty;

import java.util.List;

public interface DutyContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void getDuty();
    }

    interface View extends BaseView {
        void onDuty(List<Duty>duties);
    }

    interface Presenter extends BasePresenterInterface<View> {
        void getDuty();
        void onDuty(List<Duty>duties);
    }
}
