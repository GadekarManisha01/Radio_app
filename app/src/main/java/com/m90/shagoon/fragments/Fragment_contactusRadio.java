package com.m90.shagoon.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.m90.shagoon.R;
import com.m90.shagoon.databinding.FragmentAdvertiseRadioBinding;
import com.m90.shagoon.databinding.FragmentContactusRadioBinding;


public class Fragment_contactusRadio extends Fragment implements View.OnClickListener {
    FragmentContactusRadioBinding binding;
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contactus_radio, container, false);
        listenrs();

        return binding.getRoot();
    }

    void listenrs(){
        binding.llWhatsapp.setOnClickListener(this);
        binding.llMail.setOnClickListener(this);

    }

void wtsapp(){

    Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
    whatsappIntent.setType("text/plain");
    whatsappIntent.setPackage("com.whatsapp");
    whatsappIntent.putExtra(Intent.EXTRA_TEXT, "Your Message Here");

    String smsNumber = "918095841555";
    whatsappIntent.putExtra("jid", smsNumber + "@s.whatsapp.net");

    if (whatsappIntent.resolveActivity(getActivity().getPackageManager()) == null) {
        Toast.makeText(getContext(), "Whatsapp not installed.", Toast.LENGTH_SHORT).show();
        return;
    }

    startActivity(whatsappIntent);

}


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_whatsapp:
                wtsapp();
                break;

            case R.id.ll_mail:

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.setPackage("com.google.android.gm");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"raddioshagoon@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
                i.putExtra(Intent.EXTRA_TEXT   , "body of email");
                try {
                    startActivity(i);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

                break;


        }
    }
}
