package com.electronclass.common.util;

import java.text.DecimalFormat;

/**
 * @ClassName: AmountUtil
 * Copyright (c) 2016 hongongda, Inc.
 * @Description: 数字工具类
 * @author lyq
 * @date 2017-5-24
 */
public class AmountUtil {
	
	/**
	 * 
	    * @Title: calcReqLength
	    * @Description: 计算请求报文长度
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String calcReqLength(String reqString) {
		String reqLength = Integer.toString(reqString.getBytes().length - 4);
		while (reqLength.length() < 4) {
			reqLength = '0' + reqLength;
		}
		return reqLength;
	}
	
	/**
	 * 
	    * @Title: byte2hex
	    * @Description: byte数组转换成十六进制字符串
	    * @param @param b
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String byte2hex(byte[] b) {  
        String hs = "";  
        String stmp = "";  
  
        for (int n = 0; n < b.length; n++) {  
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)  
                hs = hs + "0" + stmp;  
            else  
                hs = hs + stmp;  
//            if (n < b.length - 1)  
//                hs = hs + ":";  
        }  
        return hs.toUpperCase();  
    }

	/**
	 * 
	    * @Title: hex2byte
	    * @Description: 十六进制字符串转换成byte数组
	    * @param @param hex
	    * @param @return    参数
	    * @return byte[]    返回类型
	    * @throws
	 */
	public static byte[] hex2byte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}
	
	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}
	
	/**
	 * 
	    * @Title: money2fen
	    * @Description: 将金额转换成以分为单位
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String money2fen(String money) {
		DecimalFormat decimalFormat = new DecimalFormat("000000000000");	
		float num = Float.parseFloat(money) * 100;
		return decimalFormat.format(num);
	}
	
	/**
	 * 
	    * @Title: money2yuan
	    * @Description: 将金额转换成以元为单位
	    * @param @param money
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	public static String money2yuan(String money) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");	
		float num = Float.parseFloat(money) / 100;
		return decimalFormat.format(num);
	}
	
}
