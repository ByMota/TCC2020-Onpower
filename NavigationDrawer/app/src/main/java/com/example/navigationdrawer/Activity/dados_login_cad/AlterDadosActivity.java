package com.example.navigationdrawer.Activity.dados_login_cad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.config.Usuario;
import com.example.navigationdrawer.config.UsuarioFireBase;
import com.example.navigationdrawer.ui.slideshow.SlideshowFragment;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseUser;

public class AlterDadosActivity extends AppCompatActivity {

    EditText editarEmail , editarNome;
    Button btnAlterarDados;
    private Usuario usuarioLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_dados);

      usuarioLogado = UsuarioFireBase.getDadosusuarioLogado();
      editarEmail = findViewById(R.id.editarEmail);
      editarNome = findViewById(R.id.editarNome);
      btnAlterarDados = findViewById(R.id.btnAlterarDados);


        FirebaseUser usuarioPerfil = UsuarioFireBase.getUsuarioAtual();
        editarNome.setText( usuarioPerfil.getDisplayName());
        editarEmail.setText( usuarioPerfil.getEmail() );

        btnAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAtualizado = editarNome.getText().toString();
                String emailAtualizado = editarEmail.getText().toString();



                UsuarioFireBase.atualizarUsuario(emailAtualizado);


                        //atualizar nome no perfil
                UsuarioFireBase.atualizarUsuario( nomeAtualizado );

                //atualizar no Banco
                usuarioLogado.setNome( nomeAtualizado );
                usuarioLogado.setEmail(emailAtualizado);
                usuarioLogado.atualizar();
                Toast.makeText(AlterDadosActivity.this, "Dados atualizados", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(AlterDadosActivity.this, MainActivity.class);
                finish();
                startActivity(i);
            }
        });

    }

}
