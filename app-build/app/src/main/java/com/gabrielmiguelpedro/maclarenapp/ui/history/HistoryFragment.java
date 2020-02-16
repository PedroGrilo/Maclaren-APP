package com.gabrielmiguelpedro.maclarenapp.ui.history;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.gabrielmiguelpedro.maclarenapp.Historic;
import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private MainActivity callback;
    private ListView listView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        listView = root.findViewById(R.id.history);


        List<String> historicFormatted = new ArrayList<>();

        final List<Historic> historicList = callback.getDb().getHistoricByUserId(callback.getUser().getUserID());

        for (Historic historic : historicList)
            historicFormatted.add(historic.getBabyCar().getBabyCarType().getName() + " - " + historic.getBabyCar().getComments() + " - " + historic.getDate());

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, historicFormatted);

        listView.setAdapter(arrayAdapter);


        return root;
    }
}