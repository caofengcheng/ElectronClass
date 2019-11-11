package com.electronclass.set.login.contract;

import android.content.Context;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.pda.mvp.entity.TeacherInfo;

import java.util.List;

/**
 * @author caofengcheng on 2019-10-31
 */
public interface LoginContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void sendSms(String phoneNum);
        void login(String phoneNum, String smsCode);

        void getClassList(String departId, String userId);
        void bound(String departId, Context context);
    }

    interface View extends BaseView {
        void onSms(boolean isSuccess);
        void onlogin(boolean str,   TeacherInfo teacherInfo);

        void onClassList(List<ClassItem> classItems);

        void onGradeList(List<ClassItem> classItems);

        void onSchoolList(List<ClassItem> classItems);

        void onBound(String msg);
    }

    interface Presenter extends BasePresenterInterface<LoginContract.View> {
        void sendSms(String phoneNum);
        void onSms(boolean isSuccess);

        void login(String phoneNum, String smsCode);
        void onlogin(boolean str, TeacherInfo teacherInfo);

        void getClassList(String departId, String userId);

        void onClassList(List<ClassItem> classItems);

        void onGradeList(List<ClassItem> classItems);

        void onSchoolList(List<ClassItem> classItems);

        void bound(String departId,Context context);

        void onBound(String msg);
    }
}
