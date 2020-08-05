package com.example.navigationdrawer.Activity.dados_login_cad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.config.ConfiguracaoFireBase;
import com.example.navigationdrawer.config.Usuario;
import com.example.navigationdrawer.config.UsuarioFireBase;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.FirebaseFirestore;

public class CadastroActivity extends AppCompatActivity {

    EditText editTextEmail, editTextSenha,editTextNome,editTextDtNasc,editTextCpf,editTextCel;
    Button btnCadastrar;

    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        autenticacao = ConfiguracaoFireBase.getAutenticacao();

        btnCadastrar = findViewById(R.id.btnCadastrar);
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDtNasc = findViewById(R.id.editTextDtNasc);
        editTextCpf = findViewById(R.id.editTextCpf);
        editTextCel = findViewById(R.id.editTextCel);
        editTextSenha = findViewById(R.id.editTextSenha);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNN.NNN.NNN.NN");
        MaskTextWatcher mtw = new MaskTextWatcher(editTextCpf, smf);
        editTextCpf.addTextChangedListener(mtw);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("(NN) NNNNN.NNNN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(editTextCel, smf2);
        editTextCel.addTextChangedListener(mtw2);

        SimpleMaskFormatter smf3 = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw3 = new MaskTextWatcher(editTextDtNasc, smf3);
        editTextDtNasc.addTextChangedListener(mtw3);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edNome = editTextNome.getText().toString();
                String edemail = editTextEmail.getText().toString();
                String edDtNasc = editTextDtNasc.getText().toString();
                String edCpf = editTextCpf.getText().toString();
                String edCel = editTextCel.getText().toString();
                String edSenha = editTextSenha.getText().toString();
                if (!edNome.isEmpty()) {
                    if (!edemail.isEmpty()){
                        if (!edDtNasc.isEmpty()){
                            if (!edCpf.isEmpty()){
                                if (!edCel.isEmpty()){
                                    if (!edSenha.isEmpty()){
                                        usuario = new Usuario();
                                        usuario.setNome(edNome);
                                        usuario.setEmail(edemail);
                                        usuario.setData(edDtNasc);
                                        usuario.setCpf(edCpf);
                                        usuario.setCelular(edCel);
                                        usuario.setSenha(edSenha);
                                        autenticacao.createUserWithEmailAndPassword(
                                                edemail, edSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    try {
                                                        //salvar no firebase
                                                        String idUsuario = task.getResult().getUser().getUid();
                                                        usuario.setId(idUsuario);
                                                        usuario.salvar();

                                                        UsuarioFireBase.atualizarUsuario( usuario.getNome());

                                                        Toast.makeText(CadastroActivity.this, "Cadastro com sucesso", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(CadastroActivity.this, LoginActivity.class);
                                                        startActivity(i);
                                                        finish();
                                                   } catch (Exception e) {
                                                       e.printStackTrace();
                                                   }
                                                } else {
                                                    String erroExcecao = "";
                                                    try {
                                                        throw task.getException();
                                                    } catch (FirebaseAuthWeakPasswordException e) {
                                                        erroExcecao = "Digite uma senha mais forte!";
                                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                                        erroExcecao = "Por favor, digite um e-mail válido";
                                                    } catch (FirebaseAuthUserCollisionException e) {
                                                        erroExcecao = "Este conta já foi cadastrada";
                                                    } catch (Exception e) {
                                                        erroExcecao = "ao cadastrar usuário: " + e.getMessage();
                                                        e.printStackTrace();
                                                    }
                                                    Toast.makeText(CadastroActivity.this,
                                                            "Erro: " + erroExcecao,
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }else{
                                        Toast.makeText(CadastroActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                                        editTextSenha.requestFocus();
                                    }
                                }else{
                                    Toast.makeText(CadastroActivity.this, "Preencha o celular", Toast.LENGTH_SHORT).show();
                                    editTextCel.requestFocus();
                                }
                            }else{
                                Toast.makeText(CadastroActivity.this, "Preencha o cpf", Toast.LENGTH_SHORT).show();
                                editTextCpf.requestFocus();
                            }
                        }else{
                            Toast.makeText(CadastroActivity.this, "Preencha a data", Toast.LENGTH_SHORT).show();
                            editTextDtNasc.requestFocus();
                        }
                    }else {
                        Toast.makeText(CadastroActivity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
                        editTextEmail.requestFocus();
                    }
                }else{
                    Toast.makeText(CadastroActivity.this, "Preencha o nome", Toast.LENGTH_SHORT).show();
                    editTextNome.requestFocus();
                }
            }
        });
    }
}
