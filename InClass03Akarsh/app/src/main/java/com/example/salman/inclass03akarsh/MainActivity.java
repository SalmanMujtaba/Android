package com.example.salman.inclass03akarsh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class MainActivity extends AppCompatActivity {
    final static String STUDENT_KEY = "STUDENT";
    TextView txtViewN, txtViewE;
    SeekBar seekB;
    String prog;
    RadioGroup rg;
    RadioButton rb;
    int selected = 0;
    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekB = (SeekBar) findViewById(R.id.seekBarProgress);

        seekB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                prog = String.valueOf(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rg = (RadioGroup) findViewById(R.id.radioGroup);
        rb = (RadioButton) rg.findViewById(rg.getCheckedRadioButtonId());

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked) {
                    // Changes the textview's text to "Checked: example radiobutton text"
                    cat = checkedRadioButton.getText().toString();
                }
            }
        });

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                txtViewN = (TextView) findViewById(R.id.textViewName);
                txtViewE = (TextView) findViewById(R.id.textViewEmail);
                String name = txtViewN.getText().toString();
                String email = txtViewE.getText().toString();

                if (name == null || name.equals("") || email == null || email.equals("") || cat == null || prog == null) {
                    makeText(getApplicationContext(), "Please fill all the inputs",
                            LENGTH_SHORT).show();
                } else {
                    Student student = new Student(name, cat, email, "POSITIVE", prog);
                    intent.putExtra(STUDENT_KEY, student);
                    startActivity(intent);
                }
            }
        });
    }


}
