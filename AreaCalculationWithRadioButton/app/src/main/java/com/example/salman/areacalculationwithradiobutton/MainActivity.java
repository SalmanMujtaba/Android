package com.example.salman.areacalculationwithradiobutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    RadioGroup rg;
    TextView txt;
    EditText edtLen1, edtLen2;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtLen1 = (EditText) findViewById(R.id.editTextLength1);
        edtLen2 = (EditText) findViewById(R.id.editTextLength2);

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        findViewById(R.id.buttonCalculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rg.getCheckedRadioButtonId() == -1) {
                    printErrorToast();
                } else {
                    if (rg.getCheckedRadioButtonId() == R.id.radioButtonTriangle) {
                        String len1 = fetchLen1();
                        String len2 = fetchLen2();
                        if (validation(len1, len2)) {
                            Double len1d = Double.valueOf(len1);
                            Double len2d = Double.valueOf(len2);
                            Double area = 0.5 * len1d * len2d;
                            area = convertToTwoDecimalPlaces(area);
                            setStringValue(area.toString());
                            printToast();
                        } else {
                            setStringValue("");
                            printErrorToast();
                        }
                    }

                    if (rg.getCheckedRadioButtonId() == R.id.radioButtonSquare) {
                        String len1 = fetchLen1();
                        if (len1.equals("") || !len1.matches("\\d+(\\.\\d+)?")) {
                            printErrorToast();
                            setStringValue("");

                        } else {
                            edtLen2.setText("");
                            Double len1d = Double.valueOf(len1);
                            Double area = len1d * len1d;
                            area = convertToTwoDecimalPlaces(area);
                            setStringValue(area.toString());
                            printToast();

                        }
                    }

                    if (rg.getCheckedRadioButtonId() == R.id.radioButtonCircle) {
                        String len1 = fetchLen1();
                        if (len1.equals("") || !len1.matches("\\d+(\\.\\d+)?")) {
                            printErrorToast();
                            setStringValue("");
                        } else {
                            edtLen2.setText("");
                            Double len1d = Double.valueOf(len1);
                            Double area = 3.14 * len1d * len1d;
                            area = convertToTwoDecimalPlaces(area);
                            setStringValue(area.toString());
                            printToast();
                        }
                    }

                    if (rg.getCheckedRadioButtonId() == R.id.radioButtonrectangle) {
                        String len1 = fetchLen1();
                        String len2 = fetchLen2();
                        if (validation(len1, len2)) {
                            Double len1d = Double.valueOf(len1);
                            Double len2d = Double.valueOf(len2);
                            Double area = len1d * len2d;
                            area = convertToTwoDecimalPlaces(area);
                            setStringValue(area.toString());
                            printToast();
                        } else {
                            printErrorToast();
                            setStringValue("");
                        }
                    }

                    if(rg.getCheckedRadioButtonId()==R.id.radioButtonClear){
                            setStringValue("");
                            edtLen1.setText("");
                            edtLen2.setText("");
                            edtLen2.clearFocus();
                    }
                }
            }
        });
    }


    public String fetchLen1(){
        return edtLen1.getText().toString();
    }

    public String fetchLen2(){
        return edtLen2.getText().toString();
    }

    public void setStringValue(String val){
        txtResult = (TextView) findViewById(R.id.textViewResult);
        txtResult.setText(val);
    }

    public void printToast(){
        makeText(getApplicationContext(), "Area Calculated",
                LENGTH_SHORT).show();
    }
    public void printErrorToast(){
        makeText(getApplicationContext(), "Invalid Inputs",
                LENGTH_SHORT).show();
    }
    public boolean validation(String len1, String len2){
        if(len1.equals("")  || len2.equals("") || !len1.matches("\\d+(\\.\\d+)?") || !len2.matches("\\d+(\\.\\d+)?")) {
            return false;
        }
        return true;
    }

    public void clear(View view) {
        setStringValue("");
        edtLen1.setText("");
        edtLen2.setText("");
        edtLen2.clearFocus();
    }

    public double convertToTwoDecimalPlaces(double val){
        return Math.round(val*100.0)/100.0;
    }
}
