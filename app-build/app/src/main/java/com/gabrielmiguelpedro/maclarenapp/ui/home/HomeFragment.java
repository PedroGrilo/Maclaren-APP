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
import com.gabrielmiguelpedro.maclarenapp.DbHelper;
import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
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

import java.io.Serializable;

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
    DbHelper db;//TESTE

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity)context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

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

            mMap.addMarker(new MarkerOptions().position(new LatLng(38.53760, -8.87806))
                    .title("BabyCar S")
                    .icon(bbycar_s)
                    .snippet("A wonderful chicken"))
                    .setTag("CHICK01");
            mMap.addMarker(new MarkerOptions().position(new LatLng(38.53745, -8.87796))
                    .title("BabyCar M")
                    .icon(bbycar_m)
                    .snippet("A wonderful chicken"))
                    .setTag("CHICK02");
            mMap.addMarker(new MarkerOptions().position(new LatLng(38.53745, -8.87816))
                    .title("BabyCar XL")
                    .icon(bbycar_xl)
                    .snippet("A wonderful chicken"))
                    .setTag("CHICK03");
            mMap.addMarker(new MarkerOptions().position(new LatLng(38.53745, -8.87826))
                    .title("BabyCar M")
                    .icon(bbycar_m)
                    .snippet("A wonderful chicken"))
                    .setTag("CHICK04");
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

       // Log.d(TAG, "Carrinhos: " + callback.getDb().getAllBabyCars().get(1).getComments());  // NAO TA A FUNFAR, PERGUNTA AO STOR!! isto diz que chama a bd recursivamente :/

        Toast.makeText(getContext(), "Marcador Selecionado:\n" + marker.getTag(),
                Toast.LENGTH_SHORT).show();
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
}