package com.m90.shagoon.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.m90.shagoon.R;
import com.m90.shagoon.databinding.FragmentHomeRadioBinding;
import com.m90.shagoon.Constants;
import com.m90.shagoon.ForegroundService;
import com.m90.shagoon.utils.Utilities;

public class Fragment_HomeRadio extends Fragment implements View.OnClickListener {
    FragmentHomeRadioBinding binding;
    View root;
    Boolean isMenuOpen = false;
    OvershootInterpolator interpolator = new OvershootInterpolator();
    Float translationY = 100f;
    Intent startIntent;
    private AudioManager audioManager = null;
    private MediaPlayer mediaPlayer;
    ProgressDialog pd;
    private String url1 = "http://procyon.shoutca.st:8262";

    private IntentFilter intentFilter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_radio, container, false);
        listenrs();
        Log.e("oncreate", "startService bfe");
        binding.fabOne.setAlpha(0f);
        binding.fabTwo.setAlpha(0f);
        binding.fabThree.setAlpha(0f);
        binding.fabfour.setAlpha(0f);
        binding.fabOne.setTranslationY(translationY);
        binding.fabTwo.setTranslationY(translationY);
        binding.fabThree.setTranslationY(translationY);
        binding.fabfour.setTranslationY(translationY);
        closeMenu(false);

        intentFilter =new IntentFilter();
        intentFilter.addAction(Constants.ACTION.SHOW_PROGRESS);
        intentFilter.addAction(Constants.ACTION.DissmisS_PROGRESS);

        if(Utilities.isNetworkAvailable(getActivity())) {
         startService();
            Log.e("TAG", "PlayService");
        }
        else {
            Utilities.dialog(getActivity(), getResources().getString(R.string.check_internetnPlay));
        }
        initControls();

        return binding.getRoot();
    }
    void listenrs(){
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading your song....");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        binding.imgNext.setOnClickListener(this);
        binding.imgPrevious.setOnClickListener(this);
        binding. fabMain.setOnClickListener(this);
        binding. fabOne.setOnClickListener(this);
        binding.fabTwo.setOnClickListener(this);
        binding.fabThree.setOnClickListener(this);
        binding.fabfour.setOnClickListener(this);


    }
    public static Intent getOpenFacebookIntent(Context context) {
        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/Raddio-Shagoon-102340598559877/"));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/Raddio-Shagoon-102340598559877/"));
        }
    }
    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getContext().checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {
                Log.v("TAG", "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                        1);
                return false;
            }
        } else {
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }
    public void callAction() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + "8095841555"));
        startActivity(callIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.img_previous:
                Toast.makeText(getActivity(),"pause",Toast.LENGTH_SHORT).show();
                Log.e("TAG", "pause");
                pauseService();
                break;

            case R.id.img_next:
                if(Utilities.isNetworkAvailable(getActivity())) {
                    Toast.makeText(getActivity(), "start", Toast.LENGTH_SHORT).show();
                    startService();
                    PlayService();
                    Log.e("TAG", "PlayService");
                }
                else {
                    Utilities.dialog(getActivity(), getResources().getString(R.string.check_internetnPlay));
                }
                break;
            case R.id.fabMain:
                if (isMenuOpen) {
                    binding.fabOne.setVisibility(View.GONE);
                    binding.fabTwo.setVisibility(View.GONE);
                    binding.fabThree.setVisibility(View.GONE);
                    binding.fabfour.setVisibility(View.GONE);
                    closeMenu();
                } else {
                    binding.fabOne.setVisibility(View.VISIBLE);
                    binding. fabTwo.setVisibility(View.VISIBLE);
                    binding. fabThree.setVisibility(View.VISIBLE);
                    binding.fabfour.setVisibility(View.GONE);
                    openMenu();
                }
                break;
            case R.id.fabOne:
                Intent facebookIntent1 = getOpenFacebookIntent(getContext());
                startActivity(facebookIntent1);
                Toast.makeText(getContext(),"cliked",Toast.LENGTH_SHORT).show();
                handleFabOne();
                if (isMenuOpen) {
                    closeMenu();
                } else {
                    openMenu();
                }
                break;
            case R.id.fabTwo:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/channel/UCoi5RaAqtVRWvFo6j72_BoA"));
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.setPackage("com.google.android.youtube");
                startActivity(intent1);
                break;
            case R.id.fabThree:
                PackageManager pkgm = getContext().getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "YOUR TEXT HERE";
                    PackageInfo info = pkgm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case R.id.fabfour:
                //   if (isPermissionGranted()) {
                    callAction();
                //    }
                break;
            default:
                break;
        }
    }

    private void handleFabOne() {
        Log.i("tag", "handleFabOne: ");
    }

    private void openMenu() {
        isMenuOpen = !isMenuOpen;
        binding.fabMain.animate().setInterpolator(interpolator).rotation(360f).setDuration(300).start();
        binding.fabOne.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabOne.setVisibility(View.VISIBLE);
        binding.fabTwo.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabTwo.setVisibility(View.VISIBLE);
        binding.fabThree.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabThree.setVisibility(View.VISIBLE);
        binding. fabfour.animate().translationY(0f).alpha(1f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabfour.setVisibility(View.GONE);
    }

    private void closeMenu() {
        isMenuOpen = !isMenuOpen;
        binding.fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        binding.fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabOne.setVisibility(View.GONE);
        binding.fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabTwo.setVisibility(View.GONE);
        binding. fabThree.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        binding. fabThree.setVisibility(View.GONE);
        binding. fabfour.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        binding. fabfour.setVisibility(View.GONE);
    }

    private void closeMenu(boolean value) {
        isMenuOpen = value;
        binding.fabMain.animate().setInterpolator(interpolator).rotation(0f).setDuration(300).start();
        binding.fabOne.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabOne.setVisibility(View.GONE);
        binding.fabTwo.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabTwo.setVisibility(View.GONE);
        binding.fabThree.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabThree.setVisibility(View.GONE);
        binding.fabfour.animate().translationY(translationY).alpha(0f).setInterpolator(interpolator).setDuration(300).start();
        binding.fabfour.setVisibility(View.GONE);

    }
    private void initControls()
    {
        try
        {
            binding.seekBarVolume.setProgress(7);
          binding.seekBarVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            binding.seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0)
                {
                }
                @Override
                public void onStartTrackingTouch(SeekBar arg0)
                {
                }
                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
                {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    Log.e("onProgressChanged", "" + progress);

                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void startService() {
        Log.e("TAG", "startService before");
        if (startIntent == null) {
            startIntent = new Intent(getActivity(), ForegroundService.class);
            startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
            getActivity().startService(startIntent);
        }
    }
    public void stopService() {
        Intent stopIntent = new Intent(getActivity(), ForegroundService.class);
        stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
        getActivity().startService(stopIntent);
    }
    public void PlayService() {
        Intent startIntent = new Intent(getActivity(), ForegroundService.class);
        startIntent.setAction(Constants.ACTION.PLAY_ACTION);
        getActivity().startService(startIntent);
    }
    public void pauseService() {
        Intent startIntent = new Intent(getActivity(), ForegroundService.class);
        startIntent.setAction(Constants.ACTION.NEXT_ACTION);
        getActivity().startService(startIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(mReceiver,intentFilter);
        Log.e("TAG", "registerReceiver");

    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constants.ACTION.SHOW_PROGRESS))
            {    showProgress();

            }
            if (intent.getAction().equals(Constants.ACTION.DissmisS_PROGRESS))
            {
                pd.dismiss();

            }

            
        }
    };

    private BroadcastReceiver dReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Constants.ACTION.SHOW_PROGRESS))
            {    showProgress();
                Utilities.dialog(getActivity(),"broadcastreceiverplay");
            }
             if (intent.getAction().equals(Constants.ACTION.DissmisS_PROGRESS))
            {
                pd.dismiss();
                Utilities.dialog(getActivity(),"dismisss");
            }

        }
    };

    @Override
    public void onPause() {
      getActivity().unregisterReceiver(mReceiver);
        super.onPause();
        Log.e("TAG", "unregisterReceivere");
    }

    void showProgress()
    {
        pd.show();
        pd.setCancelable(false);
        pd.setMessage("Loading..............");
    }
}
