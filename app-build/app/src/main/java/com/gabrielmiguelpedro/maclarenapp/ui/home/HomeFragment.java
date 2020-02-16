package com.gabrielmiguelpedro.maclarenapp.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.gabrielmiguelpedro.maclarenapp.Assets.BitmapUtils;
import com.gabrielmiguelpedro.maclarenapp.Assets.PermissionUtils;
import com.gabrielmiguelpedro.maclarenapp.BabyCar;
import com.gabrielmiguelpedro.maclarenapp.BabyCarDialog;
import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements Serializable, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnInfoWindowClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "CARRINHOS";
    private static final int REQUEST_CAMERA = 1;
    private GoogleMap mMap;
    private MapView mapView;
    private boolean mPermissionDenied = false;
    private MainActivity callback;
    private FusedLocationProviderClient fusedLocationClient; //localização atual
    private Bundle bundle;
    private View root;
    private Button buttonQRCode;
    private static Button finishButton;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        finishButton = root.findViewById(R.id.aAlugar);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        buttonQRCode = root.findViewById(R.id.buttonQRCode);
        buttonQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new QRCodeFragment()).commit();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        bundle = this.getArguments();

        if (bundle != null) {
            if (bundle.containsKey("markerId")) {
                //Toast.makeText(getContext(), bundle.get("markerId") + "", Toast.LENGTH_SHORT).show();
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    double lon = location.getLongitude();
                                    double lat = location.getLatitude();
                                    bundle.putDouble("lon", lon);
                                    bundle.putDouble("markerLon", lon);
                                    bundle.putDouble("lat", lat);
                                    bundle.putDouble("markerLat", lat);
                                }
                            }
                        });
                openDialog();
            }
        }
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

                switch (babyCar.getBabyCarType().getId()) {
                    case 1: //  pequeno
                        icon = bbycar_s;
                        latitude += 0.00002;//para testes
                    case 2: // medio
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
                        .setTag(getString(R.string.inuse) + " - " + babyCar.getInUse());
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
        try {
            bundle = new Bundle();
            String markerId = marker.getId();
            double markerLon = marker.getPosition().longitude;
            double markerLat = marker.getPosition().latitude;

            bundle.putString("markerId", markerId);
            bundle.putDouble("markerLon", markerLon);
            bundle.putDouble("markerLat", markerLat);
            getPhoneLocation();
            openDialog();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), "Localização atual :\n" + location, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != REQUEST_CAMERA) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.CAMERA)) {
            // Activar a funcionalicidades de localização se a permissão foi dada.

        } else {
            // Flag a "true" para mostrar o erro das permissões em falta.
            mPermissionDenied = true;
        }

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

    public void getPhoneLocation() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        //Toast.makeText(getContext(),"Altitude: "+location.getAltitude()+" Longitude: "+location.getLongitude(), Toast.LENGTH_LONG).show();
                        if (location != null) {
                            //Toast.makeText(getContext(),"222Altitude: "+location.getAltitude()+" Longitude: "+location.getLongitude(), Toast.LENGTH_LONG).show();
                            double lon = location.getLongitude();
                            double lat = location.getLatitude();
                            bundle.putDouble("lon", lon);
                            bundle.putDouble("lat", lat);
                        }
                    }
                });
    }

    public void openDialog() {
        BabyCarDialog babyCarDialog = new BabyCarDialog(bundle);
        babyCarDialog.show(getFragmentManager(), "DIALOGO");
        //bundle = new Bundle();
    }


    public static void setFinishButton(boolean b) {
        if(b)
            finishButton.setVisibility(View.VISIBLE);
        else
            finishButton.setVisibility(View.GONE);
    }
}