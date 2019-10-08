package com.electronclass.common.basemvp.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.ClassInfo;
import com.electronclass.pda.mvp.entity.SchoolInfo;

public interface ApplicationContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void getClassAndSchool();
    }

    interface View extends BaseView {
        void onClassAndSchool();
    }

    interface Presenter extends BasePresenterInterface<View> {
        void getClassAndSchool();
        void onClassAndSchool(ClassInfo classMessage, SchoolInfo schoolInfo,String ecardNo);
    }
}
