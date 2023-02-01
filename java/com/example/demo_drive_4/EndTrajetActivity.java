package com.example.demo_drive_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.FeatureGroupInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class EndTrajetActivity extends AppCompatActivity {

    private TextView Text_score;
    private Button button_graph;
    private Button button_rapport;
    private TextView Text_EndDistance;
    private TextView Text_prixTot;
    private TextView Text_prixMoy;
    private TextView Text_prixBas;
    DatabaseReference DataTrajet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_trajet);

        String score = getIntent().getStringExtra("Score");
        String kmTot = getIntent().getStringExtra("KmTot");
        String prixTot = getIntent().getStringExtra("PrixTot");


        Text_score = findViewById(R.id.ViewCons);
        Text_EndDistance = findViewById(R.id.R_Km);
        Text_prixTot = findViewById(R.id.R_Cost);
        Text_prixMoy = findViewById(R.id.R_AvCost);
        Text_prixBas = findViewById(R.id.R_MinCost);


        Text_score.setText(score);
        Text_EndDistance.setText(kmTot + " km");
        Text_prixTot.setText(prixTot + " €");


        List<String> LdataPrix = new ArrayList<>();
        List<String> LdataTrajet = new ArrayList<>();

        Double dataPrix;
        Double dateTrajet;

        DataTrajet = FirebaseDatabase.getInstance().getReference().child("Trajet");
        DataTrajet.orderByChild("Prix1km").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    TrajetData data_end = snapshot.getValue(TrajetData.class);
                    if (data_end != null && !LdataPrix.contains(data_end)) {
                        LdataPrix.add(data_end.Prix1km);
                    }

                }

            }
// where is rv i send you on fiverr you want me to create like that one?
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(EndTrajetActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                double minPrix = Double.MAX_VALUE;
                for (String number : LdataPrix) {
                    if (Double.valueOf(number) < minPrix) {
                        minPrix = Double.valueOf(number);
                        Log.d("ValeurPrix", String.valueOf(number));
                    }
                }

                double sum =  sumOfPrices(LdataPrix);
// here is nothing that crashes this activity right?yes but it go back to the fisrt screen and after it cruashokay
                double averagePrix = (LdataPrix.size() == 0) ? 0 : sum / LdataPrix.size();

                Double ValMinTrajet = minPrix * Double.parseDouble(kmTot);
                String ValMinTrajetFinal = String.valueOf(ValMinTrajet);

                //if you want you can delete this code and write a new ?? yes but i am trying to understand why this is happening
                Double ValMoyTrajet = averagePrix * Double.parseDouble(kmTot);
                Log.d("valMoy", String.valueOf(averagePrix));
                String txtMsg = ValMoyTrajet + " €";

                        Text_prixBas.setText(ValMinTrajetFinal + " €");
                        Text_prixMoy.setText(txtMsg);


            }
        }, 3000);
//this means 3 second working fine? Nan again in first there is a value and it change by nan can you send me screen recordingokey
// debugger stops automatically? or you press anythi no i dont press oh okay go to main activity and than back to this acit
    }

    private double sumOfPrices(List<String> ldataPrix) {

        Double sum = 0.0;
        for (String number2 : ldataPrix) {
            sum = sum + Double.parseDouble(number2);
            Log.d("Mainactiv", "ca marche " + sum);
        }
        return sum;
    }
}
// okay can you make it crash again? now there is nothing this acitivity does and MainActivity have all old code let see if it crashes n

