package com.m90.shagoon.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.m90.shagoon.R;
import com.m90.shagoon.databinding.FragmentAdvertiseRadioBinding;
import com.m90.shagoon.databinding.FragmentSocialmediaBinding;


///   avtivity for select button
public class Fragment_SocialMediaRadio extends Fragment implements View.OnClickListener {
    FragmentSocialmediaBinding binding;
    View root;
    Animation animSlideUp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_socialmedia, container, false);
        listenrs();

        animSlideUp = AnimationUtils.loadAnimation(getActivity().getApplicationContext(),
                R.anim.slide_up);
        binding.slideupView.startAnimation(animSlideUp);


        return binding.getRoot();
    }

    void listenrs(){

       binding.cvFacebook.setOnClickListener(this);
        binding.cvCall.setOnClickListener(this);
        binding.cvYoutube.setOnClickListener(this);
        binding.cvWtsaap.setOnClickListener(this);

    }


    public static Intent getOpenFacebookIntent(Context context) {

        try {
            context.getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/254175194653125")); //Trys to make intent with FB's URI
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/arkverse")); //catches and opens a url to the desired page
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.cv_facebook:
                Intent facebookIntent = getOpenFacebookIntent(getContext());
                startActivity(facebookIntent);
              break;
            case  R.id.cv_call:
                if (isPermissionGranted()) {
                    callAction();
                }

                break;

            case  R.id.cvYoutube:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=fis26HvvDII"));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);
                break;



            case R.id.cv_wtsaap:

                PackageManager pm = getContext().getPackageManager();
                try {
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    String text = "YOUR TEXT HERE";
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waIntent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                            .show();
                }

                break;
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
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }
    public void callAction() {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + "918767390210"));
        startActivity(callIntent);

    }
}
