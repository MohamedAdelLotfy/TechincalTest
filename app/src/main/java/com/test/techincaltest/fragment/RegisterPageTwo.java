package com.test.techincaltest.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.test.techincaltest.R;
import com.test.techincaltest.activity.Register;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Mohamed on 06/11/2017.
 */

public class RegisterPageTwo extends Fragment {

    private EditText etNationality, etPlaceBirth, etPIssue, etExpiryDate, etCidNumber, etPassNumber, etDateBirth,
            etIssueDate, etCidNumber2;
    private LinearLayout llPassCopy, llCivilId, llResidenceCopy, llOtherDocument;
    private ImageView imgPassCopy, imgCivilId, imgResidenceCopy, imgOtherDocument;
    public static String fName, email, cAddress, engDate, lName, phone, empName, mStatus, mNationality;
    public static int flag = 0;
    public static int keyBack = 0;
    private static final int PICK_IMAGE_REQUEST = 234;

    public static Uri filePassCopy, fileCivilId, fileResidenceCopy, fileOtherDocument;

    public static int keyPassCopy = 0, keyCivilId = 0, keyResidenceCopy = 0, keyOtherDocument = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_page_two, null);

        flag = 1;

        getData();

        init(view);

        nxtPage();

        if (keyBack == 1) {
            back();
        }

        return view;
    }

    /**
     * Initialize Component of XML File
     */
    private void init(View view) {
        etNationality = (EditText) view.findViewById(R.id.etNationality);
        etPlaceBirth = (EditText) view.findViewById(R.id.etPlaceBirth);
        etPIssue = (EditText) view.findViewById(R.id.etPIssue);
        etExpiryDate = (EditText) view.findViewById(R.id.etExpiryDate);
        etCidNumber = (EditText) view.findViewById(R.id.etCidNumber);
        etPassNumber = (EditText) view.findViewById(R.id.etPassNumber);
        etDateBirth = (EditText) view.findViewById(R.id.etDateBirth);
        etIssueDate = (EditText) view.findViewById(R.id.etIssueDate);
        etCidNumber2 = (EditText) view.findViewById(R.id.etCidNumber2);

        llPassCopy = (LinearLayout) view.findViewById(R.id.llPassCopy);
        llPassCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyPassCopy = 1;
                showFileChooser();
            }
        });
        llCivilId = (LinearLayout) view.findViewById(R.id.llCivilId);
        llCivilId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyCivilId = 1;
                showFileChooser();
            }
        });
        llResidenceCopy = (LinearLayout) view.findViewById(R.id.llResidenceCopy);
        llResidenceCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyResidenceCopy = 1;
                showFileChooser();
            }
        });
        llOtherDocument = (LinearLayout) view.findViewById(R.id.llOtherDocument);
        llOtherDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyOtherDocument = 1;
                showFileChooser();
            }
        });

        imgPassCopy = (ImageView) view.findViewById(R.id.imgPassCopy);
        imgCivilId = (ImageView) view.findViewById(R.id.imgCivilId);
        imgResidenceCopy = (ImageView) view.findViewById(R.id.imgResidenceCopy);
        imgOtherDocument = (ImageView) view.findViewById(R.id.imgOtherDocument);
    }

    /***
     * get data from other fragment to use it here */
    private void getData() {
        if (keyBack == 0) {
            fName = getArguments().getString("fName");
            email = getArguments().getString("email");
            cAddress = getArguments().getString("cAddress");
            engDate = getArguments().getString("engDate");
            lName = getArguments().getString("lName");
            phone = getArguments().getString("phone");
            empName = getArguments().getString("empName");
            mStatus = getArguments().getString("mStatus");
            mNationality = getArguments().getString("mNationality");
        } else {
            fName = RegisterPageThree.fNameT;
            email = RegisterPageThree.emailT;
            cAddress = RegisterPageThree.cAddressT;
            engDate = RegisterPageThree.engDateT;
            lName = RegisterPageThree.lNameT;
            phone = RegisterPageThree.phoneT;
            empName = RegisterPageThree.empNameT;
            mStatus = RegisterPageThree.mStatus;
            mNationality = RegisterPageThree.mNationality;
        }
    }

    /***
     * to check the empty field value */
    private void checkInput() {
        if (TextUtils.isEmpty(etNationality.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of nationality field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etPlaceBirth.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of place of birth field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etPIssue.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of place of issue field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etExpiryDate.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of expire date field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etCidNumber.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of cid number field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etPassNumber.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of pass number field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etDateBirth.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of date of birth field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etIssueDate.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of issue date field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etCidNumber2.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of cid number field", Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("nationality", etNationality.getText().toString().trim());
            bundle.putString("placeBirth", etPlaceBirth.getText().toString().trim());
            bundle.putString("pIssue", etPIssue.getText().toString().trim());
            bundle.putString("expDate", etExpiryDate.getText().toString().trim());
            bundle.putString("cidNumber", etCidNumber.getText().toString().trim());
            bundle.putString("passNumber", etPassNumber.getText().toString().trim());
            bundle.putString("dateBirth", etDateBirth.getText().toString().trim());
            bundle.putString("issueDate", etIssueDate.getText().toString().trim());
            bundle.putString("cidNumber2", etCidNumber2.getText().toString().trim());
            bundle.putString("fNameT", fName);
            bundle.putString("emailT", email);
            bundle.putString("cAddressT", cAddress);
            bundle.putString("engDateT", engDate);
            bundle.putString("lNameT", lName);
            bundle.putString("phoneT", phone);
            bundle.putString("empNameT", empName);
            bundle.putString("mStatusT", mStatus);
            bundle.putString("mNationalityT", mNationality);

            if(keyCivilId == 1){
                bundle.putString("civilId", fileCivilId.toString());
            }

            if(keyPassCopy == 1){
                bundle.putString("passCopy", filePassCopy.toString());
            }

            if(keyOtherDocument == 1){
                bundle.putString("otherDocument", fileOtherDocument.toString());
            }

            if(keyResidenceCopy == 1){
                bundle.putString("residenceCopy", fileResidenceCopy.toString());
            }


            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            RegisterPageThree registerPageThree = new RegisterPageThree();
            registerPageThree.setArguments(bundle);
            ft.replace(R.id.fRegisterLayout, registerPageThree);
            ft.commit();
        }
    }

    /***
     * to go to the nxt page with the value entered by the user in this page */
    private void nxtPage() {
        Register.txtRegisterNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                checkInput();
            }
        });
    }

    /**
     * Back Method used when user pressed on back btn
     */
    private void back() {

        etNationality.setText(RegisterPageThree.nationality);
        etPlaceBirth.setText(RegisterPageThree.placeBirth);
        etPIssue.setText(RegisterPageThree.pIssue);
        etExpiryDate.setText(RegisterPageThree.expDate);
        etCidNumber.setText(RegisterPageThree.cidNumber);
        etPassNumber.setText(RegisterPageThree.passNumber);
        etDateBirth.setText(RegisterPageThree.dateBirth);
        etIssueDate.setText(RegisterPageThree.issueDate);
        etCidNumber2.setText(RegisterPageThree.cidNumber2);

    }

    /**
     * to choose file from mobile to upload it with data when register
     */
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select an Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (keyPassCopy == 1) {
                filePassCopy = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePassCopy);
                    imgPassCopy.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (keyCivilId == 1) {
                fileCivilId = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileCivilId);
                    imgCivilId.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (keyResidenceCopy == 1) {
                fileResidenceCopy = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileResidenceCopy);
                    imgResidenceCopy.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (keyOtherDocument == 1) {
                fileOtherDocument = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileOtherDocument);
                    imgOtherDocument.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
