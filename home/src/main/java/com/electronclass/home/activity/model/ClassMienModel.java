package com.electronclass.home.activity.model;


import com.electronclass.common.database.GlobalParam;
import com.electronclass.home.activity.contract.ClassMienContract;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.ClassMienPage;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

public class ClassMienModel extends BaseModel implements ClassMienContract.Model {
    private ClassMienContract.Presenter mPresenter;

    @Override
    public void setPresenter(ClassMienContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getClassMien(String eCardNo, String userId, String classId, int pageStart, int pageSize) {
        RestManager.getRestApi().getClassMien(GlobalParam.getClassInfo().getClassId(),pageStart,pageSize)
                .compose(  RxComposer.<ServiceResponse<ClassMienPage>>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse<ClassMienPage>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<ClassMienPage> result) {
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