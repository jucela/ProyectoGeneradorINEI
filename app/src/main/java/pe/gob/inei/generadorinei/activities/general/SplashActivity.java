package pe.gob.inei.generadorinei.activities.general;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import pe.gob.inei.generadorinei.R;
import pe.gob.inei.generadorinei.activities.empresas.EmpresaActivity;
import pe.gob.inei.generadorinei.model.dao.DAOEncuesta;

public class SplashActivity extends AppCompatActivity {
    int tiempoEspera = 3000;

    TextView txtTitulo;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        txtTitulo = (TextView) findViewById(R.id.tvTituloSplash);
        progressBar = (ProgressBar) findViewById(R.id.pbProgresoCopia);
        DAOEncuesta DAOEncuesta = new DAOEncuesta(SplashActivity.this);
        if(DAOEncuesta.checkDataBase()){
//            txtTitulo.setText(getString(R.string.nombre_encuesta));
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                   Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
/*                   Intent intent = new Intent(getApplicationContext(),EmpresaActivity.class);*/
                    startActivity(intent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, tiempoEspera);
        }else{
            new MyAsyncTask().execute(0);
        }
    }

    public class MyAsyncTask extends AsyncTask<Integer,Integer,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtTitulo.setText("INICIANDO APP...");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            String nombreApp = "";
            try {
                DAOEncuesta DAOEncuesta = new DAOEncuesta(SplashActivity.this,1);
                DAOEncuesta.open();
//                nombreApp = getString(R.string.nombre_encuesta);
                DAOEncuesta.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return nombreApp;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String mensaje) {
            super.onPostExecute(mensaje);
            txtTitulo.setText(mensaje);
            progressBar.setVisibility(View.GONE);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 2000);
        }
    }
}
