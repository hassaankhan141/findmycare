package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Register extends AppCompatActivity {


    private TextView login,register;
    private ImageView tit;
    private TextInputLayout mail, password,fullname;
    EditText  number;
    private Button but;

    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        mAuth = FirebaseAuth.getInstance();
        login=findViewById(R.id.banner4);
        register =  findViewById(R.id.register_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this,MainActivity.class));
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regist();
            }
        });

        mail = findViewById(R.id.mail);
        password = findViewById(R.id.pass);
        number =  findViewById(R.id.phn);
        fullname =  findViewById(R.id.full_name);

        progressBar =  findViewById(R.id.progressBar1);
    }

    @SuppressLint("NonConstantResourceId")

    private void regist() {


            final String email = mail.getEditText().getText().toString().trim();
            final String pass = password.getEditText().getText().toString().trim();
            final String numberr = number.getText().toString().trim();


            final String username = fullname.getEditText().getText().toString().trim();








            progressBar.setVisibility(View.VISIBLE);
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
                userRef
                        .orderByChild("phone")
                        .equalTo(numberr)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {

                                    Toast.makeText(Register.this,"The Phone number is already in use by another account" ,Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);

                                } else {

                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                                    userRef
                                            .orderByChild("email")
                                            .equalTo(email)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.getValue() != null) {

                                                        Toast.makeText(Register.this,"The Email is already in use by another account" ,Toast.LENGTH_SHORT).show();
                                                        progressBar.setVisibility(View.GONE);

                                                    } else {



                                                        progressBar.setVisibility(View.GONE);




                                                        Intent phone = new Intent(Register.this, One_TimeOTP.class);
                                                        phone.putExtra("phone", "+92" + number.getText().toString());
                                                        phone.putExtra("email", email);
                                                        phone.putExtra("password", pass);
                                                        phone.putExtra("name", username);
                                                        startActivity(phone);

                                                    }

                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });


                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });









            }


        }


