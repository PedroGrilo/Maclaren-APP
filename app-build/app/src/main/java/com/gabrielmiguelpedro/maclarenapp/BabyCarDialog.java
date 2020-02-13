package com.gabrielmiguelpedro.maclarenapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class BabyCarDialog extends AppCompatDialogFragment {
    private Bundle bundle;
    private MainActivity callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity)context;
    }

    public BabyCarDialog( Bundle bundle )
    {
        this.bundle = bundle;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Alugar Carrinho")
                .setMessage("Pretende Alugar este Carrinho?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //funciona
                        String id = bundle.getString("markerId");
                        id = id.substring(1);
                        int markerId = Integer.parseInt(id);
                        markerId++;

                        double lon1 = bundle.getDouble("lon");
                        double lat1 = bundle.getDouble("lat");
                        double lon2 = bundle.getDouble("markerLon");
                        double lat2 = bundle.getDouble("markerLat");

                        double d = distanceInKmBetweenEarthCoordinates(lat1, lon1, lat2, lon2);

                        int BabyCarUse = callback.db.getUseByIdBabyCar(markerId);

                        if(d<=5 && BabyCarUse==0){
                            Toast.makeText(getContext(),"Está a menos de 5m"+d+ " ou o carro não está já está em uso.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"Está a mais de 5m: "+d+ " ou o carro já está em uso.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //funciona
                    }
                });
        return builder.create();
    }

    public double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    public double distanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2){
        double earthRadiusKm = 6371;

        double dLat = degreesToRadians(lat2-lat1);
        double dLon = degreesToRadians(lon2-lon1);

        double lat11 = degreesToRadians(lat1);
        double lat22 = degreesToRadians(lat2);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat11) * Math.cos(lat22);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return (earthRadiusKm * c)/0.0010000;
    }
}
