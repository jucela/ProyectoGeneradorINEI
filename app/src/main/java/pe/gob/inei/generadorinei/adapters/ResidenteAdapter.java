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
import pe.gob.inei.generadorinei.greendao.Residente_g;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Residente;
import pe.gob.inei.generadorinei.util.Herramientas;


public class ResidenteAdapter extends RecyclerView.Adapter<ResidenteAdapter.ViewHolder>{
    ArrayList<Residente> residentes;
    OnItemClickListener onItemClickListener;
    Context context;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public ResidenteAdapter(ArrayList<Residente> residentes, Context context, OnItemClickListener onItemClickListener) {
        this.residentes = residentes;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.residente_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String c2_p205_a;
        holder.txtNumero.setText(residentes.get(position).getId_residente());
        holder.txtNombre.setText(residentes.get(position).getNombre());

        holder.txtParentesco.setText(context.getResources().getStringArray(R.array.modulo_2_p203_parentescos)[Integer.parseInt(residentes.get(position).getParentesco())]);

        if(!Herramientas.texto(residentes.get(position).getSexo()).equals(""))
            holder.txtSexo.setText(context.getResources().getStringArray(R.array.modulo_2_p204_sexo)[Integer.parseInt(residentes.get(position).getSexo())]);
        else holder.txtSexo.setText("");

        if(Herramientas.texto(residentes.get(position).getEdad_anio()).equals("") && Herramientas.texto(residentes.get(position).getEdad_mes()).equals("")){
            holder.txtEdad.setText("");
        }else{
            if(!Herramientas.texto(residentes.get(position).getEdad_anio()).equals(""))
                holder.txtEdad.setText(String.valueOf(Herramientas.texto(residentes.get(position).getEdad_anio())) + " Años");
            else
                holder.txtEdad.setText(String.valueOf(Herramientas.texto(residentes.get(position).getEdad_mes())) + " Meses");
        }

        if(!Herramientas.texto(residentes.get(position).getEstado_civil()).equals(""))
            holder.txtEstadoCivil.setText(context.getResources().getStringArray(R.array.modulo_2_p206_estado_civil)[Integer.parseInt(residentes.get(position).getEstado_civil())]);
        else holder.txtEstadoCivil.setText("");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position);
            }
        });

        holder.txtCobertura.setText("NO");

        if(Herramientas.texto(residentes.get(position).getCobertura()).equals("0"))
            holder.txtCobertura.setText("NO");
        else if (Herramientas.texto(residentes.get(position).getCobertura()).equals("1"))
            holder.txtCobertura.setText("SI");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return residentes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNumero;
        TextView txtNombre;
        TextView txtParentesco;
        TextView txtSexo;
        TextView txtEdad;
        TextView txtEstadoCivil;
        TextView txtCobertura;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.item_residente_cardview);
            txtNumero = itemView.findViewById(R.id.item_residente_numero);
            txtNombre = itemView.findViewById(R.id.item_residente_nombre);
            txtParentesco = itemView.findViewById(R.id.item_residente_parentesco);
            txtSexo = itemView.findViewById(R.id.item_residente_sexo);
            txtEdad = itemView.findViewById(R.id.item_residente_edad);
            txtEstadoCivil = itemView.findViewById(R.id.item_residente_estado_civil);
            txtCobertura = itemView.findViewById(R.id.item_residente_cobertura);
        }
    }
}

