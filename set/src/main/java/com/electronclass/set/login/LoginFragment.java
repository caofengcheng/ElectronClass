package com.electronclass.set.login;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cncoderx.wheelview.WheelView;
import com.electronclass.common.adapter.CommonRecyclerViewAdapter;
import com.electronclass.common.base.BaseFragment;
import com.electronclass.common.base.BaseViewHolder;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.event.EventTime;
import com.electronclass.common.util.KeyboardUtils;
import com.electronclass.common.util.Tools;
import com.electronclass.generalui.WaitingDialogUtil;
import com.electronclass.pda.mvp.entity.ClassItem;
import com.electronclass.pda.mvp.entity.TeacherInfo;
import com.electronclass.set.R;
import com.electronclass.set.databinding.FragmentLoginBinding;
import com.electronclass.set.login.contract.LoginContract;
import com.electronclass.set.login.presenter.LoginPresenter;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆
 */
public class LoginFragment extends BaseFragment<LoginContract.Presenter> implements LoginContract.View {
    private View                 view;
    private FragmentLoginBinding binding;
    //只做11位判断，号段交给后台去判断
    private String               telRegex    = "^\\d{11}$";
    private boolean              canClick    = true;
    private CountDownTimer       timer;
    private PopupWindow          popupWindow;
    private boolean              isFirstShow = true;


    private CommonRecyclerViewAdapter<ClassItem> schoolAdapter;
    private CommonRecyclerViewAdapter<ClassItem> classAdapter;
    private CommonRecyclerViewAdapter<ClassItem> gradeAdapter;
    private List<ClassItem>                      classItemList  = new ArrayList<>();
    private List<ClassItem>                      gradeItemList  = new ArrayList<>();
    private List<ClassItem>                      schoolItemList = new ArrayList<>();
    private String                               departId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_login, container, false );
        view = binding.getRoot();
        init( view );
        isFirstShow = false;
        return view;
    }

    @NotNull
    @Override
    protected LoginContract.Presenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initView(View view) {
        setOnClick();
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

    }

    private void setOnClick() {

        /**
         *获取验证码
         */
        binding.getCode.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canClick) {
                    canClick = false;
                    String phoneNum = binding.phoneNumber.getText().toString().trim();

                    if (StringUtils.isNoneEmpty( phoneNum ) && phoneNum.matches( telRegex )) {
                        mPresenter.sendSms( phoneNum );
                        timer = new CountDownTimer( 60 * 1000, 1000 ) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                binding.getCode.setTextColor( Color.parseColor( "#ff999999" ) );
                                binding.getCode.setText( millisUntilFinished / 1000 + "秒" );
                            }

                            @Override
                            public void onFinish() {
                                binding.getCode.setTextColor( Color.parseColor( "#ff273eb0" ) );
                                binding.getCode.setText( "获取验证码" );
                                canClick = true;
                            }
                        }.start();
                    } else {
                        canClick = true;
                        Tools.displayToast( "请检查号码是否输入正确" );
                    }
                }
            }
        } );

        binding.loginView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideKeyboard( binding.loginView );
            }
        } );

        /**
         * 登陆
         */
        binding.login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = binding.phoneNumber.getText().toString().trim();
                String codeNum  = binding.phoneCode.getText().toString().trim();
                if (StringUtils.isNoneEmpty( phoneNum ) && StringUtils.isNoneEmpty( codeNum )) {
                    if (timer != null) {
                        timer.cancel();
                    }
                    if (GlobalParam.getSchoolInfo() == null) {
                        Tools.displayToast( "请先绑定学校" );
                        return;
                    }
                    mPresenter.login( phoneNum, codeNum );
                } else {
                    Tools.displayToast( "电话号码或验证码不能为空" );
                }
            }
        } );

        /**
         * 设置时间
         */
        binding.setWindow.attendanceTime.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.setWindow.constraintLayout.setVisibility( View.GONE );
                showTimePop();
            }
        } );


        /**
         * 确认按钮
         */
        binding.setWindow.save.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringUtils.isNoneEmpty( binding.setWindow.classMessage.getText().toString().trim() ) && StringUtils.isNoneEmpty( departId )) {
                    mPresenter.bound( departId, getActivity() );
                }
            }
        } );

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
    public void onlogin(boolean str, TeacherInfo teacherInfo) {
        if (str && teacherInfo != null) {
            binding.setWindow.setWindow.setVisibility( View.VISIBLE );
            binding.loginWindow.setVisibility( View.GONE );
            binding.setWindow.attendanceTime.setText( GlobalParam.getEventTime() );
            setClassAdapter();
            setGradeAdapter();
            setSchoolAdapter();
            mPresenter.getClassList( GlobalParam.getSchoolInfo().getSchoolId(), teacherInfo.getUserId() );
        }
    }

    /**
     * //     * 开启时间选择器
     * //
     */
    private void showTimePop() {
        LayoutInflater inflater        = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View           popupWindowView = inflater.inflate( R.layout.pop_application_select, null );
        popupWindow = new PopupWindow( popupWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true );
        popupWindow.setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        popupWindow.setOutsideTouchable( false );

        final WheelView wl_hour    = popupWindowView.findViewById( R.id.wl_hour );
        final WheelView wl_min     = popupWindowView.findViewById( R.id.wl_min );
        TextView        sureChoose = popupWindowView.findViewById( R.id.sureChoose );
        TextView        stopChoose = popupWindowView.findViewById( R.id.stopChoose );

        sureChoose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.setWindow.attendanceTime.setText( wl_hour.getCurrentItem() + ":" + wl_min.getCurrentItem() );
                GlobalParam.setEventTime( wl_hour.getCurrentItem() + ":" + wl_min.getCurrentItem() );
                popupWindow.dismiss();
                binding.setWindow.constraintLayout.setVisibility( View.VISIBLE );
                EventBus.getDefault().post( new EventTime() );
            }
        } );
        stopChoose.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                binding.setWindow.constraintLayout.setVisibility( View.VISIBLE );
            }
        } );
        popupWindow.showAtLocation( binding.setWindow.setWindow, Gravity.CENTER, 0, 0 );
    }


    /**
     * 班级列表
     */
    private void setClassAdapter() {
        classAdapter = new CommonRecyclerViewAdapter<ClassItem>( R.layout.class_list_item, false, false ) {
            @Override
            public void convert(final BaseViewHolder baseViewHolder, final ClassItem item) {
                baseViewHolder.setText( R.id.itemClass, item.getDepartName() );
                if (item.isClick()) {
                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_1 );
                } else {
                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_0 );
                }
                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < classItemList.size(); i++) {
                            classItemList.get( i ).setClick( false );
                        }
                        classItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
                        notifyDataSetChanged();
                        binding.setWindow.classMessage.setText( item.getDepartName() );
                        departId = item.getDepartId();
                    }
                } );
            }
        };
        classAdapter.bindRecyclerView( binding.setWindow.className, new LinearLayoutManager(
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
                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_1 );
                } else {
                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_0 );
                }
                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < gradeItemList.size(); i++) {
                            gradeItemList.get( i ).setClick( false );
                        }
                        gradeItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
                        notifyDataSetChanged();
                        mPresenter.getClassList( item.getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
                    }
                } );
            }
        };
        gradeAdapter.bindRecyclerView( binding.setWindow.gradeName, new LinearLayoutManager(
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
                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_1 );
                } else {
                    baseViewHolder.setBackgroundResources( R.id.itemCl, R.drawable.shape_0 );
                }
                baseViewHolder.setOnClickListener( R.id.itemClass, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (int i = 0; i < schoolItemList.size(); i++) {
                            schoolItemList.get( i ).setClick( false );
                        }
                        schoolItemList.get( baseViewHolder.getLayoutPosition() ).setClick( true );
                        notifyDataSetChanged();
                        mPresenter.getClassList( item.getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
                    }
                } );
            }
        };
        schoolAdapter.bindRecyclerView( binding.setWindow.schoolName, new LinearLayoutManager(
                getActivity(), LinearLayoutManager.VERTICAL, false ) );
    }


    @Override
    public void onClassList(List<ClassItem> classItems) {
        classItemList.addAll( classItems );
        classAdapter.setData( classItemList );
        classAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGradeList(List<ClassItem> classItems) {
        gradeItemList.clear();
        for (ClassItem classItem : classItems) {
            if (classItem.getLevel() == 5) {//组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
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
        gradeAdapter.setData( gradeItemList );
        gradeAdapter.notifyDataSetChanged();
        if (classItems.size() > 0) {
            mPresenter.getClassList( classItems.get( 0 ).getDepartId(), GlobalParam.getTeacherInfo().getUserId() );
        }
    }

    @Override
    public void onSchoolList(List<ClassItem> classItems) {
        for (ClassItem classItem : classItems) {
            if (classItem.getLevel() == 2) {//组织架构级别 对于大学：2-学部，3-学院，4-系，5-专业，6-班级；对于中小学：2-学部，5-年级，6-班级
                schoolItemList.add( classItem );
            }
        }
        for (int i = 0; i < schoolItemList.size(); i++) {
            if (i == 0) {
                schoolItemList.get( i ).setClick( true );
            } else {
                schoolItemList.get( i ).setClick( false );
            }
        }
        schoolAdapter.setData( schoolItemList );
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged( hidden );
        logger.info( "LoginFragment--setUserVisibleHint:" + getUserVisibleHint() + "    onHiddenChanged:" + hidden );
        if (!hidden && !isFirstShow) {
            binding.loginWindow.setVisibility( View.VISIBLE );
            binding.setWindow.setWindow.setVisibility( View.GONE );
            binding.phoneNumber.setText( "" );
            binding.phoneCode.setText( "" );
            if (timer != null) {
                timer.cancel();
                binding.getCode.setText( "获取验证码" );
                canClick = true;
            }
        }
    }
}
