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

    public static String getEmail(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        email = sharedPref.getString(VALOR_EMAIL, "");
        return email;

    }

    public static void saveUser(String email, Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        //guardar valores
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString(VALOR_EMAIL, email);
        edit.apply();
    }

    public static void logout(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.remove(VALOR_EMAIL);
        edit.apply();
    }


    public static String getLastcode() {
        return lastcode;
    }
}
