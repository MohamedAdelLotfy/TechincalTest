package com.test.techincaltest.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.test.techincaltest.R;

/**
 * Created by Mohamed on 06/11/2017.
 */

public class Login extends AppCompatActivity {

    /***
     * Define the main component used in the activity*/
    private TextView txtRegister, txtForget;
    private AppCompatButton btnLogin;
    private ImageView imgLoginClose;
    private EditText etLoginEmail, etLoginPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private FirebaseAuth auth;
    String email, password;

    /***
     * References ...
     * https://www.androidhive.info/2015/09/android-material-design-floating-labels-for-edittext/
     * am going to use this reference to make validation for edit text used in this activity **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, Main.class));
            finish();
        }

        init();
    }

    /***
     * this method to make action and communicate the component in the xml file in this activity */
    public void init() {

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);

        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);

        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);

        etLoginEmail.addTextChangedListener(new MyTextWatcher(etLoginEmail));
        etLoginPassword.addTextChangedListener(new MyTextWatcher(etLoginPassword));

        txtRegister = (TextView) findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        txtForget = (TextView) findViewById(R.id.txtForget);
        txtForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForgetPass.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        imgLoginClose = (ImageView) findViewById(R.id.imgLoginClose);
        imgLoginClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * Validating form for the component used in the xml file
     */
    private void submitForm() {

        if (!validateEmail()) {
            return;
        } else if (!validatePassword()) {
            return;
        } else {
            if(isNetworkAvailable()) {
                email = etLoginEmail.getText().toString().trim();
                password = etLoginPassword.getText().toString().trim();
                //authenticate user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    // there was an error
                                    if (password.length() < 6) {
                                        inputLayoutPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(Login.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(Login.this, Main.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }else{
                Toast.makeText(getApplicationContext(), R.string.txtConnection, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * to make validate for email edit text and give user error message if data enter is not validate
     */
    private boolean validateEmail() {
        String email = etLoginEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(etLoginEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    /**
     * to make validate for password edit text
     */
    private boolean validatePassword() {
        if (etLoginPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(etLoginPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.etLoginEmail:
                    validateEmail();
                    break;
                case R.id.etLoginPassword:
                    validatePassword();
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Login.this, Home.class));
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
