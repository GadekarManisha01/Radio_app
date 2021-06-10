package com.m90.shagoon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.m90.shagoon.R;
import com.m90.shagoon.databinding.FragmentAboutUsBinding;
import com.m90.shagoon.databinding.FragmentAdvertiseRadioBinding;


public class Fragment_AboutRadio extends Fragment implements View.OnClickListener {
    FragmentAboutUsBinding binding;
    View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_us, container, false);
        listenrs();
        binding.string.setText(getResources().getString(R.string.about));

        return binding.getRoot();
    }

    void listenrs(){

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {





        }
    }
}
