package com.gabrielmiguelpedro.maclarenapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class PermissionActivity extends AppCompatActivity {

    private Bundle infoFromVerificationActivity;
    TextView titulo,desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        titulo = findViewById(R.id.autorizar_titulo);
        desc = findViewById(R.id.autorizar_desc);
        infoFromVerificationActivity = getIntent().getExtras();

        assert infoFromVerificationActivity != null;
        switch (infoFromVerificationActivity.getString("PERMISSION")){
            case "LOCATION":
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                break;
            case "STORAGE":
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                break;
            default:
                finish();

        }


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("PERMISSION", "onRequestPermissionsResult: "+requestCode);
        switch (requestCode) {
            case 1: {
                titulo.setText(R.string.accept_gps_title);
                desc.setText(R.string.accept_gps_subtext);
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(PermissionActivity.this, MainActivity.class);
                    finish();
                    startActivity(intent, infoFromVerificationActivity);
                } else {
                    finish();
                }
                return;
            }
            case 2:{
                finish();
            }
        }
    }
}
