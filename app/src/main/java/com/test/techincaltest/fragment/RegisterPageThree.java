package com.test.techincaltest.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.test.techincaltest.R;
import com.test.techincaltest.activity.Main;
import com.test.techincaltest.activity.Register;

/**
 * Created by Mohamed on 06/11/2017.
 */

public class RegisterPageThree extends Fragment {

    private EditText etPassword, etConfirmPassword;
    private AppCompatButton btnRegister;
    public static String nationality, placeBirth, pIssue, expDate, cidNumber, passNumber, dateBirth, issueDate, cidNumber2, fNameT,
            emailT, cAddressT, engDateT, lNameT, phoneT, empNameT, mStatus, mNationality;
    String password, confirm, userId;
    Uri filePassCopy, fileResidenceCopy, fileOtherDocument, fileCivilId;
    private FirebaseAuth auth;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public static int flag = 0;
    StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_page_three, null);

        flag = 1;

        Register.txtRegisterNext.setVisibility(View.INVISIBLE);

        storageReference = FirebaseStorage.getInstance().getReference();

        //Get Fire base auth instance
        auth = FirebaseAuth.getInstance();

        //
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Technical Test");

        getData();

        init(view);

        onClickRegister();

        return view;
    }

    /***
     * Initialize Component of XML File*/
    private void init(View view) {

        etPassword = (EditText) view.findViewById(R.id.etPassword);

        etConfirmPassword = (EditText) view.findViewById(R.id.etConfirmPass);

        btnRegister = (AppCompatButton) view.findViewById(R.id.btnRegister);

    }

    /***
     * click on btn of register */
    private void onClickRegister() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = etPassword.getText().toString().trim();
                confirm = etConfirmPassword.getText().toString().trim();
                if (isNetworkAvailable()) {
                    if (RegisterPageTwo.keyResidenceCopy == 1) {
                        RegisterPageTwo.keyResidenceCopy = 0;
                        if (fileResidenceCopy != null) {
                            residenceCopy(userId);
                        }
                    } else if (RegisterPageTwo.keyOtherDocument == 1) {
                        RegisterPageTwo.keyOtherDocument = 0;
                        if (fileOtherDocument != null) {
                            otherDocument(userId);
                        }
                    } else if (RegisterPageTwo.keyCivilId == 1) {
                        RegisterPageTwo.keyCivilId = 0;
                        if (fileCivilId != null) {
                            civilId(userId);
                        }
                    } else if (RegisterPageTwo.keyPassCopy == 1) {
                        RegisterPageTwo.keyPassCopy = 0;
                        if (filePassCopy != null) {
                            passCopy(userId);
                        }
                    }
                    checkInput();
                } else {
                    Toast.makeText(getActivity(), R.string.txtConnection, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    /***
     * get data from other fragment to use it here */
    private void getData() {
        nationality = getArguments().getString("nationality");
        placeBirth = getArguments().getString("placeBirth");
        pIssue = getArguments().getString("pIssue");
        expDate = getArguments().getString("expDate");
        cidNumber = getArguments().getString("cidNumber");
        passNumber = getArguments().getString("passNumber");
        dateBirth = getArguments().getString("dateBirth");
        issueDate = getArguments().getString("issueDate");
        cidNumber2 = getArguments().getString("cidNumber2");
        fNameT = getArguments().getString("fNameT");
        emailT = getArguments().getString("emailT");
        cAddressT = getArguments().getString("cAddressT");
        engDateT = getArguments().getString("engDateT");
        lNameT = getArguments().getString("lNameT");
        phoneT = getArguments().getString("phoneT");
        empNameT = getArguments().getString("empNameT");
        mStatus = getArguments().getString("mStatusT");
        mNationality = getArguments().getString("mNationalityT");

        if (RegisterPageTwo.keyResidenceCopy == 1) {
            fileResidenceCopy = Uri.parse(getArguments().getString("residenceCopy"));
        }

        if (RegisterPageTwo.keyPassCopy == 1) {
            filePassCopy = Uri.parse(getArguments().getString("passCopy"));
        }

        if (RegisterPageTwo.keyOtherDocument == 1) {
            fileOtherDocument = Uri.parse(getArguments().getString("otherDocument"));
        }

        if (RegisterPageTwo.keyCivilId == 1) {
            fileCivilId = Uri.parse(getArguments().getString("civilId"));
        }

    }

    /***
     * check input if empty or not*/
    private void checkInput() {
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Please Enter the Value of password field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(confirm)) {
            Toast.makeText(getActivity(), "Please Enter the Value of confirm password field", Toast.LENGTH_LONG).show();
        } else {
            checkValidation();
        }
    }

    /***
     * check validate of inputs */
    private void checkValidation() {
        if (password.length() < 6) {
            Toast.makeText(getActivity(), "Password is to short, please enter suitable password", Toast.LENGTH_LONG).show();
        } else {
            if (!(password.equals(confirm))) {
                Toast.makeText(getActivity(), "Password don't match", Toast.LENGTH_LONG).show();
            } else {
                addUser();
            }
        }
    }

    /**
     * to register user with auth email and password
     */
    private void addUser() {
        //create user
        auth.createUserWithEmailAndPassword(emailT, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            userId = task.getResult().getUser().getUid();
                            createUser(userId);
                        }
                    }
                });
    }

    /**
     * Creating new user under 'users node'
     */
    private void createUser(String userId) {

        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        mFirebaseDatabase.child(userId).setValue(userId);
        mFirebaseDatabase.child(userId).child("fName").setValue(fNameT);
        mFirebaseDatabase.child(userId).child("lName").setValue(lNameT);
        mFirebaseDatabase.child(userId).child("email").setValue(emailT);
        mFirebaseDatabase.child(userId).child("currentAddress").setValue(cAddressT);
        mFirebaseDatabase.child(userId).child("engDate").setValue(engDateT);
        mFirebaseDatabase.child(userId).child("phone").setValue(phoneT);
        mFirebaseDatabase.child(userId).child("empName").setValue(empNameT);
        mFirebaseDatabase.child(userId).child("mStatus").setValue(mStatus);
        mFirebaseDatabase.child(userId).child("mNationality").setValue(mNationality);
        mFirebaseDatabase.child(userId).child("nationality").setValue(nationality);
        mFirebaseDatabase.child(userId).child("placeBirth").setValue(placeBirth);
        mFirebaseDatabase.child(userId).child("pIssue").setValue(pIssue);
        mFirebaseDatabase.child(userId).child("expDate").setValue(expDate);
        mFirebaseDatabase.child(userId).child("cidNumber").setValue(cidNumber);
        mFirebaseDatabase.child(userId).child("passNumber").setValue(passNumber);
        mFirebaseDatabase.child(userId).child("dateBirth").setValue(dateBirth);
        mFirebaseDatabase.child(userId).child("issueDate").setValue(issueDate);
        mFirebaseDatabase.child(userId).child("cidNumber2").setValue(cidNumber2);

        startActivity(new Intent(getActivity(), Main.class));
    }

    /**
     * Check Internet Connection
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void residenceCopy(String userId) {

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        StorageReference riversRef = storageReference.child(userId).child("images/residence.jpg");
        riversRef.putFile(fileResidenceCopy)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
    }

    private void otherDocument(String userId) {

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        StorageReference riversRef = storageReference.child(userId).child("images/other.jpg");
        riversRef.putFile(fileOtherDocument)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
    }

    private void civilId(String userId) {

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        StorageReference riversRef = storageReference.child(userId).child("images/civil.jpg");
        riversRef.putFile(fileCivilId)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
    }

    private void passCopy(String userId) {

        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        StorageReference riversRef = storageReference.child(userId).child("images/pass.jpg");
        riversRef.putFile(filePassCopy)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
    }

}
