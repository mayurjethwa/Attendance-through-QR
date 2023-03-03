package com.example.scandie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.HashMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerview;
    DatabaseReference dbref,mref1,mref2;
    AlertDialog.Builder builder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerview = new ZXingScannerView(this);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(scannerview);

        dbref = FirebaseDatabase.getInstance().getReference("Subject");
        mref2 = FirebaseDatabase.getInstance().getReference("Student");

        builder = new AlertDialog.Builder(this);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerview.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result result) {
        String data = result.getText().toString();
        String data1[] = data.split("-");
//        String timeStamp = String.join(data1[1],data1[2],data1[3]);
        String sub = data1[0].toUpperCase().toString();
        String year = data1[1].toString();
        String month = data1[2].toString();
        String td = data1[3].toString();
        String timestamp = year+"-"+month+"-"+td;
        SessionManager sessionManager = new SessionManager(this);
        HashMap<String,String> userDetails = sessionManager.getUserDetailFromSession();
        String roll = userDetails.get(SessionManager.KEY_ROLLNO);
        String name = userDetails.get(SessionManager.KEY_NAME);

        HashMap<String,String > map = new HashMap<>();
        map.put("Name :",name);


        UserHelperClass2 helperClass = new UserHelperClass2(name);

        //Subject Node
       dbref = FirebaseDatabase.getInstance().getReference("Subject").child(sub).child(timestamp).child(roll);
//        SessionManager sessionManager = new SessionManager(this);

        dbref.setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {


                   dbref.child("name").setValue(name);
//                Toast.makeText(Scanner.this, "st1"+year+"st2"+month+"st3"+td, Toast.LENGTH_SHORT).show();

//              Intent i = new Intent(getApplicationContext(),Dashboard.class);
//
//                    startActivity(i);
//               finish();

                }
        });

        //Student Node Code

        mref1 = FirebaseDatabase.getInstance().getReference("Student").child(roll);
        mref2 = mref1.child("Subject").child(sub).child(timestamp);

        mref2.setValue("").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mref1.child("name").setValue(name);
                mref2.child("timestamp").setValue(timestamp);
//                mref2.child(timestamp);

                Intent i = new Intent(getApplicationContext(),MarkedSuccess.class);

                startActivity(i);
                finish();
            }
        });
        //Exit of student Node

        //Alert Dialog Message
//        builder.setTitle("ALert!!")
//                .setMessage("Attendance Marked Successfully")
//                .setMessage("Subject: "+sub+"/n Time: "+timestamp)
//                .setCancelable(true)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
//                        finish();
//                    }
//                });





    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerview.startCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerview.setResultHandler(this);
        scannerview.startCamera();
    }
}