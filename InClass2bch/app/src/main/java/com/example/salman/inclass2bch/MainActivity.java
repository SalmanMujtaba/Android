package com.example.salman.inclass2bch;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    TextView txtWeight;
    int check1=0, check2=0, check3=0,check4=0, check5=0;
    EditText edtFeet, edtInches;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtFeet = (EditText) findViewById(R.id.editTextFeet);
        edtInches = (EditText) findViewById(R.id.editTextIn);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calculateWeight(View view) {
        String ft = null;
        String in = null;

        ft = edtFeet.getText().toString();
        in = edtInches.getText().toString();

        if(ft.equals("") || in.equals("") || !(isNumeric(ft) && isNumeric(in)))
        {
            setTheText("");
            makeText(getApplicationContext(), "Invalid Inputs",
                    LENGTH_SHORT).show();

        }
        else {
            Float feet = Float.valueOf(ft);
            Float inches = Float.valueOf(in);
            Float Ht = 0f;
            Ht = calculateHt(feet, inches);
            Float wt1 = 0f;
            Float wt2 = 0f;


            if (check1 == 1) {
                wt2 = calculateWt(18.5f, Ht);
                setTheText("Your weight should be less than" + wt2 + " lb");
            }

            if (check2 == 1) {
                wt1 = calculateWt(18.5f, Ht);
                wt2 = calculateWt(24.9f, Ht);
                setTheText("Your weight should be in between" + Math.round(wt1) + "lb to " + Math.round(wt2) + " lb");

            }

            if (check3 == 1) {
                wt1 = calculateWt(25f, Ht);
                wt2 = calculateWt(29.9f, Ht);
                setTheText("Your weight should be in between" + wt1 + "lb to " + wt2 + " lb");

            }

            if (check4 == 1) {
                wt2 = calculateWt(29.9f, Ht);
                setTheText("Your weight should be greater than" + wt2 + " lb");

            }
            makeText(getApplicationContext(), "Weight calculated",
                    LENGTH_SHORT).show();
        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton18_5:
                if (checked)
                    check1 = 1;
                    check2=check3=check4=check5=0;
                    break;
            case R.id.radioButton24_9:
                if (checked)
                    check2 = 1;
                    check1=check3=check4=check5=0;

                break;
            case R.id.radioButton29_9:
                if (checked)
                    check3 = 1;
                    check2=check1=check4=check5=0;

                break;
            case R.id.radioButtonGreatest:
                if (checked)
                    check4 = 1;
                    check2=check3=check4=check5=0;

                break;
            default: check5=1;
        }
    }


    public float calculateWt(float bmi, float ht){
        return (bmi*ht*ht)/703f;
    }

    public float calculateHt(float ft, float in){
        return ((12f*ft)+ in);
    }

    public void setTheText(String val){
        txtWeight = (TextView) findViewById(R.id.textViewWeight);
        txtWeight.setText(val);
    }

    public boolean isNumeric(String str)
    {

        return str.matches("\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }



}
