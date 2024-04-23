package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class Splash_screen extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        mAuth = FirebaseAuth.getInstance();


        final Handler handler = new Handler();

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            String uid = mAuth.getCurrentUser().getUid();
            if (mAuth.getCurrentUser()!=null){
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference uidRef = rootRef.child("Users").child(uid);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()){
                            User  user=dataSnapshot.getValue(User.class);
                            Utils.userModel=user;
                            if (dataSnapshot.child("user_type").getValue(String.class).equals("Social Media Influencer")) {

                                Intent intent = new Intent(Splash_screen.this, doctor_dashboard.class);
                                startActivity(intent);

                                //   Toast.makeText(SplashActivity.this,"Logged In Successfully",Toast.LENGTH_LONG).show();
                                finish();


                            } else if (dataSnapshot.child("user_type").getValue(String.class).equals("Brand")) {

                                Intent intent = new Intent(Splash_screen.this, patient_dashboard.class);
                                startActivity(intent);
                                //      Toast.makeText(SplashActivity.this,"Logged In Successfully",Toast.LENGTH_LONG).show();
                                finish();

                            }else if (dataSnapshot.child("user_type").getValue(String.class).equals("admin")) {

                                Intent intent = new Intent(Splash_screen.this, admin_dashboard.class);
                                startActivity(intent);
                                //      Toast.makeText(SplashActivity.this,"Logged In Successfully",Toast.LENGTH_LONG).show();
                                finish();

                            }else {
                                Intent intent = new Intent(Splash_screen.this, MainActivity.class);
                                startActivity(intent);
                                //      Toast.makeText(SplashActivity.this,"Logged In Successfully",Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                        else {
                            Intent intent = new Intent(Splash_screen.this, MainActivity.class);
                            startActivity(intent);
                            //      Toast.makeText(SplashActivity.this,"Logged In Successfully",Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }


                };
                uidRef.addListenerForSingleValueEvent(valueEventListener);
            }else{
                startActivity(new Intent(Splash_screen.this, MainActivity.class));
//                            startCircularReveal();
                finish();
            }
        }
        else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {


                    startActivity(new Intent(Splash_screen.this, MainActivity.class));
                    finish();


                }

            }, 1500);

        }
    }
}