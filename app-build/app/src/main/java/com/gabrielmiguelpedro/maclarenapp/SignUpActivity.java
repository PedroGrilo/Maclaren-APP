package com.gabrielmiguelpedro.maclarenapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.gabrielmiguelpedro.maclarenapp.Exceptions.InvalidFieldException;


public class SignUpActivity extends AppCompatActivity {


    TextView tV_email;
    Button btn_next_email;
    DBHelperMaster db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DbHelper(this);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        /* verifica as permissoes de armazenamento*/
        checkPermissionsStorage();

        tV_email = findViewById(R.id.si_email);
        btn_next_email = findViewById(R.id.btn_si_next);

        SignInBtn();//botao de login
        emailCheck();//verificação do email e continuação do registo
    }

    private void checkPermissionsStorage() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            finish();// se não tiver permissão, termina a sign in activity, para que o utilizador não possa voltar à mesma sem permissoes
            Bundle b = new Bundle();
            b.putString("PERMISSION", "STORAGE");
            Intent i = new Intent(this, PermissionActivity.class);
            i.putExtras(b);
            startActivity(i);
        }
    }

    public void SignInBtn() {
        TextView btn_iniciar_sessao = findViewById(R.id.iniciar_sessao);
        btn_iniciar_sessao.setOnClickListener(new View.OnClickListener() {
            @Override
            //acaba com a activity atual para começar a nova, ou seja, a activity de login
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
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
                        callVerificationActivity(); // chama um método para passar à proxima acitivity
                } catch (InvalidFieldException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void callVerificationActivity() {
        Intent intent = new Intent(SignUpActivity.this, VerificationCodeActivity.class);
        Bundle info = new Bundle();
        info.putString("EMAIL", tV_email.getText().toString()); //Email
        intent.putExtras(info);
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

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if (sharedPref.contains("EMAIL")) {
            finish();
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

}
