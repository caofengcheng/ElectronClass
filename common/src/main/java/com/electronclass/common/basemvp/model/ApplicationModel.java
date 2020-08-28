package com.electronclass.common.basemvp.model;

import android.content.Context;

import com.electronclass.common.basemvp.contract.ApplicationContract;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.MacAddress;
import com.electronclass.common.util.DateUtil;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ClassMessage;
import com.electronclass.pda.mvp.entity.ECardDetail;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

public class ApplicationModel extends BaseModel implements ApplicationContract.Model {
    private ApplicationContract.Presenter mPresenter;

    @Override
    public void setPresenter(ApplicationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getClassAndSchool(Context context) {
//        RestManager.getRestApi().getClassAndSchool( MacAddress.ECARDNO == null ? MacAddress.getMacAddress( context ) : MacAddress.ECARDNO )
////        RestManager.getRestApi().getClassAndSchool( Integer.parseInt(  MacAddress.getMacAddress() ))
//                .compose( RxComposer.composeSingle() )
//                .subscribe( new BaseSingle<ServiceResponse<ClassMessage>>( compositeDisposable ) {
//                    @Override
//                    public void onSuccess(ServiceResponse<ClassMessage> result) {
//                        if (result.getCode().equals( "200" )) {
//                            mPresenter.onClassAndSchool( result.getData().getClassInfo(), result.getData().getSchoolInfo(), result.getData().getEcardNo() );
//                        } else {
//                            mPresenter.onError( result.getMsg() );
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e, String errorMsg) {
//                        mPresenter.onError( errorMsg );
//                    }
//                } );
        RestManager.getRestApi().getECardDetail(MacAddress.ECARDNO == null ? MacAddress.getMacAddress(context) : MacAddress.ECARDNO)
                .compose(RxComposer.composeSingle())
                .subscribe(new BaseSingle<ServiceResponse<ECardDetail>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<ECardDetail> result) {
                        if (result.getCode().equals("200")) {
                            mPresenter.onClassAndSchool(result.getData());
                        } else {
                            mPresenter.onError(result.getMsg());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });

    }

    @Override
    public void getCardAttendance(String studentCardNo) {
        int islate = 0;
        logger.info("打卡时间：" + DateUtil.getNowDate(DateUtil.DatePattern.ONLY_HOUR_MINUTE));
        logger.info("考勤时间：" + GlobalParam.getEventTime());
        switch (DateUtil.getNowDate(DateUtil.DatePattern.ONLY_HOUR_MINUTE).compareTo(GlobalParam.getEventTime())) {
            case -1:
                islate = 0;
                break;
            case 0:
                islate = 0;
                break;
            case 1:
                islate = 1;//迟到
                break;
        }
        logger.info("是否迟到：" + islate);
        RestManager.getRestApi().getCardAttendance(GlobalParam.getEcardNo(), studentCardNo, DateUtil.getNowDate(DateUtil.DatePattern.ALL_TIME), islate,GlobalParam.getSchoolInfo().getSchoolId())
                .compose(RxComposer.<ServiceResponse>composeSingle())
                .subscribe(new BaseSingle<ServiceResponse>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse result) {
                        mPresenter.onCardAttendance(result.getMsg());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }
}
