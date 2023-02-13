package es.alexbonet.tetsingrealm.controller;

import es.alexbonet.tetsingrealm.model.Film;
import io.realm.Realm;
import io.realm.RealmResults;

public class FilmController {
    public RealmResults<Film> getAllFilms(Realm connect){
        RealmResults<Film> films = connect.where(Film.class).findAll();
        return films;
    }

    public RealmResults<Film> getFilms(Realm connect, String id_film){
        RealmResults<Film> users = connect.where(Film.class).equalTo("id_film",id_film).findAll();
        return users;
    }

    public RealmResults<Film> getFilmsEnCartelera(Realm connect){
        RealmResults<Film> users = connect.where(Film.class).equalTo("en_cartelera",true).findAll();
        return users;
    }

    public void createFilm(Realm connect, Film film){
        connect.executeTransaction (transactionRealm -> {
            transactionRealm.insert(film);
        });
    }

    public void setOutCartelera(Realm connect, String id, boolean enCartelera){
        connect.executeTransaction( transactionRealm -> {
            Film innerOtherTask = transactionRealm.where(Film.class).equalTo("_id", id).findFirst();
            innerOtherTask.setEn_cartelera(enCartelera);
        });
    }




}
