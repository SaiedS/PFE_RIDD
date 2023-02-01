package com.example.demo_drive_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FraudeActivity extends AppCompatActivity {

    private EditText textDateFraude, textIdFraude;
    private TextView textReponseFraude;
    private Button buttonDetectFraude;
    private DatePickerDialog pickerFraude;
    DatabaseReference TrajetFraude;
    Boolean DetectFraude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraude);


        textIdFraude=findViewById(R.id.EditUsernameFraude);
        textDateFraude=findViewById(R.id.EditDateFraude);
        buttonDetectFraude=findViewById(R.id.btnDetectionFraude);
        textReponseFraude=findViewById(R.id.TxtDetectionFraude);

        textDateFraude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);


                //afficheur fenetre calendrier (Date Picker dialog)
                pickerFraude = new DatePickerDialog(FraudeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String inputDate=dayOfMonth + "-" + (month + 1) + "-" + year;
                        String desiredFormat = "dd-MM-yyyy";
                        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                        SimpleDateFormat outputFormat = new SimpleDateFormat(desiredFormat);
                        try {
                            Date date = inputFormat.parse(inputDate);
                            String formattedDate = outputFormat.format(date);
                            textDateFraude.setText(formattedDate);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, year, month, day);
                pickerFraude.show();
            }
        });


        buttonDetectFraude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetectFraude=false;


                //recuperation donnee
                String Id_fraude = textIdFraude.getText().toString();
                String Date_fraude = textDateFraude.getText().toString();
                int litre;

                //ne pas oublier de rajouter le littre

                if (TextUtils.isEmpty(Id_fraude)) {
                    Toast.makeText(FraudeActivity.this, "Entrer l'id du vehicule", Toast.LENGTH_LONG).show();
                    textIdFraude.setError("L'id est requis");
                    textIdFraude.requestFocus();
                } else if (TextUtils.isEmpty(Date_fraude)) {
                    Toast.makeText(FraudeActivity.this, "Entrer la date ", Toast.LENGTH_LONG).show();
                    textDateFraude.setError("la date est requis");
                    textDateFraude.requestFocus();
                }


                TrajetFraude = FirebaseDatabase.getInstance().getReference().child("Trajet");
                TrajetFraude.orderByChild("date").equalTo(Date_fraude).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for (DataSnapshot snapshot : datasnapshot.getChildren()){
                            TrajetData data_fraude = snapshot.getValue(TrajetData.class);

                            if(data_fraude==null){
                                DetectFraude=true;
                            }else{

                            }

                            //if (data_fraude.fuelDebut-data_fraude. = data_fraude.fuelFin+
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText(FraudeActivity.this, databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


                if(DetectFraude==true){
                    textReponseFraude.setText("Fraude détécté");

                }







            }




        });







    }



}
