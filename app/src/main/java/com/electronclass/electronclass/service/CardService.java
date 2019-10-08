package com.electronclass.electronclass.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CardService extends Service {
    public CardService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException( "Not yet implemented" );
    }
}
