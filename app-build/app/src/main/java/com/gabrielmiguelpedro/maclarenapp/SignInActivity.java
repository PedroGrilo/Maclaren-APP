package com.gabrielmiguelpedro.maclarenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gabrielmiguelpedro.maclarenapp.Exceptions.InvalidFieldException;

public class SignInActivity extends AppCompatActivity{
    TextView tV_email;
    Button btn_next_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        tV_email = findViewById(R.id.si_email);
        btn_next_email = findViewById(R.id.btn_si_next);
        SignUpBtn();
        emailCheck();
    }

    public void SignUpBtn(){
        TextView btn_iniciar_sessao = findViewById(R.id.iniciar_sessao);
        btn_iniciar_sessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void emailCheck() {
        btn_next_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checkFields())
                        callVerificationScene();
                } catch (InvalidFieldException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void callVerificationScene() {
        Intent intent = new Intent(SignInActivity.this, VerificationCodeActivity.class);
        Bundle email = new Bundle();
        email.putString("EMAIL", tV_email.getText().toString()); //Email
        intent.putExtras(email);
        finish();
        startActivity(intent);
    }


    private boolean checkFields() throws InvalidFieldException {
        String ptr = "^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?$";
        if (tV_email.getText().toString().isEmpty())
            throw new InvalidFieldException();
        if (!tV_email.getText().toString().matches(ptr))
            throw new InvalidFieldException();
        return true;
    }


}
