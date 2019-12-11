package com.electronclass.common.database;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.blankj.utilcode.util.DeviceUtils;
import com.electronclass.common.util.StringUitl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 获取Mac地址
 */
public class MacAddress {
    private static Logger logger = LoggerFactory.getLogger(MacAddress.class);
    public static String ECARDNO = null;


    /**
     * 获取工具中的mac地址
     * @return
     */
    public static String getDeviceMacAddrress() {
        String macAddress = null;
        if (StringUtils.isNotEmpty(DeviceUtils.getMacAddress())) {
            ECARDNO = StringUitl.replaceString(DeviceUtils.getMacAddress(), ":");
            logger.info("utilCode --- getMAC:" + ECARDNO);
            return ECARDNO;
        }
        return macAddress;
    }

    /**
     * 6.0以下获取mac地址
     */
    public static String getMacAddress(Context context) {
        String macAddress = null;
        FileReader fstream = null;
        try {
            try {
                fstream = new FileReader("/sys/class/net/eth0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/wlan0/address");
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

            if (StringUtils.isEmpty(macAddress)) {
                WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
                if (null != info) {
                    macAddress = info.getMacAddress();
                }
            }

            if (StringUtils.isNotEmpty(macAddress)) {
                ECARDNO = StringUitl.replaceString(macAddress, ":");
                logger.info("getMAC:" + ECARDNO);
            }
            return ECARDNO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ECARDNO;
    }


}
