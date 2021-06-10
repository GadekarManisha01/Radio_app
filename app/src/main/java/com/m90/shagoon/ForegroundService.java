package com.m90.shagoon;
//  //  //  //  //  //  //  //  //Working Project


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.m90.shagoon.Constants.ACTION;
import com.m90.shagoon.home.Home;

import java.io.IOException;

import static com.m90.shagoon.Constants.ACTION.STARTFOREGROUND_ACTION;

public class ForegroundService extends Service implements MediaPlayer.OnPreparedListener ,MediaPlayer.OnErrorListener
{
    private AudioManager audioManager = null;
    public MediaPlayer mediaPlayer ;
    private String url1 = "http://procyon.shoutca.st:8262";

    private static final String LOG_TAG = "ForegroundService";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(LOG_TAG, "onCreate");
          mediaPlayer = new MediaPlayer();
         mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
         audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
       Play();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(LOG_TAG, "onStartCommand");
        if (intent.getAction().equals(ACTION.STARTFOREGROUND_ACTION)) {

            Log.e(LOG_TAG, "Received Start Foreground Intent ");

            Intent notificationIntent = new Intent(this, Home.class);
            notificationIntent.setAction(ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);
            Intent previousIntent = new Intent(this, ForegroundService.class);
            previousIntent.setAction(ACTION.PREV_ACTION);
            PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                    previousIntent, 0);
            Intent playIntent = new Intent(this, ForegroundService.class);
            playIntent.setAction(ACTION.PLAY_ACTION);
            PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                    playIntent, 0);
            Intent nextIntent = new Intent(this, ForegroundService.class);
            nextIntent.setAction(ACTION.NEXT_ACTION);
            PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                    nextIntent, 0);
            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.radiologo);

            Notification notification = new NotificationCompat.Builder(this)
                    .setContentTitle("Shagoon")
                    .setTicker("Shagoon")
                    .setContentText("My Music")
                    .setSmallIcon(R.drawable.radiologo)
                    .setLargeIcon(
                            Bitmap.createScaledBitmap(icon, 128, 128, false))
                    .setContentIntent(pendingIntent)
                    .setOngoing(true)

                    .addAction(android.R.drawable.ic_media_play, "Play",
                            pplayIntent)
                    .addAction(android.R.drawable.ic_media_next, "Stop",
                            pnextIntent).build();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                String NOTIFICATION_CHANNEL_ID = "com.m90.badshahandicappertips";
                String channelName = "My Background Service";
                NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
                chan.setLightColor(Color.BLUE);
                chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                assert manager != null;
                manager.createNotificationChannel(chan);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
                Notification notification1 = notificationBuilder.setOngoing(true)
                        .setSmallIcon(R.drawable.radiologo)
                        .setTicker("Shagoon")
                        .setContentTitle("Shagoon")
                        .setLargeIcon(
                                Bitmap.createScaledBitmap(icon, 128, 128, false)).setPriority(NotificationManager.IMPORTANCE_MIN)
                        .setCategory(Notification.CATEGORY_SERVICE)
                        .setOngoing(true)
                        .addAction(android.R.drawable.ic_media_play, "Play",
                                pplayIntent)
                        .addAction(android.R.drawable.ic_media_next, "Stop",
                                pnextIntent).build();
                startForeground(2, notification1);

            }
            else {
                //     startForeground(1, new Notification());
                startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
            }
        } else if (intent.getAction().equals(ACTION.PREV_ACTION)) {
            Toast.makeText(this,"PREV_ACTION",Toast.LENGTH_SHORT).show();
            Log.i(LOG_TAG, "Clicked Previous");
        }
        else if (intent.getAction().equals(ACTION.PLAY_ACTION)) {
                mediaPlayer.start();

            Log.i(LOG_TAG, "Clicked Play");
        } else if (intent.getAction().equals(ACTION.NEXT_ACTION)) {
            Pause();
            Log.i(LOG_TAG, "Clicked Next");
        }

        else if (intent.getAction().equals(
                ACTION.STOPFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Stop Foreground Intent");
            stopForeground(true);
            stopSelf();
        }

      return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "In onDestroy");
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startService() {
        Intent startIntent = new Intent(this, ForegroundService.class);
        startIntent.setAction(STARTFOREGROUND_ACTION);
        startService(startIntent);
    }
    public void stopService() {
        Intent stopIntent = new Intent(this, ForegroundService.class);
        stopIntent.setAction(ACTION.STOPFOREGROUND_ACTION);
        startService(stopIntent);
    }

    public void Play(){

        Intent i = new Intent (ACTION.SHOW_PROGRESS);
        sendBroadcast(i);

        Log.e(LOG_TAG, "Play");
        try {
            mediaPlayer.setDataSource(url1);
            mediaPlayer.setOnPreparedListener(this);

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    try {
            mediaPlayer.prepareAsync();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void Pause(){
        // stopService();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        Intent i = new Intent (ACTION.DissmisS_PROGRESS);
        sendBroadcast(i);

    }
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (what == -38){
              mp.reset();
           onPrepared(mp);
        }

        return false;
    }




}
