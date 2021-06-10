package com.m90.shagoon.radioFm.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.m90.shagoon.R;
import com.m90.shagoon.radioFm.model.ShecduleModel;

import java.util.ArrayList;

public class SheduleAdapter extends RecyclerView.Adapter<SheduleAdapter.MyViewHolder> {

    private static final String TAG = "CartAdapter";


    private Activity mContext;
    ArrayList<ShecduleModel> list;
    ArrayList<Integer> listInteger;


   String amt;
    private  ItemClickListener itemClickListener;

    public SheduleAdapter(Activity context, ArrayList<ShecduleModel> list, ItemClickListener itemClickListener) {
        mContext = context;
        this.list = list;
        this.itemClickListener=itemClickListener;
    }


    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    @Override

    public  MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shedule, null);

        Log.e(TAG, "onCreateViewHolder: "+amt );
        //  prefManager=new PrefManager(mContext);
        return new  MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        final ShecduleModel item = list.get(i);

        viewHolder.tv_TitleShedule.setText(String.valueOf(item.getTitle()));
        viewHolder.tv_dateshedule.setText(String.valueOf(item.getDate()));
        viewHolder.tv_rjName.setText(String.valueOf(item.getRjname()));
        Log.e("vlist", list.toString());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv_TitleShedule,tv_dateshedule,tv_rjName;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_TitleShedule = itemView.findViewById(R.id.tv_TitleShedule);
            tv_dateshedule = itemView.findViewById(R.id.tv_dateshedule);
            tv_rjName = itemView.findViewById(R.id.tv_rjName);

        }
        @Override
        public void onClick(View view) {
            if (itemClickListener != null) itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    //region Search Filter (setFilter Code)
    public void setFilter(ArrayList<ShecduleModel> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}

