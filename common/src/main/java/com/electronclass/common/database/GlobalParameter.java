package com.electronclass.common.database;


import com.electronclass.common.util.StringUitl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class GlobalParameter {
    private static  Logger logger = LoggerFactory.getLogger( GlobalParameter.class );
    public static String ECARDNO = null;

    /**
     * 6.0以下获取mac地址
     */
    public static String getMacAddress() {
        String macAddress = null;
        String str = "";
        try {
            Process           process = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address");
            InputStreamReader isr     = new InputStreamReader(process.getInputStream());
            LineNumberReader  lnr     = new LineNumberReader(isr);
            while (str != null) {
                str = lnr.readLine();
                if (str != null) {
                    macAddress = str.trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (macAddress != null){
            logger.info( "getMAC:"+ macAddress);
            ECARDNO = StringUitl.replaceString(macAddress,":"  );
            logger.info( "getMAC:"+ ECARDNO);
        }
        return ECARDNO;
    }
}
