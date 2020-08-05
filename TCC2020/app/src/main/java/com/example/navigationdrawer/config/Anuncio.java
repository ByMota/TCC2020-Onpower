package com.example.navigationdrawer.config;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.List;

public class Anuncio implements Serializable {
    private String idAnuncio, nome, qtd, tamanho, valor, categoria;
    private List<String> fotos;

    public Anuncio() {
        DatabaseReference anuncioRef = ConfiguracaoFireBase.getFirebase()
                .child("meus_anuncios");
        setIdAnuncio( anuncioRef.push().getKey() );
    }


    public void salvar(){

        String idUsuario = ConfiguracaoFireBase.getIdUsuario();
        DatabaseReference anuncioRef = ConfiguracaoFireBase.getFirebase()
                .child("meus_anuncios");

        anuncioRef.child(idUsuario)
                .child(getIdAnuncio())
                .setValue(this);

        AnuncioPublico();

    }



    public  void AnuncioPublico(){
        DatabaseReference anuncioRef = ConfiguracaoFireBase.getFirebase()
                .child("anuncios");

        anuncioRef.child(getCategoria() )
                .child(getIdAnuncio () )
                .setValue(this);
    }




    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQtd() {
        return qtd;
    }

    public void setQtd(String qtd) {
        this.qtd = qtd;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
