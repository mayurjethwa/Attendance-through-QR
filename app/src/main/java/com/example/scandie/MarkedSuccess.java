package com.example.scandie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MarkedSuccess extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_marked_success);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent i = new Intent(MarkedSuccess.this,Dashboard.class);
                MarkedSuccess.this.startActivity(i);
                finish();
            }
        },2000);
    }
}