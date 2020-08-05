package com.example.navigationdrawer.Activity.dados_login_cad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.navigationdrawer.R;

public class SeusDadosActivity extends AppCompatActivity {

    TextView txtEditaDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seus_dados);

         txtEditaDados = findViewById(R.id.txtEditaDados);

        txtEditaDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SeusDadosActivity.this, AlterDadosActivity.class);
                startActivity(i);
            }
        });

    }
}