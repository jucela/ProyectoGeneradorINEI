package pe.gob.inei.generadorinei.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.greendao.Visita_encuestador;
import pe.gob.inei.generadorinei.model.dao.SQLConstantes;

/**
 * Created by otin016 on 28/06/2017.
 */

public class VisitaEncuestadorAdapter extends RecyclerView.Adapter<VisitaEncuestadorAdapter.ViewHolder>{
    ArrayList<Visita_encuestador> visitas;
    Context context;
    OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public VisitaEncuestadorAdapter(ArrayList<Visita_encuestador> visitas, Context context, OnItemClickListener onItemClickListener) {
        this.visitas = visitas;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visita_encuestador,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.txtNumero.setText(visitas.get(position).getNumero_visita());

        holder.txtFecha.setText(
                checkDigito(Integer.parseInt(visitas.get(position).getVis_fecha_dd())) + "/" +
                        checkDigito(Integer.parseInt(visitas.get(position).getVis_fecha_mm())) + "/" +
                        checkDigito(Integer.parseInt(visitas.get(position).getVis_fecha_aa()))
        );

        holder.txtHoraInicio.setText(
                checkDigito(Integer.parseInt(visitas.get(position).getVis_hor_ini())) + ":"+
                        checkDigito(Integer.parseInt(visitas.get(position).getVis_min_ini()))
        );

        String horafinal = visitas.get(position).getVis_hor_fin();

        if(horafinal != null && !horafinal.equals("")) {
            holder.txtHoraFinal.setText(
                    checkDigito(Integer.parseInt(visitas.get(position).getVis_hor_fin())) + ":" +
                            checkDigito(Integer.parseInt(visitas.get(position).getVis_min_fin()))
            );
        }else holder.txtHoraFinal.setText("-:-");

        String resultado = visitas.get(position).getVis_resu();

        if(resultado != null && !resultado.equals(""))
            holder.txtResultado.setText(context.getResources().getStringArray(R.array.visita_array_resultados)[Integer.parseInt(resultado)]);
        else holder.txtResultado.setText("NO FINALIZADO");

        String diaProxV = visitas.get(position).getProx_vis_fecha_dd();

        if(diaProxV != null && !diaProxV.equals("")) {
            holder.txtFechaProxVisita.setText(
                    checkDigito(Integer.parseInt(diaProxV)) +
                            "/" + checkDigito(Integer.parseInt(visitas.get(position).getProx_vis_fecha_mm())) +
                            "/" + checkDigito(Integer.parseInt(visitas.get(position).getProx_vis_fecha_aa()))
            );
            holder.txtHoraProxVisita.setText(
                    checkDigito(Integer.parseInt(visitas.get(position).getProx_vis_hor())) +
                            ":" + checkDigito(Integer.parseInt(visitas.get(position).getProx_vis_min()))
            );
        }else{
            holder.txtFechaProxVisita.setText("-/-/-");
            holder.txtHoraProxVisita.setText("-:-");
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
        return visitas.size();
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNumero;
        TextView txtFecha;
        TextView txtHoraInicio;
        TextView txtHoraFinal;
        TextView txtResultado;
        TextView txtFechaProxVisita;
        TextView txtHoraProxVisita;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_visita);
            txtNumero = (TextView) itemView.findViewById(R.id.txt_item_visita_numero);
            txtFecha = (TextView) itemView.findViewById(R.id.txt_item_visita_fecha);
            txtHoraInicio = (TextView) itemView.findViewById(R.id.txt_item_visita_horai);
            txtHoraFinal = (TextView) itemView.findViewById(R.id.txt_item_visita_horaf);
            txtResultado = (TextView) itemView.findViewById(R.id.txt_item_visita_resultado);
            txtFechaProxVisita = (TextView) itemView.findViewById(R.id.txt_item_visita_fprox);
            txtHoraProxVisita = (TextView) itemView.findViewById(R.id.txt_item_visita_hprox);
        }
    }
}
