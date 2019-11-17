package com.gabrielmiguelpedro.maclarenapp;

import android.Manifest;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import static com.gabrielmiguelpedro.maclarenapp.SignInActivity.FILE_NAME;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Serializable {

    private GoogleMap mMap;
    private Bundle infoBundle;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (readUser() == null) {
            infoBundle = getIntent().getExtras();
            String email = infoBundle.getString("EMAIL");
            String code = infoBundle.getString("CODE");
            u = new UserClass(email, code);
            saveUser(u);
        } else {
            System.out.println("entro aqui");
            u = readUser();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        Toast.makeText(getApplicationContext(), u.getEmail() + " - " + u.getLastLogin(), Toast.LENGTH_SHORT).show();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public static void saveUser(User user)  {
        System.out.println("a salvar...");
        try {
            FileOutputStream out = new FileOutputStream(SignInActivity.f.getAbsolutePath());
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(user);
            System.out.println("File created");
            oout.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public User readUser() {
       User temp = null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SignInActivity.f.getAbsolutePath()));
            temp = (User) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            return temp;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveUser(u);
    }
}
