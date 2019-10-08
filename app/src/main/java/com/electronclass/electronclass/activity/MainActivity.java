package com.electronclass.electronclass.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.electronclass.aclass.ClassFragment;
import com.electronclass.application.ApplicationFragment;
import com.electronclass.attendance.AttendanceFragment;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.database.GlobalPage;
import com.electronclass.common.util.ReadThreadUtil;
import com.electronclass.common.util.SerialportManager;
import com.electronclass.common.util.Tools;
import com.electronclass.electronclass.BuildConfig;
import com.electronclass.electronclass.R;
import com.electronclass.electronclass.adapter.FragmentTabAdapter;
import com.electronclass.electronclass.contract.CardContract;
import com.electronclass.electronclass.databinding.ActivityMainBinding;
import com.electronclass.electronclass.presenter.CardPresenter;
import com.electronclass.home.HomeFragment;
import com.tencent.bugly.Bugly;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<CardContract.Presenter> implements SerialportManager.SerialportListener, CardContract.View {
    private ActivityMainBinding binding;
    private List<Fragment>      fragmentList = new ArrayList<>();
    private FragmentTabAdapter  fragmentTabAdapter;
    private ReadThreadUtil      readThreadUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initSerialPort();
    }

    @NotNull
    @Override
    protected CardContract.Presenter getPresenter() {
        return new CardPresenter();
    }

    @Override
    protected void initView() {
        initBugly();
        setFragmentList();
        setFragment();
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

    /**
     * 全局更新
     */
    private void initBugly() {
        Bugly.init( getApplicationContext(), "b1896a9375", false );
    }


    /**
     * 开启刷卡
     */
    private void initSerialPort() {
        if (BuildConfig.GUARD_PACKAGE == GlobalPage.MULAN) {
            logger.debug( "启动木兰刷卡" );
            SerialportManager.getInstance().init();
            SerialportManager.getInstance().addListener( this );
        } else if (BuildConfig.GUARD_PACKAGE == GlobalPage.HENGHONGDA) {
            logger.debug( "启动恒宏达刷卡" );
            readThreadUtil = new ReadThreadUtil();
            readThreadUtil.startReadThread( (type, cardNum) -> {
                if (type == 0) {
                    logger.info( "卡号：" + cardNum );
                    mPresenter.getCardAttendance( cardNum );
                } else {
                    Tools.displayToast( "读取出错，不兼容的卡" );
                }
            } );
        }

    }

    private void setFragment() {
        fragmentTabAdapter = new FragmentTabAdapter( this, fragmentList, R.id.frameLayout, binding.radioGroup );
    }

    private void setFragmentList() {
        fragmentList.add( new HomeFragment() );
        fragmentList.add( new ApplicationFragment() );
        fragmentList.add( new ClassFragment() );
        fragmentList.add( new AttendanceFragment() );
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopCard();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onReceiveData(String cardNo) {
        logger.info( "卡号：" + cardNo );
        mPresenter.getCardAttendance( cardNo );
    }

    /**
     * 关闭刷卡
     */
    private void stopCard() {
        if (BuildConfig.GUARD_PACKAGE == "mulan") {
            logger.debug( "关闭木兰刷卡" );
            SerialportManager.getInstance().removeListener( this );
        } else if (BuildConfig.GUARD_PACKAGE == "henghongda") {
            logger.debug( "关闭恒宏达刷卡" );
            if (readThreadUtil != null)
                readThreadUtil.stopReadThread();
        }
    }

    @Override
    public void onError(String errorMessage) {
        super.onError( errorMessage );
        Tools.displayToast( errorMessage );
    }

    @Override
    public void onCardAttendance(boolean sure) {
        if (sure) {
            Tools.displayToast( "考勤打卡成功" );
        } else {
            Tools.displayToast( "考勤打卡失败，请重新刷卡" );
        }
    }
}
