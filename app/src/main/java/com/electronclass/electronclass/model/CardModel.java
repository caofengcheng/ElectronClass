package com.electronclass.electronclass.model;

import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.DateUtil;
import com.electronclass.electronclass.contract.CardContract;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;


public class CardModel extends BaseModel implements CardContract.Model {
    private CardContract.Presenter mPresenter;

    @Override
    public void setPresenter(CardContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getCardAttendance(String studentCardNo) {
        int islate = 0;
        logger.info( "打卡时间："+ DateUtil.getNowDate(DateUtil.DatePattern.ONLY_HOUR_MINUTE));
        logger.info( "考勤时间：" +GlobalParam.getEventTime());
        switch (DateUtil.getNowDate(DateUtil.DatePattern.ONLY_HOUR_MINUTE).compareTo( GlobalParam.getEventTime() )){
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
        logger.info( "是否迟到：" + islate);
        RestManager.getRestApi().getCardAttendance( GlobalParam.getEcardNo(),studentCardNo, DateUtil.getNowDate(DateUtil.DatePattern.ALL_TIME),islate )
                .compose(  RxComposer.<ServiceResponse>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse result) {
                        if (result.getCode().equals( "200" ))
                        {
                            mPresenter.onCardAttendance( true );
                            return;
                        }else {
                            mPresenter.onCardAttendance( false );
                        }

                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }
}
