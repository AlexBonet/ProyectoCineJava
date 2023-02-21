package es.alexbonet.tetsingrealm.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Sesion extends RealmObject implements Serializable {
    @PrimaryKey
    private String id_sesion;
    private int num_sala;
    private String titulo_peli;
    private String hora_empieza;
    private int ocupacion; // duracion de la pelicula + 10

    public Sesion(int num_sala, String titulo_peli, String hora_empieza, int ocupacion) {
        this.num_sala = num_sala;
        this.titulo_peli = titulo_peli;
        this.hora_empieza = hora_empieza;
        this.ocupacion = ocupacion;
    }

    public Sesion() {
    }

    public Sesion(String id_sesion, int num_sala, String titulo_peli, String hora_empieza, int ocupacion) {
        this.id_sesion = id_sesion;
        this.num_sala = num_sala;
        this.titulo_peli = titulo_peli;
        this.hora_empieza = hora_empieza;
        this.ocupacion = ocupacion;
    }

    public String getId_sesion() {
        return id_sesion;
    }

    public void setId_sesion(String id_sesion) {
        this.id_sesion = id_sesion;
    }

    public int getNum_sala() {
        return num_sala;
    }

    public void setNum_sala(int num_sala) {
        this.num_sala = num_sala;
    }

    public String getTitulo_peli() {
        return titulo_peli;
    }

    public void setTitulo_peli(String titulo_peli) {
        this.titulo_peli = titulo_peli;
    }

    public String getHora_empieza() {
        return hora_empieza;
    }

    public void setHora_empieza(String hora_empieza) {
        this.hora_empieza = hora_empieza;
    }

    public int getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(int ocupacion) {
        this.ocupacion = ocupacion;
    }

    @NonNull
    @Override
    public String toString() {
        String str = this.hora_empieza + " - ";
        if (this.num_sala != 6){
            str += "4DX SALA: " + num_sala;
        } else if (this.num_sala == 4 || this.num_sala == 5) {
            str += "3D SALA: " + num_sala;
        } else {
            str += "2D SALA; " + num_sala;
        }
        return str;
    }
}
