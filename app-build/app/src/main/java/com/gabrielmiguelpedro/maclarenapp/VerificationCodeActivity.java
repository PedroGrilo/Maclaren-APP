package com.gabrielmiguelpedro.maclarenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gabrielmiguelpedro.maclarenapp.Exceptions.InvalidFieldException;

import java.util.Random;

import static com.gabrielmiguelpedro.maclarenapp.R.layout.activity_verification_code;

public class VerificationCodeActivity extends AppCompatActivity {

    private TextView code, btn_back;
    private Button btn_next;
    private Bundle info;
    private int generatedCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_verification_code);
        info = getIntent().getExtras();

        generatedCode = generateRandomCode();

        sendMaclarenCode();

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

                        if(!codeS.equals(String.valueOf(generatedCode)))//verifica se o campo do códgo está igual ao do mail
                            throw new InvalidFieldException();


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

    private void sendMaclarenCode() {
        String message = "<center><h1>Verify your email address</h1><br><h3>To verify your email address, enter this code in your app.<br><br><h2>"+generatedCode+"</h2></center>";
        SendEmail sendEmail = new SendEmail(this,info.getString("EMAIL"),"Your Maclaren Code",message);
        sendEmail.execute();
    }

    private int generateRandomCode(){
        Random random = new Random(System.currentTimeMillis());//para estar sempre a atualizar a cada tick
        return random.nextInt(100000) + 100000;
    }


    }

