package com.project.myapplication;

import static com.project.myapplication.Utils.context;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class patient_dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_dashboard);
    }

    public void logOut(View view) {
        startActivity(new Intent(patient_dashboard.this,MainActivity.class));
        Toast.makeText(getApplicationContext(),"Dear Patient,You have been logged out",Toast.LENGTH_SHORT).show();

    }
}