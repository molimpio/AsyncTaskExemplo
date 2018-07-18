package br.com.olimpiodev.asynctaskexemplo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText etUrl;
    private Button btBuscar;
    private ImageView ivImagem;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUrl = (EditText) findViewById(R.id.et_url);
        btBuscar = (Button) findViewById(R.id.bt_buscar);
        ivImagem = (ImageView) findViewById(R.id.iv_imagem);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("AsyncTask", "Botao clicado thread: " +Thread.currentThread().getName());
                chamarAsyncTask(etUrl.getText().toString());
            }
        });
    }

    private void chamarAsyncTask(String endereco) {
        TarefaDownload tarefaDownload = new TarefaDownload();
        Log.i("AsyncTask", "AsyncTask sendo chamado na thread: " +Thread.currentThread().getName());
        endereco = "https://www.gazetaesportiva.com/wp-content/uploads/imagem/2016/08/23/01spfc.jpg";
        tarefaDownload.execute(endereco);
    }

    private class TarefaDownload extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "Exibindo dialog na tela thread: " +Thread.currentThread().getName());
            load = ProgressDialog.show(MainActivity.this, "Aguarde", "Baixando imagem");
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                Log.i("AsyncTask", "Baixando imagem thread: " +Thread.currentThread().getName());
                bitmap = Auxiliar.baixarImagem(strings[0]);
            } catch (IOException e) {
                Log.i("AsyncTask", e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                ivImagem.setImageBitmap(bitmap);
                Log.i("AsyncTask", "Exibindo bitmap thread: " +Thread.currentThread().getName());
            } else {
                Log.i("AsyncTask", "Erro ao baixar imagem thread: " +Thread.currentThread().getName());
            }
            Log.i("AsyncTask", "Tirando ProgressDialog da tela thread: " +Thread.currentThread().getName());
            load.dismiss();
        }
    }
}

