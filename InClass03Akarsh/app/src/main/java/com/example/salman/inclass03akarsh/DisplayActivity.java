package com.example.salman.inclass03akarsh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    public static final int REQ_CODE = 100;
    public static String VALUE_KEY = null;
    TextView txtName, txtEmail, txtDepartment, txtMood;
    Student student;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        intent = new Intent("com.example.salman.inclass03akarsh.intent.action.VIEW");

        if (getIntent().getExtras() != null) {
            student = getIntent().getExtras().getParcelable(MainActivity.STUDENT_KEY);
            setName(student.name);
            setDepartment(student.department);
            setEmail(student.email);
            setMood(student.mood);
        }
    }

    public void editName(View view) {
        VALUE_KEY = "editName";
        startActivityForResult(intent, REQ_CODE);
    }

    public void editEmail(View view) {
        VALUE_KEY = "editEmail";
        startActivityForResult(intent, REQ_CODE);
    }

    public void editDepartment(View view) {
        VALUE_KEY = "editDep";
        startActivityForResult(intent, REQ_CODE);
    }

    public void editMood(View view) {
        VALUE_KEY = "editMood";
        startActivityForResult(intent, REQ_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                String returnedVal = data.getExtras().getString(VALUE_KEY);
                switch (VALUE_KEY) {
                    case "editName":
                        setName(returnedVal);
                        break;
                    case "editEmail":
                        setEmail(returnedVal);
                        break;
                    case "editDep":
                        setDepartment(returnedVal);
                        break;
                    case "editMood":
                        setMood(returnedVal);
                        break;
                }
            }
        }
    }

    public void setName(String name) {
        txtName = (TextView) findViewById(R.id.textViewName);
        txtName.setText(name);
    }

    public void setDepartment(String department) {
        txtDepartment = (TextView) findViewById(R.id.textViewDepartment);
        txtDepartment.setText(department);
    }

    public void setMood(String mood) {
        txtMood = (TextView) findViewById(R.id.textViewMood);
        txtMood.setText("You're " + mood + "%" + " " + student.account_state);
    }

    public void setEmail(String email) {
        txtEmail = (TextView) findViewById(R.id.textViewEmail);
        txtEmail.setText(email);
    }
}