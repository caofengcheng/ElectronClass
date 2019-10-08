package com.electronclass.common.util;


import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.List;

public class ListUtil {
    //List非空判断
    public static  <T> boolean isNolList(List<T> list) {
        return (list != null && list.size() > 0);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static <T>String ListToString(List<T> list){
        String str = String.join(",", (CharSequence) list);
        return str;
    }
    public static String listToString(List<String> mList) {
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            String[] mListArray = mList.toArray(new String[mList.size()]);
            for (int i = 0; i < mListArray.length; i++) {
                if (i < mListArray.length - 1) {
                    convertedListStr += mListArray[i] + ",";
                } else {
                    convertedListStr += mListArray[i];
                }
            }
            return convertedListStr;
        } else return "List is null!!!";
    }

}
