package es.alexbonet.tetsingrealm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Sala extends RealmObject {
    private String id_sala;
    @PrimaryKey
    private int num_sala;
    private int filas;
    private int columnas;
    private String tipo;

    public Sala(String id_sala, int num_sala, int filas, int columnas, String tipo) {
        this.id_sala = id_sala;
        this.num_sala = num_sala;
        this.filas = filas;
        this.columnas = columnas;
        this.tipo = tipo;
    }

    public Sala(int num_sala, int filas, int columnas, String tipo) {
        this.num_sala = num_sala;
        this.filas = filas;
        this.columnas = columnas;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
