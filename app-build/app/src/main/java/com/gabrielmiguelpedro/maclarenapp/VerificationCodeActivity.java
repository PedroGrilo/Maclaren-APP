package com.gabrielmiguelpedro.maclarenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

                        if (codeS.equals("")) //verifica se o campo do códgo está valido
                            throw new InvalidFieldException();

                        Bundle info = getIntent().getExtras();
                        Intent i;

                        //acaba com a activity atual e passa os valores para a main activity e inicia-a
                        info.putString("CODE", codeS);
                        i = new Intent(VerificationCodeActivity.this, MainActivity.class);
                        i.putExtras(info);
                        finish();
                        startActivity(i);

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

