package pe.gob.inei.generadorinei.activities.general;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import pe.gob.inei.generadorinei.AppController;
import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.activities.admin.AdminActivity;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Encuesta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Marco_hogar;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Usuario;
import pe.gob.inei.generadorinei.util.Constants;
import pe.gob.inei.generadorinei.util.TipoEncuesta;


public class LoginActivity extends AppCompatActivity {

    private TextView tvTituloEncuesta;
    private TextInputEditText edtUsuario;
    private TextInputEditText edtPassword;
    private Button btnIngresar;
    private LinearLayout lytPrincipal;
    private String tituloEncuesta;
    private DAOEncuesta DAOEncuesta;
    AppController appController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DAOEncuesta = new DAOEncuesta(this);
        conectarvistas();
        setearTituloEncuesta();
        configurarInputs();

        appController = (AppController) getApplication();

        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
        } else {
            appController.setDB();
        }

        edtUsuario.setText("ENC0001");
        edtPassword.setText("12345");

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos() == true){
                    Usuario usuario = DAOEncuesta.getUsuario(edtUsuario.getText().toString());

                    Log.e("usuario.getNombre()",""+usuario.getNombre());

                    if(edtUsuario.getText().toString().equals("ADMIN") && edtPassword.getText().toString().equals("12345")){
                        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    }else if(usuario!=null && usuario.getClave()!=null && usuario.getClave().equals(edtPassword.getText().toString())){
                        final int tipoEncuesta = Integer.parseInt(DAOEncuesta.getEncuesta().getTipo());

                        switch (tipoEncuesta){
                            case TipoEncuesta.EMPRESA:
                                Intent intent = new Intent(getApplicationContext(),MarcoActivity.class);
                                intent.putExtra("idUsuario",usuario.get_id());
                                intent.putExtra("nickUsuario",usuario.getNombre());
                                startActivity(intent);
                                finish();
                                break;
                            case TipoEncuesta.HOGAR:
                                Intent intent_h = new Intent(getApplicationContext(), Marco_hogarActivity.class);
                                intent_h.putExtra("usuario",usuario.get_id());
                                intent_h.putExtra("nickUsuario",usuario.getNombre());
                                intent_h.putExtra("idCargo",usuario.getCargo_id());
                                Log.e("usuario.getCargo_id()",""+usuario.getCargo_id());
                                startActivity(intent_h);
                                finish();
                                break;
                        }

                    }else{
                        Toast.makeText(LoginActivity.this, "USUARIO O CONTRASEÑA INCORRECTA", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Debe ingresar USUARIO y CONTRASEÑA", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void conectarvistas(){
        tvTituloEncuesta = findViewById(R.id.login_tvTituloEncuesta);
        edtUsuario = findViewById(R.id.login_edtUsuario);
        edtPassword = findViewById(R.id.login_edtPassword);
        btnIngresar = findViewById(R.id.login_btIngresar);
    }

    private void setearTituloEncuesta() {
        Encuesta encuesta = DAOEncuesta.getEncuesta();
        tituloEncuesta = encuesta.getTitulo();
        tvTituloEncuesta.setText(tituloEncuesta);
    }
    private void configurarInputs() {
        edtUsuario.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
        edtPassword.setFilters(new InputFilter[]{new InputFilter.AllCaps(),new InputFilter.LengthFilter(10)});
    }
    private boolean validarCampos(){
        boolean valido = true;
        if(edtUsuario.getText().toString().equals("") || edtPassword.getText().toString().equals("")) valido = false;
        return valido;
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




//    @Override
//    protected void onStart() {
//        super.onStart();
//        lytPrincipal = (LinearLayout) findViewById(R.id.login_layout);
//        lytPrincipal.requestFocus();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        lytPrincipal = (LinearLayout) findViewById(R.id.login_layout);
//        lytPrincipal.requestFocus();
//    }
}
