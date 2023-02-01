package com.example.demo_drive_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.os.Handler;
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.HalfGauge;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class  MainActivity extends AppCompatActivity {


    String IP = "tcp://172.20.10.2:1883";
    String topicSub = "dataOBD2/";
    MqttAndroidClient client;
    String item ="";
    Context context;
    String rpm ="0";
    String speed ="0";
    String fuel ="0";
    String time ="0";
    String pedal ="0";
    String air ="0";

    double d_pedal =0;
    double d_time=0;
    double i_speed=0;
    double i_rpm=0;
    double i_fuel=0;
    double d_air=0;
    double MoyFl = 0;

    Boolean Init, ActivTimer, Bfin;              //Permet d'initaliser certaine variable comme par exemple l'essence dès le debut ar preleve tt les 10 min
    int CptFuel, CptConso, CptFin, CptScore,CptfuelDebut;//compteur de prelevement de l'essence (pour prelever tt les 10 mins)
    String Score;
    private TextView text_Vitesse, text_Distance, text_Prix, text_ConsoPrix, text_air,text_consoInstant,text_fuelDebut,text_rpm;  //affichage de la distance et vitesse

    View Colordrive;
    double prixTotal = 0.0;
    private ProgressBar progress_fuel;
    private TextView text_fuel;

    int r=3; //variable d'incrementation des liste
    Double distanceM,distanceKm,distanceAff,distanceTotM,distanceTotKm,distanceTotAff,distanceTot,LitreCarb,LitFl100,LitFl,PrixFl,Conso30,fuelDebut ;
    int AccelerationTime;
    int highRpmTime;
    private boolean running;
    private boolean condition = false;

    private View backgroundView;
    private Handler handler = new Handler();
    int Text;
    private TextView Tanalyse;

    private Button buttonFin, buttonChart;

    List<Double> LRPM = new ArrayList<>();
    List<Double> LFuel = new ArrayList<>();
    List<Double> LFuel30 = new ArrayList<>();
    List<Double> LPedal = new ArrayList<>();
    List<Double> LSpeed = new ArrayList<>();
    List<Double> LTime = new ArrayList<>();
    List<Double> LAir = new ArrayList<>();
    List<Double> LconsoMoy = new ArrayList<>();

    // UNIT
    String unit_km = " km";
    String unit_ekm = " €/km";
    String unit_lkm = " L/100km";
    String unit_l = " L";
    String unit_e = " €";
    String unit_p = " %";


    private static final String TAG = "MainActivity";

    DatabaseReference Currenttrajet;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonFin=findViewById(R.id.BtnEndTrip);
        Colordrive =(View) findViewById(R.id.ViewDrive);

        buttonFin.setEnabled(false);

        ActivTimer=false;
        Bfin=false;

        AccelerationTime=0;
        highRpmTime=0;
        distanceTot=0.0;
        distanceM=0.0;
        distanceKm=0.0;
        distanceAff=0.0;
        distanceTotM=0.0;
        distanceTotKm=0.0;
        distanceTotAff=0.0;
        Conso30=0.0;
        Init= true;
        CptFuel=0;
        CptConso=0;
        CptFin=0;
        CptfuelDebut=0;
        CptScore=0;
        MoyFl=0.0;
        LitFl100=0.0;
        LitFl=0.0;
        PrixFl=0.0;
        LitreCarb=0.0;





        backgroundView = findViewById(R.id.RlVar1);
        text_Distance=findViewById(R.id.ViewDistance);
        text_Prix=findViewById(R.id.ViewPrice);
        text_ConsoPrix=findViewById(R.id.ViewCons);



        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(),IP, clientId);

//right hereoh okeyy, i think my job is done right? yees thanks a lot no no prbomlem
        //try {
          //  IMqttToken token = client.connect();
//            token.setActionCallback(new IMqttActionListener() {
//                @Override
//                public void onSuccess(IMqttToken asyncActionToken) {
//                    Toast.makeText(MainActivity.this,"connected",Toast.LENGTH_LONG).show();
//                    setSubscription();
//                }
//
//                @Override
//                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Toast.makeText(MainActivity.this,"connection failed",Toast.LENGTH_LONG).show();
//                }
//            });
       // } catch (Exception e) {
         //   Toast.makeText(MainActivity.this,"connection failed "+e.getMessage().toString() ,Toast.LENGTH_LONG).show();
           // e.printStackTrace();
       // }

//        client.setCallback(new MqttCallback() {
//            @Override
//            public void connectionLost(Throwable cause) {
//            }
//            @Override
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
//
//                item= message.toString();
//                JSONObject objobd = new JSONObject(item);
//                rpm = objobd.getString("1_rpm");
//                speed = objobd.getString("2_speed");
//                fuel = objobd.getString("3_fuel");
//                time = objobd.getString("4_time");
//                pedal = objobd.getString("5_pedal");
//                air = objobd.getString("6_air");
//
//                d_pedal = Double.parseDouble(pedal);
//                d_time = Double.parseDouble(time);
//                i_rpm = Double.parseDouble(rpm);
//                i_speed =Double.parseDouble(speed);
//                i_fuel = Double.parseDouble(fuel);
//                d_air = Double.parseDouble(air);
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//
//            }
//        });

             timer = new Timer();
            // Schedule a task to run every 500 milliseconds
            Currenttrajet = FirebaseDatabase.getInstance().getReference();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {


                    //ligne à ajouter

                    /*LPedal.add(d_pedal);
                    LTime.add(d_time);
                    LFuel.add(i_fuel);
                    LRPM.add(i_rpm);
                    LSpeed.add(i_speed);
                    LAir.add(d_air);*/

                    Random random = new Random();

                        double num = random.nextDouble()*100 + 1.0;
                        LFuel.add(num);
                        LRPM.add(num);
                        LSpeed.add(num);
                        LAir.add(num);



                    if(LSpeed.size()>10){
                        ActivTimer=true;
                    }
                    if(ActivTimer) {
                        //initialisation -->valeur Fuel car prelever tt les 10 min
                        if (Init) {
                            fuelDebut = Double.valueOf(LFuel.get(4));
                        }

                        if ((LRPM.get(LRPM.size() - 1) > 2200) || (LRPM.get(LRPM.size() - 1) < 1500)) {     //3000
                            highRpmTime = highRpmTime + 1;

                        } else if (Math.abs(((LSpeed.get(LSpeed.size() - 1) * 0.277778) - (LSpeed.get(LSpeed.size() - 2) * 0.277778)) / 1) > 6) {   //detection d'un freinage brusque
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Colordrive.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EB6013")));
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Colordrive.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00B050")));
                                    CptScore++;
                                }
                            });
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                double v1 = LSpeed.get(LSpeed.size() - 2) * 0.277778;
                                Log.d("v1", Double.toString(v1));//on cherche la vitesse en m/s
                                double v2 = LSpeed.get(LSpeed.size() - 1) * 0.277778;
                                distanceM = ((v1 + v2) / 2) * (1.0/3.6);
                                distanceTotM = distanceTotM + distanceM;  //distance en metre
                                distanceTotKm = distanceTotM / 1000;
                                distanceTotAff = Double.valueOf((distanceTotKm * 10) / 10);

                                double air = LAir.get(LAir.size()-1) * 1.0/14.6 *1.0/840.0 ;
                                double vair = LSpeed.get(LSpeed.size()-1)  * 1.0/3600.0;
                                double consoInst = air/vair ; //     L par km
                                double conso100km = consoInst*100.0;
                                double prixLitre = 2.0;
                                double prixconso = prixLitre * conso100km ;  // € par 100km
                                prixTotal = prixTotal+ air * prixLitre ;

                                Log.d("df",Double.toString(prixTotal));


                                text_ConsoPrix.setText(new DecimalFormat("##.#").format(prixconso)+unit_e);
                                text_Prix.setText(new DecimalFormat("##.#").format(prixTotal) );
                                text_Distance.setText(new DecimalFormat("##.#").format(distanceTotAff) + unit_km);


                                LconsoMoy.add(conso100km);


                                Log.d("Distancebugdenassim", Double.toString(distanceTotM));

                                //mise a jours compteur rpm

                            }
                        });

                        //--------------------Consomation CARBURANT--- PRIX --------------------------------------------------



                        if (LRPM.get(LRPM.size() - 1) == 0 || CptFin > 8) {   //      Marche pas si start and stop Si le conducteur à au moin conduit 5 min et qu'il est à l'arret


                            Log.d("MainActivity", "fin trajet");



                            ActivTimer = false;    //desactivation du timer
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    buttonFin.setEnabled(true);

                                    buttonFin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            try {
                                                if (timer!=null) {
                                                    timer.cancel();
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
// is working?yes okay what you want to do the timer here?beacause the timer make the time to take data in array List
                                            Score = String.valueOf(CptScore / (CptFin) * 10000);
                                            Log.d("MainActivity", "Le score" + Score);



                                            //enregistrement dans base de donnée

                                            //creation d'un indentifiant unique
                                            String TrajetID = UUID.randomUUID().toString();

                                            //recuperation de la date actuelle du telephone
                                            Calendar c = Calendar.getInstance();
                                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                            String formattedDate = df.format(c.getTime());

                                            //calcul de fin
                                            Double fuelFin=LFuel.get(LFuel.size()-1);
                                            int n =LconsoMoy.size();

                                            double sommeConso = 0.0;
                                            for (int i=0; i<n;i++){
                                                sommeConso+=LconsoMoy.get(i);
                                            }
                                            double MoyConso = sommeConso/n;

                                            //prix1km


                                            Double Prix1km= prixTotal/distanceTotAff;

                                            //device telephone

                                            TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                                            //String deviceId = telephonyManager.getDeviceId();


                                            //recuperation de l'email de l'utilisateur
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            String email = user.getEmail();




                                            Currenttrajet.child("Trajet").child(TrajetID).child("trajetId").setValue(String.valueOf(TrajetID));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("EmailUser").setValue(String.valueOf(email));
                                            //Currenttrajet.child("Trajet").child(TrajetID).child("TelId").setValue(String.valueOf(deviceId));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("dateTrajet").setValue(String.valueOf(formattedDate));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("score").setValue(String.valueOf(Score));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("fuelDebut").setValue(String.valueOf(fuelDebut.toString()));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("fuelFin").setValue(String.valueOf(fuelFin.toString()));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("dureeTrajet").setValue(String.valueOf(CptFin));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("ConsoMoyenne100km").setValue(String.valueOf(MoyConso));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("DistanceTot").setValue(String.valueOf(distanceTotAff.toString()));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("PrixTrajet").setValue(String.valueOf(prixTotal));
                                            Currenttrajet.child("Trajet").child(TrajetID).child("Prix1km").setValue(String.valueOf(Prix1km));





                                            Intent intent = new Intent(MainActivity.this, EndTrajetActivity.class);
                                            intent.putExtra("Score", String.valueOf(Score));
// one more thing if you pass here long it will go to next activity as string if you donot convert it into string
                                            intent.putExtra("KmTot",String.valueOf(distanceTotAff));
                                            intent.putExtra("PrixTot",String.valueOf(prixTotal));
                                            startActivity(intent);



                                        }
                                    });
                                }
                            });


                        }


                        r++;
                        CptFuel++;
                        CptConso++;
                        CptFin++;
                        CptfuelDebut++;
                        Log.d("MainActivity", String.valueOf(r));


                    }
                    Log.d("MainActivity", "cptFin vaut" + String.valueOf(CptFin));
                }

            }, 0, 1000);




        }


    private void setSubscription() {
        try {
            client.subscribe(topicSub, 0);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
// now crashes?yes it crash this is when we try to make connection