package com.app.samamafyp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.app.samamafyp.R;
import com.app.samamafyp.info.Info;
import com.app.samamafyp.model.UserPojo;
import com.app.samamafyp.utils.DialogUtils;
import com.app.samamafyp.utils.Utils;

public class SignupActivity extends AppCompatActivity implements Info {

    public static UserPojo userModel;
    public static String strEtPassword;
    public static Activity context;
    boolean isPassVisible = false;
    EditText etUserName;
    EditText etEmail;
    EditText etAge;
    EditText etPassword;
    EditText etConfirmPassword;
    String strEtUserName;
    String strEtEmail;
    String strEtAge;
    String strEtConfirmPassword;
    RadioButton rbMale;
    RadioButton rbFemale;
    RadioButton rbOther;
    Dialog dgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = this;
        initViews();
        dgLoading = new Dialog(this);
        DialogUtils.initLoadingDialog(dgLoading);
    }


    public void showPassword(View view) {
        if (!isPassVisible) {
            etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isPassVisible = true;
        } else {
            etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isPassVisible = false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void castStrings() {
        strEtAge = etAge.getText().toString();
        strEtEmail = etEmail.getText().toString();
        strEtUserName = etUserName.getText().toString();
        strEtPassword = etPassword.getText().toString();
        strEtConfirmPassword = etConfirmPassword.getText().toString();
    }

    private void initViews() {
        etUserName = findViewById(R.id.et_user_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_pass);
        etAge = findViewById(R.id.et_age);
        etConfirmPassword = findViewById(R.id.et_confirm_pass);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        rbOther = findViewById(R.id.rb_others);
    }

    public void back(View view) {
        finish();
    }

    public void signUp(View view) {
        castStrings();

        String gender = getGender();

        if (!Utils.validEt(etUserName, strEtUserName))
            return;

        if (!Utils.validEt(etEmail, strEtEmail))
            return;

        if (!Utils.validEt(etAge, strEtAge))
            return;

        if (!Utils.validEt(etPassword, strEtPassword))
            return;

        if (!strEtPassword.equals(strEtConfirmPassword))
            return;

        userModel = new UserPojo(strEtUserName,
                strEtEmail,
                strEtAge,
                gender,
                "",
                "",
                "",
                "", "", "");

        startActivity(new Intent(this, BMICalculatorActivity.class));

    }

    private String getGender() {
        if (rbOther.isChecked())
            return GENDER_OTHERS;
        if (rbFemale.isChecked())
            return GENDER_FEMALE;
        return GENDER_MALE;
    }

}