package com.electronclass.electronclass.activity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.WindowManager;
import com.electronclass.aclass.ClassFragment;
import com.electronclass.application.ApplicationFragment;
import com.electronclass.attendance.AttendanceFragment;
import com.electronclass.common.base.BaseActivity;
import com.electronclass.common.database.GlobalPage;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.util.Tools;
import com.electronclass.electronclass.BuildConfig;
import com.electronclass.electronclass.R;
import com.electronclass.electronclass.adapter.FragmentTabAdapter;

import com.electronclass.electronclass.databinding.ActivityMainBinding;

import com.electronclass.home.HomeFragment;
import com.electronclass.pda.mvp.base.BasePresenterInterface;
import com.tencent.bugly.Bugly;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private List<Fragment>      fragmentList = new ArrayList<>();
    private FragmentTabAdapter  fragmentTabAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        initBugly();
        setFragmentList();
        setFragment();
    }

    @NotNull
    @Override
    protected BasePresenterInterface getPresenter() {
        return null;
    }

    @Override
    protected void initView() {

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
    protected void onStart() {
        super.onStart();

    }


    /**
     * 全局更新
     */
    private void initBugly() {
        if (BuildConfig.GUARD_PACKAGE == GlobalPage.MULAN) {
            Bugly.init(getApplicationContext(), GlobalParam.MULAN_UPDATEID, false);
        }else if (BuildConfig.GUARD_PACKAGE == GlobalPage.HENGHONGDA) {
            Bugly.init(getApplicationContext(), GlobalParam.HENGHONGDA_UPDATEID, false);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
