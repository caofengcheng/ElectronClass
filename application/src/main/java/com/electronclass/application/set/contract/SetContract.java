package com.electronclass.application.set.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.ClassItem;

import java.util.List;

public interface SetContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void getClassList(String departId, String userId);

        void bound(String departCode);
    }

    interface View extends BaseView {
        void onClassList(List<ClassItem> classItems);

        void onGradeList(List<ClassItem> classItems);

        void onSchoolList(List<ClassItem> classItems);

        void onBound(String msg);

    }

    interface Presenter extends BasePresenterInterface<SetContract.View> {
        void getClassList(String departId, String userId);

        void onClassList(List<ClassItem> classItems);

        void onGradeList(List<ClassItem> classItems);

        void onSchoolList(List<ClassItem> classItems);

        void bound(String departCode);

        void onBound(String msg);
    }
}