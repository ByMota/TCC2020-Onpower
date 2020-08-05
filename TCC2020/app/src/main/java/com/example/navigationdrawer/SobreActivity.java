package com.example.navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.navigationdrawer.config.Anuncio;
import com.example.navigationdrawer.config.ConfiguracaoFireBase;
import com.example.navigationdrawer.config.Permissoes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SobreActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText campoTitulo, campoDescricao;
    private ImageView imagem1;
    private Spinner  campoCategoria;
    private CurrencyEditText campoValor;
    Button btnConfirmaCadastroAnuncio;
    private Anuncio anuncio;
    private StorageReference storage;
    private AlertDialog dialog;
    private String[] permissoes = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    private List<String> listaFotosRecuperadas = new ArrayList<>();
    private List<String> listaURLFotos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        //Configurações iniciais
        storage = ConfiguracaoFireBase.getFirebaseStorage();

        //Validar permissões
        Permissoes.validarPermissoes(permissoes, this, 1);

        inicializarComponentes();
        carregarDadosSpinner();
    }

    public void salvarAnuncio(){
        /**
         * Salvar imagem no Storage
         */
        for (int i=0; i < listaFotosRecuperadas.size(); i++){
            String urlImagem = listaFotosRecuperadas.get(i);
            int tamanhoLista = listaFotosRecuperadas.size();
            salvarFotoStorage(urlImagem, tamanhoLista, i );
        }
    }

    private void salvarFotoStorage(String urlString, final int totalFotos, int contador){

        //Criar nó no storage
        final StorageReference imagemAnuncio = storage.child("imagens")
                .child("anuncios")
                .child("categoria")
                .child( anuncio.getIdAnuncio() )
                .child("imagem"+contador);

        //Fazer upload do arquivo
        UploadTask uploadTask = imagemAnuncio.putFile( Uri.parse(urlString) );
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

               //Uri firebaseUrl = taskSnapshot.getDownloadUrl();
                imagemAnuncio.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri url = task.getResult();
                        String urlConvertida = url.toString();
                        listaURLFotos.add( urlConvertida );
                        if( totalFotos == listaURLFotos.size() ){
                            anuncio.setFotos( listaURLFotos );
                            anuncio.salvar();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                exibirMensagemErro("Falha ao fazer upload");
                Log.i("INFO", "Falha ao fazer upload: " + e.getMessage());
            }
        });

    }

    private Anuncio configurarAnuncio(){
        String categoria = campoCategoria.getSelectedItem().toString();
        String titulo = campoTitulo.getText().toString();
        String valor = campoValor.getText().toString();
        String descricao = campoDescricao.getText().toString();

        Anuncio anuncio = new Anuncio();
        anuncio.setCategoria(categoria);
        anuncio.setNome(titulo);
        anuncio.setValor(valor);
        return anuncio;
    }

    public void validarDadosAnuncio(View view){
        anuncio = configurarAnuncio();
        String valor = String.valueOf(campoValor.getRawValue());
                                    salvarAnuncio();
    }
    private void exibirMensagemErro(String texto){
        Toast.makeText(this, texto, Toast.LENGTH_SHORT).show();
    }

    public void escolherImagem(int requestCode){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( resultCode == Activity.RESULT_OK){

            //Recuperar imagem
            Uri imagemSelecionada = data.getData();
            String caminhoImagem = imagemSelecionada.toString();

            //Configura imagem no ImageView
            if( requestCode == 1 ){
                imagem1.setImageURI( imagemSelecionada );
            }
            listaFotosRecuperadas.add( caminhoImagem );
        }
    }

    private void carregarDadosSpinner(){

        /*String[] estados = new String[]{
          "SP", "MT"
        };*/

        //Configura spinner de categorias
        String[] categorias = getResources().getStringArray(R.array.Categoria);
        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                categorias
        );
        adapterCategoria.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        campoCategoria.setAdapter( adapterCategoria );
    }

    private void inicializarComponentes(){

        campoTitulo = findViewById(R.id.editTitulo);
        campoDescricao = findViewById(R.id.editDescricao);
        campoValor = findViewById(R.id.editValor);
        campoCategoria = findViewById(R.id.spinnerCategoria);
        imagem1 = findViewById(R.id.imageCadastro1);
        btnConfirmaCadastroAnuncio = findViewById(R.id.btnConfirmaCadastroAnuncio);
        imagem1.setOnClickListener((View.OnClickListener) this);

        //Configura localidade para pt -> portugues BR -> Brasil
        Locale locale = new Locale("pt", "BR");
        campoValor.setLocale( locale );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for( int permissaoResultado : grantResults ){
            if( permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o app é necessário aceitar as permissões");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        Log.d("onClick", "onClick: " + v.getId());
        switch (v.getId()) {
            case R.id.imageCadastro1:
                Log.d("onClick", "onClick: ");
                escolherImagem(1);
                break;
        }
    }
}