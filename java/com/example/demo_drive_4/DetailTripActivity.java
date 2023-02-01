package com.example.demo_drive_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailTripActivity extends AppCompatActivity {

    CustomBaseAdapter customBaseAdapter;
    RecyclerView recyclerView;
    ArrayList<DetailTrip> list;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_trip);
        recyclerView = findViewById(R.id.detail_listview);
        database = FirebaseDatabase.getInstance("https://dataobdtest-default-rtdb.europe-west1.firebasedatabase.app/").getReference("All_Trip").child("Detail_Trip");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        customBaseAdapter = new CustomBaseAdapter(this, list);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    DetailTrip detailTrip = dataSnapshot.getValue(DetailTrip.class);
                    list.add(detailTrip);

                }
                //customBaseAdapter_all.notifyDataSetChanged();

                recyclerView.setAdapter(customBaseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}