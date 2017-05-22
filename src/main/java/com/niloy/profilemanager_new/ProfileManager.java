package com.niloy.profilemanager_new;

/**
 * Created by user on 5/9/2017.
 */

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class ProfileManager extends Service implements SensorEventListener {

    MainActivity Context;

    AudioManager audioManager;
    SensorManager sensorManager;
    Sensor accelerometer;
    Sensor proximity;

    private double X;
    private  double Y;
    private double Z;

    private boolean proxySilent=false;
    private boolean isProxyRinger=false;

    private static final int SENSOR_SENSITIVITY = 4;

    @Override
    public IBinder onBind(Intent a){
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        this.audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        this.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        this.accelerometer =  sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(accelerometer != null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(proximity != null){
            sensorManager.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int acc){

    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            X = event.values[0];
            Y = event.values[1];
            Z = event.values[2];

            if ((X <= 11 && X >= -10) && (Y <= .7 && Y >= -.7) && (Z <= 10 && Z >= -8)) {
                setRinging();
            }
            if ((X <= 2.7 && X >= -2.7) && (Y <= .7 && Y >= -.5) && (Z <= -7 && Z >= -11)) {
                setSilent();
            }
            if ((X <= .5 && X >= -.5) && (Y >= 6) && (Z <= 8 && Z >= -8)) {
                setVibrate();
            }
        }

        if(event.sensor.getType() == Sensor.TYPE_PROXIMITY){
            if(event.values[0]>=SENSOR_SENSITIVITY  && event.values[0]<=SENSOR_SENSITIVITY){
                setSilent();
            }
            else {
                setRinging();
            }
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(this, "Service Started Successfully", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        sensorManager.unregisterListener(this);
        Toast.makeText(this, "Service Finished Successfully", Toast.LENGTH_SHORT).show();
    }

    public void setRinging(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        SystemClock.sleep(5);
    }

    public void setVibrate(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        SystemClock.sleep(5);
    }

    public void setSilent(){
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        SystemClock.sleep(5);
    }

}
