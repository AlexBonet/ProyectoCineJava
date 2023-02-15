package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Entrada extends RealmObject implements Serializable {
    private String id_entrada;
    @PrimaryKey
    private int num_entrada;
    private String fila; // TODO ESTO POT SER UNA CLASE FILA
    private String butaca; // TODO ESTO POT SER UNA CLASE BUTACA

    public Entrada(String id_entrada, int num_entrada, String fila, String butaca) {
        this.id_entrada = id_entrada;
        this.num_entrada = num_entrada;
        this.fila = fila;
        this.butaca = butaca;
    }

    public Entrada(int num_entrada, String fila, String butaca) {
        this.num_entrada = num_entrada;
        this.fila = fila;
        this.butaca = butaca;
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

    public String getFila() {
        return fila;
    }

    public void setFila(String fila) {
        this.fila = fila;
    }

    public String getButaca() {
        return butaca;
    }

    public void setButaca(String butaca) {
        this.butaca = butaca;
    }
}
