package com.gabrielmiguelpedro.maclarenapp.ui.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;

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

        return root;
    }


}