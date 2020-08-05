package com.example.navigationdrawer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.config.Anuncio;
import com.example.navigationdrawer.config.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoAdapterActivity extends AppCompatActivity {


    private AlertDialog dialog;
    private DatabaseReference firebaseRef;
    private String idEmpresa;
    private String idUsuarioLogado;
    private Usuario usuario;
    private int qtdItensCarrinho;
    private Double totalCarrinho;
    private int metodoPagamento;

    Spinner spinnerQtd, spinnerTam;
    TextView textNomeProduto, textPreco, ValorTotal;
    Button buttonConfirmarCompra;
    CarouselView imgCarrinho;
    private Anuncio anuncioSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho_adapter);

        spinnerTam = findViewById(R.id.spinnerTam);
        textNomeProduto = findViewById(R.id.textNomeProduto);
        textPreco = findViewById(R.id.textPreco);
        buttonConfirmarCompra = findViewById(R.id.buttonConfirmarCompra);
        imgCarrinho = findViewById(R.id.imgCarrinho);


        buttonConfirmarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CarrinhoAdapterActivity.this, TeladePagamentoActivity.class);
                finish();
                startActivity(i);
            }
        });
        carregarSpinner();

        //recupera anuncioa
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            anuncioSelecionado = (Anuncio) bundle.getSerializable("anuncioSelecionado");
            textNomeProduto.setText(anuncioSelecionado.getNome());

            textPreco.setText(anuncioSelecionado.getValor());

            ImageListener imageListener = new ImageListener() {
                @Override
                public void setImageForPosition(int position, ImageView imageView) {
                    String urlString = anuncioSelecionado.getFotos().get(position);
                    Picasso.get().load(urlString).into(imageView);
                }
            };

            imgCarrinho.setPageCount(anuncioSelecionado.getFotos().size());
            imgCarrinho.setImageListener(imageListener);
        }
    }
        public void carregarSpinner(){


        String[] Tamanho = getResources().getStringArray(R.array.Tamanho);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Tamanho
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTam.setAdapter(adapter2);

    }
}
