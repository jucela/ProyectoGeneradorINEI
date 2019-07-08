package pe.gob.inei.generadorinei.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.activities.hogares.EncuestaActivity;
import pe.gob.inei.generadorinei.activities.hogares.HogarActivity;
import pe.gob.inei.generadorinei.activities.hogares.ViviendaActivity;
import pe.gob.inei.generadorinei.activities.hogares.agregacion.ResidenteActivity;
import pe.gob.inei.generadorinei.interfaces.ActividadInterfaz;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.componentes.PRadio;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPRadio;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.util.Herramientas;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadioFragment extends ComponenteFragment {

    private PRadio pRadio;
    private ArrayList<SPRadio> subpreguntas;
    private Context  context;
    private TextView txtPregunta;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6, rb7, rb8, rb9, rb10, rb11, rb12, rb13, rb14, rb15;
    private EditText edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8, edit9, edit10, edit11, edit12, edit13, edit14, edit15;
    private RadioButton[] radioButtons;
    private EditText[] editTexts;
    private View rootView;
    private int[] idRbs = {R.id.radio_sp1, R.id.radio_sp2, R.id.radio_sp3, R.id.radio_sp4, R.id.radio_sp5, R.id.radio_sp6,
            R.id.radio_sp7, R.id.radio_sp8, R.id.radio_sp9, R.id.radio_sp10, R.id.radio_sp11, R.id.radio_sp12, R.id.radio_sp13,
            R.id.radio_sp14, R.id.radio_sp15};
    private int[] idEdits = {R.id.radio_descripcion1, R.id.radio_descripcion2, R.id.radio_descripcion3, R.id.radio_descripcion4, R.id.radio_descripcion5,
            R.id.radio_descripcion6, R.id.radio_descripcion7, R.id.radio_descripcion8, R.id.radio_descripcion9, R.id.radio_descripcion10, R.id.radio_descripcion11,
            R.id.radio_descripcion12, R.id.radio_descripcion13, R.id.radio_descripcion14, R.id.radio_descripcion15};
    private boolean cargandoDatos = false;

    private DAOEncuesta dataComponentes;

    private String id_vivienda;
    private String id_hogar;
    private String id_residente;
    int tipo_guardado;
    String tabla_guardado;
    ResidenteActivity padre_residente;
    EncuestaActivity padre_encuesta;
    HogarActivity padre_hogar;
    ViviendaActivity padre_vivienda;

    private EditText edtVacio;
    boolean sin_seleccionar=false;
    
    public RadioFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public RadioFragment(PRadio pRadio, ArrayList<SPRadio> subpreguntas, Context context, String id_vivienda) {
        this.pRadio = pRadio;
        this.subpreguntas = subpreguntas;
        this.context = context;
        this.id_vivienda = id_vivienda;
    }

    @SuppressLint("ValidFragment")
    public RadioFragment(PRadio pRadio, ArrayList<SPRadio> subpreguntas, Context context,
                         String id_vivienda, String id_hogar, String id_residente,
                         int tipo_guardado, String tabla_guardado) {
        this.pRadio = pRadio;
        this.subpreguntas = subpreguntas;
        this.context = context;
        this.id_vivienda = id_vivienda;
        this.id_hogar = id_hogar;
        this.id_residente = id_residente;
        this.tipo_guardado = tipo_guardado;
        this.tabla_guardado = tabla_guardado;

        if(context.getClass().getSimpleName().equals("ViviendaActivity")){
            this.padre_vivienda = (ViviendaActivity) context;
        }
        if(context.getClass().getSimpleName().equals("HogarActivity")){
            this.padre_hogar = (HogarActivity) context;
        }
        if(context.getClass().getSimpleName().equals("EncuestaActivity")){
            this.padre_encuesta = (EncuestaActivity) context;
        }
        if(context.getClass().getSimpleName().equals("ResidenteActivity")){
            this.padre_residente = (ResidenteActivity) context;
        }

        cargandoDatos = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_radio, container, false);
        txtPregunta = (TextView) rootView.findViewById(R.id.radio_pregunta);
        radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_grupo);

        radioButtons = new RadioButton[]{rb1,rb2,rb3,rb4, rb5,rb6,rb7,rb8,rb9,rb10,rb11,rb12,rb13,rb14,rb15};
        editTexts = new EditText[]{edit1,edit2,edit3,edit4,edit5,edit6,edit7,edit8,edit9,edit10,edit11,edit12,edit13,edit14,edit15};

        for (int i = 0; i <subpreguntas.size() ; i++) {
            radioButtons[i] = (RadioButton) rootView.findViewById(idRbs[i]);
        }
        for (int i = 0; i <subpreguntas.size() ; i++) {
            editTexts[i] = (EditText) rootView.findViewById(idEdits[i]);
        }

        edtVacio = (EditText) rootView.findViewById(R.id.radio_edittext_vacio);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llenarVista();
        cargarDatos();
    }

    @Override
    public void llenarVista(){
        txtPregunta.setText(pRadio.getNumero() + ". " + pRadio.getPregunta().toUpperCase());
        for (int i = 0; i <subpreguntas.size() ; i++) {
            final SPRadio spRadio = subpreguntas.get(i);
            final RadioButton radioButton = radioButtons[i];
            final EditText editText = editTexts[i];
            radioButton.setVisibility(View.VISIBLE);

            radioButton.setText(spRadio.getSubpregunta());
            if(!spRadio.getVar_especifique().equals("")){
                editText.setVisibility(View.VISIBLE);
                editText.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            ocultarTeclado(editText);
                            radioGroup.requestFocus();
                            return true;
                        }
                        return false;
                    }
                });
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(cargandoDatos) {
                            guardar();
                        }
                    }
                });
            }


            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(!spRadio.getVar_especifique().equals("")){
                        if(isChecked){
                            editText.setEnabled(true);
                            editText.setBackgroundResource(R.drawable.edittext_enabled);
                        } else{
                            editText.setError(null);
                            editText.setText("");
                            editText.setEnabled(false);
                            editText.setBackgroundResource(R.drawable.edittext_disabled);
                        }
                    }
                    if(sin_seleccionar){
                        if(isChecked){
                            radio_Requerido(false);
                            sin_seleccionar = false;
                        }

                    }
                }
            });
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
                int posicion = (group.indexOfChild(radioButton)/2)+1;
                //ActividadInterfaz actividadInterfaz = (ActividadInterfaz) getActivity();
                if(cargandoDatos) {
                    guardar();
                    Log.e("RadioFragment","Nombre variable: "+subpreguntas.get(1).getVar_input());
                    Log.e("RadioFragment","cargandoDatos-0");
                    Log.e("RadioFragment","posicion-"+posicion);

                    boolean tiene_flujo=false;
                    dataComponentes = new DAOEncuesta(context);
                    dataComponentes.open();
                    tiene_flujo = dataComponentes.realizar_flujo(tipo_guardado,subpreguntas.get(1).getVar_input(),id_vivienda,id_hogar,id_residente);
                    dataComponentes.close();
                    if(tiene_flujo){
                        if(context.getClass().getSimpleName().equals("ViviendaActivity")) padre_vivienda.setearPagina(padre_vivienda.paginaActual);
                        if(context.getClass().getSimpleName().equals("HogarActivity")) padre_hogar.setearPagina(padre_hogar.paginaActual);
                        if(context.getClass().getSimpleName().equals("EncuestaActivity")) padre_encuesta.setearPagina(padre_encuesta.paginaActual);
                        if(context.getClass().getSimpleName().equals("ResidenteActivity")) padre_residente.setearPagina(padre_residente.paginaActual);
                    }
                }
                //if(actividadInterfaz.existeEvento(pRadio.get_id())){
                //    actividadInterfaz.realizarEvento(pRadio.get_id(),posicion + "",cargandoDatos);
                //}
            }
        });
    }

    @Override
    public void cargarDatos(){
        String valorCheck;
        String valorEspecifique;
        dataComponentes = new DAOEncuesta(context);
        dataComponentes.open();

        if(dataComponentes.existeElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente)){
            valorCheck = dataComponentes.getElemento(tipo_guardado,tabla_guardado,subpreguntas.get(1).getVar_input(),id_vivienda,id_hogar,id_residente);
            if(!Herramientas.texto(valorCheck).equals("-1") && !Herramientas.texto(valorCheck).equals("")){
                valorCheck = "" + (Integer.parseInt(valorCheck)-1);
            }
            if(valorCheck != null ){
                if (!valorCheck.equals("")){
                    int childPos = Integer.parseInt(valorCheck);
                    if(childPos != -1) ((RadioButton) radioGroup.getChildAt(childPos*2)).setChecked(true);
                }
            }
            for (int i = 0; i < subpreguntas.size() ; i++) {
                if(!subpreguntas.get(i).getVar_especifique().equals("")){
                    valorEspecifique = dataComponentes.getElemento(tipo_guardado,tabla_guardado,subpreguntas.get(i).getVar_especifique(),id_vivienda,id_hogar,id_residente);
                    if(valorEspecifique != null)editTexts[i].setText(valorEspecifique);
                }
            }
        }

        cargandoDatos = true;

        dataComponentes.close();
    }

    @Override
    public boolean guardarDatos(){
        boolean valido=false;
        valido = validarDatos();
        if(valido)  guardar();

        return valido;
    }

    public void guardar(){
        ContentValues contentValues = new ContentValues();
        dataComponentes = new DAOEncuesta(context);
        dataComponentes.open();

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
        if(radioGroup.getCheckedRadioButtonId() == -1) contentValues.put(subpreguntas.get(1).getVar_input(),"-1");
        else contentValues.put(subpreguntas.get(1).getVar_input(),(radioGroup.indexOfChild(radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()))/2)+1);
        for (int i = 0; i < subpreguntas.size(); i++) {
            if(!subpreguntas.get(i).getVar_especifique().equals(""))contentValues.put(subpreguntas.get(i).getVar_especifique(),editTexts[i].getText().toString());
        }
        if(dataComponentes.existeElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente)){
            dataComponentes.actualizarElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente,contentValues);
        }else{
            dataComponentes.insertarElemento(tabla_guardado,contentValues);
        }

        dataComponentes.close();
    }

    @Override
    public boolean validarDatos(){
        boolean correcto = true;
        String mensaje = "";
        if(estaHabilitado()){
            if(radioGroup.getCheckedRadioButtonId() == -1){
                sin_seleccionar = true;
                correcto = false;
                radio_Requerido(true);
                if(mensaje.equals("")) mensaje = "PREGUNTA " + pRadio.getNumero() + ": DEBE SELECCIONAR UNA OPCION";
            }else{
                for (int i = 0; i <subpreguntas.size() ; i++) {
                    if(editTexts[i].isEnabled() && !subpreguntas.get(i).getSubpregunta().equals("")){
                        String campo = editTexts[i].getText().toString().trim();
                        if(campo.equals("")){
                            correcto = false;
                            if(mensaje.equals("")){
                                mensaje = "PREGUNTA " + pRadio.getNumero() + ": DEBE ESPECIFICAR";
                                editTexts[i].setError("Requerido!");
                                editTexts[i].requestFocus();
                            }
                        }
                    }
                }
            }
        }
        if(!correcto){
            mostrarMensaje(mensaje);
            edtVacio.requestFocus();
        }
        return correcto;
    }

    @Override
    public void inhabilitar(){
        radioGroup.clearCheck();
        rootView.setVisibility(View.GONE);
    }

    @Override
    public void habilitar() {
        rootView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean estaHabilitado(){
        boolean habilitado = false;
        if(rootView.getVisibility() == View.VISIBLE) habilitado = true;
        return habilitado;
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

    @Override
    public String getIdTabla(){
        return pRadio.getId_tabla();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void radio_Requerido(boolean requerido){
        for (int c = 0; c <subpreguntas.size() ; c++) {
            if(requerido) radioButtons[c].setError("Requerido!");
            else radioButtons[c].setError(null);
        }
    }
}
