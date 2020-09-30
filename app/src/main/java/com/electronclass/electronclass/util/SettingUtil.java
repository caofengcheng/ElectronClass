package com.electronclass.electronclass.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.lamy.system.Magicbox;

import com.android.xhapimanager.XHApiManager;
import com.electronclass.common.util.DateUtil;
import com.electronclass.common.util.PowerOnOffManagerUtil;
import com.electronclass.common.util.SerialportManager;
import com.electronclass.common.util.StringUitl;
import com.electronclass.electronclass.AppApplication;
import com.hikvision.dmb.time.InfoTimeApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 设备设置
 */
public class SettingUtil {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private volatile static SettingUtil settingUtil;

    private SettingUtil() {
    }

    public static SettingUtil getInstance() {
        if (settingUtil == null) {
            synchronized (SettingUtil.class) {
                if (settingUtil == null) {
                    settingUtil = new SettingUtil();
                }
            }
        }
        return settingUtil;
    }


    /**
     * 木兰定时开关机
     */
    public void ML_OFF_ON() {
        XHApiManager xhApiManager = new XHApiManager();
        xhApiManager.XHSetPowerOffOnTime(DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + "-21-00", DateUtil.tomorrow() + "-5-00", true);
        logger.info("木兰定时开关机已开启--offTime：" + DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + "-21-00" + "   onTime:" + DateUtil.tomorrow() + "-5-30");
    }

    /**
     * 恒鸿达定时开关机
     */
    public void HHD_OFF_ON(Context context) {
        PowerOnOffManagerUtil powerOnOffManagerUtil = new PowerOnOffManagerUtil();
        String[]              startTime             = {"5", "00"};
        String[]              endTime               = {"21", "00"};
        int[]                 weekdays              = {1, 1, 1, 1, 1, 1, 1};
        powerOnOffManagerUtil.setOffOrOn(context, startTime, endTime, weekdays);
        logger.info("恒鸿达定时开关机--offTime：" + Arrays.toString(endTime) + "   onTime:" + Arrays.toString(startTime));
    }

    /**
     * 海康定时开关机
     */
    public void HK_OFF_ON(){
        logger.info("海康定时开关机已开启");
        Date dateOffTime = StringUitl.stringToDate(DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + " 21:00:00");
        Date dateOnTime  = StringUitl.stringToDate(DateUtil.tomorrow() + " 5:00:00");
        logger.info("海康定时开关机--dateOnTime：" + dateOnTime + "   dateOnTime:" + dateOnTime);
        logger.info("海康定时开关机--String：" + DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + " 21-00-00" + "   dateOnTime:" + DateUtil.tomorrow() + " 5-00-00");
        InfoTimeApi.setTimeSwitch(dateOffTime.getTime(), dateOnTime.getTime());
    }


    /**
     * 大华定时开关机
     * @param context
     */
    public void DH_OFF_ON(Context context){
        Date                 dateOnTime = StringUitl.stringToDate(DateUtil.tomorrow() + " 5:00:00");
        SimpleDateFormat time2      = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String           etime      = time2.format(dateOnTime);
        logger.info("大华定时开关机已开启--" + "   dateOnTime:" + etime);
        Magicbox.enableTimmingPoweron(etime);

        Date dateOffTime = StringUitl.stringToDate(DateUtil.getNowDate(DateUtil.DatePattern.ONLY_DAY) + " 21:00:00");
        Intent intent = new Intent(
                "com.android.settings.action.REQUEST_POWER_OFF");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, dateOffTime.getTime(), pendingIntent);
    }

    /**
     * 木兰刷卡
     * @param context
     */
    public void ML_Serialport(AppApplication context){
        logger.debug("启动木兰刷卡");
        SerialportManager.getInstance().init();
        SerialportManager.getInstance().addListener(context);
    }
}
