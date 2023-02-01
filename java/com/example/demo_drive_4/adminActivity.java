package com.example.demo_drive_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class adminActivity extends AppCompatActivity {

    private Button BtnCar;
    private TextView VwScore;
    private TextView VwFraude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        BtnCar=findViewById(R.id.AllVehicle);
        VwScore=findViewById(R.id.AdminScore);
        VwFraude=findViewById(R.id.Suspection);



        BtnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(adminActivity.this, AdminVehicleActivity.class);
                startActivity(intent);

            }
        });

        VwScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminActivity.this, LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

        VwFraude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(adminActivity.this, FraudeActivity.class);
                startActivity(intent);
            }
        });



    }
}