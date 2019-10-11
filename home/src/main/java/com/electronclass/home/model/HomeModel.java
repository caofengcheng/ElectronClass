package com.electronclass.home.model;

import com.electronclass.home.contract.HomeContract;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.Inform;
import com.electronclass.pda.mvp.rest.RestManager;
import com.electronclass.pda.mvp.entity.ServiceResponse;

import java.util.List;


public class HomeModel extends BaseModel implements HomeContract.Model {
    private HomeContract.Presenter mPresenter;

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getInform(String eCardNo, String userId, String departId, final int type, int isAvaliable) {
        RestManager.getRestApi().getInform(eCardNo,userId,departId,type,isAvaliable)
                .compose(  RxComposer.<ServiceResponse<List<Inform>>>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse<List<Inform>>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<List<Inform>> result) {
                        if (!result.getCode().equals( "200" ))
                        {
                            mPresenter.onError( result.getMsg() );
                            return;
                        }
                        if (result.getData().size() == 0){
//                            mPresenter.onError( "无校园通知数据" );
                            return;
                        }
                        mPresenter.onInform(result.getData());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }

    @Override
    public void getClassMien(String eCardNo, String userId, String classId, int pageStart, int pageSize) {
        RestManager.getRestApi().getClassMien(eCardNo,userId,classId,pageStart,pageSize)
                .compose(  RxComposer.<ServiceResponse<ClassMien>>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse<ClassMien>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<ClassMien> result) {
                        if (!result.getCode().equals( "200" ))
                        {
                            mPresenter.onError( result.getMsg() );
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
