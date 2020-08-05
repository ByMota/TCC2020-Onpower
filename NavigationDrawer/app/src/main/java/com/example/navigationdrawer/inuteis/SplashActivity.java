package com.example.navigationdrawer.inuteis;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.navigationdrawer.Activity.dados_login_cad.CadastroActivity;
import com.example.navigationdrawer.Activity.dados_login_cad.LoginActivity;
import com.example.navigationdrawer.MainActivity;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.config.ConfiguracaoFireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;




public class SplashActivity extends IntroActivity {

    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new SimpleSlide.Builder()
                .title("Bem-Vindo a MyWaySports")
                .description("A melhor loja de esportes de todo território nacional")
                        .image(R.drawable.um)
                            .background(android.R.color.holo_blue_dark)
                                .build()
        );
        addSlide(new SimpleSlide.Builder()
                .title("Do Brasil para o Mundo!")
                     .description("MyWaySports é uma loja de esportes, com artigos de qualidade e um ótimo preço")
                        .image(R.drawable.dois)
                            .background(android.R.color.holo_blue_dark)
                                .build()
        );
        addSlide(new SimpleSlide.Builder()
                .title("Para finalizar, faça seu login")
                        .image(R.drawable.tres)
                            .background(android.R.color.holo_blue_dark)
                                .build()
        );
        addSlide(new FragmentSlide.Builder()
                .background(R.color.Azul)
                    .fragment(R.layout.intro_login_cad)
                        .canGoForward(false)
                            .build()
        );
    }

    public void btnFazerLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));

    }

    public void btnFazerCad(View view){
        startActivity(new Intent(this, CadastroActivity.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    private void verificarUsuarioLogado() {
        autenticacao = ConfiguracaoFireBase.getAutenticacao();
        if(autenticacao.getCurrentUser() !=null){
            abrirHome();

        }

    }

    private void abrirHome() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
