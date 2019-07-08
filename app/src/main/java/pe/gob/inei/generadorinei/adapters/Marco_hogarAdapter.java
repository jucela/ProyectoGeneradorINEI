package pe.gob.inei.generadorinei.adapters;

/**
 * Created by otin016 on 27/06/2017.
 */

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.model.pojos.encuesta.ItemMarco_hogar;


public class Marco_hogarAdapter extends RecyclerView.Adapter<Marco_hogarAdapter.ViewHolder>{
    ArrayList<ItemMarco_hogar> ItemMarco_hogars;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public Marco_hogarAdapter(ArrayList<ItemMarco_hogar> ItemMarco_hogars, OnItemClickListener onItemClickListener) {
        this.ItemMarco_hogars = ItemMarco_hogars;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marco_hogar_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtId.setText(String.valueOf(ItemMarco_hogars.get(position).get_id()));
        holder.txtAnio.setText(String.valueOf(ItemMarco_hogars.get(position).getAnio()));
        holder.txtMes.setText(String.valueOf(ItemMarco_hogars.get(position).getMes()));
        holder.txtPeriodo.setText(String.valueOf(ItemMarco_hogars.get(position).getPeriodo()));
        holder.txtZona.setText(String.valueOf(ItemMarco_hogars.get(position).getZona()));
        holder.txtNroOrden.setText(String.valueOf(ItemMarco_hogars.get(position).getNorden()));
        if(ItemMarco_hogars.get(position).getEstado().equals("1")) holder.txtEstado.setText("1");
        else holder.txtEstado.setText("0");
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemMarco_hogars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView txtId;
        TextView txtAnio;
        TextView txtMes;
        TextView txtPeriodo;
        TextView txtZona;
        TextView txtNroOrden;
        TextView txtEstado;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.marco_item_cardview);
            txtId = itemView.findViewById(R.id.marco_item_txtId);
            txtAnio = itemView.findViewById(R.id.marco_item_txtAnio);
            txtMes = itemView.findViewById(R.id.marco_item_txtMes);
            txtPeriodo = itemView.findViewById(R.id.marco_item_txtPeriodo);
            txtZona = itemView.findViewById(R.id.marco_item_txtZona);
            txtNroOrden = itemView.findViewById(R.id.marco_item_txtNroOrden);
            txtEstado = itemView.findViewById(R.id.marco_item_txtEstado);
        }
    }
}

