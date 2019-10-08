package com.electronclass.application.model;

import com.electronclass.application.contract.ApplicationContract;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.GlobalParameter;
import com.electronclass.common.util.DateUtil;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.pda.mvp.entity.Jurisdiction;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

import java.util.List;

public class ApplicationModel extends BaseModel implements ApplicationContract.Model {
    private ApplicationContract.Presenter mPresenter;

    @Override
    public void setPresenter(ApplicationContract.Presenter presenter) {
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
    public void bound(String departCode) {
        RestManager.getRestApi().bound( GlobalParameter.getMacAddress(), departCode )
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
