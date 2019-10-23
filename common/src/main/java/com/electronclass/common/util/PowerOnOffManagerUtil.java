package com.electronclass.common.util;
import android.content.Context;
import android.util.Log;


import startest.ys.com.poweronoff.PowerOnOffManager;



//开关机管理器
public class PowerOnOffManagerUtil {
    private static final String TAG = "PowerOnOffManagerUtil";


    /**
     * 3288\3368 设置周期性开关机
     * @param context
     * @param beginTime
     * @param endTime
     * @param weekdays  int[] weekdays = {1,1,1,1,1,0,0};//周一到周日工作状态,1为开机，0为不开机
     */
    public  void setOffOrOn(Context context, String[] beginTime, String[] endTime, final int[] weekdays){
        final PowerOnOffManager manager = PowerOnOffManager.getInstance(context);
        final int[] timeonArray = {Integer.parseInt(beginTime[0]),Integer.parseInt(beginTime[1])};
        final int[] timeoffArray= {Integer.parseInt(endTime[0]),Integer.parseInt(endTime[1])};
//        int[] weekdays = {1,1,1,1,1,0,0};//周一到周日工作状态,1为开机，0为不开机
        manager.clearPowerOnOffTime();
        boolean testAutoPowerOnOff = false;
        manager.setPowerOnOffWithWeekly(timeonArray,timeoffArray,weekdays);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        Thread.sleep(250);//先延迟一会再去取
                        String onTime = manager.getPowerOnTime();
                        String offTime=manager.getPowerOffTime();
                        Log.i(TAG,"设置的开机时间为-->"+onTime+"设置的关机机时间为-->"+offTime);
                        if (onTime.equals("0")&&offTime.equals("0")){
                            //没设置成功重新再设置
                            Log.i(TAG,"没设置成功重新再设置-->");
                            manager.setPowerOnOffWithWeekly(timeonArray,timeoffArray,weekdays);
                        }else {
                            Log.i(TAG,"定时开关机设置成功-->");
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}


