package com.example.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //Declare Instance variables
    private EditText mLoanAmount;
    private Spinner mInterestRate, mAmortizationPeriod, mMonthly;
    private TextView mTotalPayment;
    private Button button;

    //Spinner class is used to allow the drop-down menus
    Spinner s, s2, s3;

    //Initialize arrays to include items for the drop-down menus
    String rates[] = {"Choose Interest Rate","2.14%", "3.09%"};
    String aPeriod[] = {"Choose Amortization Period", "7 years", "10 years", "25 years", "30 years"};
    String payFrequency[] = {"Monthly"};

    //Declare adapter class to provide the views for the array values above
    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter<String> arrayAdapter2;
    ArrayAdapter<String> arrayAdapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This code is used to locate each view based on their ID
        mLoanAmount = (EditText)findViewById(R.id.editTextNumber);
        mTotalPayment = (TextView) findViewById(R.id.textView9);
        button = (Button)findViewById(R.id.button);

        //Here, each spinner finds its view by the spinner ID by using the adapter array as a String
        s = (Spinner) findViewById (R.id.spinner);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, rates);
        s.setAdapter(arrayAdapter);

        s2 = (Spinner) findViewById(R.id.spinner2);
        arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, aPeriod);
        s2.setAdapter(arrayAdapter2);

        s3 = (Spinner) findViewById(R.id.spinner3);
        arrayAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, payFrequency);
        s3.setAdapter(arrayAdapter3);


        /*
        *The following code for the three spinners is used to track the items selected by the user in the drop down.
        * An if statement is used to stop the program from showing a text display when nothing is selected from each spinner.
        * When the user does make a selection, the else statement indicates the Toast class that provide a text showing what the user has picked.
        */
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose Interest Rate")){
                    //Do nothing
                }
                else {
                    String item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(getApplicationContext(), "Selected: "+rates[position], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose Amortization Period")){
                    //Do nothing
                }
                else {
                    String item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(getApplicationContext(), "Selected: "+aPeriod[position], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Do nothing
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //When the button is clicked, the result of the mortgage payment is shown via the calculateResult method
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateResult(v);
            }
        });
    }

    //Method used to calculate the mortgage payment value
    public void calculateResult(View v) {

        //Initialize all variables to 0 as defult
        double loanAmount = Integer.parseInt(mLoanAmount.getText().toString());
        double rate = 0;
        double period = 0;
        double frequency = 0;

        //Conditions for the rate values upon user selection
        if(s.getSelectedItem().equals("2.14%")){
            rate = 0.0017833333333333332;
        }
        else if(s.getSelectedItem().equals("3.09%")){
            rate = 0.002575;
        }

        //Conditions for the amortization period values upon user selection
        if(s2.getSelectedItem().equals("7 years")){
            period = 7.0;
        }
        else if(s2.getSelectedItem().equals("10 years")){
            period = 10.0;
        }
        else if(s2.getSelectedItem().equals("25 years")){
            period = 25.0;
        }
        else if(s2.getSelectedItem().equals("30 years")){
            period = 30.0;
        }

        //One condition for the payment frequency when selected by the user
        if(s3.getSelectedItem().equals("Monthly")){
            frequency = 12.0;
        }

        //This code uses the mortgage payment formula to calulate the result
        double n = frequency * period;
        double payment = loanAmount * rate * Math.pow(1 + rate, n) / (Math.pow(1 + rate, n) - 1);
        String result = String.valueOf(new DecimalFormat("##.##").format(payment));
        mTotalPayment.setText("$ " + result + "/Month");

    }
}