package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Film extends RealmObject implements Serializable {
    @PrimaryKey
    private String id_film;
    private String titulo;
    private String descrip;
    private int duracion;
    private int edad_min;
    private String genero; //POT SER UN ENUM
    private boolean en_cartelera; //true en cartela, false fuera de cartelera
    private String urlImage;

    public Film(){}

    public Film(String id_film, String titulo, String descrip, int duracion, int edad_min, String genero, boolean en_cartelera, String urlImage) {
        this.id_film = id_film;
        this.titulo = titulo;
        this.descrip = descrip;
        this.duracion = duracion;
        this.edad_min = edad_min;
        this.genero = genero;
        this.en_cartelera = en_cartelera;
        this.urlImage = urlImage;
    }

    public Film(String titulo, String descrip, int duracion, int edad_min, String genero, boolean en_cartelera, String urlImage) {
        this.titulo = titulo;
        this.descrip = descrip;
        this.duracion = duracion;
        this.edad_min = edad_min;
        this.genero = genero;
        this.en_cartelera = en_cartelera;
        this.urlImage = urlImage;
    }

    public String getId_film() {
        return id_film;
    }

    public void setId_film(String id_film) {
        this.id_film = id_film;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getEdad_min() {
        return edad_min;
    }

    public void setEdad_min(int edad_min) {
        this.edad_min = edad_min;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public boolean isEn_cartelera() {
        return en_cartelera;
    }

    public void setEn_cartelera(boolean en_cartelera) {
        this.en_cartelera = en_cartelera;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
