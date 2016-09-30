package com.example.jrd58.homework01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.text.NumberFormat;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import org.w3c.dom.Text;


public class SimpCalc extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener, TextView.OnEditorActionListener {

    private Button calcButton;
    private Spinner spinner;
    private EditText editV1;
    private EditText editV2;
    private TextView result;

    private String v1Str = "";
    private String v2Str = "";
    private String opStr = "+";

    private SharedPreferences savedVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simp_calc);

        //Finds all the necessary views and widgets, and sets up the spinner mechanism
        editV1 = (EditText) findViewById(R.id.editV1);
        editV2 = (EditText) findViewById(R.id.editV2);
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.operations,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        calcButton = (Button) findViewById(R.id.calcButton);
        result = (TextView) findViewById(R.id.result);
        calcButton.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        savedVal = getSharedPreferences("savedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause() {
        Editor editor = savedVal.edit();
        editor.putString("v1Str", v1Str);
        editor.putString("v2Str", v2Str);
        editor.apply();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        v1Str = savedVal.getString("v1Str", "");
        v2Str = savedVal.getString("v2Str", "");
        calcDisplay();
    }
    //Methods for actually calculating from inputs
    public void calcDisplay() {
        v1Str = editV1.getText().toString();
        v2Str = editV2.getText().toString();
        float result1;
        if (v1Str.equals("")) {
            result1 = 0;
        }
        else if (v2Str.equals("")) {
            result1 = 0;
        }
        //Calculations based on operators
        else {
            if (opStr.equals("+")) {
                result1 = Float.parseFloat(v1Str) + Float.parseFloat(v2Str);
            } else if (opStr.equals("-")) {
                result1 = Float.parseFloat(v1Str) - Float.parseFloat(v2Str);
            } else if (opStr.equals("*")) {
                result1 = Float.parseFloat(v1Str) * Float.parseFloat(v2Str);
            } else {
                result1 = Float.parseFloat(v1Str) / Float.parseFloat(v2Str);
            }
        }
        //sets result label
        NumberFormat number = NumberFormat.getNumberInstance();
        result.setText(number.format(result1));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        opStr = parent.getItemAtPosition(position).toString();

    }
    public void onNothingSelected(AdapterView<?> arg0){

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            calcDisplay();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        calcDisplay();
    }
}



