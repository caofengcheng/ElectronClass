package com.electronclass.electronclass.contract;

import com.electronclass.electronclass.presenter.MainPresenter;
import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.Inform;

import java.util.List;

/**
 * @author caofengcheng on 2019-10-30
 */
public interface MainContract {
    interface Model extends BaseModelInterface {
        void setPresenter(MainPresenter mainPresenter);
        /**
         * 获取校园和班级通知
         */
        void getInform(String pageNo, String pageSize, int type);
    }

    interface View extends BaseView {

        void onInform(List<Inform> inform);
    }

    interface Presenter extends BasePresenterInterface<View> {
        void getInform(String pageNo, String pageSize, int type);

        void onInform(List<Inform> inform);
    }
}
