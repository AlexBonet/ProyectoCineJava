package es.alexbonet.tetsingrealm.db;

import java.util.UUID;

import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;
import io.realm.RealmResults;

public class Controller {
    //USERCONTROLLER C + R
    public RealmResults<Usuario> getAllUsers(Realm connect){
        RealmResults<Usuario> usuarios = connect.where(Usuario.class).findAll();
        return usuarios;
    }

    public Usuario getUser(Realm connect, String userName) {
        Usuario u = connect.where(Usuario.class).equalTo("userName",userName).findFirst();
        return u;
    }

    public Usuario getFromDNI(Realm connect, String dni) {
        Usuario u = connect.where(Usuario.class).equalTo("dni",dni).findFirst();
        return u;
    }

    public void createUser(Realm connect, Usuario usuario){
        Usuario u = usuario;
        u.setId(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(u);
        connect.commitTransaction();
    }

    //FILMCONTROLLER C + R + U
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

    public void setCartelera(Realm connect, String titulo, boolean enCartelera){
        Film f = connect.where(Film.class).equalTo("titulo",titulo).findFirst();

        connect.beginTransaction();
        f.setEn_cartelera(enCartelera);
        connect.commitTransaction();
    }

    //SALACONTROLLER C + R
    public RealmResults<Sala> getAllSalas(Realm connect){
        RealmResults<Sala> salas = connect.where(Sala.class).findAll();
        return salas;
    }

    public Sala getSala(Realm connect, int numsala){
        return connect.where(Sala.class).equalTo("num_sala",numsala).findFirst();
    }

    public void createSala(Realm connect, Sala film){
        Sala s = film;
        s.setId_sala(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(s);
        connect.commitTransaction();
    }

    //SESIONCONTROLLER C + R
    public RealmResults<Sesion> getAllSesions(Realm connect){
        RealmResults<Sesion> salas = connect.where(Sesion.class).findAll();
        return salas;
    }

    public RealmResults<Sesion> getAllSesionFromAFilm(Realm connect, String titulo){
        RealmResults<Sesion> salas = connect.where(Sesion.class).equalTo("titulo_peli",titulo).findAll();
        return salas;
    }

    public Sesion getAllSesionFromID(Realm connect, String id){
        return connect.where(Sesion.class).equalTo("id_sesion",id).findFirst();
    }

    public void createSesion(Realm connect, Sesion sesion){
        Sesion s = sesion;
        s.setId_sesion(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(s);
        connect.commitTransaction();
    }
}
