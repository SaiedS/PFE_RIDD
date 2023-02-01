package com.example.demo_drive_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomBaseAdapter_All extends RecyclerView.Adapter<CustomBaseAdapter_All.MyViewHolder> {
    Context context;
    ArrayList<Trip> list;
    private ItemClickListerner mItemListerner;

    public CustomBaseAdapter_All(Context context, ArrayList<Trip> list, ItemClickListerner itemClickListerner) {
        this.context = context;
        this.list = list;
        this.mItemListerner = itemClickListerner;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_custum_list_view_all, parent, false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Trip trip = list.get(position);
// okay? the second rows  is the price ? if you can just add an € after price Thank you a loooot no problem can ou send Western unioni dont use this what it work ? you need to login and than send with you card okey i try now. try it if you think you cannot than i will send you offer on fiverr
        // when will you try?nowi download the application okay thanks what country i send ?
        // paksitan
        // +923475872808
        // umar ali
        //city haripur
        //in an account ? no no acount
        //go in fiver
        // this is western union app?yes
        // what info you needi think fiver is better no ? the request lot of information ok yes me there

        holder.trip_date.setText(trip.getTripdate());
        Double distance  = Double.valueOf(trip.getTripdistance());
        holder.trip_distance.setText(String.format("%.02f km",distance));
        Double price  = Double.valueOf(trip.getTripprice());
        holder.trip_price.setText(String.format("%.02f€",price));

        holder.itemView.setOnClickListener(view -> {
            mItemListerner.onItemClick(list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public interface ItemClickListerner {
        void onItemClick(Trip trip);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView trip_date, trip_distance, trip_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            trip_date = itemView.findViewById(R.id.AllDate);
            trip_distance = itemView.findViewById(R.id.AllDistance);
            trip_price = itemView.findViewById(R.id.AllPrice);
        }
    }
}
