package pe.gob.inei.generadorinei.fragments;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.componentes.PFormulario;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPFormulario;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.util.NumericKeyBoardTransformationMethod;
import pe.gob.inei.generadorinei.util.TipoInput;


/**
 * A simple {@link Fragment} subclass.
 * Ricardo Morales
 */
public class FormularioFragment extends ComponenteFragment {

    private View rootView;
    private DAOEncuesta dataComponentes;
    private PFormulario formulario;
    private ArrayList<SPFormulario> subpreguntas;
    private Context context;
    private String idFormulario;
    private TextView formularioTitulo, txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10;
    private CardView cv1,cv2,cv3,cv4,cv5,cv6,cv7,cv8,cv9,cv10;
    private EditText edtSP1,edtSP2, edtSP3, edtSP4, edtSP5, edtSP6, edtSP7, edtSP8, edtSP9, edtSP10,
                    edtE1,edtE2,edtE3,edtE4,edtE5,edtE6,edtE7,edtE8,edtE9,edtE10;
    private Spinner sp1, sp2, sp3, sp4, sp5, sp6, sp7, sp8, sp9, sp10;
    private CheckBox ck1,ck2,ck3,ck4,ck5,ck6,ck7,ck8,ck9,ck10;

    private RadioGroup rg1, rg2, rg3, rg4, rg5, rg6, rg7, rg8, rg9, rg10;
    private EditText edtRG1,edtRG2, edtRG3, edtRG4, edtRG5, edtRG6, edtRG7, edtRG8, edtRG9, edtRG10;

    private LinearLayout formularioLayout, lyt1,lyt2,lyt3,lyt4,lyt5,lyt6,lyt7,lyt8,lyt9,lyt10;

    private LinearLayout lyR1,lyR2,lyR3,lyR4,lyR5,lyR6,lyR7,lyR8,lyR9,lyR10;

    private CardView[] cardViews;
    private TextView[] textViews;
    private EditText[] editTexts;
    private LinearLayout[] layoutSpinners;
    private Spinner[] spinners;
    private EditText[] edtEspecifiques;
    private CheckBox[] checkBoxes;

    private LinearLayout[] layoutRadios;
    private RadioGroup[] radios;
    private EditText[] edtEspecifiquer;

    private String id_vivienda;
    private String id_hogar;
    private String id_residente;
    int tipo_guardado;
    String tabla_guardado;

    boolean prueba=false;

    private int[] idCardViews = {R.id.formulario_sp1, R.id.formulario_sp2, R.id.formulario_sp3, R.id.formulario_sp4,
            R.id.formulario_sp5, R.id.formulario_sp6, R.id.formulario_sp7, R.id.formulario_sp8, R.id.formulario_sp9,
            R.id.formulario_sp10};

    public FormularioFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public FormularioFragment(PFormulario formulario, ArrayList<SPFormulario> subpreguntas, Context context, String id_vivienda) {
        this.formulario = formulario;
        this.subpreguntas = subpreguntas;
        this.context = context;
        this.id_vivienda = id_vivienda;
    }

    @SuppressLint("ValidFragment")
    public FormularioFragment(PFormulario formulario, ArrayList<SPFormulario> subpreguntas, Context context,
                              String id_vivienda, String id_hogar, String id_residente,
                              int tipo_guardado, String tabla_guardado) {
        this.formulario = formulario;
        this.subpreguntas = subpreguntas;
        this.context = context;
        this.id_vivienda = id_vivienda;
        this.id_hogar = id_hogar;
        this.id_residente = id_residente;
        this.tipo_guardado = tipo_guardado;
        this.tabla_guardado = tabla_guardado;
        Log.e("Formulario-tab-guard",""+this.tabla_guardado);
        prueba = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_formulario, container, false);
        formularioTitulo = (TextView) rootView.findViewById(R.id.formulario_titulo);
        formularioLayout = (LinearLayout) rootView.findViewById(R.id.formulario_layout);
        cardViews = new CardView[] {cv1, cv2, cv3, cv4, cv5, cv6, cv7, cv8, cv9, cv10};
        textViews = new TextView[]{txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8,txt9,txt10};
        editTexts = new EditText[]{edtSP1, edtSP2, edtSP3, edtSP4, edtSP5, edtSP6, edtSP7, edtSP8, edtSP9, edtSP10};
        layoutSpinners = new LinearLayout[]{lyt1,lyt2,lyt3,lyt4,lyt5,lyt6,lyt7,lyt8,lyt9,lyt10};
        layoutRadios = new LinearLayout[]{lyR1,lyR2,lyR3,lyR4,lyR5,lyR6,lyR7,lyR8,lyR9,lyR10};
        spinners = new Spinner[]{sp1, sp2, sp3, sp4, sp5, sp6, sp7, sp8, sp9, sp10};
        edtEspecifiques = new EditText[]{edtE1,edtE2,edtE3,edtE4,edtE5,edtE6,edtE7,edtE8,edtE9,edtE10};
        checkBoxes = new CheckBox[]{ck1,ck2,ck3,ck4,ck5,ck6,ck7,ck8,ck9,ck10};
        radios = new RadioGroup[]{rg1,rg2,rg3,rg4,rg5,rg6,rg7,rg8,rg9,rg10};
        edtEspecifiquer = new EditText[]{edtRG1,edtRG2,edtRG3,edtRG4,edtRG5,edtRG6,edtRG7,edtRG8,edtRG9,edtRG10};
        for (int i = 0; i <subpreguntas.size() ; i++) {
            cardViews[i] = (CardView) rootView.findViewById(idCardViews[i]);
        }
        for (int i = 0; i <subpreguntas.size() ; i++) {
            textViews[i] = (TextView) cardViews[i].findViewById(R.id.formulario_sp_textview);
            editTexts[i] = (EditText) cardViews[i].findViewById(R.id.formulario_sp_edittext);
            layoutSpinners[i] = (LinearLayout) cardViews[i].findViewById(R.id.formulario_sp_spinner_layout);
            layoutRadios[i] = (LinearLayout) cardViews[i].findViewById(R.id.formulario_sp_radioSN_layout);
            spinners[i] = (Spinner) cardViews[i].findViewById(R.id.formulario_sp_spinner);
            edtEspecifiques[i] = (EditText) cardViews[i].findViewById(R.id.formulario_sp_especifique);
            checkBoxes[i] = (CheckBox) cardViews[i].findViewById(R.id.formulario_sp_checkbox);
            radios[i] = (RadioGroup) cardViews[i].findViewById(R.id.formulario_sp_radioSN);
            edtEspecifiquer[i] = (EditText) cardViews[i].findViewById(R.id.formulario_sp_especifique_radioSN);
        }
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        llenarVista();
        if(prueba) cargarDatos();
    }


    public void llenarVista(){
        formularioTitulo.setText(formulario.getTitulo());

        formularioTitulo.setTextColor(getResources().getColor(R.color.colorIcons));
        formularioTitulo.setBackgroundColor(getResources().getColor(R.color.greyBluePrimaryDark));
        formularioTitulo.setGravity(Gravity.CENTER);
        //formularioLayout.setBackgroundColor(getResources().getColor(R.color.greyBluePrimary));

        for (int i = 0; i <subpreguntas.size() ; i++) {
            final SPFormulario spFormulario = subpreguntas.get(i);
            final EditText editText = editTexts[i];
            final EditText edtEspecifique = edtEspecifiques[i];
            final EditText edtEspecifiqueR = edtEspecifiquer[i];
            final Spinner spinner = spinners[i];
            final RadioGroup radio = radios[i];
            final LinearLayout layoutSpinner = layoutSpinners[i];
            final LinearLayout layoutRadio = layoutRadios[i];
            cardViews[i].setVisibility(View.VISIBLE);
            textViews[i].setText(spFormulario.getSubpregunta());

            if(spFormulario.getSubpregunta().equals("")) {
                Log.e("getSubpregunta()","6");
                textViews[i].setVisibility(View.GONE);
            }

            if(!spFormulario.getVar_edittext().equals("")) {
                Log.e("Entro aqui: ",""+66);
                editText.setVisibility(View.VISIBLE);

                if(spFormulario.getHab_edittext().equals("1")) editText.setEnabled(true);
                else editText.setEnabled(false);

                editText.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                        if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            ocultarTeclado(editText);
                            layoutSpinner.requestFocus();
                            return true;
                        }
                        return false;
                    }
                });
                if(Integer.parseInt(spFormulario.getTipo_edittext()) == TipoInput.TEXTO) {
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    editText.setFilters(new InputFilter[]{
                            new InputFilter.AllCaps(),
                            new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_edittext()))
                    });
                }else{
                    editText.setTransformationMethod(new NumericKeyBoardTransformationMethod());
                    editText.setFilters(new InputFilter[]{
                            new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_edittext()))
                    });
                }
            }else if(!spFormulario.getVar_spinner().equals("")){
                layoutSpinners[i].setVisibility(View.VISIBLE);
                dataComponentes = new DAOEncuesta(context);
                dataComponentes.open();
                ArrayList<String> ops = dataComponentes.getOpcionesSpinner(spFormulario.getVar_spinner());
                dataComponentes.close();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,ops);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinners[i].setAdapter(adapter);

                if(!spFormulario.getVar_esp_spinner().equals("")){
                    edtEspecifique.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                ocultarTeclado(edtEspecifique);
                                layoutSpinner.requestFocus();
                                return true;
                            }
                            return false;
                        }
                    });
                    if(Integer.parseInt(spFormulario.getTipo_esp_spinner()) == TipoInput.TEXTO) {
                        edtEspecifique.setInputType(InputType.TYPE_CLASS_TEXT);
                        edtEspecifique.setFilters(new InputFilter[]{
                                new InputFilter.AllCaps(),
                                new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_esp_spinner()))
                        });
                    }else{
                        edtEspecifique.setTransformationMethod(new NumericKeyBoardTransformationMethod());
                        edtEspecifique.setFilters(new InputFilter[]{
                                new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_esp_spinner()))
                        });
                    }
                    spinners[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(position == Integer.parseInt(spFormulario.getHab_esp_spinner())){
                                edtEspecifique.setEnabled(true);
                                edtEspecifique.setBackgroundResource(R.drawable.edittext_enabled);
                            }else{
                                edtEspecifique.setText("");
                                edtEspecifique.setEnabled(false);
                                edtEspecifique.setBackgroundResource(R.drawable.edittext_disabled);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }else edtEspecifique.setVisibility(View.GONE);
            }else if(!spFormulario.getVar_radio().equals("")){
                formularioTitulo.setTextColor(getResources().getColor(R.color.colorIneiPrimary));
                formularioTitulo.setBackgroundColor(getResources().getColor(R.color.colorIcons));
                formularioTitulo.setGravity(Gravity.LEFT);
                formularioLayout.setBackgroundColor(getResources().getColor(R.color.colorIcons));

                layoutRadios[i].setVisibility(View.VISIBLE);
                if(!spFormulario.getVar_esp_radio().equals("")){
                    edtEspecifiqueR.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                            if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                                ocultarTeclado(edtEspecifiqueR);
                                layoutRadio.requestFocus();
                                return true;
                            }
                            return false;
                        }
                    });
                    if(Integer.parseInt(spFormulario.getTipo_esp_spinner()) == TipoInput.TEXTO) {
                        edtEspecifiqueR.setInputType(InputType.TYPE_CLASS_TEXT);
                        edtEspecifiqueR.setFilters(new InputFilter[]{
                                new InputFilter.AllCaps(),
                                new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_esp_spinner()))
                        });
                    }else{
                        edtEspecifiqueR.setTransformationMethod(new NumericKeyBoardTransformationMethod());
                        edtEspecifiqueR.setFilters(new InputFilter[]{
                                new InputFilter.LengthFilter(Integer.parseInt(spFormulario.getLong_esp_spinner()))
                        });
                    }
                    radios[i].setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            controlarEspecifiqueRadio(group, checkedId,1,edtEspecifiqueR);
                        }
                    });
                }else edtEspecifiqueR.setVisibility(View.GONE);
            }

            if(tipo_guardado>1){
                formularioTitulo.setTextColor(getResources().getColor(R.color.colorIneiPrimary));
                formularioTitulo.setBackgroundColor(getResources().getColor(R.color.colorIcons));
                formularioTitulo.setGravity(Gravity.LEFT);
                formularioLayout.setBackgroundColor(getResources().getColor(R.color.colorIcons));
            }

            if (!spFormulario.getVar_check_no().equals("")){
                CheckBox checkBox = checkBoxes[i];
                checkBox.setVisibility(View.VISIBLE);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!spFormulario.getVar_edittext().equals("")){
                            if(isChecked){
                                editText.setText("");
                                editText.setEnabled(false);
                                editText.setBackgroundResource(R.drawable.edittext_disabled);
                            }else{
                                editText.setEnabled(true);
                                editText.setBackgroundResource(R.drawable.edittext_enabled);
                            }
                        }else if(!spFormulario.getVar_spinner().equals("")){
                            if(isChecked){
                                spinner.setSelection(0);
                                spinner.setEnabled(false);
                                edtEspecifique.setText("");
                                edtEspecifique.setEnabled(false);
                                edtEspecifique.setBackgroundResource(R.drawable.edittext_disabled);
                            }else{
                                spinner.setEnabled(true);
                            }
                        }else if(!spFormulario.getVar_radio().equals("")){
                            if(isChecked){
                                radio.clearCheck();
                                radio.setEnabled(false);
                                if(!spFormulario.getVar_esp_radio().equals("")){
                                    edtEspecifiqueR.setText("");
                                    edtEspecifiqueR.setEnabled(false);
                                    edtEspecifiqueR.setBackgroundResource(R.drawable.edittext_disabled);
                                }
                            }else{
                                radio.setEnabled(true);
                            }
                        }
                    }
                });
            }
        }
    }

    public void cargarDatos(){
        dataComponentes = new DAOEncuesta(context);
        dataComponentes.open();
        String valorEdit;
        String valorEsp;
        String valorSp;
        String valorRg;
        String valorCheck;
        Log.e("incio","cargarDatos");
        Log.e("tipo_guardado",""+tipo_guardado);
        Log.e("tabla_guardado",""+tabla_guardado);
        Log.e("id_vivienda",""+id_vivienda);
        Log.e("id_residente",""+id_residente);
        if(dataComponentes.existeElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente)){
            Log.e("existeElemento","true");
            for (int i = 0; i < subpreguntas.size() ; i++){
                SPFormulario spFormulario = subpreguntas.get(i);
                if(!spFormulario.getVar_edittext().equals("")){
                    valorEdit = dataComponentes.getElemento(tipo_guardado,tabla_guardado,spFormulario.getVar_edittext(),id_vivienda,id_hogar,id_residente);
                    if(valorEdit != null) editTexts[i].setText(valorEdit);
                }else if(!spFormulario.getVar_spinner().equals("")){
                    valorSp = dataComponentes.getElemento(tipo_guardado,tabla_guardado,spFormulario.getVar_spinner(),id_vivienda,id_hogar,id_residente);
                    if(valorSp != null && !valorSp.equals("")) spinners[i].setSelection(Integer.parseInt(valorSp));
                    if(edtEspecifiques[i].getVisibility()==View.VISIBLE) {
                        valorEsp = dataComponentes.getElemento(tipo_guardado,tabla_guardado,spFormulario.getVar_esp_spinner(),id_vivienda,id_hogar,id_residente);
                        if(valorEsp != null) edtEspecifiques[i].setText(valorEsp);
                    }
                }else{
                    valorRg = dataComponentes.getElemento(tipo_guardado,tabla_guardado,spFormulario.getVar_radio(),id_vivienda,id_hogar,id_residente);
                    if(valorRg != null && !valorRg.equals("") && !valorRg.equals("-1")){
                        int childPos = Integer.parseInt(valorRg);
                        Log.e("childPos",""+childPos);
                        ((RadioButton) radios[i].getChildAt(childPos)).setChecked(true);
                        if(edtEspecifiquer[i].getVisibility()==View.VISIBLE) {
                            valorEsp = dataComponentes.getElemento(tipo_guardado,tabla_guardado,spFormulario.getVar_esp_radio(),id_vivienda,id_hogar,id_residente);
                            if(valorEsp != null) edtEspecifiquer[i].setText(valorEsp);
                        }
                    }
                }
                if(!spFormulario.getVar_check_no().equals("")){
                    valorCheck = dataComponentes.getElemento(tipo_guardado,tabla_guardado,spFormulario.getVar_check_no(),id_vivienda,id_hogar,id_residente);
                    if(valorCheck != null) {if(valorCheck.equals("1"))checkBoxes[i].setChecked(true);}
                }
            }
        }
        Log.e("fin","cargarDatos");
        dataComponentes.close();
    }

    public boolean guardarDatos(){
        boolean valido=false;
        valido = validarDatos();
        if(valido)  guardar();

        return valido;
    }

    public void guardar(){
        if (subpreguntas.size() > 0){
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
            for (int i = 0; i < subpreguntas.size(); i++) {
                SPFormulario spFormulario = subpreguntas.get(i);
                if(!spFormulario.getVar_edittext().equals(""))
                    contentValues.put(spFormulario.getVar_edittext(),editTexts[i].getText().toString());
                else if(!spFormulario.getVar_spinner().equals("")){
                    contentValues.put(spFormulario.getVar_spinner(),spinners[i].getSelectedItemPosition());
                    if(edtEspecifiques[i].getVisibility() == View.VISIBLE) contentValues.put(spFormulario.getVar_esp_spinner(),edtEspecifiques[i].getText().toString());
                }else{
                    if(radios[i].getCheckedRadioButtonId() == -1){
                        contentValues.put(spFormulario.getVar_radio(),"-1");
                    }else{
                        int posicion = radios[i].indexOfChild(radios[i].findViewById(radios[i].getCheckedRadioButtonId()));
                        Log.e("posicion",""+posicion);
                        contentValues.put(spFormulario.getVar_radio(),posicion);
                        if(edtEspecifiquer[i].getVisibility() == View.VISIBLE) contentValues.put(spFormulario.getVar_esp_radio(),edtEspecifiquer[i].getText().toString());
                    }
                }
                if(!spFormulario.getVar_check_no().equals("")) {
                    if(checkBoxes[i].isChecked())contentValues.put(spFormulario.getVar_check_no(),1);
                    else contentValues.put(spFormulario.getVar_check_no(),0);
                    if(edtEspecifiquer[i].getVisibility() == View.VISIBLE) contentValues.put(spFormulario.getVar_esp_radio(),edtEspecifiquer[i].getText().toString());
                }
            }

            if(dataComponentes.existeElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente)){
                dataComponentes.actualizarElemento(tipo_guardado,tabla_guardado,id_vivienda,id_hogar,id_residente,contentValues);
            }else{
                dataComponentes.insertarElemento(tabla_guardado,contentValues);
            }
            dataComponentes.close();
        }
    }

    public boolean validarDatos(){
        boolean correcto = true;
        String mensaje = "";
        int c = 0;

        /*
        while(correcto && c < subpreguntas.size()){
            if(checkBoxes[c].getVisibility() != View.VISIBLE || !checkBoxes[c].isChecked()){
                if(editTexts[c].getVisibility() == View.VISIBLE){
                    if(editTexts[c].getText().toString().trim().equals("")){
                        correcto = false;
                        mensaje = "PREGUNTA " + formulario.getTitulo() + "(" + subpreguntas.get(c).getSubpregunta()+ ")" + ": COMPLETE LA PREGUNTA";
                    }
                }else{
                    if(spinners[c].getSelectedItemPosition() == 0){
                        correcto = false;
                        mensaje = "PREGUNTA " + formulario.getTitulo() + "(" + subpreguntas.get(c).getSubpregunta()+ ")" + ": DEBE SELECCIONAR UNA OPCION";
                    }else{
                        if(edtEspecifiques[c].isEnabled() && edtEspecifiques[c].getText().toString().trim().equals("")){
                            correcto = false;
                            mensaje = "PREGUNTA " + formulario.getTitulo() + "(" + subpreguntas.get(c).getSubpregunta()+ ")" + ": DEBE ESPECIFICAR";
                        }
                    }
                }
            }
            c++;
        }
        */

        for (int i = 0; i < subpreguntas.size(); i++) {
            SPFormulario spFormulario = subpreguntas.get(i);
            if(checkBoxes[i].getVisibility() != View.VISIBLE || !checkBoxes[i].isChecked()) {
                if (!spFormulario.getVar_edittext().equals("")) {
                    if (editTexts[i].getText().toString().trim().equals("")) {
                        correcto = false;
                        mensaje = "PREGUNTA " + formulario.getTitulo() + "(" + subpreguntas.get(i).getSubpregunta() + ")" + ": COMPLETE LA PREGUNTA";
                        editTexts[i].setError("Requerido!");
                        editTexts[i].requestFocus();
                        break;
                    }
                } else if (!spFormulario.getVar_spinner().equals("")) {
                    if (spinners[i].getSelectedItemPosition() == 0) {
                        correcto = false;
                        mensaje = "PREGUNTA " + formulario.getTitulo() + "(" + subpreguntas.get(i).getSubpregunta() + ")" + ": DEBE SELECCIONAR UNA OPCION";
                        break;
                    } else {
                        if (edtEspecifiques[i].isEnabled() && edtEspecifiques[i].getText().toString().trim().equals("")) {
                            correcto = false;
                            mensaje = "PREGUNTA " + formulario.getTitulo() + "(" + subpreguntas.get(i).getSubpregunta() + ")" + ": DEBE ESPECIFICAR";
                            edtEspecifiques[i].setError("Requerido!");
                            edtEspecifiques[i].requestFocus();
                            break;
                        }
                    }
                } else {
                    if (radios[i].getCheckedRadioButtonId() == -1) {
                        correcto = false;
                        mensaje = "PREGUNTA " + formulario.getTitulo() + "(" + subpreguntas.get(i).getSubpregunta() + ")" + ": DEBE SELECCIONAR UNA OPCION";
                        break;
                    } else {
                        if (edtEspecifiquer[i].getVisibility() == View.VISIBLE) {
                            if (edtEspecifiquer[i].isEnabled() && edtEspecifiquer[i].getText().toString().trim().equals("")) {
                                correcto = false;
                                mensaje = "PREGUNTA " + formulario.getTitulo() + "(" + subpreguntas.get(i).getSubpregunta() + ")" + ": DEBE ESPECIFICAR";
                                edtEspecifiquer[i].setError("Requerido!");
                                edtEspecifiquer[i].requestFocus();
                                break;
                            }
                        }
                    }
                }
            }
        }

        if(!correcto) mostrarMensaje(mensaje);
        return correcto;
    }

    public void inhabilitar(){
        rootView.setVisibility(View.GONE);
    }

    @Override
    public void habilitar() {
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

    public String getIdTabla(){
        return formulario.getId_modulo();
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
}
