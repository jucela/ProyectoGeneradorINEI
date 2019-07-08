package pe.gob.inei.generadorinei.activities.hogares;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.gob.inei.generadorinei.AppController;
import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.adapters.ExpandListAdapter;
import pe.gob.inei.generadorinei.fragments.CheckBoxFragment;
import pe.gob.inei.generadorinei.fragments.EditTextFragment;
import pe.gob.inei.generadorinei.fragments.FormularioFragment;
import pe.gob.inei.generadorinei.fragments.GPSFragment;
import pe.gob.inei.generadorinei.fragments.HogaresFragment;
import pe.gob.inei.generadorinei.fragments.RadioFragment;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.componentes.PCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.PEditText;
import pe.gob.inei.generadorinei.model.pojos.componentes.PFormulario;
import pe.gob.inei.generadorinei.model.pojos.componentes.PGps;
import pe.gob.inei.generadorinei.model.pojos.componentes.PRadio;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPEdittext;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPFormulario;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPRadio;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Modulo;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Pagina;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Pregunta;
import pe.gob.inei.generadorinei.util.ComponenteFragment;
import pe.gob.inei.generadorinei.util.Constants;
import pe.gob.inei.generadorinei.util.NombreSeccionFragment;
import pe.gob.inei.generadorinei.util.TipoActividad;
import pe.gob.inei.generadorinei.util.TipoComponente;
import pe.gob.inei.generadorinei.util.TipoPagina;

public class ViviendaActivity extends AppCompatActivity implements View.OnTouchListener {
    ExpandableListView expListView;
    Button btnAtras, btnSiguiente;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArrayList<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpandListAdapter listAdapter;
    DAOEncuesta daoEncuesta;

    String tituloEncuesta;
    String idVivienda;
    String idUsuario;
    ArrayList<Modulo> modulos;

    LinearLayout layoutScrolleable, lytComponente1,
            lytComponente2, lytComponente3, lytComponente4,
            lytComponente5, lytComponente6, lytComponente7,
            lytComponente8, lytComponente9, lytComponente10;
    View lytFocus;

    CoordinatorLayout layoutContenedor;

    String idModuloActual = "";
    public int paginaActual = 1;
    int contadort_pagina = 0;
    int numeroPaginasTotal;
    Fragment fragmentComponente = new Fragment();
    final String TIPO_ACTIVIDAD = TipoActividad.ACTIVIDAD_VIVIENDA;

    ArrayList<Pagina> paginas = new ArrayList<>();

    AppController appController;

    private String nickUsuario;

    int tipo_guardado;
    String tabla_guardado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activities_encuesta);

        idUsuario = getIntent().getExtras().getString("idUsuario");
        idVivienda = getIntent().getExtras().getString("idVivienda");
        nickUsuario  = getIntent().getExtras().getString("nickUsuario");

        daoEncuesta = new DAOEncuesta(this);
        tituloEncuesta = daoEncuesta.getEncuesta().getTitulo();

        daoEncuesta.getTablasEncuesta();

        paginas = daoEncuesta.getAllPaginas(TIPO_ACTIVIDAD);

        numeroPaginasTotal = daoEncuesta.getNroPaginas(TIPO_ACTIVIDAD);

        conectarVistas();
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        configurarCabeceraNavigation();
        configurarListaExpandible();

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado(btnAtras);
                /*
                if (paginaActual - 1 >= 1) {
                    do{
                        paginaActual--;
                    }while(!validoSetearPagina(paginaActual));
                    setearPagina(paginaActual);
                    setNombreSeccion(paginaActual, -1);
                }
                */
                if(contadort_pagina-1>=0) {
                    contadort_pagina--;
                    //else contadort_pagina=numeroPaginasTotal-1;
                    tipo_guardado = paginas.get(contadort_pagina).getTipo_guardado();
                    tabla_guardado = paginas.get(contadort_pagina).getTabla_guardado();
                    setearPagina(Integer.parseInt(paginas.get(contadort_pagina).getNumero()));
                    setNombreSeccion(Integer.parseInt(paginas.get(contadort_pagina).getNumero()), -1);
                    paginaActual = Integer.parseInt(paginas.get(contadort_pagina).getNumero());
                    botones();
                    Log.e("VivActTablaGuard-1", "" + tabla_guardado);
                }
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado(btnSiguiente);
                if (validarPagina(paginaActual)) {
                    if(guardarPagina(paginaActual)) {
                        if (contadort_pagina + 1 < numeroPaginasTotal) {
                            contadort_pagina++;
                            //else contadort_pagina=0;
                            Log.e("cont:" + contadort_pagina, "numero:" + paginas.get(contadort_pagina).getNumero());
                            //setearPagina(paginaActual);
                            tipo_guardado = paginas.get(contadort_pagina).getTipo_guardado();
                            tabla_guardado = paginas.get(contadort_pagina).getTabla_guardado();
                            setearPagina(Integer.parseInt(paginas.get(contadort_pagina).getNumero()));
                            //setNombreSeccion(paginaActual, 1);
                            setNombreSeccion(Integer.parseInt(paginas.get(contadort_pagina).getNumero()), 1);
                            paginaActual = Integer.parseInt(paginas.get(contadort_pagina).getNumero());
                            botones();
                            Log.e("VivActTablaGuard-2", "" + tabla_guardado);
                        }
                    }
                }
            }
        });

        paginaActual = Integer.parseInt(paginas.get(contadort_pagina).getNumero());
        tipo_guardado = paginas.get(contadort_pagina).getTipo_guardado();
        tabla_guardado = paginas.get(contadort_pagina).getTabla_guardado();

        botones();

        Log.e("VivActTablaGuard-3",""+tabla_guardado);

        setNombreSeccion(paginaActual, 1);
        //setearPagina(Integer.parseInt(paginas.get(contadort_pagina).getNumero()));
        setearPagina(paginaActual);

        appController = (AppController) getApplication();

        if (ContextCompat.checkSelfPermission(ViviendaActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ViviendaActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            appController.setDB();
        }

        ocultarTeclado(btnAtras);
    }

    private boolean validarPagina(int paginaActual) {
        return true;
    }

    private boolean guardarPagina(int paginaActual) {
        boolean valido=true;
        Log.e("guardarPagina","inicio");
        Log.e("paginaActual",""+paginaActual);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Pregunta> preguntas = daoEncuesta.getPreguntasXPagina(""+paginaActual);
        int indice = 0;
        Log.e("preguntas.size()",""+preguntas.size());
        while (indice < preguntas.size()) {
            ComponenteFragment componenteFragment = (ComponenteFragment) fragmentManager.findFragmentByTag(preguntas.get(indice).get_id());
            valido = componenteFragment.guardarDatos();
            if(!valido) break;
            indice++;
        }
        Log.e("guardarPagina","fin");
        return valido;
    }

    public void setearPagina(int numeroPagina) {
        Log.e("Viv-setearPagina","0");
        Pagina pagina = daoEncuesta.getPagina(numeroPagina+"",TIPO_ACTIVIDAD);
        if (pagina.getTipo_pagina().equals(TipoPagina.NORMAL)){
            Log.e("Viv-setearPagina","Pagina: "+pagina.getTipo_pagina());
            setearPaginaNormal(pagina);
        }  else setearPaginaScrolleable(pagina);
    }

    private void setearPaginaScrolleable(Pagina pagina) {

    }

    private void setearPaginaNormal(Pagina pagina) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (layoutScrolleable.getVisibility() == View.VISIBLE) {
            fragmentTransaction.remove(fragmentManager.findFragmentById(R.id.layout_componente_scrolleable));
            layoutScrolleable.setVisibility(View.GONE);
        }

        ArrayList<Pregunta> preguntas = daoEncuesta.getPreguntasXPagina(pagina.get_id());
        int[] layouts = {R.id.layout_componente1, R.id.layout_componente2, R.id.layout_componente3, R.id.layout_componente4, R.id.layout_componente5,
                R.id.layout_componente6, R.id.layout_componente7, R.id.layout_componente8, R.id.layout_componente9, R.id.layout_componente10};
        for (int i = 0; i < layouts.length; i++) {
            if (i<preguntas.size()) {
                int tipo = Integer.parseInt(preguntas.get(i).getTipo_pregunta());
                switch (tipo) {
                    case TipoComponente.FORMULARIO:
                        PFormulario formulario = daoEncuesta.getFormulario(preguntas.get(i).get_id());
                        ArrayList<SPFormulario> formularios = daoEncuesta.getSPFormularios(preguntas.get(i).get_id());
                        //FormularioFragment formularioFragment = new FormularioFragment(formulario, formularios, ViviendaActivity.this, idVivienda);
                        Log.e("VivActTablaGuard-4",""+tabla_guardado);
                        FormularioFragment formularioFragment = new FormularioFragment(formulario, formularios, ViviendaActivity.this, idVivienda, "", "", tipo_guardado,tabla_guardado);
                        fragmentComponente = formularioFragment;
                        break;
                    case TipoComponente.EDITTEXT:
                        PEditText pEditText = daoEncuesta.getPEditText(preguntas.get(i).get_id());
                        ArrayList<SPEdittext> spEditTexts = daoEncuesta.getSPEditTexts(preguntas.get(i).get_id());
                        //EditTextFragment editTextFragment = new EditTextFragment(pEditText, spEditTexts, ViviendaActivity.this, idVivienda);
                        EditTextFragment editTextFragment = new EditTextFragment(pEditText, spEditTexts, ViviendaActivity.this, idVivienda, "", "", tipo_guardado,tabla_guardado);
                        fragmentComponente = editTextFragment;
                        break;
                    case TipoComponente.CHECKBOX:
                        PCheckbox pCheckbox = daoEncuesta.getPCheckbox(preguntas.get(i).get_id());
                        ArrayList<SPCheckbox> spCheckBoxes = daoEncuesta.getSPCheckBoxs(preguntas.get(i).get_id());
                        //CheckBoxFragment checkBoxFragment = new CheckBoxFragment(pCheckbox, spCheckBoxes, ViviendaActivity.this, idVivienda);
                        CheckBoxFragment checkBoxFragment = new CheckBoxFragment(pCheckbox, spCheckBoxes, ViviendaActivity.this, idVivienda, "", "", tipo_guardado,tabla_guardado);
                        fragmentComponente = checkBoxFragment;
                        break;
                    case TipoComponente.RADIO:
                        PRadio pRadio = daoEncuesta.getPRadio(preguntas.get(i).get_id());
                        ArrayList<SPRadio> spRadios = daoEncuesta.getSPRadios(preguntas.get(i).get_id());
                        //RadioFragment radioFragment = new RadioFragment(pRadio, spRadios, ViviendaActivity.this, idVivienda);
                        RadioFragment radioFragment = new RadioFragment(pRadio, spRadios, ViviendaActivity.this, idVivienda, "", "", tipo_guardado,tabla_guardado);
                        fragmentComponente = radioFragment;
                        break;
                    case TipoComponente.GPS:
                        PGps gps = daoEncuesta.getGPS(preguntas.get(i).get_id());
                        //GPSFragment gpsFragment = new GPSFragment(ViviendaActivity.this, idVivienda, gps);
                        GPSFragment gpsFragment = new GPSFragment(ViviendaActivity.this, gps, idVivienda, "", "", tipo_guardado,tabla_guardado);
                        fragmentComponente = gpsFragment;
                        break;
                    case TipoComponente.HOGARES:
                        HogaresFragment hogaresFragment = new HogaresFragment(idVivienda,ViviendaActivity.this, appController);
                        fragmentComponente = hogaresFragment;
                        break;
                }
                fragmentTransaction.replace(layouts[i], fragmentComponente, preguntas.get(i).get_id());
            } else {
                if (fragmentManager.findFragmentById(layouts[i]) != null) {
                    fragmentTransaction.remove(fragmentManager.findFragmentById(layouts[i]));
                }
            }
        }

        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private boolean validoSetearPagina(int paginaActual) {
        return true;
    }

    private void configurarCabeceraNavigation() {
        View headerView = navigationView.getHeaderView(0);
        TextView txtHeaderTitulo = (TextView) headerView.findViewById(R.id.header_txtTitulo);
        TextView txtHeaderEncuestado = (TextView) headerView.findViewById(R.id.header_txtEncuestado);
        TextView txtHeaderUsuario = (TextView) headerView.findViewById(R.id.header_txtUsuario);
        txtHeaderTitulo.setText(tituloEncuesta);
        txtHeaderEncuestado.setText("Vivienda " + idVivienda);
        txtHeaderUsuario.setText("Usuario: " + idUsuario);
    }

    private void conectarVistas() {
        //Barra y Drawer
        drawerLayout = findViewById(R.id.drawer_encuesta_layout);
        toolbar = findViewById(R.id.my_toolbar);
        //Botones avanzar y retorceder
        btnAtras = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        //navigation y lista expandible
        navigationView = findViewById(R.id.navigation_view);
        expListView = findViewById(R.id.lista_expandible_navigation);
        //Layouts: scrolleable para componente visitas
        //focus para funcinamiento auxiliar
        layoutContenedor = findViewById(R.id.contenedor);
        layoutContenedor.setOnTouchListener(this);
        layoutScrolleable = findViewById(R.id.layout_componente_scrolleable);
        lytFocus =  findViewById(R.id.layout_focus);
        //layouts componentes
        lytComponente1 = findViewById(R.id.layout_componente1);
        lytComponente2 = findViewById(R.id.layout_componente2);
        lytComponente3 = findViewById(R.id.layout_componente3);
        lytComponente4 = findViewById(R.id.layout_componente4);
        lytComponente5 = findViewById(R.id.layout_componente5);
        lytComponente6 = findViewById(R.id.layout_componente6);
        lytComponente7 = findViewById(R.id.layout_componente7);
        lytComponente8 = findViewById(R.id.layout_componente8);
        lytComponente9 = findViewById(R.id.layout_componente9);
        lytComponente10 = findViewById(R.id.layout_componente10);

        lytComponente1.setOnTouchListener(this);
        lytComponente2.setOnTouchListener(this);
        lytComponente3.setOnTouchListener(this);
        lytComponente4.setOnTouchListener(this);
        lytComponente5.setOnTouchListener(this);
        lytComponente6.setOnTouchListener(this);
        lytComponente7.setOnTouchListener(this);
        lytComponente8.setOnTouchListener(this);
        lytComponente9.setOnTouchListener(this);
        lytComponente10.setOnTouchListener(this);
    }

    private void configurarListaExpandible() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        prepareListData(listDataHeader, listDataChild);
        listAdapter = new ExpandListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ArrayList<Modulo> modulos = daoEncuesta.getAllModulos(TIPO_ACTIVIDAD);
                Log.e("modulos.size()",""+modulos.size());
                Modulo modulo = modulos.get(groupPosition);
                ArrayList<Pagina> paginas = daoEncuesta.getPaginasxModulo(modulo.get_id());
                int numPagina = Integer.parseInt(paginas.get(childPosition).getNumero());
                if (numPagina < paginaActual) setNombreSeccion(numPagina, -1);
                else setNombreSeccion(numPagina, 1);
                setearPagina(numPagina);
                paginaActual = numPagina;
                return false;
            }
        });
    }

    private void prepareListData(List<String> listDataHeader, Map<String, List<String>> listDataChild) {

        ArrayList<Modulo> modulos = daoEncuesta.getAllModulos(TIPO_ACTIVIDAD);

        Log.e("modulos.size()",""+modulos.size());
        for (Modulo moduloActual : modulos) {
            //pone la cabecera
            listDataHeader.add(moduloActual.getCabecera());
            ArrayList<Pagina> paginas = daoEncuesta.getPaginasxModulo(moduloActual.get_id());
            List<String> subItems = new ArrayList<String>();
            //busca los subtitulos
            for (Pagina paginaActual : paginas) {
                subItems.add(paginaActual.getNombre());
            }
            //agrega cabecera y subtitulos
            listDataChild.put(listDataHeader.get(listDataHeader.size() - 1), subItems);
        }
    }

    public void setNombreSeccion(int nPagina, int direccion) {
        String nombreSeccion = "";
        int numeroDePagina = nPagina;
        Log.e("numeroDePagina",""+numeroDePagina);
        Log.e("TIPO_ACTIVIDAD",""+TIPO_ACTIVIDAD);
        String idModulo = daoEncuesta.getPagina(numeroDePagina + "",TIPO_ACTIVIDAD).getModulo();
        Log.e("idModuloActual","6-"+idModuloActual);
        Log.e("idModulo",""+idModulo);
        if (!idModulo.equals(idModuloActual)) {
            nombreSeccion = daoEncuesta.getModulo(idModulo,TIPO_ACTIVIDAD).getTitulo();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (direccion > 0) {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            } else {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
            }
            NombreSeccionFragment nombreSeccionFragment = new NombreSeccionFragment(nombreSeccion);
            fragmentTransaction.replace(R.id.lytNombreModulo, nombreSeccionFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            idModuloActual = idModulo;
        }
    }

    public void ocultarTeclado(View view) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public String getNickUsuario() {
        return nickUsuario;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro que desea volver al marco? (no se guardaran los cambios)")
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
                                finish();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void botones(){
        if(contadort_pagina==0){
            btnAtras.setText("");
            btnAtras.setEnabled(false);
        }
        else {
            btnAtras.setText("Ant");
            btnAtras.setEnabled(true);
        }

        if((contadort_pagina+1)==paginas.size()) btnSiguiente.setText("");
        else btnSiguiente.setText("Sig");
    }

    public static final String DEBUG_TAG = "GesturesActivity";
    float previousX=0f,previousY=0f;

    boolean entro=false;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        float x=event.getX();
        float y=event.getY();

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                //Log.e(DEBUG_TAG, "La accion ha sido ABAJO");
                entro=false;
                previousX = event.getX();
                previousY = event.getY();
                Log.e("ACTION_DOWN-x",""+previousX);
                Log.e("ACTION_DOWN-y",""+previousY);
                return true;
            case (MotionEvent.ACTION_MOVE):
                //Log.e(DEBUG_TAG, "La acción ha sido MOVER");
                Log.e("ACTION_MOVE-x0",""+previousX);
                Log.e("ACTION_MOVE-y0",""+previousY);
                Log.e("ACTION_MOVE-x1",""+event.getX());
                Log.e("ACTION_MOVE-y1",""+event.getY());
                if(!entro && previousX > event.getX() && (previousY>=(event.getY()-2) && previousY<=(event.getY()+2))){
                    Log.e(DEBUG_TAG, "La acción ha sido MOVER Izquierda");
                    entro = true;
                    if (validarPagina(paginaActual)) {
                        if(guardarPagina(paginaActual)) {
                            if (contadort_pagina + 1 < numeroPaginasTotal) {
                                contadort_pagina++;
                                //else contadort_pagina=0;
                                Log.e("cont:" + contadort_pagina, "numero:" + paginas.get(contadort_pagina).getNumero());
                                //setearPagina(paginaActual);
                                tipo_guardado = paginas.get(contadort_pagina).getTipo_guardado();
                                tabla_guardado = paginas.get(contadort_pagina).getTabla_guardado();
                                setearPagina(Integer.parseInt(paginas.get(contadort_pagina).getNumero()));
                                //setNombreSeccion(paginaActual, 1);
                                setNombreSeccion(Integer.parseInt(paginas.get(contadort_pagina).getNumero()), 1);
                                paginaActual = Integer.parseInt(paginas.get(contadort_pagina).getNumero());
                                botones();
                                Log.e("VivActTablaGuard-2", "" + tabla_guardado);
                            }
                        }
                    }
                }
                else if(!entro && previousX <= event.getX()  && (previousY>=(event.getY()-2) && previousY<=(event.getY()+2))){
                    Log.e(DEBUG_TAG, "La acción ha sido MOVER Derecha");
                    entro = true;
                    ocultarTeclado(btnAtras);
                    if(contadort_pagina-1>=0) {
                        contadort_pagina--;
                        //else contadort_pagina=numeroPaginasTotal-1;
                        tipo_guardado = paginas.get(contadort_pagina).getTipo_guardado();
                        tabla_guardado = paginas.get(contadort_pagina).getTabla_guardado();
                        setearPagina(Integer.parseInt(paginas.get(contadort_pagina).getNumero()));
                        setNombreSeccion(Integer.parseInt(paginas.get(contadort_pagina).getNumero()), -1);
                        paginaActual = Integer.parseInt(paginas.get(contadort_pagina).getNumero());
                        botones();
                        Log.e("VivActTablaGuard-1", "" + tabla_guardado);
                    }
                }
                return true;
            case (MotionEvent.ACTION_UP):
                //Log.e(DEBUG_TAG, "La acción ha sido ARRIBA");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                //Log.e(DEBUG_TAG, "La accion ha sido CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                //Log.e(DEBUG_TAG,"La accion ha sido fuera del elemento de la pantalla");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }
}
