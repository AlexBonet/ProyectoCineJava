package es.alexbonet.tetsingrealm.controller;

import java.util.ArrayList;
import java.util.UUID;

import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.Sesion;
import io.realm.Realm;
import io.realm.RealmResults;

public class SesionController {
    public RealmResults<Sesion> getAllSesions(Realm connect){
        RealmResults<Sesion> salas = connect.where(Sesion.class).findAll();
        return salas;
    }

    public RealmResults<Sesion> getAllSesionFromAFilm(Realm connect, String titulo){
        RealmResults<Sesion> salas = connect.where(Sesion.class).equalTo("titulo_peli",titulo).findAll();
        return salas;
    }

    public CharSequence[] getAllSesionFromAFilmToString(Realm connect, String titulo){
        RealmResults<Sesion> ssio = connect.where(Sesion.class).equalTo("titulo_peli",titulo).findAll();
        CharSequence[] sesiones = new CharSequence[0];
        for (int i = 0; i < ssio.size()-1; i++) {
            sesiones[i] = ssio.get(i).toString();
        }
        return sesiones;
    }

    public void createSesion(Realm connect, Sesion sesion){
        Sesion s = sesion;
        s.setId_sesion(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(s);
        connect.commitTransaction();
    }
}
