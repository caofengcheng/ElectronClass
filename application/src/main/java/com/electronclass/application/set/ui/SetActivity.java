package com.electronclass.application.set.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.electronclass.application.R;
import com.electronclass.application.databinding.ActivitySetBinding;
import com.electronclass.application.set.contract.SetContract;
import com.electronclass.application.set.presenter.SetPresenter;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.GlobalParameter;
import com.electronclass.common.util.Tools;
import com.electronclass.common.util.wheelview.WheelView;
import com.electronclass.common.util.wheelview.adapter.NumericWheelAdapter;
import com.electronclass.pda.mvp.entity.ClassItem;
import org.apache.commons.lang3.StringUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class SetActivity extends BaseActivity<SetContract.Presenter> implements SetContract.View {

    private ActivitySetBinding binding;

    private WheelView wl_hour;
    private WheelView wl_min;
    private TextView  tv_ok;

    private PopupWindow popupWindow;
    private PopupWindow popupSetClassWindow;

    private RecyclerView className;
    private RecyclerView gradeName;
    private RecyclerView schoolName;

    private CommonRecyclerViewAdapter<ClassItem> schoolAdapter;
    private CommonRecyclerViewAdapter<ClassItem> classAdapter;
    private CommonRecyclerViewAdapter<ClassItem> gradeAdapter;
    private List<ClassItem>                      classItemList = new ArrayList<>();
    private List<ClassItem>                      gradeItemList = new ArrayList<>();
    private String                               departCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_set );
        init();
    }

    @Override
    protected SetPresenter getPresenter() {
        return new SetPresenter();
    }

    @Override
    protected void initView() {
        setOnClick();
        binding.time.setText( GlobalParam.getEventTime() );
        binding.deviceId.setText( GlobalParameter.getMacAddress() );
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }

    private void setOnClick() {
        /**
         * 设置时间
         */
        binding.time.setOnClickListener( v -> showTimePop() );


        binding.setWindow.setOnClickListener( v -> finish() );

        /**
         * 确认按钮
         */
        binding.btSure.setOnClickListener( v -> {
            if (StringUtils.isNoneEmpty( binding.etOther.getText().toString().trim() ) && StringUtils.isNoneEmpty( departCode )) {
                mPresenter.bound( departCode );
            }
        } );

        /**
         * 打开班级列表
         */
        binding.etOther.setOnClickListener( v -> {
            if (GlobalParam.getSchoolInfo() == null) {
                Tools.displayToast( "请先在后台绑定班牌！" );
                return;
            }
            showSetClass();
        } );

    }

    /**
     * 班级列表
     */
    private void showSetClass() {
        LayoutInflater inflater        = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.class_list, null );
        popupSetClassWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true );
        popupSetClassWindow.setBackgroundDrawable( new ColorDrawable( Color.parseColor( "#90273eb0" ) ) );
        popupSetClassWindow.setOutsideTouchable( true );
        className = popupWindowView.findViewById( R.id.className );
        gradeName = popupWindowView.findViewById( R.id.gradeName );
        schoolName = popupWindowView.findViewById( R.id.schoolName );
        TextView back = popupWindowView.findViewById( R.id.back );
        setClassAdapter();
        setGradeAdapter();
        setSchoolAdapter();
        mPresenter.getClassList( GlobalParam.getSchoolInfo().getSchoolId(), GlobalParam.getTeacherInfo().getUserId() );
        back.setOnClickListener( v -> popupSetClassWindow.dismiss() );
        popupSetClassWindow.showAtLocation( binding.setWindow, Gravity.CENTER_HORIZONTAL, 0, 120 );
    }


    /**
     * 开启时间选择器
     */
    private void showTimePop() {
        LayoutInflater inflater        = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.pop_application_select, null );
        popupWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );
        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        popupWindow.setOutsideTouchable( false );
        wl_hour = popupWindowView.findViewById( R.id.wl_hour );
        wl_min = popupWindowView.findViewById( R.id.wl_min );
        tv_ok = popupWindowView.findViewById( R.id.tv_ok );
        initWheelView( popupWindowView );
        tv_ok.setOnClickListener( v -> {
            binding.time.setText( wl_hour.getCurrentItem() + ":" + wl_min.getCurrentItem() );
            GlobalParam.setEventTime( wl_hour.getCurrentItem() + ":" + wl_min.getCurrentItem() );
            popupWindow.dismiss();
        } );
        popupWindow.showAtLocation( binding.setWindow, Gravity.CENTER, 0, 200 );
    }

    private void initWheelView(View view) {
        Calendar            c               = Calendar.getInstance();
        NumericWheelAdapter numericAdapter1 = new NumericWheelAdapter( this, 0, 23 );
        numericAdapter1.setLabel( ":" );
        numericAdapter1.setTextSize( 23 );
        wl_hour.setViewAdapter( numericAdapter1 );
        wl_hour.setCyclic( true );// 可循环滚动

        NumericWheelAdapter numericAdapter2 = new NumericWheelAdapter( this, 0, 59 );
        numericAdapter2.setLabel( "" );
        numericAdapter2.setTextSize( 23 );
        wl_min.setViewAdapter( numericAdapter2 );
        wl_min.setCyclic( true );// 可循环滚动


        String       currenthh  = new SimpleDateFormat( "HH" ).format( c.getTime() );
        List<String> asList     = Arrays.asList( GlobalParam.getHOUR() );
        int          hour_index = asList.indexOf( currenthh );
        wl_hour.setCurrentItem( hour_index );

        String       currentmm = new SimpleDateFormat( "mm" ).format( c.getTime() );
        List<String> asList2   = Arrays.asList( GlobalParam.getMINUTE() );
        int          min_index = asList2.indexOf( currentmm );
        wl_min.setCurrentItem( min_index );

    }


    /**
     * 班级列表
     */
    private void setClassAdapter() {
        classAdapter = new CommonRecyclerViewAdapter<ClassItem>( R.layout.class_list_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final ClassItem item) {
                baseViewHolder.setText( R.id.itemClass, item.getDepartName() );
                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.etOther.setText( item.getDepartName() );
                        departCode = item.getDepartCode();
                        popupSetClassWindow.dismiss();
                    }
                } );
            }
        };
        classAdapter.bindRecyclerView( className, new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false ) );
    }

    /**
     * 年级列表
     */
    private void setGradeAdapter() {
        gradeAdapter = new CommonRecyclerViewAdapter<ClassItem>( R.layout.class_list_item, false, false ) {
            @Override
            public void convert(final BaseViewHolder baseViewHolder, final ClassItem item) {
                baseViewHolder.setText( R.id.itemClass, item.getDepartName() );
                if (item.isClick()) {
                    baseViewHolder.setBackgroundColor( R.id.itemClass, Color.parseColor( "#3BAFD3" ) );
                } else {
                    baseViewHolder.setBackgroundColor( R.id.itemClass, Color.parseColor( "#ffffff" ) );
                }
                baseViewHolder.setOnClickListener( R.id.itemClass, v -> {
                    for (int i = 0; i < classItemList.size(); i++) {
                        classItemList.get( i ).setClick( false );
                    }
                    classItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
                    notifyDataSetChanged();
                    mPresenter.getClassList( item.getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
                } );
            }
        };
        gradeAdapter.bindRecyclerView( gradeName, new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false ) );
    }

    /**
     * 学校列表
     */
    private void setSchoolAdapter() {
        schoolAdapter = new CommonRecyclerViewAdapter<ClassItem>( R.layout.class_list_item, false, false ) {
            @Override
            public void convert(final BaseViewHolder baseViewHolder, final ClassItem item) {
                baseViewHolder.setText( R.id.itemClass, item.getDepartName() );
                if (item.isClick()) {
                    baseViewHolder.setBackgroundColor( R.id.itemClass, Color.parseColor( "#3BAFD3" ) );
                } else {
                    baseViewHolder.setBackgroundColor( R.id.itemClass, Color.parseColor( "#ffffff" ) );
                }
                baseViewHolder.setOnClickListener( R.id.itemClass, v -> {
                    for (int i = 0; i < gradeItemList.size(); i++) {
                        gradeItemList.get( i ).setClick( false );
                    }
                    gradeItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
                    notifyDataSetChanged();
                    mPresenter.getClassList( item.getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
                } );
            }
        };
        schoolAdapter.bindRecyclerView( schoolName, new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false ) );
    }


    @Override
    public void onClassList(List<ClassItem> classItems) {
        classAdapter.setData( classItems );
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGradeList(List<ClassItem> classItems) {
        classItemList.clear();
        for (ClassItem classItem : classItems) {
            if (classItem.getLevel() == 5) {//组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
                classItemList.add( classItem );
            }
        }
        for (int i = 0; i < classItemList.size(); i++) {
            if (i == 0) {
                classItemList.get( i ).setClick( true );
            } else {
                classItemList.get( i ).setClick( false );
            }
        }
        gradeAdapter.setData( classItemList );
        gradeAdapter.notifyDataSetChanged();
        if (classItems.size() > 0) {
            mPresenter.getClassList( classItems.get( 0 ).getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
        }
    }

    @Override
    public void onSchoolList(List<ClassItem> classItems) {
        for (ClassItem classItem : classItems) {
            if (classItem.getLevel() == 2) {//组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
                gradeItemList.add( classItem );
            }
        }
        for (int i = 0; i < gradeItemList.size(); i++) {
            if (i == 0) {
                gradeItemList.get( i ).setClick( true );
            } else {
                gradeItemList.get( i ).setClick( false );
            }
        }
        schoolAdapter.setData( gradeItemList );
        schoolAdapter.notifyDataSetChanged();
        if (classItems.size() > 0) {
            mPresenter.getClassList( classItems.get( 0 ).getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
        }
    }

    @Override
    public void onBound(String msg) {
        Tools.displayToast( msg );
        finish();
    }

    @Override
    public void onError(String errorMessage) {
        super.onError( errorMessage );
        Tools.displayToast( errorMessage );
    }
}
