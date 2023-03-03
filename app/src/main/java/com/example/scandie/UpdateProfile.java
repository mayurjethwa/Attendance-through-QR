package com.example.scandie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {

    TextInputLayout update_rollno,update_year,update_password;
    Button updated_profile_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_student_profile);

        update_rollno = findViewById(R.id.update_rollno);
        update_year = findViewById(R.id.update_year);
        update_password = findViewById(R.id.update_password);
        updated_profile_btn = findViewById(R.id.updated_profile_btn);

        SessionManager sessionManager = new SessionManager(this);
        HashMap<String, String> userDetails = sessionManager.getUserDetailFromSession();

        updated_profile_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                    String roll = userDetails.get(SessionManager.KEY_ROLLNO);
                    String year = userDetails.get(SessionManager.KEY_YEAR);
                    String password = userDetails.get(SessionManager.KEY_PASSWORD);

                    update_rollno.getEditText().setText(roll);
                    update_year.getEditText().setText(year);
                    update_password.getEditText().setText(password);
                    Intent intent = new Intent(UpdateProfile.this, StudentProfile.class);
                    startActivity(intent);
                    Toast.makeText(UpdateProfile.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        });





    }
}