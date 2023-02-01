package com.example.demo_drive_4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Trip {

    String dateTrajet,DistanceTot,PrixTrajet;


    public String getTripdate() {
        return dateTrajet;
    }

    public String getTripdistance() {
        return DistanceTot;
    }

    public String getTripprice() {
        return PrixTrajet;
    }
}