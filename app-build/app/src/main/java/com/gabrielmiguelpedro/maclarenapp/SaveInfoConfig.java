package com.gabrielmiguelpedro.maclarenapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.Serializable;

public class SaveInfoConfig implements Serializable {

    private static final String VALOR_EMAIL = "EMAIL";
    private static final String VALOR_CODE = "CODE";
    public static String email;
    public static String lastcode;

    public static User readUser(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        email = sharedPref.getString(VALOR_EMAIL, "");
        lastcode = sharedPref.getString(VALOR_CODE, "");

        if (!email.equals("") && !lastcode.equals(""))
            return new User(email, lastcode);

        return null;

    }

    public static void saveUser(User user, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        //guardar valores
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(VALOR_EMAIL, user.getEmail());
        edit.putString(VALOR_CODE, user.getLastCode());
        edit.commit();
    }

    public static String getEmail() {
        return email;
    }

    public static String getLastcode() {
        return lastcode;
    }
}
