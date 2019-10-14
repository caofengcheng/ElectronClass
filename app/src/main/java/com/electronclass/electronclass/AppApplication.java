package com.electronclass.electronclass;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.android.xhapimanager.XHApiManager;
import com.electronclass.common.base.BaseApplication;
import com.electronclass.common.basemvp.contract.ApplicationContract;
import com.electronclass.common.basemvp.presenter.ApplicationPresenter;
import com.electronclass.common.database.GlobalPage;
import com.electronclass.common.database.GlobalParam;
import com.electronclass.common.database.MacAddress;
import com.electronclass.common.event.SettingsEvent;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.Tools;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppApplication extends BaseApplication<ApplicationContract.Presenter> implements ApplicationContract.View {
    protected Logger logger = LoggerFactory.getLogger( getClass() );
    private   int    time   = 60 * 1000;

    public static AppApplication getInstance() {
        return (AppApplication) BaseApplication.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GlobalParam.setEventTime( "07:50" );
        getBuildConfig();
        initEcardNo();
        mPresenter.getClassAndSchool();
        stopAlm();
        logger.debug( "当前版本号：" + getVersionCode() + "  版本名称：" + getVersionName() );
    }

    @NotNull
    @Override
    protected ApplicationContract.Presenter getPresenter() {
        return new ApplicationPresenter();
    }

    @Override
    public void onClassAndSchool() {
        logger.debug( "发送SettingsEvent" );
        EventBus.getDefault().postSticky( new SettingsEvent() );
    }

    @Override
    public void onError(String errorMessage) {
        Tools.displayToast( errorMessage );
        logger.debug( errorMessage );
    }


    private void stopAlm() {
        if (BuildConfig.GUARD_PACKAGE == GlobalPage.MULAN) {
            XHApiManager xhApiManager = new XHApiManager();
            xhApiManager.XHSetPowerOffOnTime( DateUtil.getNowDate( DateUtil.DatePattern.ONLY_DAY ) + "-16-30", DateUtil.tomorrow() + "-7-30", true );
            logger.info( "木兰定时开关机已开启--offTime：" + DateUtil.getNowDate( DateUtil.DatePattern.ONLY_DAY ) + "-16-30" + "   onTime:" + DateUtil.tomorrow() + "-7-30" );
        } else if (BuildConfig.GUARD_PACKAGE == GlobalPage.HENGHONGDA) {
            logger.info( "恒鸿达定时开关机已开启--offTime：" + DateUtil.getNowDate( DateUtil.DatePattern.ONLY_DAY ) + "-16-30" + "   onTime:" + DateUtil.tomorrow() + "-7-30" );
        }
    }


    /**
     * 获取版本号
     *
     * @return
     */
    private int getVersionCode() {
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0 );
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;

    }

    /**
     * 获取版本名称
     *
     * @return
     */
    private String getVersionName() {
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0 );
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 初始化班牌号
     */
    private void initEcardNo() {
        if (StringUtils.isNoneEmpty( MacAddress.getMacAddress() )) {
            GlobalParam.setEcardNo( MacAddress.getMacAddress() );
        } else {
            Tools.displayToast( "请检查设备是否有正常MAC地址" );
        }

    }

    private void getBuildConfig() {
        GlobalPage.pageConfig = BuildConfig.GUARD_PACKAGE;
    }

}
