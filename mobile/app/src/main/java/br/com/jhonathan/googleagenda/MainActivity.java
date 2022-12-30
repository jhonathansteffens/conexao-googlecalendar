package br.com.jhonathan.googleagenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    Button buttonBuscarEventos;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        initListeners();
    }

    private void initComponents() {
        buttonBuscarEventos = findViewById(R.id.buttonBuscarEventos);
        text = findViewById(R.id.textEventos);
    }

    private void initListeners() {
        buttonBuscarEventos.setOnClickListener(view -> {
            buscarEventos();
        });

    }

    private void buscarEventos() {
        String url = "http://10.0.2.2:8080/calendario/getEvents";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String jsonData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        atualizarTela(jsonData);
                    }
                });

            }
        });
    }

    private void atualizarTela(String jsonData) {
        text.setText(jsonData);
    }

}