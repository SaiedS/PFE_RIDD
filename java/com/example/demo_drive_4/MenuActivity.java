package com.example.demo_drive_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {


    private Button BtnUserMenu;
    private Button BtnAdminMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        BtnUserMenu=findViewById(R.id.userMenu);
        BtnAdminMenu=findViewById(R.id.adminMenu);


        BtnUserMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, WelcomeActivity.class);
                startActivity(intent);


            }
        });

        BtnAdminMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, adminActivity.class);
                startActivity(intent);
            }
        });




    }
}