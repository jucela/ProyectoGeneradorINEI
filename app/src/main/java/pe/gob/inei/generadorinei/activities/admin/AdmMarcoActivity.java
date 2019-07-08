package pe.gob.inei.generadorinei.activities.admin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.activities.general.SplashActivity;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;
import pe.gob.inei.generadorinei.model.dao.SQLConstantes;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Marco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Marco_hogar;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Usuario;
import pe.gob.inei.generadorinei.util.MarcoPullParser;
import pe.gob.inei.generadorinei.util.TipoEncuesta;


public class AdmMarcoActivity extends AppCompatActivity {
    ProgressBar progressBar;
    TextView txtMensaje;
    String filename = "";
    int tipoCarga;
    DAOEncuesta daoEncuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_marco);
        progressBar = (ProgressBar) findViewById(R.id.progreso_admin);
        txtMensaje = (TextView) findViewById(R.id.mensaje_admin);
        filename = getIntent().getExtras().getString("filename");
        tipoCarga = getIntent().getExtras().getInt("tipo_carga");

        if (tipoCarga == 1)
            new MyAsyncTaskCargarMarco().execute();
        else
            new MyAsyncTaskExportarBD().execute();

    }

    public void exportarBD()throws IOException {
        String inFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        InputStream myInput = new FileInputStream(inFileName);
        String outFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/bdCaptura.sqlite";
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
    }


    public class MyAsyncTaskCargarMarco extends AsyncTask<Integer,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            txtMensaje.setText("CARGANDO MARCO...");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            daoEncuesta = new DAOEncuesta(AdmMarcoActivity.this);
            final int tipoEncuesta = Integer.parseInt(daoEncuesta.getEncuesta().getTipo());
            daoEncuesta.open();
            MarcoPullParser marcoPullParser = new MarcoPullParser();

            switch (tipoEncuesta){
                case TipoEncuesta.EMPRESA:
                    ArrayList<Marco> marcos = marcoPullParser.parseXML(AdmMarcoActivity.this,filename);
                    for(Marco marco:marcos){
                        if (!daoEncuesta.existeElementoMarco(marco.get_id(),SQLConstantes.tablamarco)){
                            daoEncuesta.insertarElemento(SQLConstantes.tablamarco,marco.getContentValues());
                        }
                    }
                    break;
                case TipoEncuesta.HOGAR:
                    Log.e("AdmMarcoActivity","TipoEncuesta.HOGAR-"+TipoEncuesta.HOGAR);
                    ArrayList<Marco_hogar> marcos_hogar = marcoPullParser.parseXML_hogar(AdmMarcoActivity.this,filename);
                    Log.e("AdmMarcoActivity","doInBackground-"+"marcos_hogar.size()-"+marcos_hogar.size());
                    for(Marco_hogar marco_hogar:marcos_hogar){
                        Log.e("AdmMarcoActivity","for-marco.getId-"+marco_hogar.get_id());
                        Log.e("AdmMarcoActivity","for-marco.getUsuario_id-"+marco_hogar.getUsuario_id());
                        if (!daoEncuesta.existeElementoMarco(marco_hogar.get_id(),SQLConstantes.tablamarco_hogar)){
                            daoEncuesta.insertarElemento(SQLConstantes.tablamarco_hogar,marco_hogar.getContentValues());
                        }
                    }
                    break;
            }

            daoEncuesta.close();
            return "LISTO";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtMensaje.setText(mensaje);
            progressBar.setVisibility(View.GONE);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(AdmMarcoActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 1000);
        }
    }

    public class MyAsyncTaskExportarBD extends AsyncTask<Integer,Integer,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            txtMensaje.setText("EXPORTANDO BD...");
        }

        @Override
        protected String doInBackground(Integer... integers) {
            try {
                exportarBD();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "LISTO";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtMensaje.setText(mensaje);
            progressBar.setVisibility(View.GONE);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 1000);
        }
    }
}
