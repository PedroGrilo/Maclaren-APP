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
import com.gabrielmiguelpedro.maclarenapp.ui.wallet.wallet_saldo.BalanceFragment;

public class WalletFragment extends Fragment {

    private WalletViewModel walletViewModel;
    private MainActivity callback;
    private double total;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        total = callback.getDb().getIdTransactionsValue(callback.getUser().getUserID());
        TextView textView = root.findViewById(R.id.textViewSaldo);
        textView.setText(total + " €");

        walletViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        Button button = root.findViewById(R.id.button3);
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

        return root;
    }
}