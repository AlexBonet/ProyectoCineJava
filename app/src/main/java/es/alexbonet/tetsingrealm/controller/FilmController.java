package es.alexbonet.tetsingrealm.controller;

import java.util.UUID;

import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;
import io.realm.RealmResults;

public class FilmController {
    public RealmResults<Film> getAllFilms(Realm connect){
        RealmResults<Film> films = connect.where(Film.class).findAll();
        return films;
    }

    public Film getFilmByName(Realm connect, String titulo){
        Film film = connect.where(Film.class).equalTo("titulo",titulo).findFirst();
        return film;
    }

    public RealmResults<Film> getFilmsEnCartelera(Realm connect){
        RealmResults<Film> films = connect.where(Film.class).equalTo("en_cartelera",true).findAll();
        return films;
    }

    public void createFilm(Realm connect, Film film){
        Film f = film;
        f.setId_film(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(f);
        connect.commitTransaction();
    }

    //TODO
    public void setOutCartelera(Realm connect, String id, boolean enCartelera){
        connect.executeTransaction( transactionRealm -> {
            Film innerOtherTask = transactionRealm.where(Film.class).equalTo("_id", id).findFirst();
            innerOtherTask.setEn_cartelera(enCartelera);
        });
    }




}
