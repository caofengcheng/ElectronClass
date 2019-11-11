package com.electronclass.set.set.model;

import android.content.Context;

import com.electronclass.common.database.MacAddress;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;
import com.electronclass.set.set.contract.SetContract;

import java.util.List;

/**
 * @author caofengcheng on 2019-10-31
 */
public class SetModel extends BaseModel implements SetContract.Model {
    private SetContract.Presenter mPresenter;

    @Override
    public void setPresenter(SetContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getClassList(String departId, String userId) {
        RestManager.getRestApi().getClassList( departId, userId )
                .compose( RxComposer.<ServiceResponse<List<ClassItem>>>composeSingle() )
                .subscribe( new BaseSingle<ServiceResponse<List<ClassItem>>>( compositeDisposable ) {
                    @Override
                    public void onSuccess(ServiceResponse<List<ClassItem>> result) {
                        if (result.getCode().equals( "200" )) {
                            if (result.getData() != null && result.getData().size() > 0) {
                                if (result.getData().get( 0 ).getLevel() == 2) {
                                    mPresenter.onSchoolList( result.getData() );
                                } else if (result.getData().get( 0 ).getLevel() == 5) {
                                    mPresenter.onGradeList( result.getData() );
                                } else if (result.getData().get( 0 ).getLevel() == 6) {
                                    mPresenter.onClassList( result.getData() );
                                }
                            }

                            return;
                        } else {
                            mPresenter.onError( result.getMsg() );
                        }

                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError( errorMsg );
                    }
                } );
    }

    @Override
    public void bound(String departId, Context context) {
        RestManager.getRestApi().bound( MacAddress.getMacAddress(context), departId )
                .compose( RxComposer.<ServiceResponse>composeSingle() )
                .subscribe( new BaseSingle<ServiceResponse>( compositeDisposable ) {
                    @Override
                    public void onSuccess(ServiceResponse result) {
                        if (result.getCode().equals( "200" )) {
                            mPresenter.onBound( result.getMsg() );
                            return;
                        } else {
                            mPresenter.onError( result.getMsg() );
                        }

                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError( errorMsg );
                    }
                } );
    }

}
