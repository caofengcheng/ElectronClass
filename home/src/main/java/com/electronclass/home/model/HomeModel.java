package com.electronclass.home.model;

import com.electronclass.common.database.GlobalParam;
import com.electronclass.home.contract.HomeContract;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.ClassMienPage;
import com.electronclass.pda.mvp.entity.Inform;
import com.electronclass.pda.mvp.entity.InformPage;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

import java.util.List;


public class HomeModel extends BaseModel implements HomeContract.Model {
    private HomeContract.Presenter mPresenter;

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getInform(String pageNo, String pageSize, int type) {
        RestManager.getRestApi().getInform(GlobalParam.getClassInfo().getClassId(), pageNo, pageSize, type)
                .compose(RxComposer.<ServiceResponse<InformPage>>composeSingle())
                .subscribe(new BaseSingle<ServiceResponse<InformPage>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<InformPage> result) {
                        if (!result.getCode().equals("200")) {
                            mPresenter.onError(result.getMsg());
                            return;
                        }
                        if (result.getData().getRecords().size() == 0) {
//                            mPresenter.onError( "无校园通知数据" );
                            return;
                        }
                        mPresenter.onInform(result.getData().getRecords());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
//        RestManager.getRestApi().getInform(eCardNo,userId,departId,type,isAvaliable)
//                .compose(  RxComposer.<ServiceResponse<List<Inform>>>composeSingle() )
//                .subscribe(new BaseSingle<ServiceResponse<List<Inform>>>(compositeDisposable) {
//                    @Override
//                    public void onSuccess(ServiceResponse<List<Inform>> result) {
//                        if (!result.getCode().equals( "200" ))
//                        {
//                            mPresenter.onError( result.getMsg() );
//                            return;
//                        }
//                        if (result.getData().size() == 0){
////                            mPresenter.onError( "无校园通知数据" );
//                            return;
//                        }
//                        mPresenter.onInform(result.getData());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e, String errorMsg) {
//                        mPresenter.onError(errorMsg);
//                    }
//                });
    }

    @Override
    public void getClassMien(String eCardNo, String userId, String classId, int pageStart, int pageSize) {
//        RestManager.getRestApi().getClassMien(eCardNo, userId, classId, pageStart, pageSize)
//                .compose(RxComposer.<ServiceResponse<ClassMien>>composeSingle())
//                .subscribe(new BaseSingle<ServiceResponse<ClassMien>>(compositeDisposable) {
//                    @Override
//                    public void onSuccess(ServiceResponse<ClassMien> result) {
//                        if (!result.getCode().equals("200")) {
//                            mPresenter.onError(result.getMsg());
//                            return;
//                        }
////                        if (result.getData().getData().size() == 0){
////                            mPresenter.onNoData();
////                            return;
////                        }
//                        mPresenter.onClassMien(result.getData());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e, String errorMsg) {
//                        mPresenter.onError(errorMsg);
//                    }
//                });


        RestManager.getRestApi().getClassMien(GlobalParam.getClassInfo().getClassId(), pageStart, pageSize)
                .compose(RxComposer.<ServiceResponse<ClassMienPage>>composeSingle())
                .subscribe(new BaseSingle<ServiceResponse<ClassMienPage>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<ClassMienPage> result) {
                        if (!result.getCode().equals("200")) {
                            mPresenter.onError(result.getMsg());
                            return;
                        }
//                        if (result.getData().getData().size() == 0){
//                            mPresenter.onNoData();
//                            return;
//                        }
                        mPresenter.onClassMien(result.getData());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }
}
