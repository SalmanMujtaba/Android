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

public class EditActivity extends AppCompatActivity {

    TextView txtViewN, txtViewE, txtRes, txtViewDep;
    SeekBar seekB;
    String prog;
    RadioGroup rg;
    RadioButton rb;
    String cat;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        txtViewN = (TextView) findViewById(R.id.textViewName);
        txtViewE = (TextView) findViewById(R.id.textViewEmail);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        seekB = (SeekBar) findViewById(R.id.seekBarProgress);
        txtRes = (TextView) findViewById(R.id.textViewRes);
        txtViewDep = (TextView) findViewById(R.id.textViewDepartment);

        if (DisplayActivity.VALUE_KEY.equals("editName")) {
            txtViewN.setVisibility(View.VISIBLE);
        }

        if (DisplayActivity.VALUE_KEY.equals("editEmail")) {
            txtViewE.setVisibility(View.VISIBLE);
        }

        if (DisplayActivity.VALUE_KEY.equals("editDep")) {
            txtViewDep.setVisibility(View.VISIBLE);
            rg.setVisibility(View.VISIBLE);
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
        }

        if (DisplayActivity.VALUE_KEY.equals("editMood")) {
            seekB.setVisibility(View.VISIBLE);
            txtRes.setVisibility(View.VISIBLE);
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

        }

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtViewN.isShown()) {
                    String input = txtViewN.getText().toString();
                    if (input == null || input.length() == 0) {
                        setResult(RESULT_CANCELED);
                    } else {
                        intent = new Intent();
                        intent.putExtra(DisplayActivity.VALUE_KEY, input);
                        setResult(RESULT_OK, intent);
                    }
                }
                if (txtViewE.isShown()) {
                    String input = txtViewE.getText().toString();
                    if (input == null || input.length() == 0) {
                        setResult(RESULT_CANCELED);
                    } else {
                        intent = new Intent();
                        intent.putExtra(DisplayActivity.VALUE_KEY, input);
                        setResult(RESULT_OK, intent);
                    }
                }

                if (rg.isShown()) {
                    if (cat == null || cat.length() == 0) {
                        setResult(RESULT_CANCELED);
                    } else {
                        intent = new Intent();
                        intent.putExtra(DisplayActivity.VALUE_KEY, cat);
                        setResult(RESULT_OK, intent);
                    }
                }

                if (seekB.isShown()) {
                    if (prog == null || prog.length() == 0) {
                        setResult(RESULT_CANCELED);
                    } else {
                        intent = new Intent();
                        intent.putExtra(DisplayActivity.VALUE_KEY, prog);
                        setResult(RESULT_OK, intent);
                    }
                }

                finish();
            }
        });
    }

}
