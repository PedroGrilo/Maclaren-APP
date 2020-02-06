package com.gabrielmiguelpedro.maclarenapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable{

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle infoBundle;
    private User u;

    DbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new DbHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* verifica se o utilizador já existe,
            se sim > dá logo read
            se não > cria um novo utilizador, para que da proxima vez que iniciar a app não precisar passar pela activity de registo ou login
         */
        /*if (SaveInfoConfig.readUser(this) == null) {
            infoBundle = getIntent().getExtras();
            String email = infoBundle.getString("EMAIL");
            String code = infoBundle.getString("CODE");
            u = new User(email, code);
            SaveInfoConfig.saveUser(u, this);
        } else {
            u = SaveInfoConfig.readUser(this);
        }*/


        checkPermissions(); // verificar permissoes de localizaão

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.emailTextView);

        navUsername.setText(u.GetName()+"!");

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_wallet, R.id.nav_settings, R.id.nav_help,R.id.nav_history,R.id.nav_wallet_balance)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }


    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            finish(); // se não tiver permissão, termina a main activity, para que o utilizador não possa voltar à mesma sem permissoes

            Bundle info = new Bundle();
            if (getIntent().getExtras() == null) {//se o utilizador ja existir, o intent é vazio, entao faz-se a verificação aqui
                info.putString("EMAIL", u.getEmail());
                info.putString("CODE", u.getLastCode());
            } else {
                info = getIntent().getExtras();
            }
            info.putString("PERMISSION", "LOCATION");


            Intent i = new Intent(this, PermissionActivity.class); //vai chamar a activity de permissoes
            i.putExtras(info);
            startActivity(i);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }


    @Override
    protected void onStop() {
        super.onStop();
        SaveInfoConfig.saveUser(u, this);
    }
}
