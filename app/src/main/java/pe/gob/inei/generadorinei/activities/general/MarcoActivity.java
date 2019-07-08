package pe.gob.inei.generadorinei.activities.general;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.activities.empresas.EmpresaActivity;
import pe.gob.inei.generadorinei.activities.hogares.ViviendaActivity;
import pe.gob.inei.generadorinei.adapters.MarcoAdapter;
import pe.gob.inei.generadorinei.model.pojos.encuesta.CamposMarco;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.FiltrosMarco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.ItemMarco;
import pe.gob.inei.generadorinei.util.TipoEncuesta;


public class MarcoActivity extends AppCompatActivity {
    TextView tvCabecera1, tvCabecera2, tvCabecera3, tvCabecera4,
            tvCabecera5, tvCabecera6, tvCabecera7;
    TextView tvFiltro1, tvFiltro2, tvFiltro3, tvFiltro4;
    Spinner spFiltro1, spFiltro2, spFiltro3, spFiltro4;
    LinearLayout lytFiltro1, lytFiltro2, lytFiltro3, lytFiltro4;
    Toolbar toolbar;
    Button btnMostrarTodo;
    RecyclerView recyclerView;
    DAOEncuesta daoEncuesta;
    CamposMarco camposMarco;
    FiltrosMarco filtrosMarco;
    MarcoAdapter marcoAdapter;
    ArrayList<ItemMarco> marcos;
    String idUsuario;
    String nickUsuario;

    ArrayList<String> arrayFiltro1, arrayFiltro2, arrayFiltro3, arrayFiltro4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marco);
        daoEncuesta = new DAOEncuesta(this);
        idUsuario = getIntent().getExtras().getString("idUsuario");
        nickUsuario = getIntent().getExtras().getString("nickUsuario");
        conectarVistas();
        configurarToolbar();
        configurarFiltros();
        configurarBotonMostrarTodo();
        configurarCabeceraMarco();
        configurarRecycler();
    }

    private void configurarBotonMostrarTodo() {
        btnMostrarTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spFiltro1.setSelection(0);
            }
        });
    }

    public void conectarVistas() {
        tvCabecera1 = findViewById(R.id.tvCabeceraMarco1);
        tvCabecera2 = findViewById(R.id.tvCabeceraMarco2);
        tvCabecera3 = findViewById(R.id.tvCabeceraMarco3);
        tvCabecera4 = findViewById(R.id.tvCabeceraMarco4);
        tvCabecera5 = findViewById(R.id.tvCabeceraMarco5);
        tvCabecera6 = findViewById(R.id.tvCabeceraMarco6);
        tvCabecera7 = findViewById(R.id.tvCabeceraMarco7);
        tvFiltro1 = (findViewById(R.id.filtro1)).findViewById(R.id.tvNombreFiltro);
        tvFiltro2 = (findViewById(R.id.filtro2)).findViewById(R.id.tvNombreFiltro);
        tvFiltro3 = (findViewById(R.id.filtro3)).findViewById(R.id.tvNombreFiltro);
        tvFiltro4 = (findViewById(R.id.filtro4)).findViewById(R.id.tvNombreFiltro);
        spFiltro1 = (findViewById(R.id.filtro1)).findViewById(R.id.spFiltro);
        spFiltro2 = (findViewById(R.id.filtro2)).findViewById(R.id.spFiltro);
        spFiltro3 = (findViewById(R.id.filtro3)).findViewById(R.id.spFiltro);
        spFiltro4 = (findViewById(R.id.filtro4)).findViewById(R.id.spFiltro);
        lytFiltro1 = findViewById(R.id.filtro1);
        lytFiltro2 = findViewById(R.id.filtro2);
        lytFiltro3 = findViewById(R.id.filtro3);
        lytFiltro4 = findViewById(R.id.filtro4);
        btnMostrarTodo = findViewById(R.id.marco_btnMotrarTodo);
        toolbar = findViewById(R.id.my_toolbar);
        recyclerView = findViewById(R.id.rvItemsMarco);
    }

    private void configurarToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(daoEncuesta.getEncuesta().getTitulo());
    }

    private void configurarFiltros() {
        filtrosMarco = daoEncuesta.getFiltrosMarco();
        configurarListenerFiltro1();
        configurarListenerFiltro2();
        configurarListenerFiltro3();
        configurarListenerFiltro4();
    }

    private void configurarListenerFiltro1(){
        if (filtrosMarco.getNombre1().equals("")) lytFiltro1.setVisibility(View.GONE);
        else {
            tvFiltro1.setText(filtrosMarco.getNombre1());
            arrayFiltro1 = daoEncuesta.getArrayFiltro1(idUsuario,filtrosMarco.getFiltro1());
            cargarSpinnerFiltro(spFiltro1, arrayFiltro1);
            spFiltro1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!filtrosMarco.getNombre2().equals("")){
                        if (i > 0) arrayFiltro2 = daoEncuesta.getArrayFiltro2(idUsuario,filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString(), filtrosMarco.getFiltro2());
                        else arrayFiltro2 = new ArrayList<String>();
                        cargarSpinnerFiltro(spFiltro2, arrayFiltro2);
                        arrayFiltro3 = new ArrayList<String>();
                        cargarSpinnerFiltro(spFiltro3, arrayFiltro3);
                        arrayFiltro4 = new ArrayList<String>();
                        cargarSpinnerFiltro(spFiltro4, arrayFiltro4);
                    }
                    if (i > 0) marcos = daoEncuesta.getAllItemsMarcoFiltro1(idUsuario, daoEncuesta.getCamposMarco(), filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString());
                    else marcos = daoEncuesta.getAllItemsMarco(idUsuario, daoEncuesta.getCamposMarco());
                    marcoAdapter.setMarcos(marcos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    private void configurarListenerFiltro2(){
        if (filtrosMarco.getNombre2().equals("")) lytFiltro2.setVisibility(View.GONE);
        else {
            tvFiltro2.setText(filtrosMarco.getNombre2());

                spFiltro2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if (!filtrosMarco.getNombre3().equals("")) {
                            if (i > 0)
                                arrayFiltro3 = daoEncuesta.getArrayFiltro3(idUsuario,
                                        filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString(),
                                        filtrosMarco.getFiltro2(), spFiltro2.getSelectedItem().toString(),
                                        filtrosMarco.getFiltro3());
                            else
                                arrayFiltro3 = new ArrayList<String>();
                            cargarSpinnerFiltro(spFiltro3, arrayFiltro3);
                            arrayFiltro4 = new ArrayList<String>();
                            cargarSpinnerFiltro(spFiltro4, arrayFiltro4);
                        }
                        if (i > 0) marcos = daoEncuesta.getAllItemsMarcoFiltro2(idUsuario, daoEncuesta.getCamposMarco(),
                                filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString(),
                                filtrosMarco.getFiltro2(), spFiltro2.getSelectedItem().toString());
                        else marcos = daoEncuesta.getAllItemsMarcoFiltro1(idUsuario, daoEncuesta.getCamposMarco(),
                                filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString());
                        marcoAdapter.setMarcos(marcos);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

        }
    }

    private void configurarListenerFiltro3(){
        if (filtrosMarco.getNombre3().equals("")) lytFiltro3.setVisibility(View.GONE);
        else {
            tvFiltro3.setText(filtrosMarco.getNombre3());
            spFiltro3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!filtrosMarco.getNombre4().equals("")) {
                        if (i > 0) arrayFiltro4 = daoEncuesta.getArrayFiltro4(idUsuario,
                                filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString(),
                                filtrosMarco.getFiltro2(), spFiltro2.getSelectedItem().toString(),
                                filtrosMarco.getFiltro3(), spFiltro3.getSelectedItem().toString(), filtrosMarco.getFiltro4());
                        else arrayFiltro4 = new ArrayList<String>();
                        cargarSpinnerFiltro(spFiltro4, arrayFiltro4);
                    }

                    if (i > 0) marcos = daoEncuesta.getAllItemsMarcoFiltro3(idUsuario, daoEncuesta.getCamposMarco()
                                , filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString()
                                , filtrosMarco.getFiltro2(), spFiltro2.getSelectedItem().toString()
                                , filtrosMarco.getFiltro3(), spFiltro3.getSelectedItem().toString());
                    else marcos = daoEncuesta.getAllItemsMarcoFiltro2(idUsuario, daoEncuesta.getCamposMarco(),
                            filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString(),
                            filtrosMarco.getFiltro2(), spFiltro2.getSelectedItem().toString());
                    marcoAdapter.setMarcos(marcos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });


        }
    }

    private void configurarListenerFiltro4(){
        if (filtrosMarco.getNombre4().equals("")) lytFiltro4.setVisibility(View.GONE);
        else {
            tvFiltro4.setText(filtrosMarco.getNombre4());
            spFiltro4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i > 0) marcos = daoEncuesta.getAllItemsMarcoFiltro4(idUsuario, daoEncuesta.getCamposMarco()
                            , filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString()
                            , filtrosMarco.getFiltro2(), spFiltro2.getSelectedItem().toString()
                            , filtrosMarco.getFiltro3(), spFiltro3.getSelectedItem().toString()
                            , filtrosMarco.getFiltro4(), spFiltro4.getSelectedItem().toString());
                    else marcos = daoEncuesta.getAllItemsMarcoFiltro3(idUsuario, daoEncuesta.getCamposMarco(),
                            filtrosMarco.getFiltro1(), spFiltro1.getSelectedItem().toString(),
                            filtrosMarco.getFiltro2(), spFiltro2.getSelectedItem().toString(),
                            filtrosMarco.getFiltro3(), spFiltro3.getSelectedItem().toString());
                    marcoAdapter.setMarcos(marcos);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }

    private void cargarSpinnerFiltro(Spinner spfiltro, ArrayList<String> datos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spfiltro.setAdapter(adapter);
    }


    private void configurarCabeceraMarco() {
        camposMarco = daoEncuesta.getCamposMarco();
        configurarCampo(camposMarco.getNombre1(), camposMarco.getPeso1(), tvCabecera1);
        configurarCampo(camposMarco.getNombre2(), camposMarco.getPeso2(), tvCabecera2);
        configurarCampo(camposMarco.getNombre3(), camposMarco.getPeso3(), tvCabecera3);
        configurarCampo(camposMarco.getNombre4(), camposMarco.getPeso4(), tvCabecera4);
        configurarCampo(camposMarco.getNombre5(), camposMarco.getPeso5(), tvCabecera5);
        configurarCampo(camposMarco.getNombre6(), camposMarco.getPeso6(), tvCabecera6);
        configurarCampo(camposMarco.getNombre7(), camposMarco.getPeso7(), tvCabecera7);
    }

    private void configurarCampo(String nombreCampo, String peso, TextView textView) {
        if (nombreCampo.equals(""))
            textView.setVisibility(View.GONE);
        else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                    LinearLayout.LayoutParams.MATCH_PARENT, Integer.parseInt(peso) * 1.0f);
            textView.setLayoutParams(params);
            textView.setText(nombreCampo);
        }
    }

    private void configurarRecycler() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        marcoAdapter = new MarcoAdapter(daoEncuesta.getCamposMarco(), new MarcoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String idEncuestado) {
                final int tipoEncuesta = Integer.parseInt(daoEncuesta.getEncuesta().getTipo());
                Log.e("tipoEncuesta",""+tipoEncuesta);
                switch (tipoEncuesta){
                    case TipoEncuesta.EMPRESA:
                        Intent intent1 = new Intent(MarcoActivity.this, EmpresaActivity.class);
                        intent1.putExtra("idUsuario",idUsuario);
                        intent1.putExtra("idEmpresa",idEncuestado);
                        startActivity(intent1);
                        break;
                    case TipoEncuesta.HOGAR:
                        Log.e("TipoEncuesta.HOGAR",""+TipoEncuesta.HOGAR);
                        Intent intent2 = new Intent(MarcoActivity.this, ViviendaActivity.class);
                        intent2.putExtra("idUsuario",idUsuario);
                        intent2.putExtra("idVivienda",idEncuestado);
                        intent2.putExtra("nickUsuario",nickUsuario);
                        startActivity(intent2);
                        break;
                }
            }
        });
        recyclerView.setAdapter(marcoAdapter);
        marcos = daoEncuesta.getAllItemsMarco(idUsuario, daoEncuesta.getCamposMarco());
        marcoAdapter.setMarcos(marcos);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Está seguro que desea salir del aplicativo?")
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
}
