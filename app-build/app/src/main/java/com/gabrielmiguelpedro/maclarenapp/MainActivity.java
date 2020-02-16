package com.gabrielmiguelpedro.maclarenapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity implements Serializable {

    DBHelperClient db;
    private LinearLayout logoutbtn;
    private AppBarConfiguration mAppBarConfiguration;
    private Bundle infoBundle;
    private User user;
    TextView navUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoutbtn = findViewById(R.id.buttonLogout);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        db = new DbHelper(this); // basta usar isto para usar a bd

        user = db.getUserByEmail(SaveInfoConfig.getEmail(this));

        db.setLoggedIn(true, user);

        checkPermissions(); // verificar permissoes de localizaão

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
         navUsername = headerView.findViewById(R.id.emailTextView);

        navUsername.setText(user.GetName() + "!");

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_wallet, R.id.nav_settings, R.id.nav_history, R.id.nav_wallet_balance)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    private void Logout() {
        db.setLoggedIn(false, user);
        SaveInfoConfig.logout(this);
        Intent i = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }


    public void changeHeader(){
        user = db.getUserByEmail(SaveInfoConfig.getEmail(this));
        navUsername.setText(user.GetName() + "!");
    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            finish(); // se não tiver permissão, termina a main activity, para que o utilizador não possa voltar à mesma sem permissoes

            Bundle info = new Bundle();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public DBHelperClient getDb() {
        return db;
    }

    public User getUser() {
        return user;
    }


}
