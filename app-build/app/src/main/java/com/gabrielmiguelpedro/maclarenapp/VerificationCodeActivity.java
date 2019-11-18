package com.gabrielmiguelpedro.maclarenapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gabrielmiguelpedro.maclarenapp.Exceptions.InvalidFieldException;

import static com.gabrielmiguelpedro.maclarenapp.R.layout.activity_verification_code;

public class VerificationCodeActivity extends AppCompatActivity {

    private TextView code, btn_back;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_verification_code);
        try {
            btn_back = findViewById(R.id.btn_back_vc);
            btn_next = findViewById(R.id.btn_end_vc);
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String codeS = code.getText().toString();
                        if (codeS.equals(""))
                            throw new InvalidFieldException();

                        Bundle info = getIntent().getExtras();
                        info.putString("CODE", codeS);
                        Intent i;
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            info.putString("PERMISSION", "LOCATION");
                            i = new Intent(VerificationCodeActivity.this, PermissionActivity.class);
                            i.putExtras(info);
                            finish();
                            startActivity(i, info);
                        } else {
                            i = new Intent(VerificationCodeActivity.this, MainActivity.class);
                            finish();
                            i.putExtras(info);
                            startActivity(i, info);
                        }
                    } catch (InvalidFieldException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
            code = findViewById(R.id.code_tv);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

}

