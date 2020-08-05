package com.example.navigationdrawer.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFireBase {

   public static FirebaseAuth referenciaAutenticacao;
   private static DatabaseReference referenciaDataBase;
    private static StorageReference referenciaStorage;

    public static String getIdUsuario(){

        FirebaseAuth autenticacao = referenciaAutenticacao;
        return autenticacao.getCurrentUser().getUid();

    }


    //retorna instancia
    public static DatabaseReference getFirebase(){
        if( referenciaDataBase == null ){
            referenciaDataBase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaDataBase;
    }
    public static FirebaseAuth getAutenticacao(){
        if ( referenciaAutenticacao==null) {
            referenciaAutenticacao = FirebaseAuth.getInstance();
        }
        return referenciaAutenticacao;
    }

    //Retorna instancia do FirebaseStorage
    public static StorageReference getFirebaseStorage(){
        if( referenciaStorage == null ){
            referenciaStorage = FirebaseStorage.getInstance().getReference();
        }
        return referenciaStorage;
    }
}
