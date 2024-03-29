package com.gabrielmiguelpedro.maclarenapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gabrielmiguelpedro.maclarenapp.Exceptions.EmptyFieldException;
import com.gabrielmiguelpedro.maclarenapp.Exceptions.InvalidFieldException;
import com.sun.mail.util.MailConnectException;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

import static com.gabrielmiguelpedro.maclarenapp.R.layout.activity_verification_code;

public class VerificationCodeActivity extends AppCompatActivity implements Serializable {

    DBHelperMaster db;
    SQLiteDatabase sqLiteDatabase;
    private TextView code, btn_back;
    private Button btn_next;
    private Bundle info;
    private int generatedCode;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_verification_code);
        try {
            info = getIntent().getExtras();
            email = info.getString("EMAIL");

            db = new DbHelper(this);

            String from_activity = info.getString("FROM_ACTIVITY");

            generatedCode = generateRandomCode();
           // Toast.makeText(getApplicationContext(),generatedCode+"",Toast.LENGTH_LONG).show();

           if (from_activity.equals("SIGN_UP")) {
                int id = db.getLastID();
                User user = new User(id, email, String.valueOf(generatedCode), false, new Date(), 'C', false, 0);
                db.addUser(user);
            }

            sendMaclarenCode();

            btn_back = findViewById(R.id.btn_back_vc);
            btn_next = findViewById(R.id.btn_end_vc);
            btn_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        String codeS = code.getText().toString();

                        if (codeS.equals("")) //verifica se o campo do códgo está valido
                            throw new EmptyFieldException(getString(R.string.empty_field));

                        if (!codeS.equals(String.valueOf(generatedCode)))//verifica se o campo do códgo está igual ao do mail
                            throw new InvalidFieldException(getString(R.string.invalid_field));

                        db.setIsOk(1, email);

                        SaveInfoConfig.saveUser(email, VerificationCodeActivity.this);
                        Intent

                        //acaba com a activity atual e passa os valores para a main activity e inicia-a
                        i = new Intent(VerificationCodeActivity.this, MainActivity.class);
                        finish();
                        startActivity(i);

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        finish();
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
        String message = "<center>"+getString(R.string.emailwithcode)+"<br><br><h2>" + generatedCode + "</h2></center>";
        SendEmail sendEmail = new SendEmail(this, info.getString("EMAIL"), "Your Maclaren Code", message);
        sendEmail.execute();
    }

    private int generateRandomCode() {
        Random random = new Random(System.currentTimeMillis());//para estar sempre a atualizar a cada tick
        return random.nextInt(100000) + 100000;
    }


}

