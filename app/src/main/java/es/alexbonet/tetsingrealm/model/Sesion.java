package es.alexbonet.tetsingrealm.model;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Sesion extends RealmObject {
    @PrimaryKey
    private String id_sesion;
    private int num_sala;
    private String titulo_peli;
    private Date hora_empieza;
    private int ocupacion; // duracion de la pelicula + 10

}
