package es.alexbonet.tetsingrealm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Sesion extends RealmObject {
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
}
