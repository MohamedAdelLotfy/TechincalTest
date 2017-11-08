package com.test.techincaltest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.test.techincaltest.R;
import com.test.techincaltest.fragment.RegisterPageOne;
import com.test.techincaltest.fragment.RegisterPageThree;
import com.test.techincaltest.fragment.RegisterPageTwo;

/**
 * Created by Mohamed on 06/11/2017.
 */

public class Register extends AppCompatActivity {

    public static TextView txtRegisterNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtRegisterNext = (TextView) findViewById(R.id.txtRegisterNext);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        RegisterPageOne registerPageOne = new RegisterPageOne();
        ft.add(R.id.fRegisterLayout, registerPageOne);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (RegisterPageOne.flag == 1) {
            RegisterPageOne.flag = 0;
            startActivity(new Intent(Register.this, Login.class));
            finish();
        } else if (RegisterPageTwo.flag == 1) {
            RegisterPageTwo.flag = 0;
            RegisterPageOne.keyBack = 1;
            txtRegisterNext.setVisibility(View.VISIBLE);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            RegisterPageOne registerPageOne = new RegisterPageOne();
            ft.replace(R.id.fRegisterLayout, registerPageOne);
            ft.commit();
        } else if (RegisterPageThree.flag == 1) {
            RegisterPageThree.flag = 0;
            RegisterPageTwo.keyBack = 1;
            txtRegisterNext.setVisibility(View.VISIBLE);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            RegisterPageTwo registerPageTwo = new RegisterPageTwo();
            ft.replace(R.id.fRegisterLayout, registerPageTwo);
            ft.commit();
        } else {
            super.onBackPressed();
            startActivity(new Intent(Register.this, Login.class));
            finish();
        }
    }

}
