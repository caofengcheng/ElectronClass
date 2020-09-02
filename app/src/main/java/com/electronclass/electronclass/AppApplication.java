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
import com.electronclass.common.event.Bulb;
import com.electronclass.common.event.CardType;
import com.electronclass.common.event.FoodCard;
import com.electronclass.common.event.SchoolInfo;
import com.electronclass.common.event.SettingsEvent;
import com.electronclass.common.event.TopEvent;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.EcardType;
import com.electronclass.common.util.GlideCacheUtil;
import com.electronclass.common.util.PowerOnOffManagerUtil;
import com.electronclass.common.util.ReadThreadUtil;
import com.electronclass.common.util.SerialportManager;
import com.electronclass.common.util.SharedPreferencesUtil;
import com.electronclass.common.util.StringUitl;
import com.electronclass.common.util.Tools;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.hikvision.dmb.SwingCardCallback;
import com.hikvision.dmb.time.InfoTimeApi;
import com.hikvision.dmb.util.InfoUtilApi;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.electronclass.common.util.EcardType.HHD;


public class AppApplication extends BaseApplication<ApplicationContract.Presenter> implements ApplicationContract.View, SerialportManager.SerialportListener {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private TopEvent topEvent;
    private SchoolInfo schoolInfo;
    private Bulb bulb;
    private ReadThreadUtil readThreadUtil;
    private SwingCardCallback.Stub stub;

    public void setTopEvent(TopEvent topEvent) {
        this.topEvent = topEvent;
    }

    public void setSchoolInfo(SchoolInfo schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    public void setBulb(Bulb bulb) {
        this.bulb = bulb;
    }

    public static AppApplication getInstance() {
        return (AppApplication) BaseApplication.getInstance();
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        LeakCanary.install( this );
        getDates();
        SharedPreferencesUtil.getInstance(this, "AppApplication");
        eventTime();
        initEcardNo();
        setEcardNo();
        GlideCacheUtil.getInstance().clearImageAllCache(this);
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
        logger.debug("当前版本号：" + getVersionCode() + "  版本名称：" + getVersionName());
    }

    @NotNull
    @Override
    protected ApplicationContract.Presenter getPresenter() {
        return new ApplicationPresenter();
    }

    @Override
    public void onClassAndSchool() {
        logger.debug("发送SettingsEvent");
        initSerialPort();
        stopAlm();
        EventBus.getDefault().postSticky(new SettingsEvent());
        schoolInfo.info();
        topEvent.Event();
    }

    @Override
    public void onCardAttendance(String msg) {
        Tools.displayToast(msg);
        bulb.b(false);
    }

    @Override
    public void onError(String errorMessage) {
        Tools.displayToast(errorMessage);
        logger.debug(errorMessage);
    }

    /**
     * 设置考勤打开时间
     */
    private void eventTime() {
        if (StringUtils.isEmpty((SharedPreferencesUtil.getData(GlobalParam.EVENTTIME, "07:50")).toString())) {
            GlobalParam.setEventTime("07:50");
        } else {
            GlobalParam.setEventTime((SharedPreferencesUtil.getData(GlobalParam.EVENTTIME, "07:50")).toString());
        }
    }

    /**
     * 设置班牌号
     */
    private void setEcardNo() {
        if (StringUtils.isEmpty(GlobalParam.getEcardNo())) {
            String macAddress = MacAddress.getMacAddress(this);
            GlobalParam.setEcardNo(StringUtils.isNotEmpty(macAddress) ? macAddress : MacAddress.getDeviceMacAddrress());
        }
    }


    /**
     * 定时开关机
     */
    private void stopAlm() {
        if (EcardType.getType() == EcardType.ML) {
            XHApiManager xhApiManager = new XHApiManager();
            xhApiManager.XHSetPowerOffOnTime(DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + "-21-00", DateUtil.tomorrow() + "-5-00", true);
            logger.info("木兰定时开关机已开启--offTime：" + DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + "-21-00" + "   onTime:" + DateUtil.tomorrow() + "-5-30");
        } else if (EcardType.getType() == EcardType.HHD) {
            PowerOnOffManagerUtil powerOnOffManagerUtil = new PowerOnOffManagerUtil();
            String[] startTime = {"5", "00"};
            String[] endTime = {"21", "00"};
            int[] weekdays = {1, 1, 1, 1, 1, 1, 1};
            powerOnOffManagerUtil.setOffOrOn(this, startTime, endTime, weekdays);
            logger.info("恒鸿达定时开关机已开启--offTime：" + endTime + "   onTime:" + startTime);
        } else if (EcardType.getType() == EcardType.HK) {
            logger.info("海康定时开关机已开启");
            Date dateOffTime = StringUitl.stringToDate(DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + " 21:00:00");
            Date dateOnTime = StringUitl.stringToDate(DateUtil.tomorrow() + " 5:00:00");
            logger.info("海康定时开关机已开启--dateOnTime：" + dateOnTime + "   dateOnTime:" + dateOnTime);
            logger.info("海康定时开关机已开启--String：" + DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + " 21-00-00" + "   dateOnTime:" + DateUtil.tomorrow() + " 5-00-00");
            InfoTimeApi.setTimeSwitch(dateOffTime.getTime(), dateOnTime.getTime());
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
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
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
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
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
        if (StringUtils.isNoneEmpty(MacAddress.getMacAddress(this))) {
            GlobalParam.setEcardNo(MacAddress.getMacAddress(this));
        } else {
            Tools.displayToast("请检查设备是否有正常MAC地址");
        }

    }

    /**
     * 开启刷卡
     */
    private void initSerialPort() {
        if (EcardType.getType() == EcardType.ML) {
            logger.debug("启动木兰刷卡");
            SerialportManager.getInstance().init();
            SerialportManager.getInstance().addListener(this);
        } else if (EcardType.getType() == EcardType.HHD) {
            logger.debug("启动恒宏达刷卡");
            readThreadUtil = new ReadThreadUtil();
            readThreadUtil.startReadThread((type, cardNum) -> {
                if (type == 0) {
                    sendCardNumber(cardNum);
                } else {
                    Tools.displayToast("读取出错，不兼容的卡");
                }
            });
        } else if (EcardType.getType() == EcardType.HK) {
            logger.debug("启动海康刷卡");
            stub = new SwingCardCallback.Stub() {
                @Override
                public void getInfo(String s) {
                    String cardNum = StringUtils.substringAfterLast(s, ":");
                    sendCardNumber(cardNum);
                }
            };
            InfoUtilApi.swingCard(stub);
        }
    }

    /**
     * 关闭刷卡
     */
    private void stopCard() {
        if (EcardType.getType() == EcardType.ML) {
            logger.debug("关闭木兰刷卡");
            SerialportManager.getInstance().removeListener(this);
        } else if (EcardType.getType() == EcardType.HHD) {
            logger.debug("关闭恒宏达刷卡");
            if (readThreadUtil != null)
                readThreadUtil.stopReadThread();
        } else if (EcardType.getType() == EcardType.HK) {
            logger.debug("关闭海康刷卡");
            if (stub != null) {
                InfoUtilApi.unregisterSwingCard(stub);
            }
        }
    }


    @Override
    public void onReceiveData(String cardNo) {
        sendCardNumber(cardNo);
    }

    private void sendCardNumber(String cardNo) {
        logger.info("卡号：" + cardNo);
        if (GlobalParam.getCardType().equals(GlobalParam.MAINACTIVITY)) {
            bulb.b(true);
            mPresenter.getCardAttendance(cardNo);
        } else if (GlobalParam.getCardType().equals(GlobalParam.UPDATEACTIVITY)) {
            EventBus.getDefault().postSticky(new CardType(cardNo));
        } else if (GlobalParam.getCardType().equals(GlobalParam.FOODACTIVITY)) {
            EventBus.getDefault().postSticky(new FoodCard(cardNo));
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopCard();
    }


    /**
     * 断网重连
     */
    public void getDates() {
        if (GlobalParam.getSchoolInfo() == null) {
            mPresenter.getClassAndSchool(getApplicationContext());
        } else {
            schoolInfo.info();
            topEvent.Event();
        }
    }


}
