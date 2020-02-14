package com.gabrielmiguelpedro.maclarenapp.ui.wallet.wallet_saldo;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.gabrielmiguelpedro.maclarenapp.Transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BalanceFragment extends Fragment {
    private MainActivity callback;
    private double total;
    private double value;
    private TextView card_selected_text,saldoTV;
    private EditText editText_FWB_Valor;
    private Button button_add;
    private ListView lv;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_wallet_balance, container, false);

        button_add = root.findViewById(R.id.button_FWB_Pagamento);
        saldoTV = root.findViewById(R.id.textView_FWB_Valor);
        lv = root.findViewById(R.id.lv);
        card_selected_text = root.findViewById(R.id.card_selected);
        editText_FWB_Valor = root.findViewById(R.id.editText_FWB_Valor);

        final List<String> cards_list = new ArrayList<>(Arrays.asList(new String[]{getString(R.string.add_card)}));
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, cards_list);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String card1 = "Cartão 1 - Mastercard -  **** 5520";
                String card2 = "Cartão 2 - Visa -  **** 2220";

                card_selected_text.setText(lv.getItemAtPosition(position).equals(card1) ? card1 : card_selected_text.getText());
                card_selected_text.setText(lv.getItemAtPosition(position).equals(card2) ? card2 : card_selected_text.getText());

                if(!card_selected_text.getText().equals(getString(R.string.selecione_cartao))){
                    editText_FWB_Valor.setEnabled(true);
                    button_add.setEnabled(true);
                }

                if (cards_list.size() <= 1) {
                    cards_list.add(card1);
                    cards_list.add(card2);
                }

                arrayAdapter.notifyDataSetChanged();
            }
        });


        total = callback.getDb().getIdTransactionsValue(callback.getUser().getUserID());

        saldoTV.setText(total + "€");

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = root.findViewById(R.id.editText_FWB_Valor);
                value = Double.parseDouble(editText.getText().toString());
                callback.getDb().addTransactions(new Transactions(0, value, callback.getUser(),null));
                saldoTV.setText(callback.getDb().getIdTransactionsValue(callback.getUser().getUserID()) + "€");
            }
        });
        return root;
    }
}
