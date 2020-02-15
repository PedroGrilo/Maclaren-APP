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

import java.util.Date;

public class BabyCarDialog extends AppCompatDialogFragment {
    private Bundle bundle;
    private MainActivity callback;
    private int markerId;
    private long rowId;

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
                        markerId = Integer.parseInt(id);
                        markerId++;

                        double lon1 = bundle.getDouble("lon");
                        double lat1 = bundle.getDouble("lat");
                        double lon2 = bundle.getDouble("markerLon");
                        double lat2 = bundle.getDouble("markerLat");

                        double d = distanceInKmBetweenEarthCoordinates(lat1, lon1, lat2, lon2);

                        int BabyCarUse = callback.db.getUseByIdBabyCar(markerId);

                        int isUsing = callback.getUser().getIsUsing();

                        Toast.makeText(getContext(),"D: "+d+" Car: "+BabyCarUse+" User:"+isUsing, Toast.LENGTH_LONG).show();
                        if(d<=5 && BabyCarUse==0 && isUsing==0){
                            Date date = new Date();
                            Toast.makeText(getContext(),"Car: "+callback.db.getUseByIdBabyCar(markerId)+" User: "+callback.getUser().getIsUsing(), Toast.LENGTH_LONG).show();

                            callback.getUser().setIsUsing(1);//colocar o utilizador em uso!!
                            callback.getDb().setIsUseCar(1, String.valueOf(markerId));//colocar o carro em uso!!
                            rowId = callback.getDb().addHistoric(new Historic(0,date,callback.getUser(),callback.getDb().getBabyCarById(markerId)));


                            Toast.makeText(getContext(),"Car: "+callback.db.getUseByIdBabyCar(markerId)+" User: "+callback.getUser().getIsUsing(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getContext(),"RowId: "+rowId, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Saiu", Toast.LENGTH_SHORT).show();
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
