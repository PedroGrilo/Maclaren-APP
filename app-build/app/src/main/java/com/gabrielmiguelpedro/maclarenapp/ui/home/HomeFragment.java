package com.gabrielmiguelpedro.maclarenapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gabrielmiguelpedro.maclarenapp.Assets.BitmapUtils;
import com.gabrielmiguelpedro.maclarenapp.Assets.PermissionUtils;
import com.gabrielmiguelpedro.maclarenapp.BabyCar;
import com.gabrielmiguelpedro.maclarenapp.DbHelper;
import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment implements Serializable,GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnInfoWindowClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1 ;
    private static final String TAG = "CARRINHOS" ;
    private HomeViewModel homeViewModel;
    private GoogleMap mMap;
    private MapView mapView;
    private SupportMapFragment mapFragment;
    private boolean mPermissionDenied = false;
    private MainActivity callback;
    private int isUsing=0; //TESTE2
    private String lastSnipet; //TESTE2
    private FusedLocationProviderClient fusedLocationClient; //localização atual
    DbHelper db;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            BitmapDescriptor bbycar_s = BitmapUtils.bitmapDescriptorFromVector(getContext(),
                    R.drawable.baby_car_aluguer_s, 64, 64);

            BitmapDescriptor bbycar_m = BitmapUtils.bitmapDescriptorFromVector(getContext(),
                    R.drawable.baby_car_aluguer_m, 64, 64);

            BitmapDescriptor bbycar_xl = BitmapUtils.bitmapDescriptorFromVector(getContext(),
                    R.drawable.baby_car_aluguer_xl, 64, 64);
            // Load markers from somewhere. This is just an example!

            Log.d(TAG, "enableMyLocation: " + callback.getDb().getAllBabyCars()); // PERGUNTA AO STOR PQ QUE ISTO NAO DA?/ Ok eu pergunto

            ArrayList<BabyCar> cars = callback.getDb().getAllBabyCars();


            for (BabyCar babyCar : cars) {
                Log.d(TAG, "cars: " + babyCar.getComments());

                BitmapDescriptor icon = null;
                double latitude = 38.53760;

                double longitude = -8.87806;

                switch (babyCar.getBabyCarType().getId()){
                    case 1 : //  pequeno
                        icon = bbycar_s;
                        latitude += 0.00002;//para testes
                    case 2 : // medio
                        icon = bbycar_m;
                        longitude += 0.00002;//para testes
                    case 3: // grande
                        icon = bbycar_xl;
                        latitude += 0.00003;//para testes
                    case 4: //giaante
                        icon = bbycar_xl; //temos de fazer um icon;
                        latitude += 0.00002;//para testes
                        longitude += 0.00003;//para testes
                }


                mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                        .title(babyCar.getBabyCarType().getName())
                        .icon(icon)
                        .snippet(babyCar.getComments()))
                        .setTag(getString(R.string.inuse)+ " - " +babyCar.isInUse());
            }

            LocationManager locationManager = (LocationManager)
                    getActivity().getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
                            LatLng(location.getLatitude(), location.getLongitude()), 20));
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            locationManager.requestSingleUpdate(criteria, locationListener, null);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        getPhoneLocation();
        //Log.d(TAG, "Carrinhos: " + callback.getDb().getAllBabyCars().get(1).getComments());  // NAO TA A FUNFAR, PERGUNTA AO STOR!! isto diz que chama a bd recursivamente :/
        Toast.makeText(getContext(), "o snipet:"+marker.getSnippet()+":", Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "o lastnipet:"+lastSnipet+":", Toast.LENGTH_SHORT).show();
        if(isUsing==0 || (marker.getSnippet().equals(lastSnipet))){
            /*TODO
            * Verificar saldo da conta, se seim next(Verificar se há montante, criar tabela historico para guardar os dados)
            * Adicionar ao historico de 5 em 5 segundos coordenadas(Encher tablea cooredenadas e adciona-las ao historico)
            * Verificar se há dinhiro para a viagem senão cancelar.
            * Permitir finalizar viagem
            * Desconar dinheiro ao minuto(Defenir preço para cada tipo de carrinho)
            * */
            isUsing=1;
            marker.setSnippet("Finalizar");
            lastSnipet = marker.getSnippet();
        }else{
            Toast.makeText(getContext(), "Só pode usar um carrinho.", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "Botão da minha localização carregado",
                Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Localização atual :\n" + location, Toast.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Activar a funcionalicidades de localização se a permissão foi dada.
            enableMyLocation();
        } else {
            // Flag a "true" para mostrar o erro das permissões em falta.
            mPermissionDenied = true;
        }
    }

    public void getPhoneLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Toast.makeText(getContext(),"Localizacao"+location.toString(), Toast.LENGTH_LONG);
                        if (location != null) {
                            Toast.makeText(getContext(),"2Localizacao"+location.toString(), Toast.LENGTH_LONG);
                        }
                    }
                });
    }
}