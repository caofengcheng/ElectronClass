package com.electronclass.home.presenter;

import com.electronclass.home.contract.HomeContract;
import com.electronclass.home.model.HomeModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.ClassMienPage;
import com.electronclass.pda.mvp.entity.Inform;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> implements HomeContract.Presenter {

    @Override
    public void getInform(String pageNo, String pageSize, int type) {
        mModel.getInform(pageNo, pageSize, type);
    }

    @Override
    public void onInform(List<Inform> inform) {
        mView.onInform(inform);
    }

    @Override
    public void getClassMien(String eCardNo, String userId, String classId, int pageStart, int pageSize) {
        mModel.getClassMien(eCardNo, userId, classId, 1, 9);
    }

    @Override
    public void onClassMien(ClassMienPage classMien) {
        mView.onClassMien(classMien.getRecords());
    }


    @Override
    protected void initModel() {
        mModel = new HomeModel();
        mModel.setPresenter(this);
    }

    @Override
    public void onError(String errorMessage) {
        mView.onError(errorMessage);
    }
}
