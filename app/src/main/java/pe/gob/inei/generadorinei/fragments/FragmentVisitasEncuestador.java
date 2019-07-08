package pe.gob.inei.generadorinei.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pe.gob.inei.generadorinei.AppController;
import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.adapters.VisitaEncuestadorAdapter;
import pe.gob.inei.generadorinei.greendao.DaoSession;
import pe.gob.inei.generadorinei.greendao.Visita_encuestador;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.util.InputFilterSoloLetras;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentVisitasEncuestador extends ComponenteFragment {
    private LinearLayoutManager linearLayoutManager;
    private VisitaEncuestadorAdapter visitaEncuestadorAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAgregar;
    private TextView txtFechaFinal;
    private TextView txtResultadoFinal;

    private String numero_hogar;
    private String idVivienda;
    private String idCargo="1";

    private Context context;
    private Visita_encuestador visita;
    private Cursor cursor;
    private VisitaEncuestadorAdapter.OnItemClickListener onItemClickListener;


    int diaInicio;
    int mesInicio;
    int anioInicio;

    int horaInicio;
    int minutoInicio;
    int horaFin;
    int minutoFin;

    int diaProx;
    int mesProx;
    int anioProx;

    int horaProx;
    int minutoProx;

    ArrayList<Visita_encuestador> visitas;
    int cant_visitas=0;

    AppController appController;
    DaoSession daoSession;

    public FragmentVisitasEncuestador() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FragmentVisitasEncuestador(String idVivienda, String numero_hogar, Context context, AppController appController) {
        this.numero_hogar = numero_hogar;
        this.idVivienda = idVivienda;
        this.appController = appController;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_visitas_encuestador, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.visita_recycler);
        txtResultadoFinal = (TextView) rootView.findViewById(R.id.visitas_resultado_final);
        txtFechaFinal = (TextView) rootView.findViewById(R.id.visitas_fecha_final);
        btnAgregar = (FloatingActionButton) rootView.findViewById(R.id.visitas_btnAgregarVisita);

        daoSession = appController.getDaoSession();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (idCargo.equals("2")) btnAgregar.setVisibility(View.GONE);

        daoSession = appController.getDaoSession();
        visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);

        if(visitas!=null) cant_visitas = visitas.size();

        cargarDatos();
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        visitas = new ArrayList<Visita_encuestador>();
        visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);

        //if(visitas.size()>0){
            visitaEncuestadorAdapter = new VisitaEncuestadorAdapter(visitas, context, onItemClickListener);
            recyclerView.setAdapter(visitaEncuestadorAdapter);
        //}
        cargarDatos();
        inicializarDatos();
        setearAdapter();

        /*
        onItemClickListener = new VisitaEncuestadorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int pos) {

                daoSession = appController.getDaoSession();
                visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);

                if(visitas!=null) cant_visitas = visitas.size();


                Log.e("resultadoVisita-Pos",""+pos);
                String resultadoVisita = visitas.get(pos).getVis_resu();

                if (resultadoVisita == null) resultadoVisita = "";
                Log.e("resultadoVisita", "onItemClick: "+resultadoVisita );
                Log.e("resultadoVisita pos", "onItemClick: "+pos );
                if((pos+1)==cant_visitas && idCargo.equals("1")){
                    Log.e("", "onItemClick: " + "cesar1" );
                    if(resultadoVisita==""){
                        PopupMenu popupMenu = new PopupMenu(context,view);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_visita,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()){
                                    case R.id.opcion_editar:
                                        editarVisita(pos);
                                        break;
                                    case R.id.opcion_eliminar:
                                        eliminarVisita(pos);
                                        break;
                                    case R.id.opcion_finalizar:
                                        finalizarVisita(pos);
                                        break;
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }else{
                        Log.e("", "onItemClick: " + "cesar" );
                        PopupMenu popupMenu = new PopupMenu(context,view);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_visita2,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()){
                                    case R.id.opcion_editar:
                                        finalizarVisita(pos);
                                        break;
                                    case R.id.opcion_eliminar:
                                        eliminarVisita(pos);
                                        break;
                                    case R.id.opcion_finalizar:
                                        finalizarVisita(pos);
                                        break;
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                }
            }
        };
        */

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String resultadoFinal;

                if(visitas.size()==0){
                    resultadoFinal = "-1";
                }else{
                    resultadoFinal = visitas.get(visitas.size()-1).getVis_resu();
                    if(resultadoFinal==null) resultadoFinal = "-1";
                }

                if (!resultadoFinal.equals("1")){
                    if(visitas.size() > 0) {
                        if( !resultadoFinal.equals("-1")) agregarVisita();
                        else mostrarMensaje("DEBE FINALIZAR LA VISITA ACTUAL, ANTES DE AGREGAR UNA NUEVA");
                    }else{agregarVisita();}
                }else Toast.makeText(context, "La encuesta ya finalizó COMPLETA", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void agregarVisita(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_agregar_visita, null);
        final LinearLayout lytDialog = (LinearLayout) dialogView.findViewById(R.id.dialog_agregar_visita_lyt);
        final TextView txtNumero = (TextView) dialogView.findViewById(R.id.dialog_agregar_visita_txtNumero);
        final TextView txtFechaI = (TextView) dialogView.findViewById(R.id.dialog_agregar_visita_txtFI);
        final TextView txtHoraI = (TextView) dialogView.findViewById(R.id.dialog_agregar_visita_txtHI);

        alert.setTitle("AGREGAR VISITA");
        alert.setView(dialogView);
        alert.setPositiveButton("Agregar",null);
        alert.setNegativeButton("Cancelar",null);
        final AlertDialog alertDialog = alert.create();
//        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Calendar c = Calendar.getInstance();
                diaInicio = c.get(Calendar.DAY_OF_MONTH);
                mesInicio = c.get(Calendar.MONTH) + 1;
                anioInicio = c.get(Calendar.YEAR);
                horaInicio = c.get(Calendar.HOUR_OF_DAY);
                minutoInicio = c.get(Calendar.MINUTE);

                txtNumero.setText("VISITA N° " + checkDigito((visitaEncuestadorAdapter.getItemCount() + 1)));
                txtFechaI.setText(checkDigito(diaInicio) + "/" + checkDigito(mesInicio) + "/" + checkDigito(anioInicio));
                txtHoraI.setText(checkDigito(horaInicio) + ":" + checkDigito(minutoInicio));
                Button btnAdd = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        boolean valido = true;
                        String mensaje = "";
                        boolean vFechaInicio = true, vHoraInicio = true;
                        if(visitas.size() > 0){
                            int y = Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_aa());
                            int m = Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_mm());
                            int d = Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_dd());

                            int compHora = Integer.parseInt(visitas.get(visitas.size()-1).getVis_hor_ini());
                            int compMinuto = Integer.parseInt(visitas.get(visitas.size()-1).getVis_min_ini());


                            Date fi1 = new Date(y,m,d);
                            Date fi2 = new Date(anioInicio,mesInicio,diaInicio);
                            String sfi1 = checkDigito(d) + "/" + checkDigito(m) + "/" + checkDigito(y);
                            String sfi2 = checkDigito(diaInicio) + "/" + checkDigito(mesInicio) + "/" + checkDigito(anioInicio);
                            if(fi2.before(fi1)){
                                vFechaInicio = false;
                                if(mensaje.equals("")) mensaje = "FECHA: LA FECHA DE LA NUEVA VISITA NO DEBE SER MENOR A LA VISITA ANTERIOR";
                            }else if(d == diaInicio && m == mesInicio && y == anioInicio){
                                if((horaInicio*60 + minutoInicio) <= (compHora*60+compMinuto)){
                                    vHoraInicio = false;
                                    if(mensaje.equals("")) mensaje = "FECHA: SI LA FECHA ES LA MISMA, LA HORA DE LA NUEVA VISITA NO DEBE SER MENOR O IGUAL A LA VISITA ANTERIOR";
                                }
                            }
                        }
                        valido = vFechaInicio && vHoraInicio;
                        if(valido){
                            Visita_encuestador visita_nueva;
                            visita_nueva = new Visita_encuestador();

                            visita_nueva.setId_vivienda(idVivienda);
                            visita_nueva.setNumero_hogar(numero_hogar);
                            visita_nueva.setNumero_visita(""+(visitas.size()+1));
                            visita_nueva.setVis_fecha_dd(""+diaInicio);
                            visita_nueva.setVis_fecha_mm(""+mesInicio);
                            visita_nueva.setVis_fecha_aa(""+anioInicio);
                            visita_nueva.setVis_hor_ini(""+horaInicio);
                            visita_nueva.setVis_min_ini(""+minutoInicio);

                            daoSession = appController.getDaoSession();
                            daoSession.getVisita_encuestadorDao().insert(visita_nueva);

                            visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);
                            if(visitas.size()>0){
                                visitaEncuestadorAdapter = new VisitaEncuestadorAdapter(visitas, context, onItemClickListener);
                                recyclerView.setAdapter(visitaEncuestadorAdapter);
                            }
                            inicializarDatos();
                            setearAdapter();
//                            recyclerView.getAdapter().notifyDataSetChanged();
                            alertDialog.dismiss();
                        }else Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void editarVisita(final int posicion){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_agregar_visita, null);
        final LinearLayout lytDialog = (LinearLayout) dialogView.findViewById(R.id.dialog_agregar_visita_lyt);
        final TextView txtNumero = (TextView) dialogView.findViewById(R.id.dialog_agregar_visita_txtNumero);
        final TextView txtFechaI = (TextView) dialogView.findViewById(R.id.dialog_agregar_visita_txtFI);
        final TextView txtHoraI = (TextView) dialogView.findViewById(R.id.dialog_agregar_visita_txtHI);

        alert.setTitle("EDITAR VISITA");
        alert.setView(dialogView);
        alert.setPositiveButton("Guardar",null);
        alert.setNegativeButton("Cancelar",null);
        final AlertDialog alertDialog = alert.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                diaInicio = Integer.parseInt(visitas.get(posicion).getVis_fecha_dd());
                mesInicio = Integer.parseInt(visitas.get(posicion).getVis_fecha_mm());
                anioInicio = Integer.parseInt(visitas.get(posicion).getVis_fecha_aa());
                horaInicio = Integer.parseInt(visitas.get(posicion).getVis_hor_ini());
                minutoInicio = Integer.parseInt(visitas.get(posicion).getVis_min_ini());

                txtNumero.setText("VISITA N° " + checkDigito(Integer.parseInt(visitas.get(posicion).getNumero_visita())));
                txtFechaI.setText(checkDigito(diaInicio) + "/" + checkDigito(mesInicio) + "/" + checkDigito(anioInicio));
                txtHoraI.setText(checkDigito(horaInicio) + ":" + checkDigito(minutoInicio));
                Button btnAdd = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        boolean valido =true;
                        String mensaje = "";
                        boolean vFechaInicio = true, vHoraInicio = true;

                        if(visitas.size() > 1){
                            int d = Integer.parseInt(visitas.get(posicion-1).getVis_fecha_dd());
                            int m = Integer.parseInt(visitas.get(posicion-1).getVis_fecha_mm());
                            int y = Integer.parseInt(visitas.get(posicion-1).getVis_fecha_aa());

                            int compHora = Integer.parseInt(visitas.get(posicion-1).getVis_hor_ini());
                            int compMinuto = Integer.parseInt(visitas.get(posicion-1).getVis_min_ini());

                            Date fi1 = new Date(y,m,d);
                            Date fi2 = new Date(anioInicio,mesInicio,diaInicio);
                            String sfi1 = checkDigito(d) + "/" + checkDigito(m) + "/" + checkDigito(y);
                            String sfi2 = checkDigito(diaInicio) + "/" + checkDigito(mesInicio) + "/" + checkDigito(anioInicio);

                            if(fi2.before(fi1)){
                                vFechaInicio = false;
                                if(mensaje.equals("")) mensaje = "FECHA: LA FECHA DE LA PROXIMA VISITA NO DEBE SER MENOR A LA VISITA ACTUAL";
                            }else if(d == diaInicio && m == mesInicio && y == anioInicio){
                                if((horaInicio*60 + minutoInicio) <= (compHora*60+compMinuto)){
                                    vHoraInicio = false;
                                    if(mensaje.equals("")) mensaje = "FECHA: SI LA FECHA ES LA MISMA, LA HORA DE LA NUEVA VISITA NO DEBE SER MENOR O IGUAL A LA VISITA ANTERIOR";
                                }
                            }
                        }
                        valido = vFechaInicio && vHoraInicio;
                        if(valido){
                            Visita_encuestador visita_actualizar;
                            visita_actualizar = new Visita_encuestador();
                            visita_actualizar.setId(visitas.get(posicion).getId());
                            visita_actualizar.setId_vivienda(visitas.get(posicion).getId_vivienda());
                            visita_actualizar.setNumero_hogar(visitas.get(posicion).getNumero_hogar());
                            visita_actualizar.setNumero_visita(visitas.get(posicion).getNumero_visita());
                            visita_actualizar.setVis_fecha_dd(""+diaInicio);
                            visita_actualizar.setVis_fecha_mm(""+mesInicio);
                            visita_actualizar.setVis_fecha_aa(""+anioInicio);
                            visita_actualizar.setVis_hor_ini(""+horaInicio);
                            visita_actualizar.setVis_min_ini(""+minutoInicio);

                            daoSession = appController.getDaoSession();
                            daoSession.getVisita_encuestadorDao().update(visita_actualizar);

                            visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);
                            if(visitas.size()>0){
                                visitaEncuestadorAdapter = new VisitaEncuestadorAdapter(visitas, context, onItemClickListener);
                                recyclerView.setAdapter(visitaEncuestadorAdapter);
                            }
                            inicializarDatos();
                            setearAdapter();
                            alertDialog.dismiss();
                        }else Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void eliminarVisita(final int posicion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Está seguro que desea eliminar la visita? (no podrá revertir el cambio)")
                .setTitle("Aviso")
                .setCancelable(false)
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Sí",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                daoSession = appController.getDaoSession();
                                daoSession.getVisita_encuestadorDao().delete(visitas.get(posicion));
                                visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);
                                /*visitaEncuestadorAdapter = new VisitaEncuestadorAdapter(visitas, context, onItemClickListener);
                                recyclerView.setAdapter(visitaEncuestadorAdapter);*/
                                inicializarDatos();
                                setearAdapter();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void finalizarVisita(final int posicion){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_finalizar_visita_encuestador, null);
        final LinearLayout lytDialog = (LinearLayout) dialogView.findViewById(R.id.dialog_finalizar_visita_lyt);
        final TextView txtNumero = (TextView) dialogView.findViewById(R.id.dialog_finalizar_visita_txtNumero);
        final TextView txtHoraF = (TextView) dialogView.findViewById(R.id.dialog_finalizar_visita_txtHoraFin);
        final Spinner spResultado = (Spinner) dialogView.findViewById(R.id.dialog_finalizar_visita_spResultado);
        final EditText edtEspecifique = (EditText) dialogView.findViewById(R.id.dialog_finalizar_visita_edtEspecifique);
        final CheckBox ckProxVisita = (CheckBox) dialogView.findViewById(R.id.dialog_finalizar_visita_ckProximaVisita);
        final TextView txtFechaProxVisita = (TextView) dialogView.findViewById(R.id.dialog_finalizar_visita_txtFechaProx);
        final TextView txtHoraProxVisita = (TextView) dialogView.findViewById(R.id.dialog_finalizar_visita_txtHoraProx);
        final CardView cardViewEspecifique = (CardView) dialogView.findViewById(R.id.dialog_cardview_finalizar_especifique);
        final CardView cardViewProxFecha= (CardView) dialogView.findViewById(R.id.dialog_cardview_proxFecha);
        final CardView cardViewProxHora = (CardView) dialogView.findViewById(R.id.dialog_cardview_proxHora);
        String especifique = "";

        edtEspecifique.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(100), new InputFilterSoloLetras()});

        spResultado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos == 4 || pos == 2){
                    ckProxVisita.setEnabled(true);
                    ckProxVisita.setChecked(true);
                }else{
                    ckProxVisita.setChecked(false);
                    ckProxVisita.setEnabled(false);
                }
                if(pos == 7){
                    edtEspecifique.setEnabled(true);
                    cardViewEspecifique.setVisibility(View.VISIBLE);
                }else{
                    if(edtEspecifique.isEnabled()){
                        edtEspecifique.setText("");
                        cardViewEspecifique.setVisibility(View.GONE);
                        edtEspecifique.setEnabled(false);
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        ckProxVisita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    txtFechaProxVisita.setEnabled(true);
                    txtHoraProxVisita.setEnabled(true);
                    txtFechaProxVisita.setClickable(true);
                    txtHoraProxVisita.setClickable(true);
                    cardViewProxFecha.setCardBackgroundColor(Color.WHITE);
                    cardViewProxHora.setCardBackgroundColor(Color.WHITE);
                }else{
                    cardViewProxFecha.setCardBackgroundColor(Color.GRAY);
                    cardViewProxHora.setCardBackgroundColor(Color.GRAY);
                    txtFechaProxVisita.setClickable(false);
                    txtHoraProxVisita.setClickable(false);
                    txtFechaProxVisita.setEnabled(false);
                    txtHoraProxVisita.setEnabled(false);
                }
            }
        });

        txtFechaProxVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int yy = calendario.get(Calendar.YEAR);
                int mm = calendario.get(Calendar.MONTH);
                int dd = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        diaProx = dayOfMonth;
                        mesProx = monthOfYear + 1;
                        anioProx = year;
                        String fecha = checkDigito(diaProx) +"/"+checkDigito(mesProx)
                                +"/"+checkDigito(anioProx);
                        txtFechaProxVisita.setText(fecha);
                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        txtHoraProxVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendario = Calendar.getInstance();
                int hh = calendario.get(Calendar.HOUR_OF_DAY);
                int mm = calendario.get(Calendar.MINUTE);

                TimePickerDialog timePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourofDay, int minute) {
                        String hora = checkDigito(hourofDay) +":"+checkDigito(minute);
                        txtHoraProxVisita.setText(hora);
                        horaProx = hourofDay;
                        minutoProx = minute;
                    }
                }, hh, mm,true);
                timePicker.show();
            }
        });

        alert.setTitle("FINALIZAR VISITA");
        alert.setView(dialogView);
        alert.setPositiveButton("Finalizar",null);
        alert.setNegativeButton("Cancelar",null);
        final AlertDialog alertDialog = alert.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ocultarTeclado(lytDialog);
                Calendar c = Calendar.getInstance();
                diaProx = c.get(Calendar.DAY_OF_MONTH);
                mesProx = c.get(Calendar.MONTH) + 1;
                anioProx = c.get(Calendar.YEAR);
                horaProx = c.get(Calendar.HOUR_OF_DAY);
                minutoProx = c.get(Calendar.MINUTE);
                horaFin = horaProx;
                minutoFin = minutoProx;
                Log.e("VISITA N°",""+posicion);
                txtNumero.setText("VISITA N° " + checkDigito(Integer.parseInt(visitas.get(posicion).getNumero_visita())));
                txtHoraF.setText(checkDigito(horaFin) + ":" + checkDigito(minutoFin));
                txtFechaProxVisita.setText(checkDigito(diaProx + 1) + "/" + checkDigito(mesProx) + "/" + checkDigito(anioProx));
                txtHoraProxVisita.setText(checkDigito(horaProx) + ":" + checkDigito(minutoProx));
                Button btnFinalizarVisita = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnFinalizarVisita.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean valido = false;
                        boolean vHoraFin = true, vResultado = true, vEspecifique = true, vFechaProxima = true, vHoraProxima = true;
                        String mensaje = "";
                        int t1 = Integer.parseInt(visitas.get(posicion).getVis_hor_ini())*60 + Integer.parseInt(visitas.get(posicion).getVis_min_ini());
                        int t2 = horaFin * 60 + minutoFin;

                        if(t1 >= t2){
                            vHoraFin = false;
                            if(mensaje.equals("")) mensaje = "LA HORA DE FIN DEBE SER MAYOR A LA DE INICIO";
                        }

                        if(spResultado.getSelectedItemPosition() == 0){
                            vResultado = false;
                            if(mensaje.equals("")) mensaje = "DEBE INDICAR EL RESULTADO DE LA VISITA";
                        }
                        if(spResultado.getSelectedItemPosition() == 1){
                            if(!coberturaCorrecta()){
                                vResultado = false;
                                if(mensaje.equals("")) mensaje = "LA COBERTURA Y CIERRE ES INCORRECTA NO PUEDE FINALIZAR COMO COMPLETA";
                            }
                        }
                        if(spResultado.getSelectedItemPosition() == 4 && !ckProxVisita.isChecked()){
                            vResultado = false;
                            if(mensaje.equals("")) mensaje = "DEBE REGISTRAR INFORMACION DE LA PROXIMA VISITA";
                        }
                        if(edtEspecifique.isEnabled() && edtEspecifique.getText().toString().trim().length() < 3){
                            vEspecifique = false;
                            if(mensaje.equals("")) mensaje = "DEBE ESPECIFICAR EL RESULTADO DE LA VISITA";
                        }
                        if(ckProxVisita.isChecked()){
                            int y = Integer.parseInt(visitas.get(posicion).getVis_fecha_aa());
                            int m = Integer.parseInt(visitas.get(posicion).getVis_fecha_mm());
                            int d = Integer.parseInt(visitas.get(posicion).getVis_fecha_dd());
                            Date fi1 = new Date(y,m,d);
                            Date fi2 = new Date(anioProx,mesProx,diaProx);
                            String sfi1 = checkDigito(d) + "/" + checkDigito(m) + "/" + checkDigito(y);
                            String sfi2 = checkDigito(diaProx) + "/" + checkDigito(mesProx) + "/" + checkDigito(anioProx);

                            if(fi2.before(fi1)){
                                vFechaProxima = false;
                                if(mensaje.equals("")) mensaje = "FECHA: LA FECHA DE LA PROXIMA VISITA NO DEBE SER MENOR A LA VISITA ACTUAL";
                            }
                        }

                        valido = vHoraFin && vResultado && vEspecifique && vFechaProxima;

                        if(valido){
                            String cobertura = "0";
                            boolean finalizacion = true;
                            if (spResultado.getSelectedItemPosition() == 1) {
                                if(!coberturaCorrecta()) finalizacion = false;
                                else cobertura = "1";
                            }
                            if (finalizacion){
                                //actualizo visita con datos de finalizar
                                visitas.get(posicion).setVis_resu(String.valueOf(spResultado.getSelectedItemPosition()));
                                
                                ContentValues contentValues = new ContentValues();
                                if(edtEspecifique.isEnabled()){
                                    visitas.get(posicion).setVis_resu_esp(edtEspecifique.getText().toString());
                                }else{visitas.get(posicion).setVis_resu_esp("");}
                                visitas.get(posicion).setVis_hor_fin(""+horaFin);
                                visitas.get(posicion).setVis_min_fin(""+minutoFin);
                                //falta guardar el especifique del resultado otro
                                if(ckProxVisita.isChecked()){
                                    visitas.get(posicion).setProx_vis_fecha_dd(""+diaProx);
                                    visitas.get(posicion).setProx_vis_fecha_mm(""+mesProx);
                                    visitas.get(posicion).setProx_vis_fecha_aa(""+anioProx);
                                    visitas.get(posicion).setProx_vis_hor(""+horaProx);
                                    visitas.get(posicion).setProx_vis_min(""+minutoProx);
                                }

                                daoSession = appController.getDaoSession();
                                daoSession.getVisita_encuestadorDao().update(visitas.get(posicion));

                                visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);
                                if(visitas.size()>0){
                                    visitaEncuestadorAdapter = new VisitaEncuestadorAdapter(visitas, context, onItemClickListener);
                                    recyclerView.setAdapter(visitaEncuestadorAdapter);
                                }

//                            //MUESTRO Y GUARDO DATOS DE RESULTADO FINAL
                                final Calendar cal = Calendar.getInstance();
                                //obtengo la fecha de la ultima visita
                                int yy = Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_aa());
                                int mm = Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_mm());
                                int dd = Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_dd());

                                txtResultadoFinal.setText(getResources().getStringArray(R.array.visita_array_resultados)[spResultado.getSelectedItemPosition()]);
                                txtFechaFinal.setText(checkDigito(dd) + "/" + checkDigito(mm) + "/" + checkDigito(yy));
                                
                                alertDialog.dismiss();
                            }
                        }else Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public boolean coberturaCorrecta(){        
        return true;
    }

    public void cargarDatos(){
        daoSession = appController.getDaoSession();
        visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);
        
        if(visitas.size()>0){
            String resu = visitas.get(visitas.size()-1).getVis_resu();

            if(resu==null) txtResultadoFinal.setText("");
            else txtResultadoFinal.setText(getResources().getStringArray(R.array.visita_array_resultados)[Integer.parseInt(resu)]);

            txtFechaFinal.setText(
                    checkDigito(Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_dd())) +
                            "/" + checkDigito(Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_mm())) +
                            "/" + checkDigito(Integer.parseInt(visitas.get(visitas.size()-1).getVis_fecha_aa())));
        }else{
            txtResultadoFinal.setText("");
            txtFechaFinal.setText("");
        }
    }

    private void inicializarDatos() {
        daoSession = appController.getDaoSession();
        visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);
    }

    @Override
    public void llenarVista() {

    }

    @Override
    public void inhabilitar() {

    }

    @Override
    public void habilitar() {

    }

    @Override
    public boolean guardarDatos() {
        return true;
    }



    public boolean validarDatos(){
        boolean valido = true;
        String mensaje = "";
        if(visitas.size()>0){
            String resultado = visitas.get(visitas.size()-1).getVis_resu();
            if(resultado != null && !resultado.equals("")){
                if (Integer.parseInt(resultado) > 1){
                    valido =  false;
                    mensaje = "DEBE INICIAR UNA VISITA ANTES DE CONTINUAR";
                }
            }
        }else{
            valido =  false;
            mensaje = "DEBE INICIAR UNA VISITA ANTES DE CONTINUAR";
        }

        if(!valido){
            mostrarMensaje(mensaje);
        }
        return valido;
    }

    @Override
    public boolean estaHabilitado() {
        return false;
    }

    @Override
    public String getIdTabla() {
        return null;
    }

    public String checkDigito (int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void mostrarMensaje(String m){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(m);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setearAdapter(){
        visitaEncuestadorAdapter = new VisitaEncuestadorAdapter(visitas, context,new VisitaEncuestadorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                daoSession = appController.getDaoSession();
                visitas = (ArrayList<Visita_encuestador>) daoSession.getVisita_encuestadorDao().getVisitas(idVivienda,numero_hogar);

                if(visitas!=null) cant_visitas = visitas.size();

                Log.e("resultadoVisita-Pos",""+position);
                String resultadoVisita = visitas.get(position).getVis_resu();

                if (resultadoVisita == null) resultadoVisita = "";
                Log.e("resultadoVisita", "onItemClick: "+resultadoVisita );
                Log.e("resultadoVisita pos", "onItemClick: "+position );
                if((position+1)==cant_visitas && idCargo.equals("1")){
                    Log.e("", "onItemClick: " + "cesar1" );
                    if(resultadoVisita==""){
                        PopupMenu popupMenu = new PopupMenu(context,view);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_visita,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()){
                                    case R.id.opcion_editar:
                                        editarVisita(position);
                                        break;
                                    case R.id.opcion_eliminar:
                                        eliminarVisita(position);
                                        break;
                                    case R.id.opcion_finalizar:
                                        finalizarVisita(position);
                                        break;
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }else{
                        Log.e("", "onItemClick: " + "cesar" );
                        PopupMenu popupMenu = new PopupMenu(context,view);
                        popupMenu.getMenuInflater().inflate(R.menu.menu_visita2,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()){
                                    case R.id.opcion_editar:
                                        finalizarVisita(position);
                                        break;
                                    case R.id.opcion_eliminar:
                                        eliminarVisita(position);
                                        break;
                                    case R.id.opcion_finalizar:
                                        finalizarVisita(position);
                                        break;
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                }
            }
        });
        recyclerView.setAdapter(visitaEncuestadorAdapter);
    }
}
