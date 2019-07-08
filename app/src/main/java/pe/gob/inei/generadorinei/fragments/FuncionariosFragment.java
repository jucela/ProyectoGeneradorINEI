package pe.gob.inei.generadorinei.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import pe.gob.inei.generadorinei.AppController;
import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.greendao.DaoSession;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Encuestador;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Supervisor;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.util.InputFilterSoloLetras;
import pe.gob.inei.generadorinei.util.NumericKeyBoardTransformationMethod;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuncionariosFragment extends ComponenteFragment {
    EditText nomEncuestador, nomSupervisor, nomCoordinador;
    EditText dniEncuestador, dniSupervisor, dniCoordinador;

    Context context;

    CardView cvEncuestador, cvsupervisor, cvCoordinador;
    LinearLayout layoutPersonas;

    private String dni_encu;
    private String dni_sup;
    private  String dni_coor;
    private  String nombre_encu;
    private String nombre_sup;
    private String nombre_coord;

    private int vive;

    AppController appController;
    DaoSession daoSession;

    private DAOEncuesta dataComponentes;

    private String id_vivienda;
    private String id_hogar;
    private String id_residente;
    int tipo_guardado;
    String tabla_guardado;

    @SuppressLint("ValidFragment")
    public FuncionariosFragment(String id_vivienda, String id_hogar, Context context, AppController appController) {
        this.id_hogar = id_hogar;
        this.id_vivienda = id_vivienda;
        this.appController = appController;
        this.context = context;
    }
    
    @SuppressLint("ValidFragment")
    public FuncionariosFragment(Context context, AppController appController,
                                String id_vivienda, String id_hogar, String id_residente,
                                int tipo_guardado, String tabla_guardado) {
        this.id_vivienda = id_vivienda;
        this.id_hogar = id_hogar;
        this.id_residente = id_residente;
        this.tipo_guardado = tipo_guardado;
        this.tabla_guardado = tabla_guardado;
        this.appController = appController;
        this.context = context;
    }

    public FuncionariosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_funcionarios, container, false);
        nomEncuestador = (EditText)rootView.findViewById(R.id.funcionarios_edtnombre_encuestador);
        nomSupervisor = (EditText)rootView.findViewById(R.id.funcionarios_edtnombre_supervisor);
        nomCoordinador = (EditText)rootView.findViewById(R.id.funcionarios_edtnombre_coordinador);
        dniEncuestador = (EditText)rootView.findViewById(R.id.funcionarios_edtdni_encuestador);
        dniSupervisor = (EditText)rootView.findViewById(R.id.funcionarios_edtdni_supervisor);
        dniCoordinador = (EditText)rootView.findViewById(R.id.funcionarios_edtdni_coordinador);

        cvEncuestador = (CardView) rootView.findViewById(R.id.funcionarios_cvEncuestador);
        cvCoordinador = (CardView) rootView.findViewById(R.id.funcionarios_cvCoordinador);
        cvsupervisor = (CardView) rootView.findViewById(R.id.funcionarios_cvSupervisor);

        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configurarEditText(dniCoordinador,cvCoordinador,2,8);
        configurarEditText(dniSupervisor,cvsupervisor,2,8);
        configurarEditText(dniEncuestador,cvEncuestador,2,8);

        configurarEditText(nomEncuestador,cvEncuestador,0,100);
        configurarEditText(nomSupervisor,cvsupervisor,0,100);
        configurarEditText(nomCoordinador,cvCoordinador,0,100);

        cargarDatos();
    }

    private void configurarEditText(final EditText editText, final View viewLayout, int tipo,int longitud){
        switch (tipo){
            case 0:editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(longitud), new InputFilterSoloLetras()});break;
            case 1:editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(longitud)});break;
            case 2:editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(longitud)});
                editText.setTransformationMethod(new NumericKeyBoardTransformationMethod());break;
        }
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ocultarTeclado(editText);
                    viewLayout.requestFocus();
                    return true;
                }
                return false;
            }
        });
    }



    private void controlarEspecifiqueRadio(RadioGroup group, int checkedId, int opcionEsp, EditText editTextEspecifique) {
        int seleccionado = group.indexOfChild(group.findViewById(checkedId));
        if(seleccionado == opcionEsp){
            editTextEspecifique.setBackgroundResource(R.drawable.input_text_enabled);
            editTextEspecifique.setEnabled(true);
        }else{
            editTextEspecifique.setText("");
            editTextEspecifique.setBackgroundResource(R.drawable.input_text_disabled);
            editTextEspecifique.setEnabled(false);
        }
    }

    @Override
    public void inhabilitar() {

    }

    @Override
    public void habilitar() {

    }

    @Override
    public boolean guardarDatos() {
        boolean valido=false;
        valido = validarDatos();
        if(valido)  guardar();

        return valido;
    }

    public void guardar(){
        dataComponentes = new DAOEncuesta(context);
        dataComponentes.open();
        ContentValues contentValues = new ContentValues();
        switch (tipo_guardado){
            case 1: contentValues.put("id_vivienda", id_vivienda);
                break;
            case 2: contentValues.put("id_vivienda", id_vivienda);
                contentValues.put("id_hogar", id_hogar);
                break;
            case 3: contentValues.put("id_vivienda", id_vivienda);
                contentValues.put("id_hogar", id_hogar);
                contentValues.put("id_residente", id_residente);
                break;
        }

        dni_encu = dniEncuestador.getText().toString(); nombre_encu = nomEncuestador.getText().toString();
        dni_coor = dniCoordinador.getText().toString(); nombre_coord = nomCoordinador.getText().toString();
        dni_sup = dniSupervisor.getText().toString(); nombre_sup = nomSupervisor.getText().toString();

        contentValues.put("dni_encu", dni_encu); contentValues.put("nombre_encu", nombre_encu);
        contentValues.put("dni_coor", dni_coor); contentValues.put("nombre_coord", nombre_coord);
        contentValues.put("dni_sup", dni_sup); contentValues.put("nombre_sup", nombre_sup);

        if(dataComponentes.existeElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente)){
            dataComponentes.actualizarElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente,contentValues);
        }else{
            dataComponentes.insertarElemento(tabla_guardado,contentValues);
        }

        dataComponentes.close();
    }

    @Override
    public void cargarDatos() {
        dataComponentes = new DAOEncuesta(context);
        dataComponentes.open();

        dni_encu = dataComponentes.getElemento(tipo_guardado,tabla_guardado,"dni_encu",id_vivienda,id_hogar,id_residente);
        nombre_encu = dataComponentes.getElemento(tipo_guardado,tabla_guardado,"nombre_encu",id_vivienda,id_hogar,id_residente);
        dni_coor = dataComponentes.getElemento(tipo_guardado,tabla_guardado,"dni_coor",id_vivienda,id_hogar,id_residente);
        nombre_coord = dataComponentes.getElemento(tipo_guardado,tabla_guardado,"nombre_coord",id_vivienda,id_hogar,id_residente);
        dni_sup = dataComponentes.getElemento(tipo_guardado,tabla_guardado,"dni_sup",id_vivienda,id_hogar,id_residente);
        nombre_sup = dataComponentes.getElemento(tipo_guardado,tabla_guardado,"nombre_sup",id_vivienda,id_hogar,id_residente);

        if(dni_encu != null) dniEncuestador.setText(dni_encu);
        if(nombre_encu != null) nomEncuestador.setText(nombre_encu);
        if(dni_coor != null) dniCoordinador.setText(dni_coor);
        if(nombre_coord != null) nomCoordinador.setText(nombre_coord);
        if(dni_sup != null) dniSupervisor.setText(dni_sup);
        if(nombre_sup != null) nomSupervisor.setText(nombre_sup);

        dataComponentes.close();
    }

    @Override
    public void llenarVista() {

    }

    @Override
    public boolean validarDatos() {
        dni_encu = dniEncuestador.getText().toString();
        nombre_encu = nomEncuestador.getText().toString();
        dni_coor = dniCoordinador.getText().toString();
        nombre_coord = nomCoordinador.getText().toString();
        dni_sup = dniSupervisor.getText().toString();
        nombre_sup = nomSupervisor.getText().toString();
        if (dni_encu.trim().equals("")){
            mostrarMensaje("DNI ENCUESTADOR: FALTA COMPLETAR");
            dniEncuestador.setError("Requerido!");
            dniEncuestador.requestFocus();
            return false;
        }
        if (dni_encu.length()!=8){
            mostrarMensaje("DNI ENCUESTADOR: COMPLETAR 8 DIGITOS");
            dniEncuestador.setError("COMPLETAR 8 DIGITOS!");
            dniEncuestador.requestFocus();
            return false;
        }
        if (nombre_encu.trim().equals("")){
            mostrarMensaje("NOMBRE ENCUESTADOR: FALTA COMPLETAR");
            nomEncuestador.setError("Requerido!");
            nomEncuestador.requestFocus();
            return false;
        }
        if(!dni_sup.equals("") || !nombre_sup.equals("")){
            if (dni_sup.trim().equals("")){
                mostrarMensaje("DNI SUPERVISOR: FALTA COMPLETAR");
                dniSupervisor.setError("Requerido!");
                dniSupervisor.requestFocus();
                return false;
            }
            if (dni_sup.length()!=8){
                mostrarMensaje("DNI SUPERVISOR: COMPLETAR 8 DIGITOS");
                dniSupervisor.setError("COMPLETAR 8 DIGITOS!");
                dniSupervisor.requestFocus();
                return false;
            }
            if (nombre_sup.trim().equals("")){
                mostrarMensaje("NOMBRE SUPERVISOR: FALTA COMPLETAR");
                nomSupervisor.setError("Requerido!");
                nomSupervisor.requestFocus();
                return false;
            }
        }

        if(!dni_coor.equals("") || !nombre_coord.equals("")) {
            if (dni_coor.trim().equals("")) {
                mostrarMensaje("DNI COORDINADOR: FALTA COMPLETAR");
                dniCoordinador.setError("Requerido!");
                dniCoordinador.requestFocus();
                return false;
            }
            if (dni_coor.length() != 8) {
                mostrarMensaje("DNI COORDINADOR: COMPLETAR 8 DIGITOS");
                dniCoordinador.setError("COMPLETAR 8 DIGITOS!");
                dniCoordinador.requestFocus();
                return false;
            }
            if (nombre_coord.trim().equals("")) {
                mostrarMensaje("NOMBRE COORDINADOR: FALTA COMPLETAR");
                nomCoordinador.setError("Requerido!");
                nomCoordinador.requestFocus();
                return false;
            }
        }
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

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
