package es.alexbonet.tetsingrealm.controller;

import java.util.UUID;

import es.alexbonet.tetsingrealm.model.Sesion;
import io.realm.Realm;
import io.realm.RealmResults;

public class SesionController {
    public RealmResults<Sesion> getAllSesions(Realm connect){
        RealmResults<Sesion> salas = connect.where(Sesion.class).findAll();
        return salas;
    }

    public void createSesion(Realm connect, Sesion sesion){
        Sesion s = sesion;
        s.setId_sesion(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(s);
        connect.commitTransaction();
    }
}
