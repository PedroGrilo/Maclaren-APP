package com.gabrielmiguelpedro.maclarenapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    DBHelperClient db;
    //private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public MyService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = new DbHelper(this); //
        /*player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();*/

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //FUNCIONA player.start();
                Date date = new Date();
                db.addHistoricCoordinates(new HistoricCoordinates(0, date, 1, 1, db.getHistoricById(db.getLastIdFromTableHistoric()))); //TODO coerdenadas
            }
        }, 0, 10000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //player.stop();
    }
}
