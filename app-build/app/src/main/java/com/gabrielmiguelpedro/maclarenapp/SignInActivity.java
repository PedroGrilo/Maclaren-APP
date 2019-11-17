package com.gabrielmiguelpedro.maclarenapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.gabrielmiguelpedro.maclarenapp.Exceptions.InvalidFieldException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements Serializable {

    protected static final String FILE_NAME = "users.dat";
    TextView tV_email;
    Button btn_next_email;
    protected static File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        checkPermissionsStorage();

        tV_email = findViewById(R.id.si_email);
        btn_next_email = findViewById(R.id.btn_si_next);
        SignUpBtn();
        emailCheck();
    }

    private void checkPermissionsStorage() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            Bundle b = new Bundle();
            b.putString("PERMISSION","STORAGE");
             Intent i = new Intent(this, PermissionActivity.class);
             i.putExtras(b);
            startActivity(i);
        }
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

    @Override
    protected void onStart() {
        super.onStart();
        f = new File (getApplicationContext().getFilesDir(),FILE_NAME);
        if(readUser() != null)
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public User readUser() {
        User temp = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f.getAbsolutePath()));
            temp = (User) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return temp;
        }
    }


}
