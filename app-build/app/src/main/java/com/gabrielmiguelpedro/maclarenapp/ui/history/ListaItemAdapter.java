package com.gabrielmiguelpedro.maclarenapp.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielmiguelpedro.maclarenapp.Historic;
import com.gabrielmiguelpedro.maclarenapp.R;

import java.util.Date;
import java.util.List;

public class ListaItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<Historic> items;
    private final OnItemClickListener itemClickListener;

    public ListaItemAdapter(List<Historic> items, OnItemClickListener clickListener) {
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
                String bbycar = ((Historic) items.get(position)).getBabyCar().getBabyCarType().getName();

                historicViewHolder.nome.setText(bbycar);
                historicViewHolder.desc.setText(((Historic) items.get(position)).getBabyCar().getComments());

                if(bbycar.equals("Carrinho Pequeno")){
                    historicViewHolder.foto.setImageResource(R.drawable.baby_car_aluguer_s);
                }else if(bbycar.equals("Carrinho Médio")){
                    historicViewHolder.foto.setImageResource(R.drawable.baby_car_aluguer_m);
                }else if(bbycar.equals("Carrinho Grande")){
                    historicViewHolder.foto.setImageResource(R.drawable.baby_car_aluguer_l);
                }else if(bbycar.equals("Carrinho Gigante Edér")){
                    historicViewHolder.foto.setImageResource(R.drawable.baby_car_aluguer_xl);
                }

                historicViewHolder.data.setText("" + new Date(((Historic) items.get(position)).getDate()));


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
        TextView nome, desc,data;
        ImageView foto;

        HistoricViewHolder(View view) {
            super(view);
            foto = view.findViewById(R.id.iconeImagem);
            nome = view.findViewById(R.id.textoNome);
            desc = view.findViewById(R.id.textoDesc);
            data = view.findViewById(R.id.textoData);
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