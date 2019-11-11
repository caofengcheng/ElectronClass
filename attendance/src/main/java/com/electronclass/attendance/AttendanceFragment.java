package com.electronclass.attendance;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cncoderx.wheelview.OnWheelChangedListener;
import com.cncoderx.wheelview.WheelView;
import com.electronclass.attendance.contract.AttendanceContract;
import com.electronclass.attendance.databinding.FragmentNewAttendanceBinding;
import com.electronclass.attendance.presenter.AttendancePresenter;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.Tools;
import com.electronclass.pda.mvp.entity.AttendanceExt;
import com.electronclass.pda.mvp.entity.UserAttendanceInfo;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;


/**
 * 考勤
 */
public class AttendanceFragment extends BaseFragment<AttendanceContract.Presenter> implements AttendanceContract.View {


    private FragmentNewAttendanceBinding binding;
    private PopupWindow                  popupWindow;
    private LinearLayout                 sure;

    private CommonRecyclerViewAdapter<UserAttendanceInfo> adapter;

    private WheelView    wl_start_day;
    private CharSequence year;
    private CharSequence month;
    private CharSequence day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_new_attendance, container, false );
        View view = binding.getRoot();
        init( view );
        return view;
    }

    @NotNull
    @Override
    protected AttendanceContract.Presenter getPresenter() {
        return new AttendancePresenter();
    }

    @Override
    protected void initView(View view) {
        binding.today.setText( DateUtil.getNowYear() + "年" + DateUtil.getNowMonth() + "月" + DateUtil.getNowDay() + "日考勤" );
        binding.data.setText( DateUtil.getNowYear() + "年" + DateUtil.getNowMonth() + "月" + DateUtil.getNowDay() + "日" );
        setAdapter();
    }

    @Override
    protected void initData() {
        if (GlobalParam.getClassInfo() == null) {
            Tools.displayToast( "请先绑定班牌！" );
            return;
        }
        mPresenter.getAttendance( DateUtil.getNowDate( DateUtil.DatePattern.ALL_TIME ) );
    }

    @Override
    protected void showData() {
        binding.data.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePop();
            }
        } );

        binding.search.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAttendance( year
                        + "-" + month
                        + "-" + day
                        + "  00:00:00" );
            }
        } );
    }

    @Override
    protected void releaseData() {

    }

    @Override
    protected void getData() {

    }

    /**
     * //     * 开启时间选择器
     * //
     */
    private void showTimePop() {
        LayoutInflater inflater        = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.pop_attendance_select, null );
        popupWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );
        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        popupWindow.setOutsideTouchable( false );

        final WheelView wl_start_year  = popupWindowView.findViewById( R.id.wl_start_year );
        final WheelView wl_start_month = popupWindowView.findViewById( R.id.wl_start_month );
        wl_start_day = popupWindowView.findViewById( R.id.wl_start_day );
        TextView sureChoose = popupWindowView.findViewById( R.id.sureChoose );
        TextView stopChoose = popupWindowView.findViewById( R.id.stopChoose );
        wl_start_year.setEntries( DateUtil.Year() );
        wl_start_month.setEntries( DateUtil.Month() );
        wl_start_day.setEntries( DateUtil.thisDayEntries() );


        wl_start_year.setOnWheelChangedListener( new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                CharSequence year  = wl_start_year.getCurrentItem();
                CharSequence month = wl_start_month.getCurrentItem();
                updateDayEntries( Integer.parseInt( (String) year ), Integer.parseInt( (String) month ) );
            }
        } );
        wl_start_month.setOnWheelChangedListener( new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView view, int oldIndex, int newIndex) {
                CharSequence year  = wl_start_year.getCurrentItem();
                CharSequence month = wl_start_month.getCurrentItem();
                updateDayEntries( Integer.parseInt( (String) year ), Integer.parseInt( (String) month ) );
            }
        } );

        sureChoose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = wl_start_year.getCurrentItem();
                month = wl_start_month.getCurrentItem();
                day = wl_start_day.getCurrentItem();
                binding.data.setText( year + "年" + month + "月" + day + "日" );
                popupWindow.dismiss();
            }
        } );
        stopChoose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        } );

        int yearItem  = DateUtil.thisYear();
        int monthItem = DateUtil.thisMonth();
        wl_start_year.setCurrentIndex( yearItem, true );
        wl_start_month.setCurrentIndex( monthItem, true );
        popupWindow.showAtLocation( binding.attendanceView, Gravity.CENTER, 0, 0 );
    }


    @Override
    public void onAttendanceExt(AttendanceExt attendanceExt) {
        binding.noTo.setText( "未   到：" + (attendanceExt.getPersonNum() - attendanceExt.getAttendanceNum()) + "人" );
        binding.beLate.setText( "迟   到：" + attendanceExt.getLateNum() + "人" );
        binding.sureLate.setText( "应   到：" + attendanceExt.getPersonNum() + "人" );
        binding.actualArrival.setText( "实   到：" + attendanceExt.getAttendanceNum() + "人" );
        if (attendanceExt.getPersonNum() == 0) {
            binding.turnOutForWork.setText( "出勤率： 0% " );
        } else {
            binding.turnOutForWork.setText( "出勤率：" + attendanceExt.getAttendanceNum() / attendanceExt.getPersonNum() * 100 + "% " );
        }
    }

    @Override
    public void onUserAttendanceInfo(List<UserAttendanceInfo> userAttendanceInfo) {
        adapter.setData( userAttendanceInfo );
        adapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        adapter = new CommonRecyclerViewAdapter<UserAttendanceInfo>( R.layout.attendance_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, UserAttendanceInfo item) {
                baseViewHolder.setText( R.id.name, item.getUserName() );
                switch (item.getAttendanceType()) {
                    case 0:
                        baseViewHolder.setBackgroundResources( R.id.attendanceItem, R.drawable.shape_8_fae9ba );
                        break;
                    case 2:
                        baseViewHolder.setBackgroundResources( R.id.attendanceItem, R.drawable.shape_8_ffd2d2 );
                        break;

                }
            }
        };
        adapter.bindRecyclerView( binding.recyclerView, new GridLayoutManager(
                getActivity(), 8 ) );
    }

    /**
     * 当滚动年、月的时候判断当前月的天数，并进行设置
     *
     * @param year
     * @param month
     */
    private void updateDayEntries(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set( Calendar.YEAR, year );
        calendar.set( Calendar.MONTH, month - 1 );

        int days = calendar.getActualMaximum( Calendar.DAY_OF_MONTH );
        switch (days) {
            case 28:
                wl_start_day.setEntries( DateUtil.Day28() );
                break;
            case 29:
                wl_start_day.setEntries( DateUtil.Day29() );
                break;
            case 30:
                wl_start_day.setEntries( DateUtil.Day30() );
                break;
            case 31:
                wl_start_day.setEntries( DateUtil.Day31() );
                break;
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        if (!hidden) {
            if (GlobalParam.getClassInfo() == null) {
                Tools.displayToast( "请先绑定班牌！" );
                return;
            }
            if (mPresenter != null) {
                mPresenter.getAttendance( DateUtil.getNowDate( DateUtil.DatePattern.ALL_TIME ) );
            }
        }
    }
}
