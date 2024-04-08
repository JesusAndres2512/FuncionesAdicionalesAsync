package Moviles.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    ArrayList<String> listaItems = new ArrayList<>();
    ProgressBar barradeCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.listaSync);
        barradeCarga = findViewById(R.id.progressBar);

        // Iniciar AsyncTask para llenar la lista
        new ClaseAsincrona().execute();
    }

    private class ClaseAsincrona extends AsyncTask<Void, Integer, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Mostrar la barra de progreso antes de comenzar la tarea
            barradeCarga.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> listaDeCarga = new ArrayList<>();
            int tiempo = 600;
            for (int i = 0; i < 10; i++) {
                listaDeCarga.add("Objeto" + i);
                publishProgress((i + 1) * 10); // Actualizar el progreso
                try {
                    Thread.sleep(tiempo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return listaDeCarga;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // Actualizar la barra de progreso
            barradeCarga.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            super.onPostExecute(result);
            // Ocultar la barra de progreso
            barradeCarga.setVisibility(ProgressBar.GONE);
            // Actualizar la lista con los datos obtenidos
            listaItems.clear();
            listaItems.addAll(result);
            // Actualizar el adaptador del ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_checked,
                    listaItems);
            lista.setAdapter(adapter);
        }
    }
}
