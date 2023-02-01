package com.example.demo_drive_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllTripActivity extends AppCompatActivity {



    CustomBaseAdapter_All customBaseAdapter_all;
    RecyclerView recyclerView;
    ArrayList<Trip> list;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_trip);

        list = new ArrayList<>();
        recyclerView = findViewById(R.id.all_listview);
        database = FirebaseDatabase.getInstance().getReference().child("Trajet");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customBaseAdapter_all = new CustomBaseAdapter_All(this, list, new CustomBaseAdapter_All.ItemClickListerner() {
            @Override
            public void onItemClick(Trip trip) {
                Intent intent= new Intent(AllTripActivity.this,DetailTripActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(customBaseAdapter_all);

        database.orderByChild("dateTrajet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TrajetData trajetData = dataSnapshot.getValue(TrajetData.class);
                    Trip trip = new Trip();
                    trip.dateTrajet = trajetData.dateTrajet;
                    trip.DistanceTot = trajetData.DistanceTot;
                    trip.PrixTrajet = trajetData.PrixTrajet;
                    list.add(trip);
                    customBaseAdapter_all.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}