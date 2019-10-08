package com.electronclass.home.presenter;

import com.electronclass.home.contract.HomeContract;
import com.electronclass.home.model.HomeModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.Inform;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeContract.Model, HomeContract.View> implements HomeContract.Presenter {
    private int pageNum = 0;
    private int pages = 9999;

    @Override
    public void getInform(String eCardNo, String userId, String departId, int type, int isAvaliable) {
        mModel.getInform(eCardNo, userId, departId, type, isAvaliable);
    }

    @Override
    public void onInform(List<Inform> inform) {
        mView.onInform(inform);
    }

    @Override
    public void getClassMien(String eCardNo, String userId, String classId, int pageStart, int pageSize) {
        if (pageStart == 1){
            pageNum = 0;
            pages = 9999;
        }
        this.pageNum++;
        if ( this.pageNum > pages) {
            return;
        }
        mModel.getClassMien( eCardNo,userId,classId,pageNum,9 );
    }

    @Override
    public void onClassMien(ClassMien classMien) {
        this.pages = classMien.getTotal()/9;
        if (pageNum > 1) {
            mView.addSheltermaterials(classMien.getData());
        } else {
            mView.onClassMien(classMien.getData());
        }
        if (pageNum >= pages) {
            mView.loadMoreEnd();
        }
    }

    @Override
    public void onNoData() {
        mView.onNoData();
    }

    @Override
    protected void initModel() {
        mModel = new HomeModel();
        mModel.setPresenter(this);
    }

    @Override
    public void onError(String errorMessage) {
        mView.onError(errorMessage);
        if (pageNum > 0)
            mView.loadMoreEnd();
    }
}
