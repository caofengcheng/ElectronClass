package com.electronclass.home.activity.presenter;

import com.electronclass.home.activity.contract.ClassMienContract;
import com.electronclass.home.activity.model.ClassMienModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.ClassMien;

public class ClassMienPresenter extends BasePresenter<ClassMienContract.Model, ClassMienContract.View> implements ClassMienContract.Presenter {
    private int pageNum = 0;
    private int pages = 9999;
    @Override
    protected void initModel() {
        mModel = new ClassMienModel();
        mModel.setPresenter( this );
    }

    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
        mView.onError(errorMessage);
        if (pageNum > 0)
            mView.loadMoreEnd();
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
}