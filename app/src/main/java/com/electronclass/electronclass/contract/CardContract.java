package com.electronclass.electronclass.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;

public interface CardContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void getCardAttendance(String studentCardNo);
    }

    interface View extends BaseView {
        void onCardAttendance(boolean sure);
    }

    interface Presenter extends BasePresenterInterface<View> {
        void setView(View view);
        void getCardAttendance(String studentCardNo);
        void onCardAttendance(boolean sure);
    }
}
