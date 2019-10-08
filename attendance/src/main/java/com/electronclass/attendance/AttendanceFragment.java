package com.electronclass.attendance;


import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.electronclass.attendance.contract.AttendanceContract;
import com.electronclass.attendance.databinding.FragmentAttendanceBinding;
import com.electronclass.attendance.presenter.AttendancePresenter;
import com.electronclass.common.adapter.CommonGridLayoutAdapter;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.Tools;
import com.electronclass.common.util.wheelview.OnWheelChangedListener;
import com.electronclass.common.util.wheelview.OnWheelScrollListener;
import com.electronclass.common.util.wheelview.WheelView;
import com.electronclass.common.util.wheelview.adapter.NumericWheelAdapter;
import com.electronclass.pda.mvp.entity.AttendanceExt;
import com.electronclass.pda.mvp.entity.UserAttendanceInfo;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.List;


/**
 * 考勤
 */
public class AttendanceFragment extends BaseFragment<AttendanceContract.Presenter> implements AttendanceContract.View {

    private FragmentAttendanceBinding binding;
    private PopupWindow               popupWindow;
    private int                       curYear;
    private int                       curMonth;
    private WheelView                 wl_start_year;
    private WheelView                 wl_start_month;
    private WheelView                 wl_start_day;
    private LinearLayout              sure;

    private CommonRecyclerViewAdapter<UserAttendanceInfo> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_attendance, container, false );
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
        binding.year.setText( DateUtil.getNowYear() + "" );
        binding.month.setText( DateUtil.getNowMonth() + "" );
        binding.day.setText( DateUtil.getNowDay() + "" );
        setAdapter();
    }

    @Override
    protected void initData() {
        if (GlobalParam.getClassInfo() == null){
            Tools.displayToast( "请先绑定班牌！" );
            return;
        }
        mPresenter.getAttendance( DateUtil.getNowDate( DateUtil.DatePattern.ALL_TIME ) );
    }

    @Override
    protected void showData() {
        binding.llDate.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        } );

        binding.search.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAttendance( binding.year.getText().toString()
                        + "-" + binding.month.getText().toString()
                        + "-" + binding.day.getText().toString()
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
     * 开启时间选择器
     */
    private void showPop() {
        LayoutInflater inflater        = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.pop_term_select, null );
        popupWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );
        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        popupWindow.setOutsideTouchable( true );
        initWheelView( popupWindowView );
        popupWindow.showAtLocation( binding.clAttendance, Gravity.CENTER, 0, 0 );
    }

    private void initWheelView(View view) {
        Calendar c = Calendar.getInstance();
        curYear = c.get( Calendar.YEAR );
        curMonth = c.get( Calendar.MONTH ) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get( Calendar.DATE );
        /*****************开始时间***********************/
        wl_start_year = view.findViewById( R.id.wl_start_year );
        wl_start_month = view.findViewById( R.id.wl_start_month );
        wl_start_day = view.findViewById( R.id.wl_start_day );
        sure = view.findViewById( R.id.sure );

        NumericWheelAdapter numericWheelAdapterStart1 = new NumericWheelAdapter( getActivity(), 2000, 2100 );
        numericWheelAdapterStart1.setLabel( " " );
        wl_start_year.setViewAdapter( numericWheelAdapterStart1 );
        numericWheelAdapterStart1.setTextColor( R.color.black );
        numericWheelAdapterStart1.setTextSize( 20 );
        wl_start_year.setCyclic( true );//是否可循环滑动
        wl_start_year.addScrollingListener( startScrollListener );
        wl_start_year.addChangingListener( new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                curYear = newValue + 2000;
                initStartDayAdapter();
            }
        } );
        sure.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        } );

        NumericWheelAdapter numericWheelAdapterStart2 = new NumericWheelAdapter( getActivity(), 1, 12, "%02d" );
        numericWheelAdapterStart2.setLabel( " " );
        wl_start_month.setViewAdapter( numericWheelAdapterStart2 );
        numericWheelAdapterStart2.setTextColor( R.color.black );
        numericWheelAdapterStart2.setTextSize( 20 );
        wl_start_month.setCyclic( true );
        wl_start_month.addScrollingListener( startScrollListener );
        wl_start_month.addChangingListener( new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                curMonth = newValue + 1;
                initStartDayAdapter();
            }
        } );
        initStartDayAdapter();
        wl_start_year.setCurrentItem( curYear - 2000 );
        wl_start_month.setCurrentItem( curMonth - 1 );
        wl_start_day.setCurrentItem( curDate - 1 );

    }

    OnWheelScrollListener startScrollListener = new OnWheelScrollListener() {
        @Override
        public void onScrollingStarted(WheelView wheel) {
        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_year  = wl_start_year.getCurrentItem() + 2000;//年
            int n_month = wl_start_month.getCurrentItem() + 1;//月
            int n_day   = wl_start_day.getCurrentItem() + 1;//日
            binding.year.setText( n_year + "" );
            binding.month.setText( n_month + "" );
            binding.day.setText( n_day + "" );
        }
    };

    private void initStartDayAdapter() {
        NumericWheelAdapter numericWheelAdapterStart3 = new NumericWheelAdapter( getActivity(), 1, DateUtil.getDay( curYear, curMonth ), "%02d" );
        numericWheelAdapterStart3.setLabel( " " );
        wl_start_day.setViewAdapter( numericWheelAdapterStart3 );
        numericWheelAdapterStart3.setTextColor( R.color.black );
        numericWheelAdapterStart3.setTextSize( 20 );
        wl_start_day.setCyclic( true );
        wl_start_day.addScrollingListener( startScrollListener );
    }


    @Override
    public void onAttendanceExt(AttendanceExt attendanceExt) {
        binding.shouldBeTo.setText( "应到：" + attendanceExt.getPersonNum() + "人" );
        binding.actualArrival.setText( "实到：" + attendanceExt.getAttendanceNum() + "人" );
        if (attendanceExt.getPersonNum() == 0) {
            binding.turnOutForWork.setText( "出勤率： 0% " );
        }else{
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
                baseViewHolder.setText( R.id.firstName, item.getUserName().substring( 0, 1 ) );
                switch (item.getAttendanceType()){
                    case 0:
                        baseViewHolder.setTextColor( R.id.name,Color.parseColor( "#FF0000" ) );
                        break;
                    case 1:
                        baseViewHolder.setTextColor( R.id.name,Color.parseColor( "#ffffffff" ) );
                        break;
                    case 2:
                        baseViewHolder.setTextColor( R.id.name,Color.parseColor( "#F5FF00" ) );
                        break;

                }
            }
        };
        adapter.bindRecyclerView( binding.recyclerView, new GridLayoutManager(
                getActivity(), 10 ) );
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        if (!hidden){
            if (GlobalParam.getClassInfo() == null){
                Tools.displayToast( "请先绑定班牌！" );
                return;
            }
            if (mPresenter != null) {
                mPresenter.getAttendance( DateUtil.getNowDate( DateUtil.DatePattern.ALL_TIME ) );
            }
        }
    }
}
