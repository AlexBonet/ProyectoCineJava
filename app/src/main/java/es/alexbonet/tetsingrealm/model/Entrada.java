package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Entrada extends RealmObject implements Serializable {
    private String id_entrada;
    @PrimaryKey
    private int num_entrada;
    private int num_fila;
    private int num_butaca;
    private int num_sala;

    public Entrada(String id_entrada, int num_entrada, int num_fila, int num_butaca, int num_sala) {
        this.id_entrada = id_entrada;
        this.num_entrada = num_entrada;
        this.num_fila = num_fila;
        this.num_butaca = num_butaca;
        this.num_sala = num_sala;
    }

    public Entrada(int num_entrada, int num_fila, int num_butaca, int num_sala) {
        this.num_entrada = num_entrada;
        this.num_fila = num_fila;
        this.num_butaca = num_butaca;
        this.num_sala = num_sala;
    }

    public Entrada() {
    }

    public String getId_entrada() {
        return id_entrada;
    }

    public void setId_entrada(String id_entrada) {
        this.id_entrada = id_entrada;
    }

    public int getNum_entrada() {
        return num_entrada;
    }

    public void setNum_entrada(int num_entrada) {
        this.num_entrada = num_entrada;
    }

    public int getNum_fila() {
        return num_fila;
    }

    public void setNum_fila(int num_fila) {
        this.num_fila = num_fila;
    }

    public int getNum_butaca() {
        return num_butaca;
    }

    public void setNum_butaca(int num_butaca) {
        this.num_butaca = num_butaca;
    }

    public int getNum_sala() {
        return num_sala;
    }

    public void setNum_sala(int num_sala) {
        this.num_sala = num_sala;
    }
}
