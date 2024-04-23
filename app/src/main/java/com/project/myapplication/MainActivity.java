package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.Utils;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {



    TextView Signin,register;
    EditText Email1, pswrd1;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private static final String TAG = "" ;
    public static final int GOOGLE_SIGN_IN_CODE = 10005;
    //    GoogleSignInClient signInClient;
    FirebaseAuth.AuthStateListener mAuthListner;  //new Amna 3:34

    public static final String EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.progressBar);






        progressBar.setVisibility(View.INVISIBLE);
        Signin=findViewById(R.id.btlogin);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        register=findViewById(R.id.btreg);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });

        Email1=(EditText)findViewById(R.id.emailed);
        pswrd1=(EditText)findViewById(R.id.passed);

        mAuth = FirebaseAuth.getInstance();

    }



    private void userLogin() {
        String email=Email1.getText().toString().trim();
        String password=pswrd1.getText().toString().trim();

        if (email.isEmpty()) {
            Email1.setError("Again enter your email!");
            Email1.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Email1.setError("Enter valid email!");
            Email1.requestFocus();
            return;
        }

        if (password.length()<8){
            pswrd1.setError("Again enter your password!");
            pswrd1.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this,patient_dashboard.class));
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(MainActivity.this, "Error!!!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}