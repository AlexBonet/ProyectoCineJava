package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Sala extends RealmObject implements Serializable {
    private String id_sala;
    @PrimaryKey
    private int num_sala;
    private int filas;
    private int columnas;
    private String tipo_sala;

    public Sala(String id_sala, int num_sala, int filas, int columnas, String tipo_sala) {
        this.id_sala = id_sala;
        this.num_sala = num_sala;
        this.filas = filas;
        this.columnas = columnas;
        this.tipo_sala = tipo_sala;
    }

    public Sala(int num_sala, int filas, int columnas, String tipo_sala) {
        this.num_sala = num_sala;
        this.filas = filas;
        this.columnas = columnas;
        this.tipo_sala = tipo_sala;
    }

    public Sala() {
    }

    public String getId_sala() {
        return id_sala;
    }

    public void setId_sala(String id_sala) {
        this.id_sala = id_sala;
    }

    public int getNum_sala() {
        return num_sala;
    }

    public void setNum_sala(int num_sala) {
        this.num_sala = num_sala;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public String getTipo_sala() {
        return tipo_sala;
    }

    public void setTipo_sala(String tipo_sala) {
        this.tipo_sala = tipo_sala;
    }

    @Override
    public String toString() {
        return this.num_sala + " - " + this.tipo_sala;
    }
}
