package com.m90.shagoon.newRadio;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.m90.shagoon.R;
import com.m90.shagoon.databinding.FragmentHomeRadioBinding;
import com.m90.shagoon.databinding.NewHomeBinding;

import java.io.IOException;


///   avtivity for select button
public class Fragment_NewHome extends Fragment implements View.OnClickListener {
    // private static final int REQUEST_OVERLAY_PERMISSION = ;
    NewHomeBinding binding;
    View root;

    private ProgressBar playSeekBar;

    private Button buttonPlay;

    private Button buttonStopPlay;

    private MediaPlayer player;

    private String url1 = "http://procyon.shoutca.st:8262";

    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_home, container, false);

        initializeUIElements();

        initializeMediaPlayer();
        return binding.getRoot();
    }

    private void initializeUIElements() {

        //  playSeekBar = (ProgressBar) root.findViewById(R.id.progressBar1);
        //   playSeekBar.setMax(100);
        //   playSeekBar.setVisibility(View.INVISIBLE);

        binding.buttonPlay.setOnClickListener(this);

        binding.buttonStopPlay.setEnabled(false);
        binding.buttonStopPlay.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v == buttonPlay) {
            startPlaying();
        } else if (v == buttonStopPlay) {
            stopPlaying();
        }


    }








    private void startPlaying() {
        binding.buttonStopPlay.setEnabled(true);
        binding.buttonPlay.setEnabled(false);


        player.prepareAsync();

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

        binding.buttonPlay.setEnabled(true);
        binding.buttonStopPlay.setEnabled(false);
    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource("http://usa8-vn.mixstream.net:8138");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                playSeekBar.setSecondaryProgress(percent);
                Log.i("Buffering", "" + percent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player.isPlaying()) {
            player.stop();
        }
    }







}
