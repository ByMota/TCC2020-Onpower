package com.example.navigationdrawer.Activity.dados_login_cad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.config.ConfiguracaoFireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    //private FirebaseAuth autenticacao = FirebaseAuth.getInstance();

    EditText email, senha;
    Button btnLogar;
    TextView txtMudarSenha;

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = ConfiguracaoFireBase.getAutenticacao();
        verificarAutenticacao();

        btnLogar = findViewById(R.id.btnLogar);
        email = findViewById(R.id.email);
        senha = findViewById(R.id.senha);
        txtMudarSenha = findViewById(R.id.txtMudarSenha);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edemail = email.getText().toString();
                String edSenha = senha.getText().toString();
                if ( !edemail.isEmpty()){
                    if (!edSenha.isEmpty()){
                        autenticacao.signInWithEmailAndPassword(
                                edemail, edSenha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() ){
                                             Toast.makeText(LoginActivity.this,
                                               "Login efetuado"
                                                 , Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                finish();
                                startActivity(i);
                            }else {
                                Toast.makeText(LoginActivity.this,
                                        "Email ou senha incorretos",Toast.LENGTH_SHORT).show();
                            }
                          }
                        });
                    }else{
                        Toast.makeText(LoginActivity.this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                        senha.requestFocus();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Preencha o email", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }
            }
        });
        txtMudarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperaSenha();
            }
        });
    }
    private void recuperaSenha() {
        String edemail = email.getText().toString();

        if (edemail.isEmpty()){
            Toast.makeText(getBaseContext(), " Insira o campo do email.", Toast.LENGTH_SHORT).show();
        }else{
            enviarEmail(edemail);
        }
    }
    private void enviarEmail(String edemail) {
        autenticacao.sendPasswordResetEmail(edemail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(getBaseContext(), "Uma menssagem com o link para redefinição de " +
                        "senha foi enviado para o seu email.", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getBaseContext(), " Erro ao redefinir senha.", Toast.LENGTH_SHORT).show();

            }
        });

    }
    public void  verificarAutenticacao(){
        autenticacao = ConfiguracaoFireBase.getAutenticacao();
        if ( autenticacao.getCurrentUser() !=null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}

/*
//validação
campoEmail = findViewById(R.id.editLoginEmail);
        textoEmail = campoEmail.getText().toString();

        if (!textoEmail.isEmpty()){
        String regex = "[a-z0-9._-]+@[a-z0-9.-]+\\.[a-z]{2,4}";
        Pattern pattern = Pattern.compile(regex);
        String source = textoEmail;
        Matcher matcher = pattern.matcher(source);
        if (matcher.find() && matcher.group().equals(source)){
        //se positivo continue seu código aqui
        } else {
        campoEmail.setError("Formato de e-mail inválido");
        campoEmail.requestFocus();
        }
        } else {
        campoEmail.setError("Preencha o campo E-mail");
        campoEmail.requestFocus();
//        }
*/

//logar user
     /*   autenticacao.signInWithEmailAndPassword("vinicius@gmail.com", "123456")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
@Override
public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful() ){
        Log.i("signIn", "sucesso ao logar lek");
        }else{
        Log.i("signIn", "sem sucesso ao logar lek");
        }
        }
        });

//deslogar
//autenticacao.signOut();

//verifica usuario logado
        /*if (autenticacao.getCurrentUser()!=null ){
            Log.i("CreateUser", "sucessolek");
        }else{
            Log.i("CreateUser", "sem sucessolek");
        }*/

//add user
       /* autenticacao.createUserWithEmailAndPassword("vinicius@gmail.com", "123456")
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful() ){
                                    Log.i("CreateUser", "sucessolek");
                                }else{
                                    Log.i("CreateUser", "sem sucessolek");
                                }
                            }
                        });*/