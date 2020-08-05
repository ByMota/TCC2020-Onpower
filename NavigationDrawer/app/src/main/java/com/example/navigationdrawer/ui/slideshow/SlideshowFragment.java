package com.example.navigationdrawer.ui.slideshow;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.navigationdrawer.Activity.dados_login_cad.AlterDadosActivity;
import com.example.navigationdrawer.Activity.dados_login_cad.LoginActivity;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.SobreActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    private FirebaseAuth deslogar = FirebaseAuth.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        Button btnDados = view.findViewById(R.id.btnDados);
        Button btnSair = view.findViewById(R.id.btnSair);
        Button btnSobre = view.findViewById(R.id.btnSobre);

        btnDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AlterDadosActivity.class);
                startActivity(i);
            }
        });
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SobreActivity.class);
                startActivity(i);

            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                deslogar.signOut();
                getActivity().finish();
                startActivity(i);
            }
        });

        return view;
    }
}