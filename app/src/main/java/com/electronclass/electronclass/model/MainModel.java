package com.electronclass.electronclass.model;

import com.electronclass.common.database.GlobalParam;
import com.electronclass.electronclass.contract.MainContract;
import com.electronclass.electronclass.presenter.MainPresenter;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.Inform;
import com.electronclass.pda.mvp.entity.InformPage;
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
    }
}
