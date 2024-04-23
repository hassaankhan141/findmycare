package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class One_TimeOTP extends AppCompatActivity {
    EditText otpNumberOne,getOtpNumberTwo,getOtpNumberThree,getOtpNumberFour,getOtpNumberFive,otpNumberSix;
    TextView numview;
    TextView resendOTP;
    Button verifyPhone;
    Boolean otpValid = true;
    FirebaseAuth firebaseAuth;
    PhoneAuthCredential phoneAuthCredential;
    PhoneAuthProvider.ForceResendingToken token;
    String verificationId;
    String  phone,email1,password1,names;
    ProgressBar progressBar;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_otp);

        progressDialog=new ProgressDialog(One_TimeOTP.this);
        Intent data = getIntent();
        phone = data.getStringExtra("phone");
        email1 = data.getStringExtra("email");
        password1 = data.getStringExtra("password");
        names = data.getStringExtra("name");



        progressBar = (ProgressBar) findViewById(R.id.progressBarVerify) ;




        firebaseAuth = FirebaseAuth.getInstance();



        otpNumberOne = findViewById(R.id.etC1);
        getOtpNumberTwo = findViewById(R.id.etC2);
        getOtpNumberThree = findViewById(R.id.etC3);
        getOtpNumberFour = findViewById(R.id.etC4);
        getOtpNumberFive = findViewById(R.id.etC5);
        otpNumberSix = findViewById(R.id.etC6);
        numview = (TextView)findViewById(R.id.tvMobile);

        verifyPhone = findViewById(R.id.btnVerify);
        resendOTP = findViewById(R.id.textView7);


        numview.setText(phone);


        EditText[] otpTextViews = {otpNumberOne, getOtpNumberTwo, getOtpNumberThree,getOtpNumberFour,getOtpNumberFive,otpNumberSix};

        for (EditText  currTextView : otpTextViews) {
            currTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    nextTextView().requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

                public EditText nextTextView() {
                    int i;
                    for (i = 0; i < otpTextViews.length - 1; i++) {
                        if (otpTextViews[i] == currTextView)
                            return otpTextViews[i + 1];
                    }
                    return otpTextViews[i];
                }
            });
        }

        verifyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                validateField(otpNumberOne);
                validateField(getOtpNumberTwo);
                validateField(getOtpNumberThree);
                validateField(getOtpNumberFour);
                validateField(getOtpNumberFive);
                validateField(otpNumberSix);


                if (otpValid){
                    // send otp to the user
                    String otp = otpNumberOne.getText().toString() + getOtpNumberTwo.getText().toString() + getOtpNumberThree.getText().toString() + getOtpNumberFour.getText().toString() +
                            getOtpNumberFive.getText().toString() + otpNumberSix.getText().toString();


                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

                    verifyAuthentication(credential);


                }



            }
        });







        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationId = s;
                token = forceResendingToken;
                resendOTP.setVisibility(View.GONE);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendOTP.setVisibility(View.VISIBLE);
                Toast.makeText(One_TimeOTP.this, "OTP Verification Time Out."+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuthentication(phoneAuthCredential);
                resendOTP.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Toast.makeText(One_TimeOTP.this, "OTP Verification Failed."+e.toString(), Toast.LENGTH_SHORT).show();


            }
        };

        sendOTP(phone);


        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOTP(phone);
            }
        });

    }

    public void sendOTP(String phoneNumber){


        PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions
                        .newBuilder(FirebaseAuth.getInstance())
                        .setActivity(this)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(mCallbacks)
                        .build());
    }

    public void resendOTP(String phoneNumber){


        PhoneAuthProvider.verifyPhoneNumber(
                PhoneAuthOptions
                        .newBuilder(FirebaseAuth.getInstance())
                        .setActivity(this)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setCallbacks(mCallbacks)
                        .build());
    }


    public void validateField(EditText field){
        if(field.getText().toString().isEmpty()){
            field.setError("Required");
            otpValid = false;
        }else {
            otpValid = true;
        }
    }


    public void verifyAuthentication(PhoneAuthCredential credential){

        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.createUserWithEmailAndPassword(email1,password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(email1,password1,phone,FirebaseAuth.getInstance().getCurrentUser().getUid());

                            FirebaseDatabase.getInstance().getReference("users")
                                    .child("name")
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){


                                                firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                    @Override
                                                    public void onSuccess(AuthResult authResult) {
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(One_TimeOTP.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                        Intent phone = new Intent(One_TimeOTP.this,MainActivity.class);
                                                        startActivity(phone);
                                                        onStartNewActivity();

                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(One_TimeOTP.this, "OTP is Invalid", Toast.LENGTH_SHORT).show();
                                                    }
                                                });


                                            }
                                            else{
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(One_TimeOTP.this,"Registeration Failed",Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(One_TimeOTP.this,"The email is already in use by another account" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    public void finish() {
        super.finish();
        onLeaveThisActivity();
    }

    protected void onStartNewActivity() {
        overridePendingTransition(R.anim.slideup, R.anim.slidedown);
    }

    protected void onLeaveThisActivity() {
        overridePendingTransition(R.anim.slide_down_reverse, R.anim.slide_up_reverse);
    }




    }
