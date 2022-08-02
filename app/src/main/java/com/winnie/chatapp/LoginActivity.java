package com.winnie.chatapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    MaterialButton btn_login, btn_signup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        //initializing views
        btn_login = findViewById(R.id.btn_login);
        TextInputEditText edt_email = findViewById(R.id.edt_email);
        TextInputEditText edt_password = findViewById(R.id.edt_password);
        btn_signup = findViewById(R.id.btn_signup);

        //signing in with email and password
        btn_login.setOnClickListener(view -> signIn(edt_email.getText().toString().trim(), edt_password.getText().toString().trim()));

        //to register activity
        btn_signup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
            startActivity(intent);
        });


    }

    private void signIn(String email, String password) {
        //email and password validation
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "all fields required", Toast.LENGTH_SHORT).show();
        }else{
            SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.setContentText("Logging in");
            pDialog.setCancelable(false);
            pDialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {

                        pDialog.dismissWithAnimation();
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent  intent = new Intent(LoginActivity.this,ConversationActivity.class);
                        startActivity(intent);
                        finish();

                    }).addOnFailureListener(e ->{
                                pDialog.dismissWithAnimation();
                                Toast.makeText(LoginActivity.this, "login failed! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent =  new Intent(this,ConversationActivity.class);
            startActivity(intent);
            finish();
        }
    }
}