package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Venta extends RealmObject implements Serializable {
    private String id_venta;
    @PrimaryKey
    private int num_venta;
    private int num_entrada;
    private int importe;
    private String nombre_empleado; // O QUE EL HA VENGUT LA MAQUINA
    private Date hora; // curentTimeStamp

    public Venta(String id_venta, int num_venta, int num_entrada, int importe, String nombre_empleado, Date hora) {
        this.id_venta = id_venta;
        this.num_venta = num_venta;
        this.num_entrada = num_entrada;
        this.importe = importe;
        this.nombre_empleado = nombre_empleado;
        this.hora = hora;
    }

    public Venta(int num_venta, int num_entrada, int importe, String nombre_empleado, Date hora) {
        this.num_venta = num_venta;
        this.num_entrada = num_entrada;
        this.importe = importe;
        this.nombre_empleado = nombre_empleado;
        this.hora = hora;
    }

    public Venta() {
    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public int getNum_venta() {
        return num_venta;
    }

    public void setNum_venta(int num_venta) {
        this.num_venta = num_venta;
    }

    public int getNum_entrada() {
        return num_entrada;
    }

    public void setNum_entrada(int num_entrada) {
        this.num_entrada = num_entrada;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
}
