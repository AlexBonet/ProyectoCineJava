package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;

public class Butaca implements Serializable {
    private int fila;
    private int colunna;
    private int num_sala;
    private boolean libre_ocupada;

    public Butaca(int fila, int colunna, int num_sala, boolean libre_ocupada) {
        this.fila = fila;
        this.colunna = colunna;
        this.num_sala = num_sala;
        this.libre_ocupada = libre_ocupada;
    }

    public Butaca(int fila, int colunna, int num_sala) {
        this.fila = fila;
        this.colunna = colunna;
        this.num_sala = num_sala;
    }

    public Butaca(int fila, int colunna) {
        this.fila = fila;
        this.colunna = colunna;
    }

    public Butaca() {
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColunna() {
        return colunna;
    }

    public void setColunna(int colunna) {
        this.colunna = colunna;
    }

    public int getNum_sala() {
        return num_sala;
    }

    public void setNum_sala(int num_sala) {
        this.num_sala = num_sala;
    }

    public boolean isLibre_ocupada() {
        return libre_ocupada;
    }

    public void setLibre_ocupada(boolean libre_ocupada) {
        this.libre_ocupada = libre_ocupada;
    }
}
