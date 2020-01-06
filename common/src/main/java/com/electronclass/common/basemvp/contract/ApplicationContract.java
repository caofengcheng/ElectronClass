package com.electronclass.common.basemvp.contract;

import android.content.Context;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.ClassInfo;
import com.electronclass.pda.mvp.entity.SchoolInfo;

public interface ApplicationContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void getClassAndSchool(Context context);
        void getCardAttendance(String studentCardNo);
    }

    interface View extends BaseView {
        void onClassAndSchool();
        void onCardAttendance(String msg);
    }

    interface Presenter extends BasePresenterInterface<View> {
        void getClassAndSchool(Context context);
        void onClassAndSchool(ClassInfo classMessage, SchoolInfo schoolInfo,String ecardNo);

        void getCardAttendance(String studentCardNo);
        void onCardAttendance(String msg);
    }
}
