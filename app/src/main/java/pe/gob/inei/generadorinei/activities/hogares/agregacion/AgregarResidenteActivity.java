package pe.gob.inei.generadorinei.activities.hogares.agregacion;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;



import java.util.ArrayList;

import pe.gob.inei.generadorinei.AppController;
import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.greendao.DaoSession;
import pe.gob.inei.generadorinei.greendao.Residente_g;
import pe.gob.inei.generadorinei.util.Herramientas;
import pe.gob.inei.generadorinei.util.InterfazOperaciones;
import pe.gob.inei.generadorinei.util.NumericKeyBoardTransformationMethod;

public class AgregarResidenteActivity extends AppCompatActivity implements InterfazOperaciones {

    TextInputEditText c2_p202_TextInputET, c2_p205_a_TextInputET, c2_p205_m_TextInputET;
    Spinner c2_p203_Spinner,c2_p206_Spinner;
    RadioGroup c2_p204_RadioGroup,c2_p207_RadioGroup;
    Toolbar toolbar;

    private String numero_residente;
    private String numero_hogar;
    private String id_vivienda;
    private String numero;
    private String idJefeHogar;
    private String id_informante = "";
    private String c2_p202;
    private int c2_p203;
    private int c2_p204;
    private String c2_p205_a;
    private String c2_p205_m;
    private String edadJefeHogar="0";
    private String c2_p206;
    private int c2_p207;
    private int cant_p_s_h=0,cant_p_s_m=0;

    boolean jefe_hogar=false,existe_conyuge=false;
    int cant_padres_suegros=0,edad_jefe_hogar=0;

    boolean editar=true;

    int sexo_residente=-1;

    Residente_g residente;
    AppController appController;
    DaoSession daoSession;
    boolean nuevo=true;

    private LinearLayout linearLayout202,linearLayout203,linearLayout204,linearLayout205,linearLayout206;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_residente);
        appController = (AppController) getApplication();
        daoSession = appController.getDaoSession();

        numero_residente = getIntent().getExtras().getString("numero_residente");
        numero_hogar = getIntent().getExtras().getString("numero_hogar");
        id_vivienda = getIntent().getExtras().getString("id_vivienda");
        numero = getIntent().getExtras().getString("numero_hogar");
        idJefeHogar = getIntent().getExtras().getString("idJefeHogar");

        if(daoSession.getResidenteDao().getId(id_vivienda,numero_hogar,numero_residente)==-1) nuevo = true;
        else nuevo=false;
        
        linearLayout202 = (LinearLayout) findViewById(R.id.layout_m2_p202);
        linearLayout203 = (LinearLayout) findViewById(R.id.layout_m2_p203);
        linearLayout204 = (LinearLayout) findViewById(R.id.layout_m2_p204);
        linearLayout205 = (LinearLayout) findViewById(R.id.layout_m2_p205);
        linearLayout206 = (LinearLayout) findViewById(R.id.layout_m2_p206);


        c2_p202_TextInputET = (TextInputEditText) findViewById(R.id.mod2_202_textinputedittext_C2_P202);
        c2_p203_Spinner = (Spinner) findViewById(R.id.mod2_203_spinner_C2_P203);
        c2_p204_RadioGroup = (RadioGroup) findViewById(R.id.mod2_204_radiogroup_C2_P204);
        c2_p205_a_TextInputET = (TextInputEditText) findViewById(R.id.mod2_205_textinputedittext_C2_P205_A);
        c2_p205_m_TextInputET = (TextInputEditText) findViewById(R.id.mod2_205_textinputedittext_C2_P205_M);
        c2_p206_Spinner = (Spinner) findViewById(R.id.mod2_206_spinner_C2_P206);
        c2_p207_RadioGroup = (RadioGroup) findViewById(R.id.mod2_207_radiogroup_C2_P207);
        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AGREGAR RESIDENTE");
        getSupportActionBar().setSubtitle("RESIDENTE Nº " + numero);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        configurarEditText(c2_p202_TextInputET,linearLayout202,1,40);
        configurarEditText(c2_p205_a_TextInputET,linearLayout205,2,2);
        configurarEditText(c2_p205_m_TextInputET,linearLayout205,2,2);

        c2_p205_a_TextInputET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")){
                    c2_p205_m_TextInputET.setEnabled(true);
                }else{
                    c2_p205_m_TextInputET.setEnabled(false);
                    if (Integer.parseInt(editable.toString()) < 12){
                        c2_p206_Spinner.setSelection(0);
                        linearLayout206.setVisibility(View.GONE);
                    }else{
                        linearLayout206.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        c2_p205_m_TextInputET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")){
                    c2_p205_a_TextInputET.setEnabled(true);
                    linearLayout206.setVisibility(View.VISIBLE);
                }else {
                    linearLayout206.setVisibility(View.GONE);
                    c2_p205_a_TextInputET.setEnabled(false);
                }
            }
        });

        cargarDatos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_guardar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_guardar:
                llenarVariables();
                if (validarDatos()){
                    guardarDatos();
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public String getNombreTabla() {
        return "";
    }


    @Override
    public void llenarVariables(){
        id_informante = "";
        c2_p202 = c2_p202_TextInputET.getText().toString();
        c2_p203 = c2_p203_Spinner.getSelectedItemPosition();
        c2_p204 = c2_p204_RadioGroup.indexOfChild(c2_p204_RadioGroup.findViewById(c2_p204_RadioGroup.getCheckedRadioButtonId()));
        c2_p205_a = c2_p205_a_TextInputET.getText().toString();
        c2_p205_m = c2_p205_m_TextInputET.getText().toString();
        c2_p206 = c2_p206_Spinner.getSelectedItemPosition() + "";
        c2_p207 = c2_p207_RadioGroup.indexOfChild(c2_p207_RadioGroup.findViewById(c2_p207_RadioGroup.getCheckedRadioButtonId()));
    }

    public boolean validarDatos(){
        llenarVariables();
        if(c2_p202.trim().equals("")){mostrarMensaje("PREGUNTA 202: DEBE INDICAR EL NOMBRE"); return false;}
        if(c2_p203 == 0) {mostrarMensaje("PREGUNTA 203: DEBE INDICAR EL PARENTESCO"); return false;}
        else{
            if (c2_p203 == 3 || c2_p203 == 5){
                int edad = 0;
                if (!c2_p205_a.equals("")) edad = Integer.parseInt(c2_p205_a);
                if(edadJefeHogar.equals("0")){mostrarMensaje("PREGUNTA 205: FALTA INGRESAR EDAD DE JEFE DE HOGAR, DEBE COMPLETAR PRIMERO LA INFORMACIÓN DEL JEFE DE HOGAR"); return false;}
                if (edad > Integer.parseInt(edadJefeHogar)){mostrarMensaje("PREGUNTA 205: SI ES HIJO/A O NIETO/A, LA EDAD DEBE SER MENOR A LA EDAD DEL JEFE DEL HOGAR ("+edadJefeHogar+")"); return false;}
                if (c2_p203 == 3 && (Integer.parseInt(edadJefeHogar) - edad)<12){mostrarMensaje("PREGUNTA 205: La diferencia de edades del jefe del hogar("+edadJefeHogar+") y el hijo no corresponde"); return false;}
                if (c2_p203 == 5 && (Integer.parseInt(edadJefeHogar) - edad)<30){mostrarMensaje("PREGUNTA 205: La diferencia de edades del jefe del hogar("+edadJefeHogar+") y el nieto  no corresponde"); return false;}
            }
        }
        if(!jefe_hogar && c2_p203 == 1){mostrarMensaje("PREGUNTA 203: Existe más de un jefe de hogar"); return false;}

        if(cant_padres_suegros>=4 && c2_p203 == 6){mostrarMensaje("PREGUNTA 203: Existe más de cuatro padres/suegros en el hogar"); return false;}
        if(existe_conyuge && c2_p203 == 2){mostrarMensaje("PREGUNTA 203: Existe más de un cónyuge en el hogar"); return false;}
        int edadd = 0;
        if (!c2_p205_a.equals("")) edadd = Integer.parseInt(c2_p205_a);
        if(c2_p203==1 && edadd<12) {mostrarMensaje("PREGUNTA 205: La edad del Jefe del hogar debe ser mayor o igual a 12 años"); return false;}
        if(c2_p203==2 && edadd<12) {mostrarMensaje("PREGUNTA 205: El cónyuge debe ser mayor o igual a 12 años"); return false;}
        if(c2_p203==6 && edadd<33) {mostrarMensaje("PREGUNTA 205: Los padres o suegros deben ser mayor o igual a 33 años"); return false;}
        if(c2_p203==9 && edadd<5) {mostrarMensaje("PREGUNTA 205: La trabajadora del hogar debe ser mayor o igual a 5 años"); return false;}
        if(c2_p203==4 && (edadd<12 || edadd>80)) {mostrarMensaje("PREGUNTA 205: Verificar la edad del yerno o nuera"); return false;}
        if(c2_p204 == -1) {mostrarMensaje("PREGUNTA 204: DEBE INDICAR EL SEXO"); return false;}
        if(c2_p203==6){
            if(editar){
                if(sexo_residente!=c2_p204){
                    if(cant_p_s_h>=2 && c2_p204==1){
                        mostrarMensaje("PREGUNTA 204: YA INGRESO DOS PADRES/SUEGOS - HOMBRE"); return false;
                    }
                    if(cant_p_s_m>=2 && c2_p204==2){
                        mostrarMensaje("PREGUNTA 204: YA INGRESO DOS PADRES/SUEGOS - MUJER"); return false;
                    }
                }
            }else{
                if(cant_p_s_h>=2 && c2_p204==1){
                    mostrarMensaje("PREGUNTA 204: YA INGRESO DOS PADRES/SUEGOS - HOMBRE"); return false;
                }
                if(cant_p_s_m>=2 && c2_p204==2){
                    mostrarMensaje("PREGUNTA 204: YA INGRESO DOS PADRES/SUEGOS - MUJER"); return false;
                }
            }
        }
        if(c2_p205_a.trim().equals("") && c2_p205_m.trim().equals("")) {mostrarMensaje("PREGUNTA 205: DEBE INDICAR LA EDAD EN AÑOS O MESES"); return false;}
        if(!c2_p205_a.trim().equals("")) {
            if(Integer.parseInt(c2_p205_a)<1 || Integer.parseInt(c2_p205_a)>99){
                mostrarMensaje("PREGUNTA 205: AÑOS DEBE SER MAYOR QUE CERO");
                return false;
            }
        }
        if(!c2_p205_m.trim().equals("")) {
            if(Integer.parseInt(c2_p205_m)<0 || Integer.parseInt(c2_p205_m)>11){
                mostrarMensaje("PREGUNTA 205: MESES DEBE ESTAR EN EL INTERVALO DE 0 A 11");
                return false;
            }
        }

        if(!c2_p205_a.trim().equals("")){
            if(Integer.parseInt(c2_p205_a)<12){
                if(c2_p203==1 || c2_p203==2 || c2_p203==4 || c2_p203==6 || c2_p203==10){
                    mostrarMensaje("PREGUNTA 203: NO SE DEBE SELECCIONAR LAS OPCIONES (Jefe/a del hogar, Esposo/a o compañero/a, Yerno/Nuera, Padres/Suegros, Pensionista)"); return false;
                }
            }
        }
        if(!c2_p205_m.trim().equals("")){
            if(c2_p203==1 || c2_p203==2 || c2_p203==4 || c2_p203==6 || c2_p203==10){
                mostrarMensaje("PREGUNTA 203: NO SE DEBE SELECCIONAR LAS OPCIONES (Jefe/a del hogar, Esposo/a o compañero/a, Yerno/Nuera, Padres/Suegros, Pensionista)"); return false;
            }
        }
        if (linearLayout206.getVisibility()==View.VISIBLE){
            if(c2_p206.equals("0")) {mostrarMensaje("PREGUNTA 206: DEBE INDICAR EL ESTADO CIVIL"); return false;}
            if(!jefe_hogar && c2_p203 == 4 && c2_p206.equals("6")){mostrarMensaje("PREGUNTA 206: Estado civil de yerno/nuera no corresponde"); return false;}
        }else{
            c2_p206 = "";
        }
        if(c2_p207 == -1) {mostrarMensaje("PREGUNTA 207: DEBE INDICAR SI LLEGÓ DE VENEZUELA"); return false;}

        if (Integer.parseInt(numero) > 1) {
            if (c2_p203 == 1) {mostrarMensaje("PREGUNTA 203: SOLO PUEDE HABER UN JEFE DE HOGAR"); return false;}
        }

        return true;
    }

    @Override
    public void cargarDatos() {
        daoSession = appController.getDaoSession();
        editar=false;
        int edad_p=0;
        jefe_hogar = false; existe_conyuge = false; cant_padres_suegros = 0; edad_jefe_hogar = 0;

        Log.e("cargarDatos-nuevo:",""+nuevo);

        if(!nuevo){
            Log.e("cargarDatos-id_viv:",""+id_vivienda);
            Log.e("cargarDatos-numero_h:",""+numero_hogar);
            Log.e("cargarDatos-numero_r:",""+numero_residente);
            Residente_g residente = daoSession.getResidenteDao().getResidente(id_vivienda,numero_hogar,numero_residente);
            if (Herramientas.texto(residente.getNumero_residente()).equals("1")){
                c2_p202_TextInputET.setEnabled(false);
                c2_p203_Spinner.setEnabled(false);
                jefe_hogar = true;
            }else {
                jefe_hogar = false;
            }
            Log.e("cargarDatos-getC2_p202:",""+residente.getC2_p202());
            c2_p202_TextInputET.setText(residente.getC2_p202());
            c2_p203_Spinner.setSelection(Integer.parseInt(residente.getC2_p203()));
            if (!Herramientas.texto(residente.getC2_p204()).equals("")){
                ((RadioButton)c2_p204_RadioGroup.getChildAt(Integer.parseInt(residente.getC2_p204()))).setChecked(true);
                sexo_residente = Integer.parseInt(residente.getC2_p204());
            }
            c2_p205_a_TextInputET.setText(Herramientas.texto(residente.getC2_p205_a()));
            c2_p205_m_TextInputET.setText(Herramientas.texto(residente.getC2_p205_m()));
            if(!Herramientas.texto(residente.getC2_p206()).equals(""))c2_p206_Spinner.setSelection(Integer.parseInt(residente.getC2_p206()));
            if (!Herramientas.texto(residente.getC2_p207()).equals(""))((RadioButton)c2_p207_RadioGroup.getChildAt(Integer.parseInt(residente.getC2_p207()))).setChecked(true);
            if(!Herramientas.texto(residente.getC2_p205_a()).equals("")) edad_p = Integer.parseInt(residente.getC2_p205_a());
            if(edad_p<12){
                c2_p206_Spinner.setSelection(0);
                linearLayout206.setVisibility(View.GONE);
            }else{
                linearLayout206.setVisibility(View.VISIBLE);
            }
        }
        Residente_g residente_jefe = daoSession.getResidenteDao().getResidente(id_vivienda,numero_hogar,"1");
        if(Herramientas.texto(residente_jefe.getC2_p205_a()).equals("")){
            edadJefeHogar = "0";
        }else {
            edadJefeHogar = residente_jefe.getC2_p205_a();
        }
        ArrayList<Residente_g> residentes;

        residentes = new ArrayList<>();

        residentes = daoSession.getResidenteDao().getResidentes(id_vivienda,numero_hogar);

        if(Integer.parseInt(numero)<=residentes.size()){
            editar = true;
        }
        cant_p_s_h=0; cant_p_s_m = 0;
        if(residentes.size()>0){
            for(Residente_g r: residentes){
                if(Herramientas.texto(r.getC2_p203()).equals("1")){
                    if(Herramientas.texto(r.getC2_p205_a()).equals("")){r.setC2_p205_a("0");}
                    edad_jefe_hogar = Integer.parseInt(r.getC2_p205_a());
                }
                if(Herramientas.texto(r.getC2_p203()).equals("2")){
                    existe_conyuge = true;
                }
                if(Herramientas.texto(r.getC2_p203()).equals("6")){
                    cant_padres_suegros++;
                    if(Herramientas.texto(r.getC2_p204()).equals("1")){
                        cant_p_s_m++;
                    }
                    if(Herramientas.texto(r.getC2_p204()).equals("2")){
                        cant_p_s_m++;
                    }
                }
            }
        }
    }

    @Override
    public void llenarVista() {

    }

    @Override
    public void guardarDatos(){
        daoSession = appController.getDaoSession();
        residente = new Residente_g();

        if(nuevo){
            residente.setId_vivienda(id_vivienda);
            residente.setNumero_hogar(numero_hogar);
            residente.setNumero_residente(numero_residente);
        }else{
            residente = daoSession.getResidenteDao().getResidente(id_vivienda,numero_hogar,numero_residente);
        }

        residente.setC2_p202(c2_p202);
        residente.setC2_p203(c2_p203+"");
        residente.setC2_p204(c2_p204+"");
        residente.setC2_p205_a(c2_p205_a);
        residente.setC2_p205_m(c2_p205_m);
        residente.setC2_p206(c2_p206);
        residente.setC2_p207(c2_p207+"");

        if(nuevo) daoSession.getResidenteDao().insert(residente);
        else daoSession.getResidenteDao().update(residente);
    }

    public void mostrarMensaje(String m){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(m);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void configurarEditText(final EditText editText, final View view, int tipo,int longitud){
        if (tipo == 1) editText.setFilters(new InputFilter[]{new InputFilter.AllCaps(), new InputFilter.LengthFilter(longitud)});

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ocultarTeclado(editText);
                    view.requestFocus();
                    return true;
                }
                return false;
            }
        });
        if (tipo == 2) {
            editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(longitud)});
            editText.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        }
    }

    public void ocultarTeclado(View view){
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }   
}
