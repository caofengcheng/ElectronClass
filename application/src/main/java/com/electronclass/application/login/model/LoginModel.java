package com.electronclass.application.login.model;


import com.electronclass.application.login.contract.LoginContract;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.Jurisdiction;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

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
                        if (result.getCode().equals( "200" )) {
                            switch (result.getData().getPermission()) {
                                case 0:
                                    mPresenter.onlogin( false );
                                    mPresenter.onError( "没有权限!" );
                                    break;
                                case 15:
                                    mPresenter.onlogin( true );
                                    mPresenter.onError( "班主任登录!" );
                                    break;
                                case 240:
                                    mPresenter.onlogin( true );
                                    mPresenter.onError( "校管理员登录!" );
                                    break;
                                case 255:
                                    mPresenter.onlogin( true );
                                    mPresenter.onError( "班主任和管理员登录!" );
                                    break;
                            }
                            GlobalParam.setTeacherInfo( result.getData().getTeacherInfo() );

                            return;
                        } else {
                            mPresenter.onlogin( false );
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