package com.electronclass.set.login.model;

import android.content.Context;

import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.MacAddress;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.pda.mvp.entity.Jurisdiction;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;
import com.electronclass.set.login.contract.LoginContract;

import java.util.List;

/**
 * @author caofengcheng on 2019-10-31
 */
public class LoginModel extends BaseModel implements LoginContract.Model {
    private LoginContract.Presenter mPresenter;

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void sendSms(String phoneNum) {
        RestManager.getRestApi().sendSms( phoneNum )
                .compose( RxComposer.<ServiceResponse>composeSingle() )
                .subscribe( new BaseSingle<ServiceResponse>( compositeDisposable ) {
                    @Override
                    public void onSuccess(ServiceResponse result) {
                        if (result.getCode().equals( "200" )) {
                            mPresenter.onSms( true );
                            return;
                        } else {
                            mPresenter.onSms( false );
                        }

                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError( errorMsg );
                    }
                } );
    }

    @Override
    public void login(String phoneNum, String smsCode) {
        RestManager.getRestApi().login( phoneNum, smsCode )
                .compose( RxComposer.<ServiceResponse<Jurisdiction>>composeSingle() )
                .subscribe( new BaseSingle<ServiceResponse<Jurisdiction>>( compositeDisposable ) {
                    @Override
                    public void onSuccess(ServiceResponse<Jurisdiction> result) {
                        if (result.getCode().equals( "200" ) && result.getData() != null) {
                            switch (result.getData().getPermission()) {
                                case 0:
                                    mPresenter.onlogin( false, null );
                                    mPresenter.onError( "没有权限!" );
                                    break;
                                case 15:
                                    mPresenter.onlogin( true, result.getData().getTeacherInfo() );
                                    mPresenter.onError( "班主任登录!" );
                                    break;
                                case 240:
                                    mPresenter.onlogin( true, result.getData().getTeacherInfo() );
                                    mPresenter.onError( "校管理员登录!" );
                                    break;
                                case 255:
                                    mPresenter.onlogin( true, result.getData().getTeacherInfo() );
                                    mPresenter.onError( "班主任和管理员登录!" );
                                    break;
                            }
                            GlobalParam.setTeacherInfo( result.getData().getTeacherInfo() );

                            return;
                        } else {
                            mPresenter.onlogin( false, null );
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
        RestManager.getRestApi().bound( MacAddress.getMacAddress( context ), departId )
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
