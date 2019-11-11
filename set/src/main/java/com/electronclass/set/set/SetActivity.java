package com.electronclass.set.set;

import android.os.Bundle;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.set.R;
import com.electronclass.set.set.contract.SetContract;
import com.electronclass.set.set.presenter.SetPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SetActivity extends BaseActivity<SetContract.Presenter> implements SetContract.View {

//    private ActivitySetBinding binding;

//    private WheelView wl_hour;
//    private WheelView wl_min;
    private TextView  tv_ok;

    private PopupWindow popupWindow;


    private CommonRecyclerViewAdapter<ClassItem> schoolAdapter;
    private CommonRecyclerViewAdapter<ClassItem> classAdapter;
    private CommonRecyclerViewAdapter<ClassItem> gradeAdapter;
    private List<ClassItem>                      classItemList = new ArrayList<>();
    private List<ClassItem>                      gradeItemList = new ArrayList<>();
    private List<ClassItem>                      schoolItemList = new ArrayList<>();
    private String                               departId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(  R.layout.activity_set );
        init();
    }

    @NotNull
    @Override
    protected SetContract.Presenter getPresenter() {
        return new SetPresenter();
    }

    @Override
    protected void initView() {
//        binding.attendanceTime.setText( GlobalParam.getEventTime() );
//        setOnClick();
//        setClassAdapter();
//        setGradeAdapter();
//        setSchoolAdapter();
    }

    @Override
    protected void initData() {
//        mPresenter.getClassList( GlobalParam.getSchoolInfo().getSchoolId(), GlobalParam.getTeacherInfo().getUserId() );
    }

    @Override
    protected void showData() {

    }

    @Override
    protected void releaseData() {

    }

    @Override
    public void onClassList(List<ClassItem> classItems) {

    }

    @Override
    public void onGradeList(List<ClassItem> classItems) {

    }

    @Override
    public void onSchoolList(List<ClassItem> classItems) {

    }

    @Override
    public void onBound(String msg) {

    }

//    private void setOnClick() {
//        /**
//         * 设置时间
//         */
//        binding.attendanceTime.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePop();
//            }
//        } );
//
//
//        /**
//         * 确认按钮
//         */
//        binding.save.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (StringUtils.isNoneEmpty( binding.classMessage.getText().toString().trim() ) && StringUtils.isNoneEmpty( departId )) {
//                    mPresenter.bound( departId, SetActivity.this );
//                }
//            }
//        } );
//
//
//    }
//
//
//    /**
//     * 开启时间选择器
//     */
//    private void showTimePop() {
//        LayoutInflater inflater        = (LayoutInflater) this.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
//        View           popupWindowView = inflater.inflate( R.layout.pop_application_select, null );
//        popupWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );
//        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
//        popupWindow.setOutsideTouchable( false );
//        wl_hour = popupWindowView.findViewById( R.id.wl_hour );
//        wl_min = popupWindowView.findViewById( R.id.wl_min );
//        tv_ok = popupWindowView.findViewById( R.id.tv_ok );
//        initWheelView( popupWindowView );
//        tv_ok.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.attendanceTime.setText( wl_hour.getCurrentItem() + ":" + wl_min.getCurrentItem() );
//                GlobalParam.setEventTime( wl_hour.getCurrentItem() + ":" + wl_min.getCurrentItem() );
//                popupWindow.dismiss();
//            }
//        } );
//        popupWindow.showAtLocation( binding.setWindow, Gravity.CENTER, 0, 200 );
//    }
//
//    private void initWheelView(View view) {
//        Calendar            c               = Calendar.getInstance();
//        NumericWheelAdapter numericAdapter1 = new NumericWheelAdapter( this, 0, 23 );
//        numericAdapter1.setLabel( ":" );
//        numericAdapter1.setTextSize( 23 );
//        wl_hour.setViewAdapter( numericAdapter1 );
//        wl_hour.setCyclic( true );// 可循环滚动
//
//        NumericWheelAdapter numericAdapter2 = new NumericWheelAdapter( this, 0, 59 );
//        numericAdapter2.setLabel( "" );
//        numericAdapter2.setTextSize( 23 );
//        wl_min.setViewAdapter( numericAdapter2 );
//        wl_min.setCyclic( true );// 可循环滚动
//
//
//        String       currenthh  = new SimpleDateFormat( "HH" ).format( c.getTime() );
//        List<String> asList     = Arrays.asList( GlobalParam.getHOUR() );
//        int          hour_index = asList.indexOf( currenthh );
//        wl_hour.setCurrentItem( hour_index );
//
//        String       currentmm = new SimpleDateFormat( "mm" ).format( c.getTime() );
//        List<String> asList2   = Arrays.asList( GlobalParam.getMINUTE() );
//        int          min_index = asList2.indexOf( currentmm );
//        wl_min.setCurrentItem( min_index );
//
//    }
//
//
//    /**
//     * 班级列表
//     */
//    private void setClassAdapter() {
//        classAdapter = new CommonRecyclerViewAdapter<ClassItem>( R.layout.class_list_item, false, false ) {
//            @Override
//            public void convert(final BaseViewHolder baseViewHolder, final ClassItem item) {
//                baseViewHolder.setText( R.id.itemClass, item.getDepartName() );
//                if (item.isClick()) {
//                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_1 );
//                } else {
//                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_0 );
//                }
//                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        for (int i = 0; i < gradeItemList.size(); i++) {
//                            classItemList.get( i ).setClick( false );
//                        }
//                        classItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
//                        notifyDataSetChanged();
//                        binding.classMessage.setText( item.getDepartName() );
//                        departId = item.getDepartId();
//                    }
//                } );
//            }
//        };
//        classAdapter.bindRecyclerView( binding.className, new LinearLayoutManager(
//                this, LinearLayoutManager.VERTICAL, false ) );
//    }
//
//    /**
//     * 年级列表
//     */
//    private void setGradeAdapter() {
//        gradeAdapter = new CommonRecyclerViewAdapter<ClassItem>( R.layout.class_list_item, false, false ) {
//            @Override
//            public void convert(final BaseViewHolder baseViewHolder, final ClassItem item) {
//                baseViewHolder.setText( R.id.itemClass, item.getDepartName() );
//                if (item.isClick()) {
//                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_1 );
//                } else {
//                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_0 );
//                }
//                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        for (int i = 0; i < gradeItemList.size(); i++) {
//                            gradeItemList.get( i ).setClick( false );
//                        }
//                        gradeItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
//                        notifyDataSetChanged();
//                        mPresenter.getClassList( item.getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
//                    }
//                } );
//            }
//        };
//        gradeAdapter.bindRecyclerView( binding.gradeName, new LinearLayoutManager(
//                this, LinearLayoutManager.VERTICAL, false ) );
//    }
//
//    /**
//     * 学校列表
//     */
//    private void setSchoolAdapter() {
//        schoolAdapter = new CommonRecyclerViewAdapter<ClassItem>( R.layout.class_list_item, false, false ) {
//            @Override
//            public void convert(final BaseViewHolder baseViewHolder, final ClassItem item) {
//                baseViewHolder.setText( R.id.itemClass, item.getDepartName() );
//                if (item.isClick()) {
//                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_1 );
//                } else {
//                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_0 );
//                }
//                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        for (int i = 0; i < schoolItemList.size(); i++) {
//                            schoolItemList.get( i ).setClick( false );
//                        }
//                        schoolItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
//                        notifyDataSetChanged();
//                        mPresenter.getClassList( item.getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
//                    }
//                } );
//            }
//        };
//        schoolAdapter.bindRecyclerView( binding.schoolName, new LinearLayoutManager(
//                this, LinearLayoutManager.VERTICAL, false ) );
//    }
//
//
//    @Override
//    public void onClassList(List<ClassItem> classItems) {
//        classItemList.addAll( classItems );
//        classAdapter.setData( classItemList );
//        classAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onGradeList(List<ClassItem> classItems) {
//        gradeItemList.clear();
//        for (ClassItem classItem : classItems) {
//            if (classItem.getLevel() == 5) {//组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
//                gradeItemList.add( classItem );
//            }
//        }
//        for (int i = 0; i < gradeItemList.size(); i++) {
//            if (i == 0) {
//                gradeItemList.get( i ).setClick( true );
//            } else {
//                gradeItemList.get( i ).setClick( false );
//            }
//        }
//        gradeAdapter.setData( gradeItemList );
//        gradeAdapter.notifyDataSetChanged();
//        if (classItems.size() > 0) {
//            mPresenter.getClassList( classItems.get( 0 ).getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
//        }
//    }
//
//    @Override
//    public void onSchoolList(List<ClassItem> classItems) {
//        for (ClassItem classItem : classItems) {
//            if (classItem.getLevel() == 2) {//组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
//                schoolItemList.add( classItem );
//            }
//        }
//        for (int i = 0; i < schoolItemList.size(); i++) {
//            if (i == 0) {
//                schoolItemList.get( i ).setClick( true );
//            } else {
//                schoolItemList.get( i ).setClick( false );
//            }
//        }
//        schoolAdapter.setData( schoolItemList );
//        schoolAdapter.notifyDataSetChanged();
//        if (classItems.size() > 0) {
//            mPresenter.getClassList( classItems.get( 0 ).getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
//        }
//    }
//
//    @Override
//    public void onBound(String msg) {
//        Tools.displayToast( msg );
//        finish();
//    }
//
//    @Override
//    public void onError(String errorMessage) {
//        super.onError( errorMessage );
//        Tools.displayToast( errorMessage );
//    }
}
