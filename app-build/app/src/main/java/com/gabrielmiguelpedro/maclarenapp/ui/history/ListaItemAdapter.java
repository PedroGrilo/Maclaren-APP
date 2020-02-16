package com.gabrielmiguelpedro.maclarenapp.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielmiguelpedro.maclarenapp.DbHelper;
import com.gabrielmiguelpedro.maclarenapp.Historic;
import com.gabrielmiguelpedro.maclarenapp.R;
import com.gabrielmiguelpedro.maclarenapp.Transactions;

import java.util.Date;
import java.util.List;

public class ListaItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Transactions> items;
    private final OnItemClickListener itemClickListener;

    public ListaItemAdapter(List<Transactions> items, OnItemClickListener clickListener) {
        this.items = items;
        this.itemClickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        Object currObject = items.get(position);
        if (currObject != null) {
            return 1;
        }
        return 0;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 0:
            case 1:
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_historic, parent, false);
                return new HistoricViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case 0:
            case 1:
            default:
                HistoricViewHolder historicViewHolder = (HistoricViewHolder) holder;
                String bbycar = ((Transactions) items.get(position)).getHistoric().getBabyCar().getBabyCarType().getName();

                historicViewHolder.nome.setText(bbycar);
                historicViewHolder.desc.setText(((Transactions) items.get(position)).getHistoric().getBabyCar().getComments());

                if(bbycar.equals(DbHelper.CAR_S)){
                    historicViewHolder.foto.setImageResource(R.drawable.baby_car_aluguer_s);
                }else if(bbycar.equals(DbHelper.CAR_M)){
                    historicViewHolder.foto.setImageResource(R.drawable.baby_car_aluguer_m);
                }else if(bbycar.equals(DbHelper.CAR_L)){
                    historicViewHolder.foto.setImageResource(R.drawable.baby_car_aluguer_l);
                }else if(bbycar.equals(DbHelper.CAR_XL)){
                    historicViewHolder.foto.setImageResource(R.drawable.baby_car_aluguer_xl);
                }else{
                    historicViewHolder.foto.setImageResource(R.drawable.money);
                }

                double value = Math.abs(items.get(position).getValue());

                historicViewHolder.data.setText((new Date(((Transactions) items.get(position)).getHistoric().getDate()))+"");
                historicViewHolder.cost.setText(value  + "€");

                historicViewHolder.bind(items.get(position), itemClickListener);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Object item);
    }

    class HistoricViewHolder extends RecyclerView.ViewHolder {
        TextView nome, desc,data,cost;
        ImageView foto;

        HistoricViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.iconeImagem);
            nome = view.findViewById(R.id.textoNome);
            desc = view.findViewById(R.id.textoDesc);
            data = view.findViewById(R.id.textoData);
            cost = view.findViewById(R.id.textoCost);
        }

        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}