package pe.gob.inei.generadorinei.fragments;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.componentes.PEditText;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPEdittext;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.util.NumericKeyBoardTransformationMethod;
import pe.gob.inei.generadorinei.util.TipoInput;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditTextFragment extends ComponenteFragment {
    private PEditText pEditText;
    private ArrayList<SPEdittext> subpreguntas;
    private Context context;
    private String id_vivienda;
    private String id_hogar;
    private String id_residente;


    private TextView txtPregunta;
    private LinearLayout edtLyt1,edtLyt2,edtLyt3;
    private EditText edtSP1,edtSP2,edtSP3;
    private TextView txt1, txt2, txt3;
    private View rootView;

    private LinearLayout[] linearLayouts;
    private EditText[] editTexts;
    private TextView[] textViews;
    private boolean cargandoDatos = false;

    int tipo_guardado;
    String tabla_guardado;

    private DAOEncuesta dataComponentes;

    public EditTextFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public EditTextFragment(PEditText pEditText, ArrayList<SPEdittext> subpreguntas, Context context,
                            String id_vivienda) {
        this.pEditText = pEditText;
        this.subpreguntas = subpreguntas;
        this.context = context;
        this.id_vivienda = id_vivienda;
    }

    @SuppressLint("ValidFragment")
    public EditTextFragment(PEditText pEditText, ArrayList<SPEdittext> subpreguntas, Context context,
                            String id_vivienda, String id_hogar, String id_residente,
                            int tipo_guardado, String tabla_guardado) {
        this.pEditText = pEditText;
        this.subpreguntas = subpreguntas;
        this.context = context;
        this.id_vivienda = id_vivienda;
        this.id_hogar = id_hogar;
        this.id_residente = id_residente;
        this.tipo_guardado = tipo_guardado;
        this.tabla_guardado = tabla_guardado;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit_text, container, false);
        txtPregunta = rootView.findViewById(R.id.edittext_pregunta);
        edtLyt1 = (LinearLayout) rootView.findViewById(R.id.edittext_sp1);
        edtLyt2 = (LinearLayout) rootView.findViewById(R.id.edittext_sp2);
        edtLyt3 = (LinearLayout) rootView.findViewById(R.id.edittext_sp3);
        edtSP1 = (EditText) edtLyt1.findViewById(R.id.edit_text_input);
        edtSP2 = (EditText) edtLyt2.findViewById(R.id.edit_text_input);
        edtSP3 = (EditText) edtLyt3.findViewById(R.id.edit_text_input);
        txt1 = (TextView) edtLyt1.findViewById(R.id.edit_text_input_texto);
        txt2 = (TextView) edtLyt2.findViewById(R.id.edit_text_input_texto);
        txt3 = (TextView) edtLyt3.findViewById(R.id.edit_text_input_texto);


        linearLayouts = new LinearLayout[]{edtLyt1,edtLyt2,edtLyt3};
        editTexts = new EditText[]{edtSP1,edtSP2,edtSP3};
        textViews = new TextView[]{txt1, txt2, txt3};
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llenarVista();
        cargarDatos();
    }

    public void llenarVista(){
        txtPregunta.setText(pEditText.getNumero() + ". " + pEditText.getPregunta().toUpperCase());
        for (int i = 0; i < subpreguntas.size(); i++) {
            SPEdittext spEditText = subpreguntas.get(i);
            final LinearLayout linearLayout = linearLayouts[i];
            final EditText editText = editTexts[i];
            final TextView textView = textViews[i];
            editText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        ocultarTeclado(editText);
                        linearLayout.requestFocus();
                        return true;
                    }
                    return false;
                }
            });
            linearLayout.setVisibility(View.VISIBLE);
            textView.setText(spEditText.getSubpregunta());
            if(Integer.parseInt(spEditText.getTipo()) == TipoInput.TEXTO) {
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                editText.setFilters(new InputFilter[]{
                        new InputFilter.AllCaps(),
                        new InputFilter.LengthFilter(Integer.parseInt(spEditText.getLongitud()))
                });
            }else{
                editText.setTransformationMethod(new NumericKeyBoardTransformationMethod());
                editText.setFilters(new InputFilter[]{
                        new InputFilter.LengthFilter(Integer.parseInt(spEditText.getLongitud()))
                });
            }
        }
    }

    public void cargarDatos(){
        dataComponentes = new DAOEncuesta(context);
        dataComponentes.open();

        if(dataComponentes.existeElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente)){
            String[] variables = new String[subpreguntas.size()];
            for (int i = 0; i < subpreguntas.size() ; i++) variables[i] = subpreguntas.get(i).getVar_input();
            String[] valores = dataComponentes.getElementos(tipo_guardado,tabla_guardado,variables,id_vivienda,id_hogar,id_residente);
            for (int i = 0; i < valores.length; i++) {if(valores[i] != null) editTexts[i].setText(valores[i]);}
        }

        dataComponentes.close();
    }

    public boolean guardarDatos(){
        boolean valido=false;
        valido = validarDatos();
        if(valido)  guardar();

        return valido;
    }

    public void guardar(){
        Log.e("guardarDatos",""+2);
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

        for (int i = 0; i < subpreguntas.size(); i++) {
            String variable = subpreguntas.get(i).getVar_input();
            String valor = editTexts[i].getText().toString();
            contentValues.put(variable, valor);
        }

        if(dataComponentes.existeElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente)){
            dataComponentes.actualizarElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente,contentValues);
        }else{
            dataComponentes.insertarElemento(tabla_guardado,contentValues);
        }

        Log.e("guardarDatos",""+3);
        dataComponentes.close();
    }

    public boolean validarDatos(){
        boolean correcto = true;
        String mensaje = "";
        int c = 0;
        if(estaHabilitado()){
            while(correcto && c < linearLayouts.length){
                if(linearLayouts[c].getVisibility() == View.VISIBLE){
                    if(editTexts[c].getText().toString().trim().equals("")){
                        correcto = false;
                        mensaje = "PREGUNTA " + pEditText.getNumero() + ": COMPLETE LA PREGUNTA";
                        editTexts[c].setError("Requerido!");
                        editTexts[c].requestFocus();
                    }
                }
                c++;
            }
        }
        if(!correcto) mostrarMensaje(mensaje);

        return correcto;
    }

    public void inhabilitar(){
        for (int i = 0; i <subpreguntas.size() ; i++) editTexts[i].setText("");
        rootView.setVisibility(View.GONE);
    }

    public void habilitar(){
        rootView.setVisibility(View.VISIBLE);
    }

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
    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public String getIdTabla(){
        return pEditText.getId_tabla();
    }
}
