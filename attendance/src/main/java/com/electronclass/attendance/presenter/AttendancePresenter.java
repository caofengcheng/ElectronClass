package com.electronclass.attendance.presenter;

import com.electronclass.attendance.contract.AttendanceContract;
import com.electronclass.attendance.model.AttendanceModel;
import com.electronclass.pda.mvp.base.BasePresenter;
import com.electronclass.pda.mvp.entity.Attendance;

public class AttendancePresenter extends BasePresenter<AttendanceContract.Model, AttendanceContract.View> implements AttendanceContract.Presenter {

    @Override
    public void onError(String errorMessage) {
        mView.onError( errorMessage );
    }

    @Override
    protected void initModel() {
        mModel = new AttendanceModel();
        mModel.setPresenter( this );
    }

    @Override
    public void getAttendance(String eventDate) {
        mModel.getAttendance( eventDate );
    }

    @Override
    public void onAttendance(Attendance attendance) {
        mView.onAttendanceExt( attendance.getAttendanceExt() );
        if (attendance.getUserAttendanceInfo().size() > 0) {
            mView.onUserAttendanceInfo( attendance.getUserAttendanceInfo() );
        } else {
            mView.onError( "暂无学生数据" );
        }

    }
}
