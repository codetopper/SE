package com.example.karat.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.karat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class ResetPwDisplay extends AppCompatActivity {

    private static final String TAG = ResetPwDisplay.class.getSimpleName();
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Button reset;
    private EditText emailEditText;
    private Switch domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw_display);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        String email;
        boolean isStaff = domain.isChecked();

        if (isStaff) {
            email = "Staff-".concat(emailEditText.getText().toString());
        } else {
            email = "Customer-".concat(emailEditText.getText().toString());
        }

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        } else {
                            Log.d(TAG, "User not found...");
                        }
                    }
                });

    }

    private void initializeUI(){
        reset = findViewById(R.id.ResetPw);
        emailEditText = findViewById(R.id.email);
        domain = findViewById(R.id.domain);
    }
}
