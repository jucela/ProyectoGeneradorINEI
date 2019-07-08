package pe.gob.inei.generadorinei.activities.hogares.agregacion;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import pe.gob.inei.generadorinei.fragments.FragmentVisitasEncuestador;
import pe.gob.inei.generadorinei.fragments.FuncionariosFragment;
import pe.gob.inei.generadorinei.fragments.RadioFragment;
import pe.gob.inei.generadorinei.fragments.ResidentesFragment;
import pe.gob.inei.generadorinei.greendao.DaoSession;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.componentes.PCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.PEditText;
import pe.gob.inei.generadorinei.model.pojos.componentes.PFormulario;
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

public class ResidenteActivity extends AppCompatActivity {

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
    String usuario = "ENC0001";
    String encuestado = "1266";
    ArrayList<Modulo> modulos;

    LinearLayout layoutScrolleable, lytComponente1,
            lytComponente2, lytComponente3, lytComponente4,
            lytComponente5, lytComponente6, lytComponente7,
            lytComponente8, lytComponente9, lytComponente10;
    View lytFocus;

    String idModuloActual = "";
    public int paginaActual = 1;
    int contadort_pagina = 0;
    int numeroPaginasTotal;
    Fragment fragmentComponente = new Fragment();
    final String TIPO_ACTIVIDAD = TipoActividad.ACTIVIDAD_RESIDENTE;

    ArrayList<Pagina> paginas = new ArrayList<>();

    String idVivienda;
    String numero_hogar;
    String numero_residente;
    String numero;
    String idJefeHogar;

    AppController appController;

    int tipo_guardado;
    String tabla_guardado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activities_encuesta);

        appController = (AppController) getApplication();

        idVivienda = getIntent().getExtras().getString("id_vivienda");
        numero_hogar = getIntent().getExtras().getString("numero_hogar");
        numero_residente = getIntent().getExtras().getString("numero_residente");
        numero = getIntent().getExtras().getString("numero_hogar");
        idJefeHogar = getIntent().getExtras().getString("idJefeHogar");

        daoEncuesta = new DAOEncuesta(this);
        tituloEncuesta = daoEncuesta.getEncuesta().getTitulo();
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
                if(contadort_pagina-1>=0) contadort_pagina--;
                else contadort_pagina=numeroPaginasTotal-1;
                tipo_guardado = paginas.get(contadort_pagina).getTipo_guardado();
                tabla_guardado = paginas.get(contadort_pagina).getTabla_guardado();
                Log.e("Hogar_g-tipo_guardado:",""+tipo_guardado);
                Log.e("Hogar_g-tabla_guardado:",""+tabla_guardado);
                setearPagina(Integer.parseInt(paginas.get(contadort_pagina).getNumero()));
                setNombreSeccion(Integer.parseInt(paginas.get(contadort_pagina).getNumero()), -1);
                paginaActual = Integer.parseInt(paginas.get(contadort_pagina).getNumero());
                botones();
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
                            tipo_guardado = paginas.get(contadort_pagina).getTipo_guardado();
                            tabla_guardado = paginas.get(contadort_pagina).getTabla_guardado();
                            Log.e("Hogar_g-tipo_guardado:", "" + tipo_guardado);
                            Log.e("Hogar_g-tabla_guardado:", "" + tabla_guardado);
                            setearPagina(Integer.parseInt(paginas.get(contadort_pagina).getNumero()));
                            setNombreSeccion(Integer.parseInt(paginas.get(contadort_pagina).getNumero()), 1);
                            paginaActual = Integer.parseInt(paginas.get(contadort_pagina).getNumero());
                            botones();
                        } else {

                            finish();
                        }
                    }
                }
            }
        });

        paginas = daoEncuesta.getAllPaginas(TIPO_ACTIVIDAD);
        paginaActual = Integer.parseInt(paginas.get(contadort_pagina).getNumero());
        tipo_guardado = paginas.get(contadort_pagina).getTipo_guardado();
        tabla_guardado = paginas.get(contadort_pagina).getTabla_guardado();
        setNombreSeccion(paginaActual, 1);
        setearPagina(paginaActual);

        botones();

        if (ContextCompat.checkSelfPermission(ResidenteActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ResidenteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            appController.setDB();
        }

        Log.e("Hogar_g-tipo_guardado:",""+tipo_guardado);
        Log.e("Hogar_g-tabla_guardado:",""+tabla_guardado);
    }

    private boolean validarPagina(int paginaActual) {
        return true;
    }

    private boolean guardarPagina(int paginaActual) {
        boolean valido=true;
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Pregunta> preguntas = daoEncuesta.getPreguntasXPagina(""+paginaActual);
        int indice = 0;
        while (indice < preguntas.size()) {
            ComponenteFragment componenteFragment = (ComponenteFragment) fragmentManager.findFragmentByTag(preguntas.get(indice).get_id());
            valido = componenteFragment.guardarDatos();
            if(!valido) break;
            indice++;
        }
        return valido;
    }

    public void setearPagina(int numeroPagina) {
        Pagina pagina = daoEncuesta.getPagina(numeroPagina+"",TIPO_ACTIVIDAD);
        if (pagina.getTipo_pagina().equals(TipoPagina.NORMAL)) setearPaginaNormal(pagina);
        else setearPaginaScrolleable(pagina);
    }

    private void setearPaginaScrolleable(Pagina pagina) {
    }

    private void setearPaginaNormal(Pagina pagina) {
        Log.e("setearPaginaNormal",""+1);
        appController = (AppController) getApplication();
        DaoSession daoSession = appController.getDaoSession();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (layoutScrolleable.getVisibility() == View.VISIBLE) {
            fragmentTransaction.remove(fragmentManager.findFragmentById(R.id.layout_componente_scrolleable));
            layoutScrolleable.setVisibility(View.GONE);
        }

        Log.e("pagina.get_id()",""+pagina.get_id());

        ArrayList<Pregunta> preguntas = daoEncuesta.getPreguntasXPagina(pagina.get_id());

        Log.e("preguntas.size(): ",""+preguntas.size());

        int[] layouts = {R.id.layout_componente1, R.id.layout_componente2, R.id.layout_componente3, R.id.layout_componente4, R.id.layout_componente5,
                R.id.layout_componente6, R.id.layout_componente7, R.id.layout_componente8, R.id.layout_componente9, R.id.layout_componente10};
        for (int i = 0; i < layouts.length; i++) {
            if (i<preguntas.size()) {
                int tipo = Integer.parseInt(preguntas.get(i).getTipo_pregunta());
                Log.e("setearPaginaNormal-tipo",""+tipo);
                switch (tipo) {
                    case TipoComponente.FORMULARIO:
                        PFormulario formulario = daoEncuesta.getFormulario(preguntas.get(i).get_id());
                        ArrayList<SPFormulario> formularios = daoEncuesta.getSPFormularios(preguntas.get(i).get_id());
                        //FormularioFragment formularioFragment = new FormularioFragment(formulario, formularios, HogarActivity.this, idVivienda);
                        FormularioFragment formularioFragment = new FormularioFragment(formulario, formularios, ResidenteActivity.this, idVivienda, numero_hogar, numero_residente, tipo_guardado,tabla_guardado);
                        fragmentComponente = formularioFragment;
                        break;
                    case TipoComponente.EDITTEXT:
                        PEditText pEditText = daoEncuesta.getPEditText(preguntas.get(i).get_id());
                        ArrayList<SPEdittext> spEditTexts = daoEncuesta.getSPEditTexts(preguntas.get(i).get_id());
                        //EditTextFragment editTextFragment = new EditTextFragment(pEditText, spEditTexts, HogarActivity.this, encuestado);
                        EditTextFragment editTextFragment = new EditTextFragment(pEditText, spEditTexts, ResidenteActivity.this, idVivienda, numero_hogar, numero_residente, tipo_guardado,tabla_guardado);
                        fragmentComponente = editTextFragment;
                        break;
                    case TipoComponente.CHECKBOX:
                        PCheckbox pCheckbox = daoEncuesta.getPCheckbox(preguntas.get(i).get_id());
                        ArrayList<SPCheckbox> spCheckBoxes = daoEncuesta.getSPCheckBoxs(preguntas.get(i).get_id());
                        //CheckBoxFragment checkBoxFragment = new CheckBoxFragment(pCheckbox, spCheckBoxes, HogarActivity.this, encuestado);
                        CheckBoxFragment checkBoxFragment = new CheckBoxFragment(pCheckbox, spCheckBoxes, ResidenteActivity.this, idVivienda, numero_hogar, numero_residente, tipo_guardado,tabla_guardado);
                        fragmentComponente = checkBoxFragment;
                        break;
                    case TipoComponente.RADIO:
                        PRadio pRadio = daoEncuesta.getPRadio(preguntas.get(i).get_id());
                        ArrayList<SPRadio> spRadios = daoEncuesta.getSPRadios(preguntas.get(i).get_id());
                        //RadioFragment radioFragment = new RadioFragment(pRadio, spRadios, HogarActivity.this, encuestado);
                        RadioFragment radioFragment = new RadioFragment(pRadio, spRadios, ResidenteActivity.this, idVivienda, numero_hogar, numero_residente, tipo_guardado,tabla_guardado);
                        fragmentComponente = radioFragment;
                        break;
                    case TipoComponente.VISITAS:
                        appController = (AppController) getApplication();
                        daoSession = appController.getDaoSession();
                        FragmentVisitasEncuestador visitasFragment = new FragmentVisitasEncuestador(idVivienda, numero_hogar, ResidenteActivity.this, appController);
                        fragmentComponente = visitasFragment;
                        break;
                    case TipoComponente.FUNCIONARIOS:
                        appController = (AppController) getApplication();
                        //FuncionariosFragment funcionariosFragment = new FuncionariosFragment(idVivienda, numero_hogar,HogarActivity.this, appController);
                        FuncionariosFragment funcionariosFragment = new FuncionariosFragment(ResidenteActivity.this, appController, idVivienda, numero_hogar, numero_residente, tipo_guardado,tabla_guardado);
                        fragmentComponente = funcionariosFragment;
                        break;
                    case TipoComponente.RESIDENTES:
                        appController = (AppController) getApplication();
                        daoSession = appController.getDaoSession();
                        ResidentesFragment residentesFragment = new ResidentesFragment(idVivienda, numero_hogar, ResidenteActivity.this, appController);
                        fragmentComponente = residentesFragment;
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
        txtHeaderEncuestado.setText("Empresa " + encuestado);
        txtHeaderUsuario.setText("Usuario: " + usuario);
    }

    private void conectarVistas() {
        //Barra y Drawer
        drawerLayout = findViewById(R.id.drawer_encuesta_layout);
        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Botones avanzar y retorceder
        btnAtras = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        //navigation y lista expandible
        navigationView = findViewById(R.id.navigation_view);
        expListView = findViewById(R.id.lista_expandible_navigation);
        //Layouts: scrolleable para componente visitas
        //focus para funcinamiento auxiliar
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
        String idModulo = daoEncuesta.getPagina(numeroDePagina + "",TIPO_ACTIVIDAD).getModulo();
        Log.e("idModulo",""+idModulo);
        Log.e("idModuloActual",""+idModuloActual);
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro que desea volver al listado de personas? (no se guardaran los cambios)")
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
        Drawable[] drawables = btnSiguiente.getCompoundDrawables();
        Drawable leftCompoundDrawable = drawables[0];
        Drawable img = this.getResources().getDrawable(R.drawable.ic_action_save);

        if(contadort_pagina==0){
            btnAtras.setText("");
            btnAtras.setEnabled(false);
        }
        else {
            btnAtras.setText("Ant");
            btnAtras.setEnabled(true);
        }

        if((contadort_pagina+1)==paginas.size()) {
            btnSiguiente.setText("");
        }else{
            btnSiguiente.setText("Sig");
            img = null;
        }

        btnSiguiente.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
    }
}
