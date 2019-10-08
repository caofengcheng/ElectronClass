package com.electronclass.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtils {

    /**
     * 隐藏键盘的方法
     *
     *
     */
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService( Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        if (imm.isActive())
        imm.hideSoftInputFromWindow( view.getWindowToken(), 0);

    }

    /**
     * 显示键盘的方法
     *
     *
     */

    public static void showSoftInputFromWindow(EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }


}
