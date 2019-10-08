package com.electronclass.aclass.model;

import com.electronclass.aclass.contract.DutyContract;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.DateUtil;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.Coures;
import com.electronclass.pda.mvp.entity.Duty;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

import java.util.List;

public class DutyModel extends BaseModel implements DutyContract.Model {
    private DutyContract.Presenter mPresenter;

    @Override
    public void setPresenter(DutyContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getDuty() {
        String date = DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY);
        RestManager.getRestApi().getDuty( GlobalParam.getEcardNo(),null,GlobalParam.getClassInfo().getClassId(),date,1)
                .compose(  RxComposer.<ServiceResponse<List<Duty>>>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse<List<Duty>>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<List<Duty>> result) {
                        if (!result.getCode().equals( "200" ))
                        {
                            mPresenter.onError( result.getMsg() );
                            return;
                        }
                        if (result.getData().size() == 0){
                            mPresenter.onError( "无值日数据" );
                            return;
                        }
                        mPresenter.onDuty(result.getData());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }
}
