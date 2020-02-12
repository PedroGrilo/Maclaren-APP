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
                        Toast.makeText(getContext(),"Longitude: "+bundle.getDouble("longe"),Toast.LENGTH_LONG).show();
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
