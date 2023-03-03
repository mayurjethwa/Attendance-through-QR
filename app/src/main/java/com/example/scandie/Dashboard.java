package com.example.scandie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Range;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Dashboard extends AppCompatActivity {

    ImageButton home_btn,scan_btn,profile_btn;
    LinearLayout home_layout,scan_layout,profile_layout;
    ImageView logout_btn,alert_icon;
    TextView foot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SessionManager sessionManager = new SessionManager(this);


        alert_icon = findViewById(R.id.alert_icon);
        foot = findViewById(R.id.foot);

        home_btn = findViewById(R.id.home_btn);
        profile_btn = findViewById(R.id.profile_btn);
        logout_btn = findViewById(R.id.logout_btn);
        scan_btn = findViewById(R.id.scan_btn);

        home_layout = findViewById(R.id.home_layout);
        scan_layout = findViewById(R.id.scan_layout);
        profile_layout = findViewById(R.id.profile_layout);

        String unicode = "\uD83C\uDDEE\uD83C\uDDF3";
        foot.setText("Made in India "+unicode);


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

        alert_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Alert.class));
            }
        });

//        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();
//        String roll = userDetails.get(SessionManager.KEY_ROLLNO);
//
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rollno").child("rollno");
//
//
//        Query checkUser = reference.child(roll);
//
//        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                int per = Integer.parseInt(snapshot.child("Subject").child("CN").getValue().toString());
//
//                if (per<=75){
//                    alert_tv.setText("Your attendance is lower");
//                }else{
//                    alert_tv.setText("Hello");
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });







        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUserFromSession();
                Intent intent = new Intent(Dashboard.this, Login.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                Toast.makeText(Dashboard.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
        });







    }
}