package com.example.navigationdrawer.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private String id, nome, email,data, cpf, celular, senha;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference fireBaseRef = ConfiguracaoFireBase.getFirebase();
        DatabaseReference usuarioRef = fireBaseRef.child("usuarios").child(getId());
        usuarioRef.setValue(this);
    }

    public void atualizar(){
        DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebase();
        DatabaseReference usuarioref = firebaseRef.child("usuarios").child(getId());

        Map<String, Object> valoresuser = converterParaMap();
        usuarioref.updateChildren(valoresuser);
    }

    public Map<String, Object> converterParaMap(){
        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", getEmail());
        usuarioMap.put("nome", getNome());
        usuarioMap.put("id", getId());

        return usuarioMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
