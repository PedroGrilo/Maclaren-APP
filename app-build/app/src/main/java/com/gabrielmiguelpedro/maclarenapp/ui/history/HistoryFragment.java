package com.gabrielmiguelpedro.maclarenapp.ui.history;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielmiguelpedro.maclarenapp.Historic;
import com.gabrielmiguelpedro.maclarenapp.MainActivity;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.gabrielmiguelpedro.maclarenapp.Transactions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private MainActivity callback;
    private RecyclerView recyclerView;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        historyViewModel = ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

         List<Transactions> historicList = callback.getDb().getHistoricByUserId(callback.getUser().getUserID());

        recyclerView= root.findViewById(R.id.historico);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ListaItemAdapter adapter = new ListaItemAdapter(historicList, new
                ListaItemAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Object item) {
                    }
                });
        recyclerView.setAdapter(adapter);


        return root;
    }
}