package pe.gob.inei.generadorinei.activities.general;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.activities.hogares.ViviendaActivity;
import pe.gob.inei.generadorinei.adapters.MarcoAdapter;
import pe.gob.inei.generadorinei.adapters.Marco_hogarAdapter;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.dao.SQLConstantes;
import pe.gob.inei.generadorinei.model.pojos.encuesta.ItemMarco_hogar;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Marco_hogar;

public class Marco_hogarActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Marco_hogarAdapter marcoAdapter;
    private ArrayList<ItemMarco_hogar> ItemMarco_hogars;
    private ArrayList<String> anios;
    private ArrayList<String> meses;
    private ArrayList<String> periodos;
    private ArrayList<String> zonas;
    private String nickUsuario;
    private String usuario;
    private String idCargo;
    private Spinner spAnio;
    private Spinner spMeses;
    private Spinner spPeriodos;
    private Spinner spZonas;
    private Button btnFiltrar;
    private Button btnMostrarTodo;
    private LinearLayoutManager linearLayoutManager;

    DAOEncuesta daoEncuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marco_hogar);
        nickUsuario = getIntent().getExtras().getString("nickUsuario");
        usuario = getIntent().getExtras().getString("usuario");
        idCargo = getIntent().getExtras().getString("idCargo");


        spAnio = (Spinner) findViewById(R.id.marco_sp_anio);
        spMeses = (Spinner) findViewById(R.id.marco_sp_mes);
        spPeriodos = (Spinner) findViewById(R.id.marco_sp_periodo);
        spZonas = (Spinner) findViewById(R.id.marco_sp_zona);
        btnFiltrar = (Button) findViewById(R.id.marco_btnFiltrar);
        btnMostrarTodo = (Button) findViewById(R.id.marco_btnMotrarTodo);

        Toolbar toolbar =  (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.nombre_encuesta));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Marco_hogarActivity.this);
                builder.setMessage("¿Está seguro que desea salir de la aplicación?")
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
        });



        recyclerView = (RecyclerView) findViewById(R.id.recycler_encuestado);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        spAnio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0) obtenerMeses(Integer.parseInt(spAnio.getSelectedItem().toString()));
                if(i == 0) meses = new ArrayList<String>();
                cargarSpinerMeses(meses);
                periodos = new ArrayList<String>();
                cargarSpinerPeriodos(periodos);
                zonas = new ArrayList<String>();
                cargarSpinerZonas(zonas);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spMeses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0) obtenerPeriodos(Integer.parseInt(spMeses.getSelectedItem().toString()));
                if(i == 0) periodos = new ArrayList<String>();
                cargarSpinerPeriodos(periodos);
                zonas = new ArrayList<String>();
                cargarSpinerZonas(zonas);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spPeriodos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > 0) obtenerZonas(Integer.parseInt(spPeriodos.getSelectedItem().toString()));
                if(i == 0) zonas = new ArrayList<String>();
                cargarSpinerZonas(zonas);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(zonas.size() > 1 && spZonas.getSelectedItemPosition() != 0){
                    obtenerMarcoFiltrado(spAnio.getSelectedItem().toString(),
                            spMeses.getSelectedItem().toString(),
                            spPeriodos.getSelectedItem().toString(),
                            spZonas.getSelectedItem().toString());
                }else{
                    Toast.makeText(Marco_hogarActivity.this, "DEBE SELECCIONAR TODOS LOS CAMPOS ANTES DE FILTRAR", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnMostrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerMarcoTotal();
                spAnio.setSelection(0);
            }
        });
    }

    public void obtenerMarcoFiltrado(String anio,String mes, String periodo, String conglomerado){
        ItemMarco_hogars = new ArrayList<>();
        daoEncuesta = new DAOEncuesta(Marco_hogarActivity.this);
        daoEncuesta.open();
//        ItemMarco_hogars = data.getListMarcoFiltrado(Integer.parseInt(anio), Integer.parseInt(mes),Integer.parseInt(periodo),Integer.parseInt(conglomerado));
        ItemMarco_hogars = daoEncuesta.getListMarcoFiltrado2(anio, mes,periodo,conglomerado);
        daoEncuesta.close();
        setearAdapter();
    }

    public void obtenerMarcoTotal(){
        inicializarDatos();
        cargarSpinerAnios(anios);
        cargarSpinerMeses(meses);
        cargarSpinerPeriodos(periodos);
        cargarSpinerZonas(zonas);
        setearAdapter();
    }

    public void obtenerMeses(int anio){
        meses = new ArrayList<String>();
        meses.add("Seleccione");
        for(ItemMarco_hogar ItemMarco_hogar : ItemMarco_hogars){
            if(Integer.parseInt(ItemMarco_hogar.getAnio())== anio){
                if(!meses.contains(ItemMarco_hogar.getMes())){
                    meses.add(String.valueOf(ItemMarco_hogar.getMes()));
                }
            }
        }
    }
    public void obtenerPeriodos(int mes){
        periodos = new ArrayList<String>();
        periodos.add("Seleccione");
        for(ItemMarco_hogar ItemMarco_hogar : ItemMarco_hogars){
            if(Integer.parseInt(ItemMarco_hogar.getMes())== mes){
                if(!periodos.contains(ItemMarco_hogar.getPeriodo())){
                    periodos.add(String.valueOf(ItemMarco_hogar.getPeriodo()));
                }
            }
        }
    }
    public void obtenerZonas(int periodo){
        zonas = new ArrayList<String>();
        zonas.add("Seleccione");
        for(ItemMarco_hogar ItemMarco_hogar : ItemMarco_hogars){
            if(Integer.parseInt(ItemMarco_hogar.getPeriodo())== periodo){
                if(!zonas.contains(ItemMarco_hogar.getZona())){
                    zonas.add(String.valueOf(ItemMarco_hogar.getZona()));
                }
            }
        }
    }

    public void cargarSpinerAnios(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnio.setAdapter(adapter);
    }
    public void cargarSpinerMeses(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMeses.setAdapter(adapter);
    }

    public void cargarSpinerPeriodos(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPeriodos.setAdapter(adapter);
    }

    public void cargarSpinerZonas(ArrayList<String> datos){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spZonas.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_marco_hogar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.opcion_exportar:
                /*
                Intent intent = new Intent(Marco_hogarActivity.this,ExportarActivity.class);
                intent.putExtra("idUsuario",idUsuario);
                startActivity(intent);
                */
                return true;
            case R.id.opcion_importar:
                /*
                Intent intent1 = new Intent(Marco_hogarActivity.this,ImportarActivity.class);
                startActivity(intent1);
                */
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @SuppressLint("NewApi")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Está seguro que desea salir de la aplicación?")
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
        return super.onKeyDown(keyCode, event);
    }
    private void inicializarDatos() {
        ItemMarco_hogars = new ArrayList<ItemMarco_hogar>();
        anios = new ArrayList<String>();
        meses = new ArrayList<String>();
        periodos = new ArrayList<String>();
        zonas = new ArrayList<String>();
        daoEncuesta = new DAOEncuesta(this);
        daoEncuesta.getTablasEncuesta();
        daoEncuesta.open();
        Log.e("idUsuario", "inicializarDatos: "+usuario );
        Log.e("idCargo", "inicializarDatos: "+idCargo );
        if (idCargo.equals("1")) ItemMarco_hogars = daoEncuesta.getListMarco(usuario);
        else ItemMarco_hogars = daoEncuesta.getListMarcoSupervisor(usuario);
        daoEncuesta.close();
        anios.add("Seleccione");
        Log.e("ItemMarco_hogars.size", "inicializarDatos: "+ ItemMarco_hogars.size());
        if (ItemMarco_hogars.size()>0) anios.add(String.valueOf(ItemMarco_hogars.get(0).getAnio()));
    }

    public void setearAdapter(){
        marcoAdapter = new Marco_hogarAdapter(ItemMarco_hogars, new Marco_hogarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                daoEncuesta.open();
                if(!daoEncuesta.existeElemento(1,SQLConstantes.tablacaratula,ItemMarco_hogars.get(position).get_id(),"","")){
                    String _id=ItemMarco_hogars.get(position).get_id();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("id_vivienda", _id);
                    contentValues.put("CAR_DEP", daoEncuesta.getElemento(0,SQLConstantes.tablamarco_hogar,SQLConstantes.marco_departamento,_id,"",""));
                    contentValues.put("CAR_PROV", daoEncuesta.getElemento(0,SQLConstantes.tablamarco_hogar,SQLConstantes.marco_provincia,_id,"",""));
                    contentValues.put("CAR_DIST", daoEncuesta.getElemento(0,SQLConstantes.tablamarco_hogar,SQLConstantes.marco_distrito,_id,"",""));
                    contentValues.put("CAR_CENTRO_POB", daoEncuesta.getElemento(0,SQLConstantes.tablamarco_hogar,SQLConstantes.marco_nomccpp,_id,"",""));
                    contentValues.put("CAR_ZONA", daoEncuesta.getElemento(0,SQLConstantes.tablamarco_hogar,SQLConstantes.marco_zona,_id,"",""));
                    contentValues.put("CAR_MANZ", daoEncuesta.getElemento(0,SQLConstantes.tablamarco_hogar,SQLConstantes.marco_mza,_id,"",""));
                    contentValues.put("CAR_VIVIENDA", daoEncuesta.getElemento(0,SQLConstantes.tablamarco_hogar,SQLConstantes.marco_norden,_id,"",""));
                    daoEncuesta.insertarElemento(SQLConstantes.tablacaratula,contentValues);
                }
                daoEncuesta.close();

                Intent intent = new Intent(getApplicationContext(), ViviendaActivity.class);
                intent.putExtra("nickUsuario", nickUsuario);
                intent.putExtra("idVivienda", ItemMarco_hogars.get(position).get_id()+"");
                intent.putExtra("vivienda_zona", ItemMarco_hogars.get(position).getZona()+"");
                intent.putExtra("vivienda_mes", ItemMarco_hogars.get(position).getMes()+"");
                intent.putExtra("vivienda_anio", ItemMarco_hogars.get(position).getAnio()+"");
                intent.putExtra("vivienda_periodo", ItemMarco_hogars.get(position).getPeriodo()+"");
                intent.putExtra("usuario", usuario);
                intent.putExtra("idUsuario", usuario);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(marcoAdapter);
    }

    @Override
    protected void onResume() {
        inicializarDatos();
        cargarSpinerAnios(anios);
        cargarSpinerMeses(meses);
        cargarSpinerPeriodos(periodos);
        cargarSpinerZonas(zonas);
        setearAdapter();
        super.onResume();
    }
}
