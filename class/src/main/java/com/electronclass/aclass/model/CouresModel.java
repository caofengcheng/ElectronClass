package com.electronclass.aclass.model;

import com.electronclass.aclass.contract.CouresContract;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.DateUtil;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassMien;
import com.electronclass.pda.mvp.entity.Coures;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

import java.util.List;

public class CouresModel extends BaseModel implements CouresContract.Model {
    private CouresContract.Presenter mPresenter;

    @Override
    public void setPresenter(CouresContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getCoures() {
        String date = DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY);
        RestManager.getRestApi().getCoures( GlobalParam.getEcardNo(),null,GlobalParam.getClassInfo().getClassId(),date,0)
                .compose(  RxComposer.<ServiceResponse<List<Coures>>>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse<List<Coures>>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<List<Coures>> result) {
                        if (!result.getCode().equals( "200" ))
                        {
                            mPresenter.onError( result.getMsg() );
                            return;
                        }
                        if (result.getData().size() == 0){
                            mPresenter.onError( "无课表" );
                            return;
                        }
                        mPresenter.onCoures(result.getData());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }
}
