package com.gabrielmiguelpedro.maclarenapp.ui.wallet.wallet_saldo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gabrielmiguelpedro.maclarenapp.R;

public class BalanceFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_wallet_balance, container, false);
    return root;
    }
}
