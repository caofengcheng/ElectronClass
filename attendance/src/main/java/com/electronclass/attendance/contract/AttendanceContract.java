package com.electronclass.attendance.contract;

import com.electronclass.pda.mvp.base.BaseModelInterface;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.electronclass.pda.mvp.base.BaseView;
import com.electronclass.pda.mvp.entity.Attendance;
import com.electronclass.pda.mvp.entity.AttendanceExt;
import com.electronclass.pda.mvp.entity.UserAttendanceInfo;

import java.util.List;

public interface AttendanceContract {
    interface Model extends BaseModelInterface {
        void setPresenter(Presenter presenter);
        void getAttendance(String eventDate);
    }

    interface View extends BaseView {
        void onAttendanceExt(AttendanceExt attendanceExt);
        void onUserAttendanceInfo(List<UserAttendanceInfo> userAttendanceInfo);
    }

    interface Presenter extends BasePresenterInterface<View> {
        void getAttendance(String eventDate);
        void onAttendance(Attendance attendance);
    }
}
