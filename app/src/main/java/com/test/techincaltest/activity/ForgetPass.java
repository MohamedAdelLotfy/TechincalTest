package com.test.techincaltest.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.test.techincaltest.R;

/**
 * Created by Mohamed on 08/11/2017.
 */

public class ForgetPass extends AppCompatActivity {


    private EditText etForgetMail;
    private AppCompatButton btnForget;
    private FirebaseAuth auth;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        auth = FirebaseAuth.getInstance();

        init();

    }

    /***
     * Initialize Component of XML File*/
    private void init() {
        etForgetMail = (EditText) findViewById(R.id.etForgetMail);
        btnForget = (AppCompatButton) findViewById(R.id.btnForget);
        btnForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    forgetPass();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.txtConnection, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /***
     * Forget Password method to reset password using mail*/
    private void forgetPass() {
        email = etForgetMail.getText().toString().trim();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPass.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgetPass.this, Login.class));
                            finish();
                        } else {
                            Toast.makeText(ForgetPass.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ForgetPass.this, Login.class));
        finish();
    }

    /**
     * Check Internet Connection
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
