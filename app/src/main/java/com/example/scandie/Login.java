package com.example.scandie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextView signup;
    Button login_btn;
    TextInputLayout login_roll, login_pass;

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        signup = findViewById(R.id.signup_tv);
        login_btn = findViewById(R.id.login_btn);
        login_roll = findViewById(R.id.login_rollno);
        login_pass = findViewById(R.id.login_password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
                finish();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validateRegno() || !validatePassword()){
                    return;
                }

                isUser();

//                Intent intent = new Intent(Login.this,StudentProfile.class);
//                startActivity(intent);
//                finish();
            }
        });
    }

    private Boolean validateRegno() {
        String value = login_roll.getEditText().getText().toString();
        String numberPattern = "[0-9]+";

        if (value.isEmpty()) {
            login_roll.setError("Field cannot be empty");
            return false;
        } else if(!value.matches(numberPattern)) {
            login_roll.setError("Field should contain only number");
            return true;
        } else {
            login_roll.setError(null);
            login_roll.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String value = login_pass.getEditText().getText().toString();
        String passPattern = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";
        String pass_length = ".{4,}";

        if (value.isEmpty()) {
            login_pass.setError("Field cannot be empty");
            return false;
        } else {
            login_pass.setError(null);
            login_pass.setErrorEnabled(false);
            return true;
        }
    }


    private void isUser() {

        final String userEnteredRegno = login_roll.getEditText().getText().toString();
        final String userEnteredPassword = login_pass.getEditText().getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rollno").child("rollno");

        Query checkUser = reference.orderByChild("roll").equalTo(userEnteredRegno);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    login_roll.setError(null);
                    login_roll.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredRegno).child("password").getValue(String.class);

                    if(passwordFromDB.equals(userEnteredPassword)){

                        login_roll.setError(null);
                        login_roll.setErrorEnabled(false);

                        String nameFromDB = snapshot.child(userEnteredRegno).child("name").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredRegno).child("email").getValue(String.class);
                        String regnoFromDB = snapshot.child(userEnteredRegno).child("regno").getValue(String.class);
                        String rollFromDB = snapshot.child(userEnteredRegno).child("roll").getValue(String.class);
                        String branchFromDB = snapshot.child(userEnteredRegno).child("branch").getValue(String.class);
                        String yearFromDB = snapshot.child(userEnteredRegno).child("year").getValue(String.class);

                        //Create Session
                        SessionManager sessionManager = new SessionManager(Login.this);
                        sessionManager.createLoginSession(nameFromDB,emailFromDB,regnoFromDB,rollFromDB,passwordFromDB,branchFromDB,yearFromDB);

                        startActivity(new Intent(getApplicationContext(),Dashboard.class));

//                        Intent intent = new Intent(getApplicationContext(),StudentProfile.class);
//                        intent.putExtra("name",nameFromDB);
//                        intent.putExtra("email",emailFromDB);
//                        intent.putExtra("regno",regnoFromDB);
//                        intent.putExtra("roll",rollFromDB);
//
//                        startActivity(intent);

                    }
                    else {
                        login_pass.setError("Wrong Password");
                        login_pass.requestFocus();
                    }

                }
                else {
                    login_roll.setError("No such user Exists");
                    login_roll.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}