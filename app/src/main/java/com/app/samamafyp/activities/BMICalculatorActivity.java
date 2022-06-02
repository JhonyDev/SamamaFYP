package com.app.samamafyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.samamafyp.R;
import com.app.samamafyp.info.Info;
import com.app.samamafyp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class BMICalculatorActivity extends AppCompatActivity implements Info {

    EditText etHeight;
    EditText etWeight;
    EditText etBMI;

    String strEtHeight;
    String strEtWeight;
    String strEtBMI;
    String strIsHeart;
    String strIsDiabetic;
    String strIsBloodPressure;
    CheckBox cbDiabetic, cbBloodPressure, cbHeartDisease;

    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);
        initViews();
        initTextWatcher();
        btnSignUp.setOnClickListener(view -> signUp());
    }

    private void initTextWatcher() {
        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                castStrings();
                calculateBmi();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void calculateBmi() {
        try {
            double weight = Double.parseDouble(etWeight.getText().toString());
            double height = Double.parseDouble(etHeight.getText().toString()) / 0.032808;
            double bmi = (weight / (height * height)) * 10000;
            etBMI.setText(String.valueOf(bmi));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void castStrings() {
        strEtHeight = etHeight.getText().toString();
        strEtWeight = etWeight.getText().toString();
        strEtBMI = etBMI.getText().toString();
        strIsHeart = String.valueOf(cbHeartDisease.isChecked());
        strIsBloodPressure = String.valueOf(cbBloodPressure.isChecked());
        strIsDiabetic = String.valueOf(cbDiabetic.isChecked());
    }

    private void initViews() {
        etHeight = findViewById(R.id.et_height);
        etWeight = findViewById(R.id.et_weight);
        etBMI = findViewById(R.id.et_phone);
        btnSignUp = findViewById(R.id.bmi);
        cbDiabetic = findViewById(R.id.cbDiabetic);
        cbBloodPressure = findViewById(R.id.cbBloodPressure);
        cbHeartDisease = findViewById(R.id.cbHeartDisease);
    }

    public void back(View view) {
        finish();
    }

    private void signUp() {
        castStrings();
        SignupActivity.userModel.setBMI(strEtBMI);
        SignupActivity.userModel.setHeight(strEtHeight);
        SignupActivity.userModel.setWeight(strEtWeight);
        SignupActivity.userModel.setIsDiabetic(strIsDiabetic);
        SignupActivity.userModel.setIsBloodPressure(strIsBloodPressure);
        SignupActivity.userModel.setIsHeartDisease(strIsHeart);

        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(SignupActivity.userModel.getEmail(), SignupActivity.strEtPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                        writeData();
                    else
                        Toast.makeText(BMICalculatorActivity.this,
                                Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void writeData() {
        String uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        FirebaseDatabase.getInstance().getReference()
                .child(NODE_USERS)
                .child(uid)
                .setValue(SignupActivity.userModel)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Utils.userPojo = SignupActivity.userModel;
                        startActivity(new Intent(BMICalculatorActivity.this, DashboardActivity.class));
                        MainActivity.context.finish();
                        SignupActivity.context.finish();
                        finish();
                    } else
                        Toast.makeText(BMICalculatorActivity.this,
                                Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

}