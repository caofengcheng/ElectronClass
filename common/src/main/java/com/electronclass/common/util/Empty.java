package com.electronclass.common.util;

import android.text.Editable;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class Empty {
    public static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public static boolean hasEmpty(String... strings) {
        for (String s : strings) {
            if (Empty.isEmpty(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isEmpty(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isEmpty(Editable text) {
        return text == null || isEmpty(text.toString().trim());
    }

    public static boolean isEmpty(CharSequence text) {
        return text == null || isEmpty(text.toString().trim());
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    public static boolean isNotEmpty(Map map) {
        return !isEmpty(map);
    }

    public static boolean isNotEmpty(Collection c) {
        return !isEmpty(c);
    }

    public static boolean isNotEmpty(String str) {

        return !isEmpty(str);
    }

    public static boolean isNotEmpty(String... strs) {
        if (Empty.isEmpty(strs)) {
            return false;
        }
        for (String s : strs) {
            if (Empty.isEmpty(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(Editable text) {
        return !isEmpty(text);
    }

    public static boolean isNotEmpty(CharSequence text) {
        return !isEmpty(text);
    }

    public static boolean isNotEmpty(Object... array) {
        return !isEmpty(array);
    }

    public static boolean isAllEmpty(String... strings) {
        for (String s : strings) {
            if (Empty.isNotEmpty(s)) {
                return false;
            }
        }
        return true;
    }
}