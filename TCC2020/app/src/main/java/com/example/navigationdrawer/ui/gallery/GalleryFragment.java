package com.example.navigationdrawer.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.navigationdrawer.InfoCovidActivity;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.config.Anuncio;
import com.example.navigationdrawer.config.ConfiguracaoFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private DatabaseReference anuncioRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        TextView textCovid = root.findViewById(R.id.textCovid);

        textCovid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InfoCovidActivity.class);
                startActivity(i);
            }
        });


        return root;
    }

    /**public void ligar(){
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("Tel", GalleryFragment.get));
        startActivity(i);
    }

   /** public void recuperarAnunciosPorCategoria(){

        //Configura nó por categoria
        anuncioRef = ConfiguracaoFireBase.getFirebase()
                .child("anuncios")
                .child( filtroCategoria );

        anuncioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAnuncios.clear();
                for(DataSnapshot anuncios: dataSnapshot.getChildren() ){

                    Anuncio anuncio = anuncios.getValue(Anuncio.class);
                    listaAnuncios.add( anuncio );
                    Collections.reverse( listaAnuncios );
                    adapterAnuncios.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }**/
}