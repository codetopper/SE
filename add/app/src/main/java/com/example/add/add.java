package com.example.add;// Each new activity has its own layout and Java files,
// here we build the logic for adding two number

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class add extends AppCompatActivity {

    // define the global variable

    // variable number1, number2 for input input number
    // Add_button, result textView

    EditText number1;
    EditText number2;
    Button Add_button;
    TextView result;
    int ans=0;
    manager m1 = new manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // by ID we can use each component which id is assign in xml file
        number1=(EditText) findViewById(R.id.editText_first_no);
        number2=(EditText) findViewById(R.id.editText_second_no);
        Add_button=(Button) findViewById(R.id.add_button);
        result = (TextView) findViewById(R.id.textView_answer);

        // Add_button add clicklistener
        Add_button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                // num1 or num2 double type
                // get data which is in edittext, convert it to string
                // using parse Double convert it to Double type
//                int num1 = Integer.parseInt(number1.getText().toString());
//                m1.setNum1((int) Double.parseDouble(number1.getText().toString()));
//                int num2 = Integer.parseInt(number2.getText().toString());
//                m1.setNum2((int) Double.parseDouble(number2.getText().toString()));
                // add both number and store it to sum
                // set it ot result textview
                result.setText(Integer.toString(m1.addition(Integer.parseInt(number1.getText().toString()), Integer.parseInt(number2.getText().toString()))));
            }
        });
    }
}
