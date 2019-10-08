package com.electronclass.common.util;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class DeviceUuidFactory {
    protected static final String PREFS_FILE      = "device_id.xml";
    protected static final String PREFS_DEVICE_ID = "device_id";
    protected static       UUID   uuid;

    public DeviceUuidFactory(Context context) {
        if (uuid == null) {
            synchronized (DeviceUuidFactory.class) {
                if (uuid == null) {
                    final SharedPreferences prefs = context.getSharedPreferences( PREFS_FILE, 0 );
                    final String            id    = prefs.getString( PREFS_DEVICE_ID, null );
                    if (id != null) {
                        // Use the ids previously computed and stored in the prefs file
                        uuid = UUID.fromString( id );
                    } else {
                        final String androidId = Settings.Secure.getString( context.getContentResolver(), Settings.Secure.ANDROID_ID );
                        try {
                            if (!"9774d56d682e549c".equals( androidId )) {
                                uuid = UUID.nameUUIDFromBytes( androidId.getBytes( "utf8" ) );
                            } else {
                                if (ActivityCompat.checkSelfPermission( context, Manifest.permission.READ_PHONE_STATE ) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                }
                                final String deviceId = ((TelephonyManager) context.getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                            }
                        } catch (UnsupportedEncodingException e) {
                            throw new RuntimeException(e);
                        }
                        // Write the value out to the prefs file
                        prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).commit();
                    }
                }
            }
        }
    }

    public static UUID getDeviceUuid() {
        return uuid;
    }

}
