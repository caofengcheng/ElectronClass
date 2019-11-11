package com.electronclass.common.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;

import android_serialport_api.SerialPort;

/**
 * Created by sw on 2018/9/27/027.
 */

//处理刷卡的业务
public class ReadThreadUtil {
    private final String          TAG = "ReadThreadUtil";
    static        SerialPort      sp;
    static        FileInputStream mInputStream;
    private       ReadThread      mReadThread;
    private       readListener    listener;

    public void startReadThread(readListener listener) {
        mReadThread = new ReadThread();
        mReadThread.start();
        this.listener = listener;
    }

    public void stopReadThread() {
        if (mReadThread != null)
            mReadThread.interrupt();
    }


    public interface readListener {
        void onRead(int type, String cardNum);
    }

    class ReadThread extends Thread {
        public void run() {
            super.run();
            try {
                //设置串口号、波特率
                String serial_port = "ttyS3";
                sp = new SerialPort( new File( "/dev/"+serial_port ),
                        9600, 0 );
                Log.i( TAG, "当前的串口为--->" + "/dev/" + serial_port );

                mInputStream = (FileInputStream) sp.getInputStream();
                while (!isInterrupted()) {
                    int    size;
                    byte[] buffer = new byte[64];
                    if (mInputStream == null)
                        return;
                    int count = 0;
                    while (count == 0) {
                        count = mInputStream.available();
                    }
                    if (mInputStream.available() >= 7) {
                        size = mInputStream.read( buffer );
                        if (size > 0) {
                            onDataReceived( buffer, size );
                        } else {
                        }
                    }
                    Thread.sleep( 10 );
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void onDataReceived(final byte[] buffer, final int size) {
        try {
            String carNum = "";
            try {
                byte[] tmp = new byte[size];
                System.arraycopy( buffer, 0, tmp, 0, size );
                Log.i( TAG, "读卡数据1--->" + AmountUtil.byte2hex( tmp ) );
                String effecNum = "";
                if (AmountUtil.byte2hex( tmp ).length() == 24) {
                    effecNum = AmountUtil.byte2hex( tmp ).substring( 14, AmountUtil.byte2hex( tmp ).length() - 2 );
                } else if (AmountUtil.byte2hex( tmp ).length() == 14) {
                    effecNum = AmountUtil.byte2hex( tmp ).substring( 4, AmountUtil.byte2hex( tmp ).length() - 2 );
                } else if (AmountUtil.byte2hex( tmp ).length() == 18) {
                    effecNum = AmountUtil.byte2hex( tmp ).substring( 6, AmountUtil.byte2hex( tmp ).length() - 4 );
                }
//                else if (AmountUtil.byte2hex(tmp).length() == 20) {  //ID 卡的算法
//                        effecNum = AmountUtil.byte2hex(tmp).substring(10, AmountUtil.byte2hex(tmp).length() - 4);
//                }
                Log.i( TAG, "截取后的数据--->" + effecNum );
                if (TextUtils.isEmpty( effecNum )) {
                    listener.onRead( 1, "" );
                    return;
                }
                long dec_num = Long.parseLong( effecNum, 16 );
                carNum = String.valueOf( dec_num );
                Log.i( TAG, "读卡数据2--->" + carNum );
                listener.onRead( 0, carNum );
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}




