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

import java.util.Objects;

public class PermissionActivity extends AppCompatActivity {

    TextView titulo, desc;
    Intent infoFromVerificationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        titulo = findViewById(R.id.autorizar_titulo);
        desc = findViewById(R.id.autorizar_desc);
        infoFromVerificationActivity = getIntent();

        /* verifica a key que foi passada na intent para apresentar o respectivo texto no background da dialog */
        switch (Objects.requireNonNull(infoFromVerificationActivity.getExtras().getString("PERMISSION"))) {
            case "LOCATION":
                titulo.setText(R.string.accept_gps_title);
                desc.setText(R.string.accept_gps_subtext);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                break;
            case "STORAGE":
                titulo.setText(R.string.accept_storage_title);
                desc.setText(R.string.accept_storage_subtext);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                break;
            case "CAMERA":
                titulo.setText(R.string.accept_camera_title);
                desc.setText(   R.string.accept_camera_subtitle);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 3);
                break;
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.d("PERMISSION", "onRequestPermissionsResult: " + requestCode);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /* Se o utilizador aceitar as permissões de localizaçao */
                    redirectActivity(MainActivity.class);
                    break;
                } else {
                    /* Se o utilizador recusar as permissões */
                    Toast.makeText(getApplicationContext(), "PRECISA DE ACEITAR AS PERMISSÕES PARA CONTINUAR", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /* Se o utilizador aceitar as permissões de armazenamento */
                    redirectActivity(SignUpActivity.class);
                    break;
                } else {
                    /* Se o utilizador recusar as permissões */
                    Toast.makeText(getApplicationContext(), "PRECISA DE ACEITAR AS PERMISSÕES PARA CONTINUAR", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            case 3: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    /* Se o utilizador aceitar as permissões de localizaçao */
                    finish();
                    break;
                } else {
                    /* Se o utilizador recusar as permissões */
                    Toast.makeText(getApplicationContext(), "PRECISA DE ACEITAR AS PERMISSÕES PARA CONTINUAR", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }




                // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void redirectActivity(Class activityDestiny) {
        finish();
        Intent i = new Intent(PermissionActivity.this, activityDestiny);
        i.putExtras(infoFromVerificationActivity.getExtras());
        startActivity(i);
    }

}
