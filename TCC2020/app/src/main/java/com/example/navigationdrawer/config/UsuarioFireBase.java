package com.example.navigationdrawer.config;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFireBase {
    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth usuario = ConfiguracaoFireBase.getAutenticacao();
        return usuario.getCurrentUser();
    }

    public static  void atualizarUsuario(String nome){
        try {
            //usuario logado no app
            FirebaseUser user = getUsuarioAtual();
            //config objeto de alteração
            UserProfileChangeRequest profile = new UserProfileChangeRequest
                    .Builder()
                        .setDisplayName( nome )
                            .build();
            user.updateProfile( profile ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if ( !task.isSuccessful() ){
                        Log.d("Perfil", "erro ao salvar");
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static  Usuario getDadosusuarioLogado(){
        FirebaseUser firebaseUser = getUsuarioAtual();

        Usuario usuario = new Usuario();
        usuario.setEmail(firebaseUser.getEmail());
        usuario.setNome(firebaseUser.getEmail());
        usuario.setId(firebaseUser.getUid());

        return  usuario;
    }
}
