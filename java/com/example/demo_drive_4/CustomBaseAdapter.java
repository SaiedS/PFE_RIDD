package com.example.demo_drive_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomBaseAdapter extends RecyclerView.Adapter<CustomBaseAdapter.MyViewHolder> {
    Context context;
    ArrayList<DetailTrip> list;

    public CustomBaseAdapter(Context context, ArrayList<DetailTrip> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_custom_list_view,parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        DetailTrip detailtrip = list.get(position);
        holder.tripdistance.setText(detailtrip.getTripdistance());
        holder.tripprice.setText(detailtrip.getTripprice());
        holder.tripaveragecost.setText(detailtrip.getTripaveragecost());
        holder.tripminimumcost.setText(detailtrip.getTripminimumcost());
        holder.tripavcons.setText(detailtrip.getTripavcons());
        holder.fuelbefore.setText(detailtrip.getFuelbefore());
        holder.fuelafter.setText(detailtrip.getFuelafter());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tripdistance,tripprice,tripaveragecost,tripminimumcost,tripavcons,fuelbefore,fuelafter;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tripdistance = itemView.findViewById(R.id.DetailDistance);
            tripprice = itemView.findViewById(R.id.DetailCost);
            tripaveragecost = itemView.findViewById(R.id.DetailAvCost);
            tripminimumcost = itemView.findViewById(R.id.DetailMinCost);
            tripavcons = itemView.findViewById(R.id.DetailAvCons);
            fuelbefore = itemView.findViewById(R.id.DetailFuelBefore);
            fuelafter = itemView.findViewById(R.id.DetailFuelAfter);
        }
    }
}