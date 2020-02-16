package com.gabrielmiguelpedro.maclarenapp;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    DBHelperClient db;
    private FusedLocationProviderClient fusedLocationClient; //localização atual
    private double lat;
    private double lon;
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
                getPhoneLocation();
                Date date = new Date();
                db.addHistoricCoordinates(new HistoricCoordinates(0, date, lon, lat, db.getHistoricById(db.getLastIdFromTableHistoric()))); //TODO coerdenadas
            }
        }, 0, 10000);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //player.stop();
    }

    public void getPhoneLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //Toast.makeText(getContext(),"Altitude: "+location.getAltitude()+" Longitude: "+location.getLongitude(), Toast.LENGTH_LONG).show();
                        if (location != null) {
                            //Toast.makeText(getContext(),"222Altitude: "+location.getAltitude()+" Longitude: "+location.getLongitude(), Toast.LENGTH_LONG).show();
                            lon = location.getLongitude();
                            lat = location.getLatitude();
                        }
                    }
                });
    }
}
