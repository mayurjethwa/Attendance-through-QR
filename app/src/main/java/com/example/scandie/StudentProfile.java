package com.example.scandie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class StudentProfile extends AppCompatActivity {

    TextInputLayout profile_name,profile_email,profile_regno,profile_rollno,profile_branch,profile_year;
    ImageButton home_btn,scan_btn,profile_btn;
    LinearLayout home_layout,scan_layout,profile_layout;
    ImageView logout_btn,alert_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_profile);

        //Hooks
        profile_name = findViewById(R.id.profile_name);
        profile_email = findViewById(R.id.profile_email);
        profile_regno = findViewById(R.id.profile_regno);
        profile_rollno = findViewById(R.id.profile_rollno);
        profile_branch = findViewById(R.id.profile_branch);
        profile_year = findViewById(R.id.profile_year);
//        update_btn = findViewById(R.id.update_btn);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();

        String name = userDetails.get(SessionManager.KEY_NAME);
        String email = userDetails.get(SessionManager.KEY_EMAIL);
        String regno = userDetails.get(SessionManager.KEY_REGNO);
        String roll = userDetails.get(SessionManager.KEY_ROLLNO);
        String branch = userDetails.get(SessionManager.KEY_BRANCH);
        String year = userDetails.get(SessionManager.KEY_YEAR);

        profile_name.getEditText().setText(name);
        profile_email.getEditText().setText(email);
        profile_regno.getEditText().setText(regno);
        profile_rollno.getEditText().setText(roll);
        profile_branch.getEditText().setText(branch);
        profile_year.getEditText().setText(year);

//        update_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(StudentProfile.this,UpdateProfile.class);
//                Toast.makeText(StudentProfile.this, "Update Your Profil", Toast.LENGTH_SHORT).show();
//                startActivity(i);
//            }
//        });

        home_btn = findViewById(R.id.home_btn);
        profile_btn = findViewById(R.id.profile_btn);
        scan_btn = findViewById(R.id.scan_btn);
        logout_btn = findViewById(R.id.logout_btn);
        alert_icon = findViewById(R.id.alert_icon);

        home_layout = findViewById(R.id.home_layout);
        scan_layout = findViewById(R.id.scan_layout);
        profile_layout = findViewById(R.id.profile_layout);

        profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),StudentProfile.class);
                startActivity(intent);
            }
        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Scanner.class));
            }
        });

        home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Dashboard.class);
                startActivity(intent);
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



        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUserFromSession();
                Intent intent = new Intent(StudentProfile.this, Login.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                Toast.makeText(StudentProfile.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();

            }
        });


        //SHowAllData
//        showAllUserData();

    }

//    private void showAllUserData() {
//
//        Intent intent = getIntent();
//        String user_name = intent.getStringExtra("name");
//        String user_email = intent.getStringExtra("email");
//        String user_regno = intent.getStringExtra("regno");
//        String user_roll = intent.getStringExtra("roll");
//
//        profile_name.getEditText().setText(user_name);
//        profile_email.getEditText().setText(user_email);
//        profile_regno.getEditText().setText(user_regno);
//        profile_rollno.getEditText().setText(user_roll);
//    }
}