package com.gabrielmiguelpedro.maclarenapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.gabrielmiguelpedro.maclarenapp.ui.home.HomeFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BabyCarDialog extends AppCompatDialogFragment {
    private Bundle bundle;
    private MainActivity callback;
    private int markerId;
    private long rowId;
    private double d;
    private int BabyCarUse;
    private int isUsing;
    private String firstDate;
    private String lastDate;
    private int totalTime;
    private double finalDistance;
    private double cost;

    public BabyCarDialog(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(),getTheme()));
        builder.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
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

                d = distanceInKmBetweenEarthCoordinates(lat1, lon1, lat2, lon2);

                BabyCarUse = callback.db.getUseByIdBabyCar(markerId);

                isUsing = callback.getDb().getIsUsingById(callback.getUser().getUserID());

                Boolean verifications = false;

                if (d > 10)
                    Toast.makeText(getContext(), R.string.mindistance, Toast.LENGTH_LONG).show();
                else if (BabyCarUse != 0)
                    Toast.makeText(getContext(), R.string.babycarisused, Toast.LENGTH_LONG).show();
                else if (isUsing != 0)
                    Toast.makeText(getContext(), R.string.isusing, Toast.LENGTH_LONG).show();
                else
                  verifications = true;


                if (callback.getDb().getIdTransactionsValue(callback.getUser().getUserID()) > 0) {
                    if (verifications) {
                        Toast.makeText(getContext(),R.string.estaalugar,Toast.LENGTH_LONG).show();
                        long date = System.currentTimeMillis();
                        callback.getDb().setIsUsing(1, callback.getUser());//colocar o utilizador em uso!!
                        callback.getDb().setIsUseCar(1, String.valueOf(markerId));//colocar o carro em uso!!
                        rowId = callback.getDb().addHistoric(new Historic(0, date, callback.getUser(), callback.getDb().getBabyCarById(markerId)));

                       HomeFragment.setFinishButton(true);

                        callback.startService(new Intent(getContext(), MyService.class));//chamar o service
                    } else if (callback.getDb().getIsUsingById(callback.getUser().getUserID()) == 1) {

                        callback.stopService(new Intent(getContext(), MyService.class));
                        callback.getDb().setIsUsing(0, callback.getUser());//tirar utilzador de uso
                        callback.getDb().setIsUseCar(0, String.valueOf(markerId));//tirar o carro em uso!!

                        firstDate = callback.getDb().getFirstDateFromCoerdenates(callback.getDb().getLastIdFromTableHistoric());
                        lastDate = callback.getDb().getLastDateFromCoerdenates(callback.getDb().getLastIdFromTableHistoric());
                        firstDate = firstDate.substring(11, 20);
                        lastDate = lastDate.substring(11, 20);

                        /** DATE **/
                        try {
                            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                            Date fD = dateFormat.parse(firstDate);
                            Date lD = dateFormat.parse(lastDate);
                            totalTime = lD.getMinutes() - fD.getMinutes();
                        } catch (ParseException e) {
                            Log.e("TEST", "Exception", e);
                        }
                        /** /DATE **/

                        /** DISTANCIA **/
                        int aux = 0;

                        ArrayList<HistoricCoordinates> arrayList = callback.getDb().getHistoricCoordinatesById(callback.getDb().getLastIdFromTableHistoric());
                        for (HistoricCoordinates historicCoordinates : arrayList) {
                            try {
                                finalDistance += distanceInKmBetweenEarthCoordinates(arrayList.get(aux).getLat(), arrayList.get(aux).getLonge(), arrayList.get(++aux).getLat(), arrayList.get(++aux).getLonge());
                            } catch (Exception e) {
                                Toast.makeText(getContext(), "Exption", Toast.LENGTH_SHORT);
                            }

                        }
                        /** /DISTANCIA **/

                        /** COST **/
                        int carID = callback.getDb().getHistoricCarId();

                        double baseCost = callback.getDb().getCarTypeCost(carID);

                        cost = (totalTime * baseCost) + (finalDistance / 100);

                        int id_historic = callback.getDb().getLastIdFromTableHistoric();
                        Historic historic = new Historic();
                        historic.setId(id_historic);

                        Transactions transactions = new Transactions(1, -cost, callback.getUser(), historic, new Date());
                        callback.db.addTransactions(transactions);
                        HomeFragment.setFinishButton(false);
                        /** /COST **/
                    }
                } else {
                    Toast.makeText(getContext(), R.string.nomoney, Toast.LENGTH_LONG).show();
                }

            }
        })
                .setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //funciona
                    }
                });
        if (callback.getDb().getIsUsingById(callback.getUser().getUserID()) == 1) {
            builder.setTitle(R.string.finalizarAluguer)
                    .setMessage("");
        } else {
            builder.setTitle(R.string.alugarCarro)
                    .setMessage("");
        }
        return builder.create();
    }

    public double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    public double distanceInKmBetweenEarthCoordinates(double lat1, double lon1, double lat2, double lon2) {
        double earthRadiusKm = 6371;

        double dLat = degreesToRadians(lat2 - lat1);
        double dLon = degreesToRadians(lon2 - lon1);

        double lat11 = degreesToRadians(lat1);
        double lat22 = degreesToRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat11) * Math.cos(lat22);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (earthRadiusKm * c) / 0.0010000;
    }
}
