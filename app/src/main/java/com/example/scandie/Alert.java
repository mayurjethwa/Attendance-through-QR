package com.example.scandie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Alert extends AppCompatActivity {

    ImageButton home_btn,scan_btn,profile_btn;
    LinearLayout home_layout,scan_layout,profile_layout;
    ImageView logout_btn;
    TextView CN_pr,DWM_pr,IP_pr,SE_pr,TCS_pr;
    TextView CN_ab,DWM_ab,IP_ab,SE_ab,TCS_ab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SessionManager sessionManager = new SessionManager(this);


//        alert_card_text = findViewById(R.id.alert_card_text);
//        alert_card = findViewById(R.id.alert_card);
        CN_pr = findViewById(R.id.alert_card_CNpr);
        DWM_pr = findViewById(R.id.alert_card_DWMpr);
        TCS_pr = findViewById(R.id.alert_card_TCSpr);
        IP_pr = findViewById(R.id.alert_card_IPpr);
        SE_pr = findViewById(R.id.alert_card_SEpr);

        CN_ab = findViewById(R.id.alert_card_CNab);
        DWM_ab = findViewById(R.id.alert_card_DWMab);
        TCS_ab = findViewById(R.id.alert_card_TCSab);
        IP_ab = findViewById(R.id.alert_card_IPab);
        SE_ab = findViewById(R.id.alert_card_SEpr);




        home_btn = findViewById(R.id.home_btn);
        profile_btn = findViewById(R.id.profile_btn);
        logout_btn = findViewById(R.id.logout_btn);
        scan_btn = findViewById(R.id.scan_btn);

        home_layout = findViewById(R.id.home_layout);
        scan_layout = findViewById(R.id.scan_layout);
        profile_layout = findViewById(R.id.profile_layout);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My Notification","My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudentProfile.class);
                startActivity(intent);
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent);
            }
        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i =new Intent(getApplicationContext(),ScannerDashboard.class);
//                startActivity(i);
                startActivity(new Intent(getApplicationContext(),Scanner.class));

            }
        });

        home_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }
        });

        scan_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Scanner.class));
            }
        });

        profile_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudentProfile.class);
                startActivity(intent);
            }
        });

        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();
        String roll = userDetails.get(SessionManager.KEY_ROLLNO);
        String sub[] = {"CN","TCS","IP","DWM","SE"};

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rollno").child("rollno");
//        DatabaseReference ref2 = reference.child("Subject").child("CN");

        Query checkUser = reference.child(roll);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int per_CN = Integer.parseInt(snapshot.child("Subject").child(sub[0]).getValue().toString());
                int per_TCS = Integer.parseInt(snapshot.child("Subject").child(sub[1]).getValue().toString());
                int per_IP = Integer.parseInt(snapshot.child("Subject").child(sub[2]).getValue().toString());
                int per_DWM = Integer.parseInt(snapshot.child("Subject").child(sub[3]).getValue().toString());
                int per_SE = Integer.parseInt(snapshot.child("Subject").child(sub[4]).getValue().toString());

                NotificationCompat.Builder builder = new NotificationCompat.Builder(Alert.this,"My Notifications");


                NotificationManagerCompat managerCompat;


                if (per_CN<=75){
                    CN_ab.setVisibility(View.VISIBLE);
                    CN_ab.setText("Dear Student, Your Attendance for "+sub[0]+" Your attendance is lower");
                    CN_pr.setVisibility(View.GONE);

                    builder.setContentTitle("My Title");
                    builder.setContentText("Hello from Scandie");
                    builder.setSmallIcon(R.drawable.login_logo);
                    builder.setAutoCancel(true);
                    managerCompat = NotificationManagerCompat.from(Alert.this);
                    managerCompat.notify(1,builder.build());
                }else {
                    CN_pr.setText("Dear Student, Your Attendance for "+sub[0]+" greater than 75");
                }


                if (per_TCS<=75){
                    TCS_ab.setVisibility(View.VISIBLE);
                    TCS_ab.setText("Dear Student, Your Attendance for "+sub[1]+" Your attendance is lower");
                    TCS_pr.setVisibility(View.GONE);
                    builder.setContentTitle("My Title");
                    builder.setContentText("Hello from Scandie");
                    builder.setSmallIcon(R.drawable.login_logo);
                    builder.setAutoCancel(true);
                    managerCompat = NotificationManagerCompat.from(Alert.this);
                    managerCompat.notify(1,builder.build());
                }else {
                    TCS_pr.setText("Dear Student, Your Attendance for "+sub[1]+" greater than 75");
                }


                if (per_IP<=75){
                    IP_ab.setVisibility(View.VISIBLE);
                    IP_ab.setText("Dear Student, Your Attendance for "+sub[2]+" Your attendance is lower");
                    IP_pr.setVisibility(View.GONE);
                    builder.setContentTitle("My Title");
                    builder.setContentText("Hello from Scandie");
                    builder.setSmallIcon(R.drawable.login_logo);
                    builder.setAutoCancel(true);
                    managerCompat = NotificationManagerCompat.from(Alert.this);
                    managerCompat.notify(1,builder.build());
                }else {
                    IP_pr.setText("Dear Student, Your Attendance for "+sub[2]+" greater than 75");
                }


                if (per_DWM<=75){
                    DWM_ab.setVisibility(View.VISIBLE);
                    DWM_ab.setText("Dear Student, Your Attendance for "+sub[3]+" Your attendance is lower");
                    DWM_pr.setVisibility(View.GONE);
                    builder.setContentTitle("My Title");
                    builder.setContentText("Hello from Scandie");
                    builder.setSmallIcon(R.drawable.login_logo);
                    builder.setAutoCancel(true);
                    managerCompat = NotificationManagerCompat.from(Alert.this);
                    managerCompat.notify(1,builder.build());
                }else {
                    DWM_pr.setText("Dear Student, Your Attendance for "+sub[3]+" greater than 75");
                }


                if (per_SE<=75){
                    SE_ab.setVisibility(View.VISIBLE);
                    SE_ab.setText("Dear Student, Your Attendance for "+sub[4]+" Your attendance is lower");
                    SE_pr.setVisibility(View.GONE);
                    builder.setContentTitle("My Title");
                    builder.setContentText("Hello from Scandie");
                    builder.setSmallIcon(R.drawable.login_logo);
                    builder.setAutoCancel(true);
                    managerCompat = NotificationManagerCompat.from(Alert.this);
                    managerCompat.notify(1,builder.build());
                }else {
                    SE_pr.setText("Dear Student, Your Attendance for "+sub[4]+" greater than 75");
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });







        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUserFromSession();
                Intent intent = new Intent(Alert.this, Login.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                Toast.makeText(Alert.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
        });
    }
}