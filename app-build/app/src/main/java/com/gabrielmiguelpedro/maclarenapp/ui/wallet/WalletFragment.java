package com.gabrielmiguelpedro.maclarenapp.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.gabrielmiguelpedro.maclarenapp.ui.wallet.wallet_saldo.BalanceFragment;

public class WalletFragment extends Fragment {

    private WalletViewModel walletViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wallet, container, false);

        walletViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        Button button = root.findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BalanceFragment balanceFragment= new BalanceFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(((ViewGroup)getView().getParent()).getId(), balanceFragment, "balanceFragment")
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(getContext(), "PRECISA DE ACEITAR AS PERMISSÕES PARA CONTINUAR", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}