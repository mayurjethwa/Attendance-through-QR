//package com.example.scandie;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.HashMap;
//
//public class TapActivity extends AppCompatActivity {
//
//    Button tap_btn;
//    DatabaseReference dbref;
//    DatabaseReference mref;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tap);
//
//
//
//        tap_btn = findViewById(R.id.tap_btn);
//        SessionManager sessionManager = new SessionManager(this);
//
//        String data = getIntent().getStringExtra("data");
//        String name = getIntent().getStringExtra("name");
//        String roll = getIntent().getStringExtra("roll");
//
//
//        UserHelperClass2 userHelperClass = new UserHelperClass2(name);
//
//        String data1[] = data.split("-");
//
//        String data2 = String.join(data1[1],data1[2],data1[3]);
//        dbref = FirebaseDatabase.getInstance().getReference("Subject").child(data1[0]);
//        Query checkUser = dbref;
//
//
//        tap_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.hasChild(""+data1[0])){
//                            mref =  dbref.push().child(data2);
//                        }
//                        else {
//                            dbref = FirebaseDatabase.getInstance().getReference("Subject").child(data1[0]);
//                            mref =  dbref.child(data2);
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
////                mref =  dbref.child(data2);
//
//
//
//                mref.push().setValue(userHelperClass);
////                dbref.child(roll).child("name").setValue(name);
//
//                Toast.makeText(TapActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(getApplicationContext(),Dashboard.class);
//                startActivity(i);
//
//            }
//        });
//
//    }
//}
//
//
//
//
////1.split string
////2.subject name and timestamp
////3.above using data insert roll and name in timestamp
////4.Studnr->roll->subject->subname->timestamp
