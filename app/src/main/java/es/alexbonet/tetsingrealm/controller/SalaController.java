package es.alexbonet.tetsingrealm.controller;

import java.util.UUID;

import es.alexbonet.tetsingrealm.model.Sala;
import io.realm.Realm;
import io.realm.RealmResults;

public class SalaController {
    public RealmResults<Sala> getAllSalas(Realm connect){
        RealmResults<Sala> salas = connect.where(Sala.class).findAll();
        return salas;
    }

    public void createSala(Realm connect, Sala film){
        Sala s = film;
        s.setId_sala(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(s);
        connect.commitTransaction();
    }
}
