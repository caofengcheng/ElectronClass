package com.electronclass.home.activity.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.ClassMienMessage;

import java.util.List;

public interface ClassMienContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        /**
         * 获取班级风采
         * @param eCardNo
         * @param userId
         * @param classId
         * @param pageStart
         * @param pageSize
         */
        void getClassMien(String eCardNo, String userId,
                          String classId,int pageStart,int pageSize);
    }

    interface View extends BaseView {
        void onClassMien(List<ClassMienMessage> classMienMessages);

        void loadMoreFail();
        void loadMoreEnd();
        void EnableLoadMore();
        void addSheltermaterials(List<ClassMienMessage> classMienMessages);
        void onNoData();
    }

    interface Presenter extends BasePresenterInterface<ClassMienContract.View> {
        void getClassMien(String eCardNo, String userId,
                          String classId,int pageStart,int pageSize);

        void onClassMien(ClassMien classMien);

        void onNoData();
    }
}