package com.m90.shagoon.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.m90.shagoon.R;
import com.m90.shagoon.databinding.FragmentAdvertiseRadioBinding;
import com.m90.shagoon.databinding.FragmentSheduleRadioBinding;
import com.m90.shagoon.radioFm.adapter.SheduleAdapter;
import com.m90.shagoon.radioFm.model.ShecduleModel;

import java.util.ArrayList;


public class Fragment_ScheduleRadio extends Fragment implements View.OnClickListener {
    FragmentSheduleRadioBinding binding;
    View root;
    SheduleAdapter sheduleAdapter;
    ArrayList<ShecduleModel> modelArrayListShedule ;
    private String[] titleList = new String[]{"Shubharamph", "shaam shandar","bawara mann",
            "14/2/2021","15/2/2021"};
    private String[] dateList = new String[]{"11.30 am","12.30 pm","11.30 am"};
    private String[] nameList = new String[]{"abcd","pqrs","jklm"};




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shedule_radio, container, false);
        listenrs();
        setRvSchedule();
        return binding.getRoot();
    }

    void listenrs(){


    }

    private void setRvSchedule() {
        modelArrayListShedule = arrayShedule();

        binding.rvShedule.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.rvShedule.setItemAnimator(new DefaultItemAnimator());
        binding.rvShedule.setHasFixedSize(true);

        sheduleAdapter = new SheduleAdapter(getActivity(), modelArrayListShedule,
                new SheduleAdapter.ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {


                    }
                });
        binding.rvShedule.setAdapter(sheduleAdapter);

    }

    private ArrayList<ShecduleModel> arrayShedule(){
        ArrayList<ShecduleModel> list = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            ShecduleModel sheduleModel = new ShecduleModel();
            sheduleModel.setDate(dateList[i]);//by sneha
            sheduleModel.setRjname(nameList[i]);
            sheduleModel.setTitle(titleList[i]);
            list.add(sheduleModel);
        }
        return list;
    }

















    @Override
    public void onClick(View v) {
        switch (v.getId())
        {





        }
    }
}
