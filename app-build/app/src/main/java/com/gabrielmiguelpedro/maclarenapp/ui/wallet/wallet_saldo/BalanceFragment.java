package com.gabrielmiguelpedro.maclarenapp.ui.wallet.wallet_saldo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gabrielmiguelpedro.maclarenapp.BabyCar;
import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.gabrielmiguelpedro.maclarenapp.Transactions;

import java.util.ArrayList;

public class BalanceFragment extends Fragment {
    private MainActivity callback;
    private double total;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity)context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_wallet_balance, container, false);

    total = callback.getDb().getIdTransactionsValue(0);
    TextView textView = (TextView) root.findViewById(R.id.textView_FWB_Valor);
    textView.setText(total+"");

    return root;
    }
}
