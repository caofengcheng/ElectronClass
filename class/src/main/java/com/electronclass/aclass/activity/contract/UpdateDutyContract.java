package com.electronclass.aclass.activity.contract;

import android.content.Context;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;


public interface UpdateDutyContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void addOrUpdateDuty(Context context, String id, String password, String task, String name, String eventDate);
        void deleteDuty(Context context,String id, String password);
    }

    interface View extends BaseView {
        void onSuccress(String msg);
        void onDeleteDuty(String msg);
    }

    interface Presenter extends BasePresenterInterface<View> {
        @Override
        void setView(View view);

        void addOrUpdateDuty(Context context,String id,String password,String task,String name,String eventDate);

        void onSuccress(String msg);

        void deleteDuty(Context context,String id, String password);

        void onDeleteDuty(String msg);
    }
}