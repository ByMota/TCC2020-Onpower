package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.navigationdrawer.ui.home.HomeFragment;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class TeladePagamentoActivity extends AppCompatActivity {

    TextView textView2, textView6;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telade_pagamento);

            textView2 = findViewById(R.id.textView2);
        textView6 = findViewById(R.id.textView6);
        button2 = findViewById(R.id.button2);


button2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i = new Intent(TeladePagamentoActivity.this, MainActivity.class);
        finish();
        startActivity(i);
    }
});

    }
}