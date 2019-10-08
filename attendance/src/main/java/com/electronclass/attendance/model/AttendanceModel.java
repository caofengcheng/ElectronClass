package com.electronclass.attendance.model;

import com.electronclass.attendance.contract.AttendanceContract;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.DateUtil;
import com.electronclass.pda.mvp.base.BaseModel;
import com.electronclass.pda.mvp.base.BaseSingle;
import com.electronclass.pda.mvp.base.RxComposer;
import com.electronclass.pda.mvp.entity.Attendance;
import com.electronclass.pda.mvp.entity.Coures;
import com.electronclass.pda.mvp.entity.ServiceResponse;
import com.electronclass.pda.mvp.rest.RestManager;

import java.util.List;

public class AttendanceModel extends BaseModel implements AttendanceContract.Model {
    private AttendanceContract.Presenter mPresenter;

    @Override
    public void setPresenter(AttendanceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void getAttendance(String eventDate) {
        RestManager.getRestApi().getAttendance( GlobalParam.getEcardNo(),null,GlobalParam.getClassInfo().getClassId(),eventDate)
                .compose(  RxComposer.<ServiceResponse<Attendance>>composeSingle() )
                .subscribe(new BaseSingle<ServiceResponse<Attendance>>(compositeDisposable) {
                    @Override
                    public void onSuccess(ServiceResponse<Attendance> result) {
                        if (!result.getCode().equals( "200" ))
                        {
                            mPresenter.onError( result.getMsg() );
                            return;
                        }
                        mPresenter.onAttendance(result.getData());
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        mPresenter.onError(errorMsg);
                    }
                });
    }
}
