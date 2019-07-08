package pe.gob.inei.generadorinei.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import pe.gob.inei.generadorinei.AppController;
import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.activities.hogares.HogarActivity;
import pe.gob.inei.generadorinei.activities.hogares.ViviendaActivity;
import pe.gob.inei.generadorinei.adapters.HogarAdapter;
import pe.gob.inei.generadorinei.greendao.DaoSession;
import pe.gob.inei.generadorinei.greendao.Hogar_g;
import pe.gob.inei.generadorinei.greendao.Residente_g;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Hogar;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Residente;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.util.Herramientas;
import pe.gob.inei.generadorinei.util.InputFilterSoloLetras;

/**
 * A simple {@link Fragment} subclass.
 */
public class HogaresFragment extends ComponenteFragment {

    String idVivienda;
    Context context;
    TextView numeroHogaresTextView;
    RecyclerView hogaresRecyclerView;
    FloatingActionButton agregarHogarFAB;
    RecyclerView.LayoutManager layoutManager;
    HogarAdapter hogarAdapter;
    ArrayList<Hogar> hogarArrayList = new ArrayList<>();

    DAOEncuesta daoEncuesta;

    AppController appController;

    @SuppressLint("ValidFragment")
    public HogaresFragment(String idVivienda, Context context, AppController appController) {
        this.idVivienda = idVivienda;
        this.appController = appController;
        this.context = context;
        daoEncuesta = new DAOEncuesta(context);
    }

    public HogaresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_hogares, container, false);
        numeroHogaresTextView = (TextView) rootView.findViewById(R.id.hogares_textview_numero_hogares);
        hogaresRecyclerView = (RecyclerView) rootView.findViewById(R.id.hogares_recyclerview);
        agregarHogarFAB = (FloatingActionButton) rootView.findViewById(R.id.hogares_fab);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hogaresRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        hogaresRecyclerView.setLayoutManager(layoutManager);
        cargarDatos();
        inicializarDatos();
        setearAdapter();
        agregarHogarFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarHogar();
            }
        });

    }

    private void inicializarDatos() {
        hogarArrayList = daoEncuesta.getAllHogares(idVivienda);

        for(Hogar hogar:hogarArrayList){
            Log.e("Nombre_jefe",""+ Herramientas.texto(hogar.getNom_jefe()));
        }
    }

    public void setearAdapter(){
        hogarAdapter = new HogarAdapter(hogarArrayList, context,new HogarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                if(hogarArrayList.size() == position + 1){
                    PopupMenu popupMenu = new PopupMenu(context,view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_hogar_2,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId()){
                                case R.id.opcion_hogar_iniciar:
                                    iniciarHogar(position);
                                    break;
                                case R.id.opcion_hogar_editar:
                                    editarHogar(hogarArrayList.get(position));
                                    break;
                                case R.id.opcion_hogar_eliminar:
                                    deseaEliminarDatos(hogarArrayList.get(position));
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }else{
                    PopupMenu popupMenu = new PopupMenu(context,view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_hogar_1,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId()){
                                case R.id.opcion_hogar_iniciar:
                                    iniciarHogar(position);
                                    break;
                                case R.id.opcion_hogar_editar:
                                    editarHogar(hogarArrayList.get(position));
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }


            }
        });
        hogaresRecyclerView.setAdapter(hogarAdapter);
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

    @Override
    public void cargarDatos() {
        hogarArrayList = daoEncuesta.getAllHogares(idVivienda);
        numeroHogaresTextView.setText(""+hogarArrayList.size());
    }

    @Override
    public void llenarVista() {

    }

    @Override
    public boolean validarDatos() {
        return true;
    }

    @Override
    public boolean estaHabilitado() {
        return false;
    }

    @Override
    public String getIdTabla() {
        return null;
    }


    public void agregarHogar(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_jefe_hogar, null);
        final EditText jefeEditText = (EditText) dialogView.findViewById(R.id.dialog_jefe_edittext_nombre);

        jefeEditText.setFilters(new InputFilter[] {new InputFilter.AllCaps(),new InputFilter.LengthFilter(100),new InputFilterSoloLetras()});

        alert.setTitle("AGREGAR HOGAR");
        alert.setView(dialogView);
        alert.setPositiveButton("Agregar",null);
        alert.setNegativeButton("Cancelar",null);
        final AlertDialog alertDialog = alert.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btnAdd = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!jefeEditText.getText().toString().trim().equals("")){
                            int numero = hogarArrayList.size()+1;

                            Hogar hogarr = new Hogar();
                            hogarr.setId_vivienda(idVivienda);
                            hogarr.setId_hogar((hogarArrayList.size()+1)+"");
                            hogarr.setNom_jefe(jefeEditText.getText().toString());
                            hogarr.setEstado("0");
                            if (numero == 1) {
                                hogarr.setPrincipal("1");
                            }
                            else {
                                hogarr.setPrincipal("0");
                            }

                            daoEncuesta.open();

                            if(daoEncuesta.existeElemento(2,Hogar.class.getSimpleName(),hogarr.getId_vivienda(),hogarr.getId_hogar(),"")){
                                //insertar
                                daoEncuesta.actualizarElemento(2,Hogar.class.getSimpleName(),hogarr.getId_vivienda(),hogarr.getId_hogar(),"",hogarr.getContentValues());
                            }else{
                                daoEncuesta.insertarElemento(Hogar.class.getSimpleName(),hogarr.getContentValues());
                            }

                            Residente residente = new Residente();
                            residente.setId_vivienda(hogarr.getId_vivienda());
                            residente.setId_hogar(hogarr.getId_hogar());
                            residente.setId_residente("1");
                            String nombre = jefeEditText.getText().toString();
                            if(nombre.indexOf(" ") > -1){
                                nombre = nombre.substring(0,nombre.indexOf(" "));
                            }
                            residente.setNombre(nombre);
                            residente.setParentesco("1");

                            if(daoEncuesta.existeElemento(3,Residente.class.getSimpleName(),residente.getId_vivienda(),residente.getId_hogar(),residente.getId_residente())){
                                //insertar
                                daoEncuesta.actualizarElemento(3,Residente.class.getSimpleName(),residente.getId_vivienda(),residente.getId_hogar(),residente.getId_residente(),residente.getContentValues());
                            }else{
                                daoEncuesta.insertarElemento(Residente.class.getSimpleName(),residente.getContentValues());
                            }

                            daoEncuesta.close();

                            inicializarDatos();
                            setearAdapter();
                            actualizarNumeroHogares();
                            alertDialog.dismiss();
                        }else Toast.makeText(context, "DEBE INDICAR NOMBRES Y APELLIDOS DEL JEDE DE HOGAR", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void editarHogar(final Hogar hogar){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        final View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_jefe_hogar, null);
        final EditText jefeEditText = (EditText) dialogView.findViewById(R.id.dialog_jefe_edittext_nombre);
        jefeEditText.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(40), new InputFilterSoloLetras()});
        alert.setTitle("EDITAR HOGAR");
        alert.setView(dialogView);
        alert.setPositiveButton("Guardar",null);
        alert.setNegativeButton("Cancelar",null);
        final AlertDialog alertDialog = alert.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                jefeEditText.setText(hogar.getNom_jefe());
                Button btnAdd = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        if(!jefeEditText.getText().toString().trim().equals("")){
                            String nombre = jefeEditText.getText().toString();
                            Log.e("editarHogar N:",""+nombre);

                            daoEncuesta.open();

                            Residente residente = new Residente();
                            residente.setId_vivienda(hogar.getId_vivienda());
                            residente.setId_hogar(hogar.getId_hogar());
                            residente.setId_residente("1");
                            if(nombre.indexOf(" ") > -1){
                                nombre = nombre.substring(0,nombre.indexOf(" "));
                            }
                            hogar.setNom_jefe(nombre);
                            residente.setNombre(nombre);
                            residente.setParentesco("1");

                            if(daoEncuesta.existeElemento(2,Hogar.class.getSimpleName(),hogar.getId_vivienda(),hogar.getId_hogar(),"")){
                                //insertar
                                daoEncuesta.actualizarElemento(2,Hogar.class.getSimpleName(),hogar.getId_vivienda(),hogar.getId_hogar(),"",hogar.getContentValues());
                            }else{
                                daoEncuesta.insertarElemento(Hogar.class.getSimpleName(),hogar.getContentValues());
                            }

                            if(daoEncuesta.existeElemento(3,Residente.class.getSimpleName(),residente.getId_vivienda(),residente.getId_hogar(),residente.getId_residente())){
                                //insertar
                                daoEncuesta.actualizarElemento(3,Residente.class.getSimpleName(),residente.getId_vivienda(),residente.getId_hogar(),residente.getId_residente(),residente.getContentValues());
                            }else{
                                daoEncuesta.insertarElemento(Residente.class.getSimpleName(),residente.getContentValues());
                            }

                            daoEncuesta.close();


                            inicializarDatos();
                            setearAdapter();
                            alertDialog.dismiss();
                        }else Toast.makeText(context, "DEBE INDICAR NOMBRES Y APELLIDOS DEL JEDE DE HOGAR", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.show();
    }

    public void iniciarHogar(int position){
        Intent intent = new Intent(context, HogarActivity.class);
        intent.putExtra("id_vivienda",hogarArrayList.get(position).getId_vivienda());
        intent.putExtra("numero_hogar",hogarArrayList.get(position).getId_hogar());
        ViviendaActivity viviendaActivity = (ViviendaActivity)getActivity();
        intent.putExtra("nickUsuario", viviendaActivity.getNickUsuario());
        startActivity(intent);
    }

    public void eliminarhogar(final Hogar hogar){
        daoEncuesta.eliminarHogar(hogar);
        inicializarDatos();
        setearAdapter();
        actualizarNumeroHogares();
    }

    public void actualizarNumeroHogares(){
        numeroHogaresTextView.setText(hogarArrayList.size()+"");
    }

    public void deseaEliminarDatos(final Hogar hogar){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Está seguro que desea eliminar el hogar?, se perderán todos los datos asociados al hogar y sus residentes")
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
                                eliminarhogar(hogar);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        setearAdapter();
//    }
}
