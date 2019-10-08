package com.electronclass.electronclass.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.electronclass.electronclass.activity.MainActivity;

import org.slf4j.LoggerFactory;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public        org.slf4j.Logger logger      = LoggerFactory.getLogger( getClass() );
    private final String           ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        logger.info( "收到开机广播" );
        /**
         * 如果 系统 启动的消息，则启动 APP 主页活动
         */
        if (ACTION_BOOT.equals( intent.getAction() )) {
            Intent intentMainActivity = new Intent( context, MainActivity.class );
            intentMainActivity.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity( intentMainActivity );
        }

    }
}
