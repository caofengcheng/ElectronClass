package com.electronclass.application.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.ClassItem;

import java.util.List;

public interface ApplicationContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);

        void sendSms(String phoneNum);

        void login(String phoneNum, String smsCode);

        void getClassList(String departId, String userId);

        void bound(String departCode);
    }

    interface View extends BaseView {
        void onSms(boolean isSuccess);

        void onlogin(boolean str);

        void onClassList(List<ClassItem> classItems);

        void onGradeList(List<ClassItem> classItems);

        void onSchoolList(List<ClassItem> classItems);

        void onBound(String msg);
    }

    interface Presenter extends BasePresenterInterface<View> {
        void sendSms(String phoneNum);

        void onSms(boolean isSuccess);

        void login(String phoneNum, String smsCode);

        void onlogin(boolean str);

        void getClassList(String departId, String userId);

        void onClassList(List<ClassItem> classItems);

        void onGradeList(List<ClassItem> classItems);

        void onSchoolList(List<ClassItem> classItems);

        void bound(String departCode);

        void onBound(String msg);
    }
}
