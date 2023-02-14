package es.alexbonet.tetsingrealm.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Venta extends RealmObject {
    private String id_venta;
    @PrimaryKey
    private int num_venta;
    private String nombre_empleado; // O QUE EL HA VENGUT LA MAQUINA
    private int num_entrada;
    private int importe;
    private Date hora; // curentTimeStamp
}
