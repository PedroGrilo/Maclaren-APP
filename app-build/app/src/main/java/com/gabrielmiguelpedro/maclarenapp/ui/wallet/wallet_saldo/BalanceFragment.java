package com.gabrielmiguelpedro.maclarenapp.ui.wallet.wallet_saldo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabrielmiguelpedro.maclarenapp.BabyCar;
import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.gabrielmiguelpedro.maclarenapp.Transactions;
import com.gabrielmiguelpedro.maclarenapp.User;

import java.util.ArrayList;
import java.util.Date;

public class BalanceFragment extends Fragment {
    private MainActivity callback;
    private double total;
    private double value;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity)context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    final View root = inflater.inflate(R.layout.fragment_wallet_balance, container, false);

    total = callback.getDb().getIdTransactionsValue(0);
    TextView textView = (TextView) root.findViewById(R.id.textView_FWB_Valor);
    textView.setText(total+"");

    Button button = (Button)root.findViewById(R.id.button_FWB_Pagamento);
    
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText editText = (EditText) root.findViewById(R.id.editText_FWB_Valor);
            value = Double.parseDouble(editText.getText().toString());

            callback.getDb().addTransactions(new Transactions(0,value, callback.getUser()));
        }
    });
    return root;
    }
}
