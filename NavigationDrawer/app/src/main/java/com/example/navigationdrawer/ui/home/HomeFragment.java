package com.example.navigationdrawer.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationdrawer.CarrinhoAdapterActivity;
import com.example.navigationdrawer.R;
import com.example.navigationdrawer.adapter.AdapterAnuncios;
import com.example.navigationdrawer.config.Anuncio;
import com.example.navigationdrawer.config.ConfiguracaoFireBase;
import com.example.navigationdrawer.config.RecyclerItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {
    private int[] mImagens = new int[]{R.drawable.big, R.drawable.cs, R.drawable.grafite, R.drawable.fundorosa};

    private HomeViewModel homeViewModel;
    private List<Anuncio> listaAnuncios = new ArrayList<>();
    private AdapterAnuncios adapterAnuncios;
    private DatabaseReference anuncioRef;
    private String filtroCategoria = "";
    private FirebaseAuth autenticacao;
    private MaterialSearchView materialSearchView;
    private boolean filtrandoPorCateogoria = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        CarouselView carrosel = (CarouselView) root.findViewById(R.id.carrosel);
        Button categoriaHome = (Button) root.findViewById(R.id.categoriaHome);
        final RecyclerView listProdutos = (RecyclerView) root.findViewById(R.id.listProdutos);


        //CarouselView  =(R.id.carrosel);
        carrosel.setPageCount(mImagens.length);
        carrosel.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImagens[position]);
            }
        });




        autenticacao = ConfiguracaoFireBase.getAutenticacao();
        anuncioRef = ConfiguracaoFireBase.getFirebase().child("anuncios");


        //config Recycler view
        listProdutos.setLayoutManager(new LinearLayoutManager(getContext()));
        listProdutos.setHasFixedSize(true);
        adapterAnuncios = new AdapterAnuncios(listaAnuncios, this);
        listProdutos.setAdapter(adapterAnuncios);

        //recupearAnuncios
        recuperarAnunciosPublicos();

        //carregarSpinner

        categoriaHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FiltraPorCategoria();
            }
        });

        //Aplicar evento de clique
        listProdutos.addOnItemTouchListener(new RecyclerItemClickListener(
                context,listProdutos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Anuncio anuncioSelecionado = listaAnuncios.get( position);
                Intent i = new Intent(getActivity(), CarrinhoAdapterActivity.class);

                i.putExtra("anuncioSelecionado", anuncioSelecionado);
                startActivity(i);
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        }));

        return root;
    }



    public void recuperarAnunciosPublicos(){
        listaAnuncios.clear();
        anuncioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot categorias: dataSnapshot.getChildren() ){
                        for(DataSnapshot anuncios: categorias.getChildren() ){
                            Anuncio anuncio = anuncios.getValue(Anuncio.class);
                            listaAnuncios.add( anuncio );
                            Collections.reverse( listaAnuncios );
                            adapterAnuncios.notifyDataSetChanged();
                        }
                    }
                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void FiltraPorCategoria() {
        AlertDialog.Builder dialogCategoria = new AlertDialog.Builder(context);
        dialogCategoria.setTitle("Selecione a categoria");

        View viewSpinner = getLayoutInflater().inflate(R.layout.dialog_spinner, null);

        //Configura spinner de estados
        final Spinner spinnerCategoria = viewSpinner.findViewById(R.id.spinnerFiltro);
        String[] estados = getResources().getStringArray(R.array.Categoria);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_item,
                estados
        );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinnerCategoria.setAdapter( adapter );

        dialogCategoria.setView( viewSpinner );

        dialogCategoria.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtroCategoria = spinnerCategoria.getSelectedItem().toString();
                recuperarAnunciosPorCategoria();
                filtrandoPorCateogoria = true;
            }
        });
        dialogCategoria.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = dialogCategoria.create();
        dialog.show();

    }

    private void recuperarAnunciosPorCategoria() {

        //Configura nó por estado
        anuncioRef = ConfiguracaoFireBase.getFirebase()
                .child("anuncios")
                .child(filtroCategoria);

        anuncioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAnuncios.clear();
                for (DataSnapshot categorias: dataSnapshot.getChildren() ){
                    for(DataSnapshot anuncios: categorias.getChildren() ){


                        Anuncio anuncio = anuncios.getValue(Anuncio.class);
                        listaAnuncios.add( anuncio );


                    }
                }
                Collections.reverse( listaAnuncios );
                adapterAnuncios.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //Declara um atributo para guardar o context.
    private Context context;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }



}