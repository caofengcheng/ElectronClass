package com.electronclass.common.basemvp.model;

import com.electronclass.common.basemvp.contract.ApplicationContract;
import com.electronclass.common.database.MacAddress;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassMessage;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

public class ApplicationModel extends BaseModel implements ApplicationContract.Model {
    private ApplicationContract.Presenter mPresenter;

    @Override
    public void setPresenter(ApplicationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getClassAndSchool() {
        RestManager.getRestApi().getClassAndSchool( MacAddress.ECARDNO == null ? MacAddress.getMacAddress() : MacAddress.ECARDNO )
//        RestManager.getRestApi().getClassAndSchool( Integer.parseInt(  MacAddress.getMacAddress() ))
                .compose( RxComposer.composeSingle())
                .subscribe(new BaseSingle<ServiceResponse<ClassMessage>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<ClassMessage> result) {
                        if (result.getCode().equals( "200" )) {
                            mPresenter.onClassAndSchool( result.getData().getClassInfo(), result.getData().getSchoolInfo(),result.getData().getEcardNo() );
                        }else {
                            mPresenter.onError( result.getMsg() );
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });

    }
}
