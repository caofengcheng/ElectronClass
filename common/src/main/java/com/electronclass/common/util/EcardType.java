package com.electronclass.common.util;

/**
 * 设备类型
 */

public class EcardType {
    public static int type;
    public static int HK = 1;//海康
    public static int HHD = 2;//恒泓达
    public static int ML = 3;//木兰
    public static int DH = 4;//大华

    public static int getType() {
        return type;
    }

    public static void setType(int type) {
        EcardType.type = type;
    }
}


