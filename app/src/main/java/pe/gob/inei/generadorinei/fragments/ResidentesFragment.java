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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import pe.gob.inei.generadorinei.AppController;
import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.activities.hogares.EncuestaActivity;
import pe.gob.inei.generadorinei.activities.hogares.agregacion.AgregarResidenteActivity;
import pe.gob.inei.generadorinei.activities.hogares.agregacion.ResidenteActivity;
import pe.gob.inei.generadorinei.adapters.ResidenteAdapter;
import pe.gob.inei.generadorinei.greendao.DaoSession;
import pe.gob.inei.generadorinei.greendao.Residente_g;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Residente;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.util.Herramientas;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResidentesFragment extends ComponenteFragment {

    String numero_hogar;
    String id_vivienda;
    Context context;

    TextView txtNroPersonas;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ResidenteAdapter residenteAdapter;

    AppController appController;
    DaoSession daoSession;

    DAOEncuesta daoEncuesta;

    ArrayList<Residente> residenteArrayList = new ArrayList<>();

    public ResidentesFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ResidentesFragment(String id_vivienda, String numero_hogar, Context context, AppController appController) {
        this.numero_hogar = numero_hogar;
        this.id_vivienda = id_vivienda;
        this.appController = appController;
        this.context = context;
        daoEncuesta = new DAOEncuesta(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_residentes, container, false);
        fab = (FloatingActionButton) rootView.findViewById(R.id.mod2_fab);
        txtNroPersonas = (TextView) rootView.findViewById(R.id.mod2_txtNumeroPersonas);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.mod2_recyclerview);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = (residenteArrayList.size()+1);
                //if (Herramientas.texto(residentes.get(0).getCobertura()).equals("1")){
                    //Intent intent = new Intent(context, AgregarResidenteActivity.class);
                    Intent intent = new Intent(context, ResidenteActivity.class);
                    intent.putExtra("idEncuestado",numero_hogar + "_" + num);
                    intent.putExtra("numero", num + "");
                    intent.putExtra("numero_residente", num + "");
                    intent.putExtra("numero_hogar", numero_hogar);
                    intent.putExtra("id_vivienda", id_vivienda);
                    intent.putExtra("idJefeHogar", residenteArrayList.get(0).getId_residente());
                    startActivity(intent);
                //}else { mostrarMensaje("ANTES DE INGRESAR ALGÚN MIEMBRO DEL HOGAR, DEBE COMPLETAR LA INFORMACIÓN DEL JEFE DE HOGAR");}

            }
        });
        cargarDatos();
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
        inicializarDatos();
        setearAdapter();
    }

    @Override
    public void llenarVista() {

    }

    public void mostrarMensaje(String m){
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(m);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
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

    public void inicializarDatos(){
        /*residentes = new ArrayList<>();
        daoSession = appController.getDaoSession();
        residentes = daoSession.getResidenteDao().getResidentes(id_vivienda,numero_hogar);
        txtNroPersonas.setText(residentes.size()+"");*/

        residenteArrayList = daoEncuesta.getAllResidentes(id_vivienda,numero_hogar);
        txtNroPersonas.setText(residenteArrayList.size()+"");

        for(Residente residente: residenteArrayList){
            Log.e("Sexo",""+Herramientas.texto(residente.getSexo()));
        }
    }

    public void setearAdapter(){
        residenteAdapter = new ResidenteAdapter(residenteArrayList,context, new ResidenteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                    final PopupMenu popupMenu = new PopupMenu(context,view);
                    if (residenteArrayList.size() == position + 1){
                        popupMenu.getMenuInflater().inflate(R.menu.menu_residente_1,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()){
                                    case R.id.opcion_iniciar_encuesta:
                                        //Residente_g residente = residentes.get(0);
                                        Residente residente = residenteArrayList.get(0);
                                        if(Herramientas.texto(residente.getSexo()).equals("")){
                                            Toast.makeText(context, "DEBE COMPLETAR LOS DATOS DEL JEFE DE HOGAR ANTES DE INICIAR LA ENCUESTA", Toast.LENGTH_SHORT).show();
                                        }else{
                                                //ocultar_mostrar_p625(idEncuestado);
                                                Intent intent1 = new Intent(context, EncuestaActivity.class);
                                                intent1.putExtra("numero_residente", residenteArrayList.get(position).getId_residente() + "");
                                                intent1.putExtra("numero_hogar", numero_hogar);
                                                intent1.putExtra("id_vivienda", id_vivienda);
                                                startActivity(intent1);
                                        }
                                        break;
                                    case R.id.opcion_editar:
                                        //Intent intent2 = new Intent(context, AgregarResidenteActivity.class);
                                        Intent intent2 = new Intent(context, ResidenteActivity.class);
                                        intent2.putExtra("numero_residente", residenteArrayList.get(position).getId_residente() + "");
                                        intent2.putExtra("numero_hogar", numero_hogar);
                                        intent2.putExtra("id_vivienda", id_vivienda);
                                        intent2.putExtra("numero_residente_jefe", residenteArrayList.get(0).getId_residente());
                                        startActivity(intent2);
                                        break;
                                    case R.id.opcion_eliminar:
                                        if (position > 0) deseaEliminarDatos(position);
                                        else
                                            Toast.makeText(context, "NO PUEDE ELIMINAR AL JEFE DE HOGAR", Toast.LENGTH_SHORT).show();
                                        break;
                                }

                                return true;
                            }
                        });
                        popupMenu.show();
                    }else{
                        popupMenu.getMenuInflater().inflate(R.menu.menu_residente_2,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()){
                                    case R.id.opcion_iniciar_encuesta:
                                        Residente residente = residenteArrayList.get(0);
                                        if(Herramientas.texto(residente.getSexo()).equals("")){
                                            Toast.makeText(context, "DEBE COMPLETAR LOS DATOS DEL JEFE DE HOGAR ANTES DE INICIAR LA ENCUESTA", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Intent intent1 = new Intent(context, EncuestaActivity.class);
                                            intent1.putExtra("numero_residente", residenteArrayList.get(position).getId_residente() + "");
                                            intent1.putExtra("numero_hogar", numero_hogar);
                                            intent1.putExtra("id_vivienda", id_vivienda);
                                            startActivity(intent1);
                                        }
                                        break;
                                    case R.id.opcion_editar:
                                        //Intent intent2 = new Intent(context, AgregarResidenteActivity.class);
                                        Intent intent2 = new Intent(context, ResidenteActivity.class);
                                        intent2.putExtra("numero_residente", residenteArrayList.get(position).getId_residente() + "");
                                        intent2.putExtra("numero_hogar", numero_hogar);
                                        intent2.putExtra("id_vivienda", id_vivienda);
                                        intent2.putExtra("numero_residente_jefe", residenteArrayList.get(0).getId_residente());
                                        startActivity(intent2);
                                        break;
                                }

                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                }
        });
        recyclerView.setAdapter(residenteAdapter);
    }

    public void eliminarEncuestado(int position){
        daoEncuesta.eliminarResidente(residenteArrayList.get(position));
        inicializarDatos();
        setearAdapter();
    }

    public void deseaEliminarDatos(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Está seguro que desea eliminar el residente?, se perderán todos los datos asociados al encuestado/a")
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
                                eliminarEncuestado(position);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        inicializarDatos();
        setearAdapter();
    }

}
