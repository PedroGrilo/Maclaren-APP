package com.gabrielmiguelpedro.maclarenapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class BabyCarDialog extends AppCompatDialogFragment {
    private Bundle bundle;

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
                        double lon1 = bundle.getDouble("lon");
                        double lat1 = bundle.getDouble("lat");
                        double lon2 = bundle.getDouble("markerLon");
                        double lat2 = bundle.getDouble("markerLat");
                        String id = bundle.getString("markerId");

                        double R = 6371e3; // metres
                        double a1 = Math.toRadians(lat1);
                        double a2 = Math.toRadians(lat2);
                        double da = Math.toRadians(lat2-lat1);
                        double dd = Math.toRadians(lon2-lon1);

                        double a = Math.sin(da/2) * Math.sin(da/2) +
                                Math.cos(a1) * Math.cos(a2) *
                                        Math.sin(dd/2) * Math.sin(dd/2);
                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

                        double d = R * c;

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
}
