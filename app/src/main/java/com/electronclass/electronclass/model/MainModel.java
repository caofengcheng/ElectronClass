package com.electronclass.electronclass.model;

import com.electronclass.electronclass.contract.MainContract;
import com.electronclass.electronclass.presenter.MainPresenter;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.Inform;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

import java.util.List;

/**
 * @author caofengcheng on 2019-10-30
 */
public class MainModel  extends BaseModel implements MainContract.Model {
    private MainContract.Presenter mPresenter;

    @Override
    public void setPresenter(MainPresenter mainPresenter) {
        this.mPresenter = mainPresenter;
    }

    @Override
    public void getInform(String eCardNo, String userId, String departId, int type, int isAvaliable) {
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
}
