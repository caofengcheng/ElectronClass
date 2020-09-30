package com.electronclass.electronclass.broadcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.lamy.system.Magicbox;


/**
 * 定时关机
 */
public class ShutDownReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.android.settings.action.REQUEST_POWER_OFF")) {
            shutDown(context);
        }
    }

    private void shutDown(Context context) {
        Magicbox
                .shutdown(false);
//        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
    }

}
