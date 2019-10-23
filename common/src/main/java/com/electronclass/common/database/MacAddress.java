package com.electronclass.common.database;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.electronclass.common.util.StringUitl;
import com.electronclass.common.util.Tools;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 获取Mac地址
 */
public class MacAddress {
    private static  Logger logger = LoggerFactory.getLogger( MacAddress.class );
    public static String ECARDNO = null;

    /**
     * 6.0以下获取mac地址
     */
    public static String getMacAddress(Context context) {
//        String macAddress = null;
//        String str = "";
//        try {
//            Process           process = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
//            InputStreamReader isr     = new InputStreamReader(process.getInputStream());
//            LineNumberReader  lnr     = new LineNumberReader(isr);
//            while (str != null) {
//                str = lnr.readLine();
//                if (str != null) {
//                    macAddress = str.trim();
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String macAddress = null;
        FileReader fstream = null;
        try {
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    macAddress = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            if (!StringUtils.isNotEmpty( macAddress)){
                WifiManager wifiMgr = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
                if (null != info) {
                    macAddress = info.getMacAddress();
                }
            }

            if (StringUtils.isNotEmpty( macAddress)){
                logger.info( "getMAC:"+ macAddress);
                ECARDNO = StringUitl.replaceString(macAddress,":"  );
                logger.info( "getMAC:"+ ECARDNO);
            }
            return ECARDNO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ECARDNO;
    }




}
