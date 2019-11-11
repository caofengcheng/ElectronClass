package com.electronclass.home.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.ClassMienMessage;
import com.electronclass.pda.mvp.entity.Inform;

import java.util.List;

public interface HomeContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);

        /**
         * 获取校园和班级通知
         * @param eCardNo
         * @param userId
         * @param departId
         * @param type
         * @param isAvaliable
         */
        void getInform(String eCardNo, String userId,
                       String departId, int type,
                       int isAvaliable);

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
        void onInform(List<Inform> inform);

        void onClassMien(List<ClassMienMessage> classMienMessages);
    }

    interface Presenter extends BasePresenterInterface<View> {

        void getInform(String eCardNo, String userId,
                       String departId, int type,
                       int isAvaliable);

        void onInform(List<Inform> inform);

        void getClassMien(String eCardNo, String userId,
                          String classId,int pageStart,int pageSize);

        void onClassMien(ClassMien classMien);
    }
}
