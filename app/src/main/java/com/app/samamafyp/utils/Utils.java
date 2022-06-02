package com.app.samamafyp.utils;

import android.widget.EditText;

import com.app.samamafyp.model.UserPojo;

public class Utils {
    public static UserPojo userPojo;
    public static boolean validEt(EditText etUserName, String strEtUserName) {
        if (strEtUserName.isEmpty()) {
            etUserName.setError("Field Empty");
            return false;
        }
        return true;
    }
}
