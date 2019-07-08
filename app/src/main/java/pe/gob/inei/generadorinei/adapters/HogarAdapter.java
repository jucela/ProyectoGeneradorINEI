package pe.gob.inei.generadorinei.adapters;

/**
 * Created by otin016 on 27/06/2017.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.greendao.Hogar_g;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Hogar;


public class HogarAdapter extends RecyclerView.Adapter<HogarAdapter.ViewHolder>{
    ArrayList<Hogar> hogars;
    Context context;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public HogarAdapter(ArrayList<Hogar> hogars, Context context, OnItemClickListener onItemClickListener) {
        this.hogars = hogars;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hogar_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtNumero.setText(hogars.get(position).getId_hogar());
        holder.txtJefe.setText(hogars.get(position).getNom_jefe());
        holder.txtEstado.setText(hogars.get(position).getEstado());
        holder.txtNumeroViven.setText(hogars.get(position).getNroviven());
        if(!hogars.get(position).getEstado().equals("0")){
            holder.txtEstado.setText(context.getResources().getStringArray(R.array.visita_array_resultados)[Integer.parseInt(hogars.get(position).getEstado())]);
        }else{
            holder.txtEstado.setText("Sin estado");
        }
        if(hogars.get(position).getPrincipal().equals("1")){
            holder.txtPrincipal.setText("SI");
        }else{
            holder.txtPrincipal.setText("NO");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hogars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNumero;
        TextView txtNumeroViven;
        TextView txtJefe;
        TextView txtEstado;
        TextView txtPrincipal;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_hogar_cardview);
            txtNumero = itemView.findViewById(R.id.item_hogar_textview_numero);
            txtNumeroViven = itemView.findViewById(R.id.item_hogar_textview_numero_viven);
            txtJefe = itemView.findViewById(R.id.item_hogar_textview_jefe);
            txtEstado = itemView.findViewById(R.id.item_hogar_textview_estado);
            txtPrincipal = itemView.findViewById(R.id.item_hogar_textview_principal);
        }
    }
}

