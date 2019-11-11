package com.electronclass.electronclass.presenter;

import com.electronclass.electronclass.contract.MainContract;
import com.electronclass.electronclass.model.MainModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.Inform;

import java.util.List;

/**
 * @author caofengcheng on 2019-10-30
 */
public class MainPresenter extends BasePresenter<MainContract.Model,MainContract.View> implements MainContract.Presenter {


    @Override
    protected void initModel() {
        mModel = new MainModel();
        mModel.setPresenter( this );
    }

    @Override
    public void onError(String errorMessage) {
       mView.onError( errorMessage );
    }

    @Override
    public void getInform(String eCardNo, String userId, String departId, int type, int isAvaliable) {
        mModel.getInform(eCardNo, userId, departId, type, isAvaliable);
    }

    @Override
    public void onInform(List<Inform> inform) {
        mView.onInform(inform);
    }
}
