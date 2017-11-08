package com.test.techincaltest.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.test.techincaltest.R;

/**
 * Created by Mohamed on 08/11/2017.
 */

public class Main extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private String uId;
    private AppCompatButton btnLogout;
    private TextView txtUserData;
    private String fName, lName, cidNumber, cidNumber2, currentAddress, dateBirth, email, empName, engDate, expDate, issueDate,
            mNationality, mStatus, nationality, pIssue, passNumber, phone, placeBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        uId = user.getUid();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Main.this, Login.class));
                    finish();
                } else {
                }
            }
        };

        mFirebaseDatabase.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fName = dataSnapshot.child("fName").getValue().toString();
                if (TextUtils.isEmpty(fName)) {
                    fName = "";
                }
                lName = dataSnapshot.child("lName").getValue().toString();
                if (TextUtils.isEmpty(lName)) {
                    lName = "";
                }
                cidNumber = dataSnapshot.child("cidNumber").getValue().toString();
                if (TextUtils.isEmpty(cidNumber)) {
                    cidNumber = "";
                }
                cidNumber2 = dataSnapshot.child("cidNumber2").getValue().toString();
                if (TextUtils.isEmpty(cidNumber2)) {
                    cidNumber2 = "";
                }
                currentAddress = dataSnapshot.child("currentAddress").getValue().toString();
                if (TextUtils.isEmpty(currentAddress)) {
                    currentAddress = "";
                }
                dateBirth = dataSnapshot.child("dateBirth").getValue().toString();
                if (TextUtils.isEmpty(dateBirth)) {
                    dateBirth = "";
                }
                email = dataSnapshot.child("email").getValue().toString();
                if (TextUtils.isEmpty(email)) {
                    email = "";
                }
                empName = dataSnapshot.child("empName").getValue().toString();
                if (TextUtils.isEmpty(empName)) {
                    empName = "";
                }
                engDate = dataSnapshot.child("engDate").getValue().toString();
                if (TextUtils.isEmpty(engDate)) {
                    engDate = "";
                }
                issueDate = dataSnapshot.child("issueDate").getValue().toString();
                if (TextUtils.isEmpty(issueDate)) {
                    issueDate = "";
                }
                mNationality = dataSnapshot.child("mNationality").getValue().toString();
                if (TextUtils.isEmpty(mNationality)) {
                    mNationality = "";
                }
                mStatus = dataSnapshot.child("mStatus").getValue().toString();
                if (TextUtils.isEmpty(mStatus)) {
                    mStatus = "";
                }
                nationality = dataSnapshot.child("nationality").getValue().toString();
                if (TextUtils.isEmpty(nationality)) {
                    nationality = "";
                }
                pIssue = dataSnapshot.child("pIssue").getValue().toString();
                if (TextUtils.isEmpty(pIssue)) {
                    pIssue = "";
                }
                passNumber = dataSnapshot.child("passNumber").getValue().toString();
                if (TextUtils.isEmpty(passNumber)) {
                    passNumber = "";
                }
                phone = dataSnapshot.child("phone").getValue().toString();
                if (TextUtils.isEmpty(phone)) {
                    phone = "";
                }
                placeBirth = dataSnapshot.child("placeBirth").getValue().toString();
                if (TextUtils.isEmpty(placeBirth)) {
                    placeBirth = "";
                }

                txtUserData.setText("Hello, " + fName + " " + lName + "\n" +
                        "Email : " + email + "  phone : " + phone + "\n" +
                        "Current Address : " + currentAddress + "\n" +
                        "Birth Date : " + dateBirth + "  " + "Place of Birth : " + placeBirth + "\n" +
                        "CID Number : " + cidNumber + "   " + "CID Number 2 : " + cidNumber2 + "\n" +
                        "Emp Name : " + empName + "   " + "Engage Date : " + engDate + "\n" +
                        "Issue Date : " + issueDate + "   " + "Place of Issue : " + pIssue + "\n" +
                        "Marital Status : " + mStatus + "   " + "Nationality : " + mNationality);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /***
     * Initialize Component of XML File*/
    private void init() {
        txtUserData = (TextView) findViewById(R.id.txtUserData);
        btnLogout = (AppCompatButton) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    logout();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.txtConnection, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /***
     * Logout Method*/
    private void logout() {
        auth.signOut();
        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(Main.this, Home.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Main.this, Home.class));
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
