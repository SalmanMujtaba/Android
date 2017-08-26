package com.example.salman.inclass02ch;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.*;

public class MainActivity extends AppCompatActivity {

    EditText edtWeight, feet, inches;
    TextView txtBMI;
    TextView txtStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtWeight = (EditText) findViewById(R.id.weight);
        feet = (EditText) findViewById(R.id.feet);
        inches = (EditText) findViewById(R.id.inches);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calCulateBMI(View view)
    {
        String weight = edtWeight.getText().toString();
        String ft = feet.getText().toString();
        String in = inches.getText().toString();

        if(weight.equals("") || ft.equals("") || in.equals(""))
        {
            makeText(getApplicationContext(), "Invalid Inputs",
                    LENGTH_SHORT).show();
        }
        else if(!(isNumeric(weight) && isNumeric(ft) && isNumeric(in))){
            makeText(getApplicationContext(), "The input entered is not a number",
                    LENGTH_SHORT).show();
        }
        else {
            Float feet = Float.valueOf(ft);
            Float inches = Float.valueOf(in);
            Float wt = Float.valueOf(weight);

            Float totalHt = calculateHt(feet, inches);
            Float BMI = calculateBMI(wt, totalHt);

            String BMIvalue = getResources().getString(R.string.bmiValue) + " " + BMI;
            txtBMI = (TextView) findViewById(R.id.bmiValue);

            DecimalFormat df = new DecimalFormat("###.#");

            BMI = Float.valueOf(df.format(BMI));
            txtBMI.setText("Your BMI: " + BMI.toString());

            String bmiCat = getResources().getString(R.string.bmiStatus);
            txtStatus = (TextView) findViewById(R.id.bmiStatus);
            txtStatus.setText("You are " + bmiStatus(BMI).toLowerCase());

            makeText(getApplicationContext(), "BMI calculated",
                    LENGTH_SHORT).show();
        }
    }

    public boolean isNumeric(String str)
    {
        return str.matches("\\d+(\\.\\d+)?");
    }

    public float calculateHt(float feet2, float inches2){
        return  (Float.valueOf(feet2) * 12f) + Float.valueOf(inches2);

    }

    public float calculateBMI(float weight, float totalHt){

        return  (Float.valueOf(weight)/(totalHt*totalHt)) * 703f;

    }

    public String bmiStatus(Float bmi){
        if(bmi<=18.5)
        {
            return "Underweight";
        }

        if(bmi>=18.5 && bmi<=24.9)
        {
            return "Normal Weight";
        }

        if(bmi>=25 && bmi<=29.9)
        {
            return "Overweight";
        }

        if(bmi>29)
        {
            return "Obesity";
        }

        return null;
    }



}
