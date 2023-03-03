package com.example.scandie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView login_tv;
    TextInputLayout reg_name, reg_email, reg_regno, reg_rollno, reg_password, reg_confirmpass;
    Button register_btn;
    AutoCompleteTextView reg_branch,reg_year;
    FirebaseDatabase firebase;
    DatabaseReference reference,reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        String[] Branch = new String[]{"CM", "IT", "CYSE", "AI-DS", "ECS", "EXTC"};
        String[] Year = new String[]{"FE", "SE", "TE", "BE"};

        login_tv = findViewById(R.id.login_tv);
        reg_name = findViewById(R.id.name);
        reg_email = findViewById(R.id.email);
        reg_regno = findViewById(R.id.register_regno);
        reg_rollno = findViewById(R.id.rollno);
        reg_branch = findViewById(R.id.branch);
        reg_year = findViewById(R.id.year);
        reg_password = findViewById(R.id.regpassword);
        reg_confirmpass = findViewById(R.id.confirmPassword);
        register_btn = findViewById(R.id.register_btn);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.dropdown,Branch);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this,R.layout.dropdown,Year);

        reg_branch.setAdapter(adapter);
        reg_year.setAdapter(adapter1);

        reg_branch.setOnItemSelectedListener(this);
        reg_year.setOnItemSelectedListener(this);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase = FirebaseDatabase.getInstance();
                reference = firebase.getReference("rollno");




                if(!validateName() || !validateEmail() || !validateRegno() || !validateRollno() || !validatePassword() || !validateConfirmPass()){
                    return;
                }

                String sub[] = {"CN","SE","TCS","IP","DWM"};
                //Get all the values
                String name = reg_name.getEditText().getText().toString();
                String email = reg_email.getEditText().getText().toString();
                String regno = reg_regno.getEditText().getText().toString();
                String rollno = reg_rollno.getEditText().getText().toString();
                String password = reg_password.getEditText().getText().toString();
                String conf_pass = reg_confirmpass.getEditText().getText().toString();
                String branch = reg_branch.getText().toString();
                String year = reg_year.getText().toString();






                UserHelperClass helperClass = new UserHelperClass(name, email, regno, rollno, password, conf_pass,branch,year);

                reference.child("rollno").child(rollno).setValue(helperClass);
                reference.child("rollno").child(rollno).child("Subject").child(sub[0]).setValue("0");
                reference.child("rollno").child(rollno).child("Subject").child(sub[1]).setValue("0");
                reference.child("rollno").child(rollno).child("Subject").child(sub[2]).setValue("0");
                reference.child("rollno").child(rollno).child("Subject").child(sub[3]).setValue("0");
                reference.child("rollno").child(rollno).child("Subject").child(sub[4]).setValue("0");






                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();

            }
        });

        login_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private Boolean validateName() {
        String value = reg_name.getEditText().getText().toString();
        String namePattern = "[A-Z][a-z]";

        if (value.isEmpty()) {
            reg_name.setError("Field cannot be empty");
            return false;
        } else if (value.matches(namePattern)) {
            reg_name.setError("Field should contain only text");
            return false;
        } else {
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String value = reg_email.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (value.isEmpty()) {
            reg_email.setError("Field cannot be empty");
            return false;
        } else if (!value.matches(emailPattern)) {
            reg_email.setError("Invalid email address");
            return false;
        } else {
            reg_email.setError(null);
            reg_email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateRegno() {
        String value = reg_regno.getEditText().getText().toString();
        String numberPattern = "[0-9]+";

        if (value.isEmpty()) {
            reg_regno.setError("Field cannot be empty");
            return false;
        } else if(!value.matches(numberPattern)) {
            reg_regno.setError("Field should contain only number");
            return true;
        } else {
            reg_regno.setError(null);
            reg_regno.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateRollno() {
        String value = reg_rollno.getEditText().getText().toString();
        String numberPattern = "[0-9]+";

        if (value.isEmpty()) {
            reg_rollno.setError("Field cannot be empty");
            return false;
        } else if(!value.matches(numberPattern)) {
            reg_rollno.setError("Field should contain only number");
            return true;
        } else {
            reg_rollno.setError(null);
            reg_rollno.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String value = reg_password.getEditText().getText().toString();
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
            reg_password.setError("Field cannot be empty");
            return false;
        } else  if(!value.matches(pass_length)){
            reg_password.setError("Password must be at least 4 characters");
            return false;
        } else if (!value.matches(passPattern)) {
            reg_password.setError("Password is too weak");
            return false;
        } else {
            reg_password.setError(null);
            reg_password.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateConfirmPass() {
        String pass_value = reg_password.getEditText().getText().toString();
        String value = reg_confirmpass.getEditText().getText().toString();
        String passPattern = "^" +
                "(?=.*[0-9])" +  //digit must occur at least once
                "(?=.*[a-z])" +  //a lower case alphabet must occur at least once.
                "(?=.*[A-Z])" +  //represents an upper case alphabet that must occur at least once.
                "(?=.*[a-zA-Z])" + //represents any letters
                "(?=.*[@#$%^&-+=()])" +  //represents a special character that must occur at least once.
                "(?=\\\\S+$)" +  //white spaces don’t allowed in the entire string.
                ".{8,20}" +  //represents at least 8 characters and at most 20 characters.
                "$";  //represents the end of the string.

        if (value.isEmpty()) {
            reg_confirmpass.setError("Field cannot be empty");
            return false;
        } else if (!value.equals(pass_value)) {
            reg_confirmpass.setError("Password is not matching");
            return false;
        } else {
            reg_password.setError(null);
            reg_password.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        Toast.makeText(this, "Select the item", Toast.LENGTH_SHORT).show();
    }

}