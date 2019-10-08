package com.electronclass.common.util;

import android.Manifest;
import android.app.Activity;
import android.support.annotation.NonNull;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class PermissionUtil {

    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.INTERNET
    };

    public static void requestEachRxPermission(Activity activity, final OnPermissionListener listener) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.request(PERMISSIONS).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean granted) throws Exception {
                listener.onPermissionResult(granted);
            }
        });

    }

    public interface OnPermissionListener {
        void onPermissionResult(@NonNull Boolean granted);
    }
}
