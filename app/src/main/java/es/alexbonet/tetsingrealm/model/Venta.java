package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Venta extends RealmObject implements Serializable {
    private String id_venta;
    @PrimaryKey
    private int num_venta;
    private String nombre_empleado; // O QUE EL HA VENGUT LA MAQUINA
    private int num_entrada;
    private int importe;
    private Date hora; // curentTimeStamp
}
