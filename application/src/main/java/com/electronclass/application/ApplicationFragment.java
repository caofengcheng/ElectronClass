package com.electronclass.application;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.electronclass.application.contract.ApplicationContract;
import com.electronclass.application.databinding.FragmentApplicationBinding;
import com.electronclass.application.presenter.ApplicationPresenter;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.GlobalParameter;
import com.electronclass.common.module.AppModule;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.KeyboardUtils;
import com.electronclass.common.util.Tools;
import com.electronclass.common.util.wheelview.OnWheelChangedListener;
import com.electronclass.common.util.wheelview.OnWheelScrollListener;
import com.electronclass.common.util.wheelview.WheelView;
import com.electronclass.common.util.wheelview.adapter.NumericWheelAdapter;
import com.electronclass.pda.mvp.entity.AppItem;
import com.electronclass.pda.mvp.entity.ClassInfo;
import com.electronclass.pda.mvp.entity.ClassItem;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


/**
 * 应用中心
 */
public class ApplicationFragment extends BaseFragment<ApplicationContract.Presenter> implements ApplicationContract.View {

    private View                                 view;
    private FragmentApplicationBinding           binding;
    private CommonRecyclerViewAdapter<AppItem>   commonRecyclerViewAdapter;
    private CommonRecyclerViewAdapter<ClassItem> schoolAdapter;
    private CommonRecyclerViewAdapter<ClassItem> classAdapter;
    private CommonRecyclerViewAdapter<ClassItem> gradeAdapter;
    private List<AppItem>                        appItems      = new ArrayList<>();
    private List<ClassItem>                      classItemList = new ArrayList<>();
    private List<ClassItem>                      gradeItemList = new ArrayList<>();
    private String                               departCode;

    private PopupWindow  popupWindow;
    private PopupWindow  popupSetWindow;
    private PopupWindow  popupSetClassWindow;
    private PopupWindow  popupLoginWindow;
    private WheelView    wl_hour;
    private WheelView    wl_min;
    private TextView     time;
    private TextView     deviceId;
    private TextView     tv_ok;
    private TextView     btSure;
    private TextView     etOther;
    private RecyclerView className;
    private RecyclerView gradeName;
    private RecyclerView schoolName;

    private String[] xiaoshi_start =
            {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                    "19", "20", "21", "22", "23"};

    private String[] fenzhong_start =
            {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                    "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36",
                    "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54",
                    "55", "56", "57", "58", "59"};
    //只做11位判断，号段交给后台去判断
    private String   telRegex       = "^\\d{11}$";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_application, container, false );
        view = binding.getRoot();
        init( view );
        return view;
    }

    @NotNull
    @Override
    protected ApplicationContract.Presenter getPresenter() {
        return new ApplicationPresenter();
    }

    @Override
    protected void initView(View view) {
        AppItem appItem = new AppItem();
        appItem.setName( "设置" );
        appItem.setCode( AppModule.SET );
        appItem.setImage( R.drawable.sz );

        AppItem appItem1 = new AppItem();
        appItem1.setName( "添加应用" );
        appItem1.setCode( AppModule.MORE );
        appItem1.setImage( R.drawable.add );
        appItems.add( appItem );
        appItems.add( appItem1 );
        setAdapter();

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

    @Override
    protected void getData() {
        logger.info( "切换界面刷新" );
    }


    @Override
    public void onSms(boolean isSuccess) {
        if (isSuccess) {
            Tools.displayToast( "验证码发送成功" );
        } else {
            Tools.displayToast( "验证码发送失败" );
        }
    }

    @Override
    public void onlogin(boolean str) {
        if (str) {
            popupLoginWindow.dismiss();
            showSetPop();
        }
    }

    @Override
    public void onClassList(List<ClassItem> classItems) {
        classAdapter.setData( classItems );
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGradeList(List<ClassItem> classItems) {
        classItemList.clear();
        for (ClassItem classItem : classItems){
            if (classItem.getLevel() == 5){//组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
                classItemList.add( classItem );
            }
        }
        for (int i = 0; i < classItemList.size(); i++) {
            if (i == 0) {
                classItemList.get( i).setClick( true );
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
        for (ClassItem classItem : classItems){
            if (classItem.getLevel() == 2){//组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
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
    }

    @Override
    public void onError(String errorMessage) {
        super.onError( errorMessage );
        Tools.displayToast( errorMessage );
    }

    private void setAdapter() {
        commonRecyclerViewAdapter = new CommonRecyclerViewAdapter<AppItem>( R.layout.app_item, false, false ) {
            @Override
            public void convert(BaseViewHolder baseViewHolder, final AppItem item) {
                baseViewHolder.setBackgroundResources( R.id.imageView, item.getImage() );
                baseViewHolder.setText( R.id.appName, item.getName() );
                baseViewHolder.setOnClickListener( R.id.clAppItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getClick( item.getCode() );
                    }
                } );

            }
        };
        commonRecyclerViewAdapter.setData( appItems );
        commonRecyclerViewAdapter.notifyDataSetChanged();
        binding.recyclerView.setLayoutManager( new GridLayoutManager( getActivity(), 5 ) );
        binding.recyclerView.setAdapter( commonRecyclerViewAdapter );
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
                        etOther.setText( item.getDepartName() );
                        departCode = item.getDepartCode();
                        popupSetClassWindow.dismiss();
                    }
                } );
            }
        };
        classAdapter.bindRecyclerView( className, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
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
                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < classItemList.size(); i++) {
                            classItemList.get( i ).setClick( false );
                        }
                        classItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
                        notifyDataSetChanged();
                        mPresenter.getClassList( item.getDepartId(), GlobalParam.getTeacherInfo().getUserId());
                    }
                } );
            }
        };
        gradeAdapter.bindRecyclerView( gradeName, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
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
                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < gradeItemList.size(); i++) {
                            gradeItemList.get( i ).setClick( false );
                        }
                        gradeItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
                        notifyDataSetChanged();
                        mPresenter.getClassList( item.getDepartId(), GlobalParam.getTeacherInfo().getUserId());
                    }
                } );
            }
        };
        schoolAdapter.bindRecyclerView( schoolName, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }

    private void getClick(int code) {
        switch (code) {
            case AppModule.SET:
                showLogin();
                break;
            case AppModule.MORE:
//                AppItem appItem = new AppItem();
//                appItem.setName( "添加应用" );
//                appItem.setCode( AppModule.MORE );
//                appItem.setImage( R.drawable.add );
//                appItems.add( appItem );
//                commonRecyclerViewAdapter.setData( appItems );
//                commonRecyclerViewAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 开启设置
     */
    private void showSetPop() {
        LayoutInflater inflater        = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.set_window, null );
        popupSetWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true );
        time = popupWindowView.findViewById( R.id.time );
        deviceId = popupWindowView.findViewById( R.id.deviceId );
        etOther = popupWindowView.findViewById( R.id.etOther );
        btSure = popupWindowView.findViewById( R.id.btSure );
        ConstraintLayout setWindow  = popupWindowView.findViewById( R.id.setWindow );
        ConstraintLayout setWindow1 = popupWindowView.findViewById( R.id.setWindow1 );
        time.setText( GlobalParam.getEventTime() );
        time.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        } );
        setWindow.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSetWindow.dismiss();
            }
        } );
        setWindow1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        } );
        btSure.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNoneEmpty( etOther.getText().toString().trim() ) && StringUtils.isNoneEmpty( departCode )) {
                    mPresenter.bound( departCode );
                }
                popupSetWindow.dismiss();
            }
        } );
        etOther.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalParam.getSchoolInfo() == null){
                    Tools.displayToast( "请先在后台绑定班牌！" );
                    return;
                }
                showSetClass();
            }
        } );
        deviceId.setText( GlobalParameter.getMacAddress() );
        popupSetWindow.showAtLocation( binding.clApplication, Gravity.CENTER_HORIZONTAL, 0, 120 );
    }


    /**
     * 登录
     */
    private void showLogin() {
        LayoutInflater inflater        = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View     popupWindowView = inflater.inflate( R.layout.login, null );
        popupLoginWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true );
        final EditText number   = popupWindowView.findViewById( R.id.phoneNumber );
        final EditText code     = popupWindowView.findViewById( R.id.phoneCode );
        final TextView getCode  = popupWindowView.findViewById( R.id.getPhoneCode );
        final TextView sure     = popupWindowView.findViewById( R.id.tvSure );
        final TextView deselect = popupWindowView.findViewById( R.id.deselect );

        final boolean[]        canClick = {true};
        final CountDownTimer[] timer    = new CountDownTimer[1];


        popupWindowView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideKeyboard( popupWindowView );
            }
        } );


        deselect.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupLoginWindow.dismiss();
            }
        } );


        //获取验证码
        getCode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canClick[0]) {
                    canClick[0] = false;
                    String phoneNum = number.getText().toString().trim();

                    if (StringUtils.isNoneEmpty( phoneNum ) && phoneNum.matches( telRegex )) {
                        mPresenter.sendSms( phoneNum );
                        timer[0] = new CountDownTimer( 60 * 1000, 1000 ) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                getCode.setTextColor( Color.parseColor( "#ff999999" ) );
                                getCode.setText( millisUntilFinished / 1000 + "秒" );
                            }

                            @Override
                            public void onFinish() {
                                getCode.setTextColor( Color.parseColor( "#ff273eb0" ) );
                                getCode.setText( "获取验证码" );
                                canClick[0] = true;
                            }
                        }.start();
                    } else {
                        canClick[0] = true;
                        Tools.displayToast( "请检查号码是否输入正确" );
                    }
                }
            }
        } );

        sure.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = number.getText().toString().trim();
                String codeNum  = code.getText().toString().trim();

                if (StringUtils.isNoneEmpty( phoneNum ) && StringUtils.isNoneEmpty( codeNum )) {
                    if (timer[0] != null) {
                        timer[0].cancel();
                    }
                    mPresenter.login( phoneNum, codeNum );
//                    showSetPop();
//                    popupLoginWindow.dismiss();
                } else {
                    Tools.displayToast( "电话号码或验证码不能为空" );
                }
            }
        } );

        popupLoginWindow.showAtLocation( binding.clApplication, Gravity.CENTER, 0, 0 );
    }

    /**
     * 班级列表
     */
    private void showSetClass() {
        LayoutInflater inflater        = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
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
        back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSetClassWindow.dismiss();
            }
        } );
        popupSetClassWindow.showAtLocation( binding.clApplication, Gravity.CENTER_HORIZONTAL, 0, 120 );
    }


    /**
     * 开启时间选择器
     */
    private void showPop() {
        LayoutInflater inflater        = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.pop_application_select, null );
        popupWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );
        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        popupWindow.setOutsideTouchable( false );
        wl_hour = popupWindowView.findViewById( R.id.wl_hour );
        wl_min = popupWindowView.findViewById( R.id.wl_min );
        tv_ok = popupWindowView.findViewById( R.id.tv_ok );
        initWheelView( popupWindowView );
        tv_ok.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time.setText( wl_hour.getCurrentItem() + ":" + wl_min.getCurrentItem() );
                GlobalParam.setEventTime( wl_hour.getCurrentItem() + ":" + wl_min.getCurrentItem() );
                popupWindow.dismiss();
            }
        } );
        popupWindow.showAtLocation( binding.clApplication, Gravity.CENTER, 0, 200 );
    }

    private void initWheelView(View view) {
        Calendar            c               = Calendar.getInstance();
        NumericWheelAdapter numericAdapter1 = new NumericWheelAdapter( getActivity(), 0, 23 );
        numericAdapter1.setLabel( ":" );
        numericAdapter1.setTextSize( 23 );
        wl_hour.setViewAdapter( numericAdapter1 );
        wl_hour.setCyclic( true );// 可循环滚动

        NumericWheelAdapter numericAdapter2 = new NumericWheelAdapter( getActivity(), 0, 59 );
        numericAdapter2.setLabel( "" );
        numericAdapter2.setTextSize( 23 );
        wl_min.setViewAdapter( numericAdapter2 );
        wl_min.setCyclic( true );// 可循环滚动


        String       currenthh  = new SimpleDateFormat( "HH" ).format( c.getTime() );
        List<String> asList     = Arrays.asList( xiaoshi_start );
        int          hour_index = asList.indexOf( currenthh );
        wl_hour.setCurrentItem( hour_index );

        String       currentmm = new SimpleDateFormat( "mm" ).format( c.getTime() );
        List<String> asList2   = Arrays.asList( fenzhong_start );
        int          min_index = asList2.indexOf( currentmm );
        wl_min.setCurrentItem( min_index );

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        if (!hidden) {

        }
    }


}
