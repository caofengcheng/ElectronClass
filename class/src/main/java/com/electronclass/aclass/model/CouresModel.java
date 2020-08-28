package com.electronclass.aclass.model;

import com.electronclass.aclass.contract.CouresContract;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.DateUtil;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.Coures;
import com.electronclass.pda.mvp.entity.CouresNode;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class CouresModel extends BaseModel implements CouresContract.Model {
    private CouresContract.Presenter mPresenter;

    @Override
    public void setPresenter(CouresContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getCoures() {
        if (GlobalParam.getClassInfo() == null || StringUtils.isEmpty(  GlobalParam.getClassInfo().getClassId())){
            mPresenter.onError("未绑定班级");
            return;
        }
//        String date = DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY);
//        RestManager.getRestApi().getCoures( GlobalParam.getEcardNo(),null,GlobalParam.getClassInfo().getClassId(),date,0)
//                .compose(  RxComposer.<ServiceResponse<List<Coures>>>composeSingle() )
//                .subscribe(new BaseSingle<ServiceResponse<List<Coures>>>(compositeDisposable) {
//                    @Override
//                    public void onSuccess(ServiceResponse<List<Coures>> result) {
//                        if (!result.getCode().equals( "200" ))
//                        {
//                            mPresenter.onError( result.getMsg() );
//                            return;
//                        }
//                        if (result.getData() == null || result.getData().size() == 0){
//                            mPresenter.onError( "无课表" );
//                            return;
//                        }
//                        mPresenter.onCoures(result.getData());
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e, String errorMsg) {
//                        mPresenter.onError(errorMsg);
//                    }
//                });

        String date = DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY);
        RestManager.getRestApi().getCoures( GlobalParam.getClassInfo().getClassId())
                .compose(  RxComposer.<ServiceResponse<List<CouresNode>>>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse<List<CouresNode>>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<List<CouresNode>> result) {
                        if (!result.getCode().equals( "200" ))
                        {
                            mPresenter.onError( result.getMsg() );
                            return;
                        }
                        if (result.getData() == null || result.getData().size() == 0){
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
