package pe.gob.inei.generadorinei.activities.empresas;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.adapters.ExpandListAdapter;
import pe.gob.inei.generadorinei.fragments.CheckBoxFragment;
import pe.gob.inei.generadorinei.fragments.EditTextFragment;
import pe.gob.inei.generadorinei.fragments.RadioFragment;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.componentes.PCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.PEditText;
import pe.gob.inei.generadorinei.model.pojos.componentes.PRadio;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPEdittext;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPRadio;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Modulo;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Pagina;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Pregunta;
import pe.gob.inei.generadorinei.util.NombreSeccionFragment;
import pe.gob.inei.generadorinei.util.TipoActividad;
import pe.gob.inei.generadorinei.util.TipoComponente;
import pe.gob.inei.generadorinei.util.TipoPagina;

public class EmpresaActivity extends AppCompatActivity {
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
    String idUsuario;
    String idEmpresa;
//    ArrayList<Modulo> modulos;

    LinearLayout layoutScrolleable, lytComponente1,
            lytComponente2, lytComponente3, lytComponente4,
            lytComponente5, lytComponente6, lytComponente7,
            lytComponente8, lytComponente9, lytComponente10;
    View lytFocus;

    String idModuloActual = "";
    int paginaActual = 1;
    int numeroPaginasTotal;
    Fragment fragmentComponente = new Fragment();
    final String TIPO_ACTIVIDAD = TipoActividad.ACTIVIDAD_EMPRESA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activities_encuesta);
        idEmpresa = "1";//getIntent().getExtras().getString("idEmpresa");
        idUsuario = "cesar";//getIntent().getExtras().getString("idUsuario");

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
                if (paginaActual - 1 >= 1) {
                    do{
                        paginaActual--;
                    }while(!validoSetearPagina(paginaActual));
                    setearPagina(paginaActual);
                    setNombreSeccion(paginaActual, -1);
                }

            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado(btnSiguiente);
                if (validarPagina(paginaActual)) {
                    guardarPagina(paginaActual);
                    do{
                        if (paginaActual + 1 <= numeroPaginasTotal) paginaActual++;
                        else paginaActual = 1;
                    }while(!validoSetearPagina(paginaActual));
                    setearPagina(paginaActual);
                    setNombreSeccion(paginaActual, 1);
                }
            }
        });

        setNombreSeccion(1, 1);
        setearPagina(1);
    }

    private boolean validarPagina(int paginaActual) {
        return true;
    }

    private void guardarPagina(int paginaActual) {
    }

    public void setearPagina(int numeroPagina) {
        Pagina pagina = daoEncuesta.getPagina(numeroPagina+"",TIPO_ACTIVIDAD);
        if (pagina.getTipo_pagina().equals(TipoPagina.NORMAL)) setearPaginaNormal(pagina);
        else setearPaginaScrolleable(pagina);
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
                    case TipoComponente.EDITTEXT:
                        PEditText pEditText = daoEncuesta.getPEditText(preguntas.get(i).get_id());
                        ArrayList<SPEdittext> spEditTexts = daoEncuesta.getSPEditTexts(preguntas.get(i).get_id());
                        EditTextFragment editTextFragment = new EditTextFragment(pEditText, spEditTexts, EmpresaActivity.this, idEmpresa);
                        fragmentComponente = editTextFragment;
                        break;
                    case TipoComponente.CHECKBOX:
                        PCheckbox pCheckbox = daoEncuesta.getPCheckbox(preguntas.get(i).get_id());
                        ArrayList<SPCheckbox> spCheckBoxes = daoEncuesta.getSPCheckBoxs(preguntas.get(i).get_id());
                        CheckBoxFragment checkBoxFragment = new CheckBoxFragment(pCheckbox, spCheckBoxes, EmpresaActivity.this, idEmpresa);
                        fragmentComponente = checkBoxFragment;
                        break;
                    case TipoComponente.RADIO:
                        PRadio pRadio = daoEncuesta.getPRadio(preguntas.get(i).get_id());
                        ArrayList<SPRadio> spRadios = daoEncuesta.getSPRadios(preguntas.get(i).get_id());
                        RadioFragment radioFragment = new RadioFragment(pRadio, spRadios, EmpresaActivity.this, idEmpresa);
                        fragmentComponente = radioFragment;
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
        txtHeaderEncuestado.setText("Empresa " + idEmpresa);
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

}
