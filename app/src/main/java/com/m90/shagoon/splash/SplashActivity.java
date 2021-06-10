package com.m90.shagoon.splash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.m90.shagoon.R;
import com.m90.shagoon.databinding.ActivitySplashBinding;
import com.m90.shagoon.home.Home;
import com.m90.shagoon.utils.SessionHelper;

import com.m90.shagoon.utils.PrefManager;
import com.m90.shagoon.utils.Utilities;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    ActivitySplashBinding binding;
    Activity activity;
    Handler handler;
    PrefManager prefManager;
    ProgressDialog progressDialog;
    SessionHelper sessionHelper;
    Animation animdown,animup;
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
activity=SplashActivity.this;
        prefManager = new PrefManager(this);
        progressDialog = new ProgressDialog(this);
        sessionHelper = new SessionHelper(this);

        animup= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        binding.img.setAnimation(animup);

        animdown= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
        binding.imgsound.setAnimation(animdown);

        animation();
    }

    public void animation() {
       // progressDialog.show();
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Utilities.isNetworkAvailable(activity)){

                    if (sessionHelper.isLoggedIn()) {
                        Utilities.launchActivity(SplashActivity.this, Home.class, true);
                    } else {
                        Utilities.launchActivity(SplashActivity.this, Home.class, true);
                    }
                }
                else {
                    Utilities.dialogNetwork(activity, getResources().getString(R.string.check_internet));
                    // Toast.makeText(getActivity(), getResources().getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
                }

          }
        }, 3000);
    }

}