package com.electronclass.electronclass.broadcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.electronclass.electronclass.AppApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author caofengcheng on 2019-11-22
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    protected Logger logger = LoggerFactory.getLogger( getClass() );
    @Override
    public void onReceive(Context context, Intent intent) {
        logger.info("onReceive");
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            logger.info("--Network Type  = " + networkInfo.getTypeName());
            logger.info("--Network State = " + networkInfo.getState());
            if (networkInfo.isConnected()) {
                AppApplication.getInstance().getDates();//断网重新获取数据
                logger.info("--bxm Network connected");
            }
        } else {
            logger.info("--bxm Network unavailable");
        }

    }
}
