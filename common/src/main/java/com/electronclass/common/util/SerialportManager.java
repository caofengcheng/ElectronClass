package com.electronclass.common.util;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import android_serialport_api.SerialPort;


/**
 * 串口管理.
 * Created by linlingrong on 2017-07-19.
 */

public class SerialportManager {
    private static final SerialportManager       mInstance           = new SerialportManager();
    private              Logger                  logger              = LoggerFactory.getLogger( SerialportManager.class );
    private              ObjectMapper            objectMapper        = new ObjectMapper();
    private              SerialPort              mSerialPort;
    private              OutputStream            mOutputStream;
    private              InputStream             mInputStream;
    //接收的串口数据
    private              String                  receiveComData      = "";
    private              ReadThread              mReadThread;
    private              Set<SerialportListener> serialPortListeners = new HashSet<>();

    MyThread a;
    boolean  run;

    public SerialportManager() {

    }

    public static SerialportManager getInstance() {
        return mInstance;
    }

    public void init() {
        logger.debug( "===> initCom" );
        run=true;
        try {
            //初始化SerialPort
            SerialPort mSerialPort = new SerialPort( new File( "/dev/ttyS1" ),
                    19200, 0 );
            //开启循环读取串口线程
            a = new MyThread( mSerialPort );
            a.start();
        } catch (SecurityException e1) {
            Log.e( "tag", "read:" + e1.toString() );

        } catch (IOException e1) {
            Log.e( "tag", "read:" + e1.toString() );
        }
    }

    public void addListener(SerialportListener listener) {
        serialPortListeners.add( listener );
    }

    public void removeListener(SerialportListener listener) {
        logger.debug( "listener: {}", listener );
        if (listener != null) {
            serialPortListeners.remove( listener );
            run = false;
        }
    }


    protected void onDataReceived(final byte[] buffer, final int size) {
        String tmp = BytesUtil.bytesToHexString( buffer );

        receiveComData += tmp.substring( 0, size * 2 ).toUpperCase();
        logger.debug( "===> receiveComData:" + receiveComData );
        analyzeComInstruction();
    }

    private void sendReceiveData(String cardNo) {
        if (StringUtils.isBlank( cardNo )) {
            logger.error( "读取到的卡号为空" );
            return;
        }
        for (SerialportListener listener : serialPortListeners) {
            if (listener != null) {
                listener.onReceiveData( cardNo );
            }
        }
    }

    /**
     * 解析指令信息
     */
    private void analyzeComInstruction() {
        try {
            if (receiveComData.length() < 18) {
                return;
            }
            int headPos = -1;
            headPos = receiveComData.indexOf( "5AA5" );
            if (headPos == -1) {
                return;
            }
            receiveComData = receiveComData.substring( headPos );
            if (receiveComData.length() < 18) {
                return;
            }
            String data = receiveComData.substring( 0, 18 );
            if (receiveComData.length() == 18) {
                receiveComData = "";
            } else {
                receiveComData = receiveComData.substring( 19 );
            }
            analyzeComData( data );
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析串口数据
     */
    private void analyzeComData(String comData) {
        logger.debug( "===> analyze ComData:" + comData );
        int    control    = Integer.valueOf( comData.substring( 6, 8 ) );
        int    dataLength = Integer.valueOf( comData.substring( 8, 10 ) );
        String dataStr    = comData.substring( 10, 10 + dataLength * 2 );
        switch (control) {
            case 1:
                keyHandle( dataStr );
                break;
            case 2:
                cardHandle( dataStr );
                break;
            case 3:
                temperatureHandle( dataStr );
                break;
            case 4:
                screenHandle( dataStr );
                break;
            case 5:
                break;
            case 6:
                fingerprintHandle( comData );
                break;
            default:
                break;
        }
    }

    /**
     * 按钮操作
     */
    private void keyHandle(String keyData) {

    }

    /**
     * 读卡器操作
     */
    private void cardHandle(String cardData) {
        sendReceiveData( cardData );
    }

    /**
     * 体温测量
     */
    private void temperatureHandle(String temperatureData) {
        logger.debug( "temperatureHandle", temperatureData );
    }

    /**
     * 屏幕状态
     */
    private void screenHandle(String screenData) {

    }

    /**
     * 指纹
     */
    private void fingerprintHandle(String fingerData) {
        Log.d( "fingerprintHandle", fingerData );
    }

    /**
     * 发送串口数据
     */
    private void sendComData(String comData) {
        logger.debug( "SerialportManager===> 发送串口数据:" + comData );
        final byte sendData[] = new byte[9];
        int        j          = 0;
        for (int i = 0; i < comData.length(); i = i + 2) {
            j = i / 2;
            sendData[j] = (byte) Integer.parseInt( comData.substring( i, i + 2 ), 16 );
        }
        try {
            mOutputStream.write( sendData );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //串口byte[]数据转化为ic卡号
    public static String byte2hex(byte b[]) {
        if (b == null) {
            throw new IllegalArgumentException(
                    "Argument b ( byte array ) is null! " );
        }
        String hs   = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString( b[n] & 0xff );
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        String hex = hs.toUpperCase();
        return hex;
    }

    private static String hex2CompleteHexString(String paramString) {
        String str = paramString;
        int    len = 8;
        if ((paramString != null) && (!"".equals( paramString ))) {
            if (paramString.length() != 8) {
                len = paramString.length();
            }
        }
        for (int j = 0; j < 8 - len; j++) {

            str = "0" + str;
        }
        return str;
    }

    public static String getCardNo(String paramString) {
        String str = hex2CompleteHexString( bytesToHexString(
                byteHigh2Byte( toByteArray( paramString ) ) ).replace( " ", "" ) );
        Long          c             = anyRadixString2Long( str, 16 );
        DecimalFormat decimalFormat = new DecimalFormat( "0000000000" );
        return decimalFormat.format( c );
    }

    /**
     * Convert byte[] to hex string
     *
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder( "" );
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int    v  = src[i] & 0xFF;
            String hv = Integer.toHexString( v );
            if (hv.length() < 2) {
                stringBuilder.append( 0 );
            }
            stringBuilder.append( hv );
        }
        return stringBuilder.toString();
    }

    private static byte[] byteHigh2Byte(byte[] paramArrayOfByte) {
        byte[] arrayOfByte = null;
        int    len         = paramArrayOfByte.length;
        if (len > 0) {
            arrayOfByte = new byte[len];
        }
        for (int j = 0; j < len; j++) {
            arrayOfByte[j] = paramArrayOfByte[len - j - 1];
        }
        Log.e( "byteHigh2Byte", "byteHigh2Byte:" + bytesToHexString( arrayOfByte ) );
        return arrayOfByte;
    }

    private static long anyRadixString2Long(String paramString, int paramInt) {
        long l = 0L;
        if ((paramString != null) && (!"".equals( paramString ))) {
            l = Long.parseLong( paramString, paramInt );
        }
        return l;
    }

    private static byte[] toByteArray(String hexString) {

        int    hexStringLength = hexString.length();
        byte[] byteArray       = null;
        int    count           = 0;
        char   c;
        int    i;

        // Count number of hex characters
        for (i = 0; i < hexStringLength; i++) {

            c = hexString.charAt( i );
            if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a'
                    && c <= 'f') {
                count++;
            }
        }

        byteArray = new byte[(count + 1) / 2];
        boolean first = true;
        int     len   = 0;
        int     value;
        for (i = 0; i < hexStringLength; i++) {

            c = hexString.charAt( i );
            if (c >= '0' && c <= '9') {
                value = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                value = c - 'A' + 10;
            } else if (c >= 'a' && c <= 'f') {
                value = c - 'a' + 10;
            } else {
                value = -1;
            }

            if (value >= 0) {

                if (first) {

                    byteArray[len] = (byte) (value << 4);

                } else {

                    byteArray[len] |= value;
                    len++;
                }

                first = !first;
            }
        }

        return byteArray;
    }

    public interface SerialportListener {
        void onReceiveData(String cardNo);
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[64];
                    if (mInputStream == null)
                        return;
                    /**
                     * 这里的read要尤其注意，它会一直等待数据，等到天荒地老，海枯石烂。如果要判断是否接受完成，只有设置结束标识，或作其他特殊的处理。
                     */
                    size = mInputStream.read( buffer );
                    if (size > 0) {
                        onDataReceived( buffer, size );
                    }
                    Thread.sleep( 100 );
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyThread extends Thread {
        SerialPort serialPort;

        public MyThread(SerialPort k) {
            this.serialPort = k;
        }

        @Override
        public void run() {
            super.run();
            while (run && !isInterrupted()) {
                try {
                    InputStream mInputStream = serialPort.getInputStream();
                    byte[]      arrayOfByte  = new byte[64];
                    if (mInputStream != null) {
                        try {
                            int k = mInputStream.read( arrayOfByte );
                            if (k > 1) {
                                //串口byte[]数据转化为ic卡号
                                String       hex = byte2hex( arrayOfByte ).substring( 4, 12 );
                                final String re  = getCardNo( hex );
                                logger.info( "ic卡号："+re );
                                if (re != null) {
                                    for (SerialportListener listener : serialPortListeners) {
                                        if (listener != null) {
                                            listener.onReceiveData( re );
                                        }
                                    }
                                }

                            }
                        } catch (IOException e) {
                        }
                    }
                } catch (Exception e) {
                }
            }

        }

    }
}
