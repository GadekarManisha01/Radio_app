package com.m90.shagoon.newRadio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.m90.shagoon.R;

import java.io.IOException;

public class RadiooverflowAct extends AppCompatActivity implements View.OnClickListener,MediaPlayer.OnPreparedListener {
    private ProgressBar playSeekBar;
    private Button buttonPlay;
    private Button buttonStopPlay;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radiooverflow);

        initializeUIElements();

        player = new MediaPlayer();
        try {
            // private String url1 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
            player.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
        }
        catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        try {

            player.setOnPreparedListener(this);
            player.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setSecondaryProgress(percent);
                Log.e("Buffering", "" + percent);
            }
        });






        //     initializeMediaPlayer();
    }

    private void initializeUIElements() {

        playSeekBar = (ProgressBar) findViewById(R.id.progressBar1);
        playSeekBar.setMax(100);
        playSeekBar.setVisibility(View.INVISIBLE);

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonPlay.setOnClickListener(this);

        buttonStopPlay = (Button) findViewById(R.id.buttonStopPlay);
        buttonStopPlay.setEnabled(false);
        buttonStopPlay.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v == buttonPlay) {
            buttonStopPlay.setEnabled(true);
            buttonPlay.setEnabled(false);
            playSeekBar.setVisibility(View.VISIBLE);
             //startPlaying();
        } else if (v == buttonStopPlay) {
            stopPlaying();
        }
    }

    private void startPlaying() {
        buttonStopPlay.setEnabled(true);
        buttonPlay.setEnabled(false);
        playSeekBar.setVisibility(View.VISIBLE);

        /* player.prepareAsync();
        try {
            player.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
*/

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });




    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
        }

        buttonPlay.setEnabled(true);
        buttonStopPlay.setEnabled(false);
        playSeekBar.setVisibility(View.INVISIBLE);
    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            // private String url1 = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3";
            player.setDataSource("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
        }
        catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        try {
            player.prepareAsync();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setSecondaryProgress(percent);
                Log.e("Buffering", "" + percent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            player.stop();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}