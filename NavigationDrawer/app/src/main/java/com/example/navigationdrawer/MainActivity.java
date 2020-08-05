package com.example.navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigationdrawer.config.UsuarioFireBase;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    ImageView imageView_Carrinho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView_Carrinho = findViewById(R.id.imageView_Carrinho);
        imageView_Carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CarrinhoAdapterActivity.class);
                startActivity(i);
            }
        });

        // Cria referencia para toda a area do navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        //Cria referencia para a area de navegação
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        View headerView = navigationView.getHeaderView(0);

        // Obtém a referência dos TextViews a partir do NavigationView
        TextView text1 = (TextView) headerView.findViewById(R.id.text1);
        TextView text2 = (TextView) headerView.findViewById(R.id.text2);

        FirebaseUser usuarioPerfil = UsuarioFireBase.getUsuarioAtual();

        text1.setText( usuarioPerfil.getDisplayName());
        text2.setText(usuarioPerfil.getEmail());

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        //Configura area que ira carregar os fragments
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //Configura menu superior da navegação
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //Configura
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}