package com.electronclass.aclass.activity.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;


public interface UpdateDutyContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void addOrUpdateDuty(String id,String studentCardNo,String task,String name,String eventDate);
        void deleteDuty(String id);
    }

    interface View extends BaseView {
        void onSuccress(String msg);
        void onDeleteDuty(String msg);
    }

    interface Presenter extends BasePresenterInterface<View> {
        @Override
        void setView(View view);

        void addOrUpdateDuty(String id,String studentCardNo,String task,String name,String eventDate);

        void onSuccress(String msg);

        void deleteDuty(String id);

        void onDeleteDuty(String msg);
    }
}