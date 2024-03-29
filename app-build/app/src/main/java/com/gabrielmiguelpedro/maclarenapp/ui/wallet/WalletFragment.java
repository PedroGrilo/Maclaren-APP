package com.gabrielmiguelpedro.maclarenapp.ui.wallet;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.gabrielmiguelpedro.maclarenapp.Transactions;
import com.gabrielmiguelpedro.maclarenapp.ui.wallet.wallet_saldo.BalanceFragment;

import java.util.Date;

public class WalletFragment extends Fragment {

    private MainActivity callback;
    private double total;
    TextView textView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View root = inflater.inflate(R.layout.fragment_wallet, container, false);
        textView = root.findViewById(R.id.textViewSaldo);

        updateWallet();

        Button button = root.findViewById(R.id.button3);
        Button button1 = root.findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BalanceFragment balanceFragment = new BalanceFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, balanceFragment); //o primeiro parametro é tp o atual, ou seja, a view atual e nós queremos dar replace pelo o balance fragment
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.getDb().addTransactionsDeposit(new Transactions(0, 5, callback.getUser(), new Date()));
                updateWallet();
            }
        });
        return root;
    }

    private void updateWallet() {
        total = callback.getDb().getIdTransactionsValue(callback.getUser().getUserID());
        textView.setText(String.format("%10.2f", total) + " €");
    }
}