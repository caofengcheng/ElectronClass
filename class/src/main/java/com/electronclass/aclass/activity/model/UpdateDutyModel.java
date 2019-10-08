package com.electronclass.aclass.activity.model;

import com.electronclass.aclass.activity.contract.UpdateDutyContract;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;



public class UpdateDutyModel extends BaseModel implements UpdateDutyContract.Model {
    private UpdateDutyContract.Presenter mPresenter;

    @Override
    public void setPresenter(UpdateDutyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void addOrUpdateDuty(String id, String studentCardNo, String task, String name, String eventDate) {
        RestManager.getRestApi().addOrUpdateDuty(id, GlobalParam.getEcardNo(),studentCardNo,task,name,eventDate)
                .compose(  RxComposer.<ServiceResponse>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse result) {
                        if (!result.getCode().equals( "200" ))
                        {
                            mPresenter.onError( result.getMsg() );
                            return;
                        }
                        mPresenter.onSuccress(result.getMsg());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }

    @Override
    public void deleteDuty(String id) {
        RestManager.getRestApi().deleteDuty(id, GlobalParam.getEcardNo())
                .compose(  RxComposer.<ServiceResponse>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse result) {
                        if (!result.getCode().equals( "200" ))
                        {
                            mPresenter.onError( result.getMsg() );
                            return;
                        }
                        mPresenter.onDeleteDuty(result.getMsg());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }
}