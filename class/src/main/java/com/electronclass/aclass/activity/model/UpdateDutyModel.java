package com.electronclass.aclass.activity.model;

import android.content.Context;

import com.electronclass.aclass.activity.contract.UpdateDutyContract;
import com.electronclass.common.database.MacAddress;
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
    public void addOrUpdateDuty(Context context,String id, String password, String task, String name, String eventDate) {
        RestManager.getRestApi().addOrUpdateDuty(id, MacAddress.getMacAddress( context ),password,task,name,eventDate)
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
    public void deleteDuty(Context context,String id, String password) {
        RestManager.getRestApi().deleteDuty(id, MacAddress.getMacAddress( context ),password)
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