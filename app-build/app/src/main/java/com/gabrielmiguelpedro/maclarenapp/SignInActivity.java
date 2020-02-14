package com.gabrielmiguelpedro.maclarenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gabrielmiguelpedro.maclarenapp.Exceptions.EmailErrorException;
import com.gabrielmiguelpedro.maclarenapp.Exceptions.EmptyFieldException;
import com.gabrielmiguelpedro.maclarenapp.Exceptions.InvalidFieldException;

public class SignInActivity extends AppCompatActivity {
    TextView tV_email, btn_back;
    Button btn_next;
    DBHelperMaster db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(this);
        setContentView(R.layout.activity_sign_up);
        tV_email = findViewById(R.id.si_email);
        btn_next = findViewById(R.id.btn_si_next);
        btn_back = findViewById(R.id.btn_back_su);
        backBtn();
        emailCheck();
    }

    private void backBtn() {
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void emailCheck() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (checkFields())
                        callVerificationScene();
                } catch (InvalidFieldException | EmailErrorException | EmptyFieldException e) {
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


    private boolean checkFields() throws InvalidFieldException,EmptyFieldException, EmailErrorException {
        String ptr = "^[a-z0-9.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?$";
        String email =tV_email.getText().toString();
        if (email.isEmpty())
            throw new EmptyFieldException(getString(R.string.empty_field));
        if (!email.matches(ptr))
            throw new InvalidFieldException(getString(R.string.invalid_field));
        if(!db.checkEmail(email))
            throw new EmailErrorException(getString(R.string.email_doesnt_exist));
        return true;
    }

}
