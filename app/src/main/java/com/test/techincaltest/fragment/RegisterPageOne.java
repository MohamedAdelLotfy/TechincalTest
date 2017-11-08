package com.test.techincaltest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.test.techincaltest.R;
import com.test.techincaltest.activity.Register;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

/**
 * Created by Mohamed on 06/11/2017.
 */

public class RegisterPageOne extends Fragment {


    private EditText etFname, etEmail, etCAddress, etEngDate, etLname, etPhone, etEmpName;
    private MaterialBetterSpinner spMaritalStatus, spMotherNationality;
    private String mStatus, mNationality;
    private ArrayList<String> lstStatus = new ArrayList<>();
    private ArrayList<String> lstNationality = new ArrayList<>();
    public static int flag = 0;
    public static int keyBack = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg_page_one, null);

        flag = 1;

        init(view);

        getMarital();

        getNationality();

        nxtPage();

        if (keyBack == 1) {
            keyBack = 0;
            back();
        }

        return view;
    }

    /***
     * Initialize Component of XML File*/
    private void init(View view) {
        etFname = (EditText) view.findViewById(R.id.etFName);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etCAddress = (EditText) view.findViewById(R.id.etCAddress);
        etEngDate = (EditText) view.findViewById(R.id.etEngDate);
        etLname = (EditText) view.findViewById(R.id.etLName);
        etPhone = (EditText) view.findViewById(R.id.etPhone);
        etEmpName = (EditText) view.findViewById(R.id.etEmployerName);

        spMaritalStatus = (MaterialBetterSpinner) view.findViewById(R.id.spMaritalStatus);
        spMotherNationality = (MaterialBetterSpinner) view.findViewById(R.id.spMotherNationality);
    }

    /***
     * to get the value of selected marital status*/
    private void getMarital() {
        lstStatus.add("Married");
        lstStatus.add("Single");
        lstStatus.add("Divorced");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, lstStatus);
        spMaritalStatus.setAdapter(adapter);
        spMaritalStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mStatus = spMaritalStatus.getText().toString();
            }
        });
    }

    /***
     * to get the value of selected mother nationality */
    private void getNationality() {
        lstNationality.add("Egyptian");
        lstNationality.add("Saudi");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_dropdown_item_1line, lstNationality);
        spMotherNationality.setAdapter(adapter);
        spMotherNationality.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mNationality = spMotherNationality.getText().toString();
            }
        });
    }

    /***
     * to check the empty field value */
    private void checkInput() {
        if (TextUtils.isEmpty(etFname.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of first name field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of email field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etCAddress.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of current address field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etEngDate.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of eng date field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etLname.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of last name field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etPhone.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of phone field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(etEmpName.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of employee name field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(spMaritalStatus.getText().toString())) {
            Toast.makeText(getActivity(), "Please Enter the Value of marital status field", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(spMotherNationality.getText().toString().trim())) {
            Toast.makeText(getActivity(), "Please Enter the Value of mother nationality field", Toast.LENGTH_LONG).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("fName", etFname.getText().toString().trim());
            bundle.putString("email", etEmail.getText().toString().trim());
            bundle.putString("cAddress", etCAddress.getText().toString().trim());
            bundle.putString("engDate", etEngDate.getText().toString().trim());
            bundle.putString("lName", etLname.getText().toString().trim());
            bundle.putString("phone", etPhone.getText().toString().trim());
            bundle.putString("empName", etEmpName.getText().toString().trim());
            bundle.putString("mStatus", mStatus);
            bundle.putString("mNationality", mNationality);

            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            RegisterPageTwo registerPageTwo = new RegisterPageTwo();
            registerPageTwo.setArguments(bundle);
            ft.replace(R.id.fRegisterLayout, registerPageTwo);
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
     * Back Method used when user pressed on back btn*/
    private void back() {

        etFname.setText(RegisterPageTwo.fName);
        etEmail.setText(RegisterPageTwo.email);
        etCAddress.setText(RegisterPageTwo.cAddress);
        etEngDate.setText(RegisterPageTwo.engDate);
        etLname.setText(RegisterPageTwo.lName);
        etPhone.setText(RegisterPageTwo.phone);
        etEmpName.setText(RegisterPageTwo.empName);
        spMaritalStatus.setText(RegisterPageTwo.mStatus);
        spMotherNationality.setText(RegisterPageTwo.mNationality);

    }

}
