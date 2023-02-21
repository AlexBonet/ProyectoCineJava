package es.alexbonet.tetsingrealm.db;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import es.alexbonet.tetsingrealm.model.Butaca;
import es.alexbonet.tetsingrealm.model.Entrada;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.Usuario;
import es.alexbonet.tetsingrealm.model.Venta;
import io.realm.Realm;
import io.realm.RealmResults;

public class Controller {
    //USERCONTROLLER
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

    public void updateUser(Realm connect, Usuario usuario){
        connect.beginTransaction();
        connect.copyToRealmOrUpdate(usuario);
        connect.commitTransaction();
    }

    public void deleteUser(Realm connect, Usuario usuario){
        Usuario u;
        if ((u = getUser(connect, usuario.getUserName())) != null){
            connect.beginTransaction();
            u.deleteFromRealm();
            connect.commitTransaction();
        }
    }
    public void deleteUserByName(Realm connect, String userName){
        Usuario u;
        if ((u = getUser(connect, userName)) != null){
            connect.beginTransaction();
            u.deleteFromRealm();
            connect.commitTransaction();
        }
    }
    public void deleteUserByDNI(Realm connect, String dni){
        Usuario u;
        if ((u = getFromDNI(connect, dni)) != null){
            connect.beginTransaction();
            u.deleteFromRealm();
            connect.commitTransaction();
        }
    }

    //FILMCONTROLLER
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

    public void updateFilm(Realm connect, Film film){
        connect.beginTransaction();
        connect.copyToRealmOrUpdate(film);
        connect.commitTransaction();
    }
    public void deleteFilm(Realm connect, Film film){
        Film f;
        if ((f = getFilmByName(connect, film.getTitulo())) != null){
            connect.beginTransaction();
            f.deleteFromRealm();
            connect.commitTransaction();
        }
    }
    public void deleteFilmName(Realm connect, String name){
        Film f;
        if ((f = getFilmByName(connect, name)) != null){
            connect.beginTransaction();
            f.deleteFromRealm();
            connect.commitTransaction();
        }
    }

    //SALACONTROLLER
    public RealmResults<Sala> getAllSalas(Realm connect){
        RealmResults<Sala> salas = connect.where(Sala.class).findAll();
        return salas;
    }

    public Sala getSala(Realm connect, int numsala){
        return connect.where(Sala.class).equalTo("num_sala",numsala).findFirst();
    }

    public void createSala(Realm connect, Sala sala){
        Sala s = sala;
        s.setId_sala(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(s);
        connect.commitTransaction();
    }

    public void updateSala(Realm connect, Sala sala){
        connect.beginTransaction();
        connect.copyToRealmOrUpdate(sala);
        connect.commitTransaction();
    }
    public void deleteSala(Realm connect, Sala sala){
        Sala s;
        if ((s = getSala(connect, sala.getNum_sala())) != null){
            connect.beginTransaction();
            s.deleteFromRealm();
            connect.commitTransaction();
        }
    }

    //SESIONCONTROLLER
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

    public void updateSesion(Realm connect, Sesion sesion){
        connect.beginTransaction();
        connect.copyToRealmOrUpdate(sesion);
        connect.commitTransaction();
    }

    public void deleteSesion(Realm connect, String id_sesion) {
        Sesion s;
        if((s = getAllSesionFromID(connect, id_sesion)) != null){
            connect.beginTransaction();
            s.deleteFromRealm();
            connect.commitTransaction();
        }
    }

    // ENTRADACONTROLLER
    public RealmResults<Entrada> getAllEntradas(Realm connect){
        return connect.where(Entrada.class).findAll();
    }

    public RealmResults<Entrada> getAllEntradasFromSesion(Realm connect, String id_sesion){
        return connect.where(Entrada.class).equalTo("id_sesion", id_sesion).findAll();
    }

    public Entrada getEntradaByNum(Realm connect, int num){
        return connect.where(Entrada.class).equalTo("num_entrada", num).findFirst();
    }

    public void createEntrada(Realm connect, Entrada entrada){
        Entrada e = entrada;
        e.setId_entrada(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(e);
        connect.commitTransaction();
    }

    public void updateEntrada(Realm connect, Entrada sala){
        connect.beginTransaction();
        connect.copyToRealmOrUpdate(sala);
        connect.commitTransaction();
    }
    public void deleteEntrada(Realm connect, Entrada entrada){
        Entrada s;
        if ((s = getEntradaByNum(connect, entrada.getNum_entrada())) != null){
            connect.beginTransaction();
            s.deleteFromRealm();
            connect.commitTransaction();
        }
    }

    // VENTACONTROLLER
    public RealmResults<Venta> getAllVentas(Realm connect){
        return connect.where(Venta.class).findAll();
    }

    public void createVenta(Realm connect, Venta venta){
        Venta v = venta;
        v.setId_venta(UUID.randomUUID().toString());

        connect.beginTransaction();
        connect.copyToRealm(v);
        connect.commitTransaction();
    }

    public List<Butaca> getAllButacasOcupadasDeSala(Realm connect, String id_sesion){
        RealmResults<Entrada> entradas = getAllEntradasFromSesion(connect, id_sesion);
        List<Butaca> butacas = new LinkedList<>();
        for(Entrada e : entradas){
            butacas.add(new Butaca(e.getNum_fila(), e.getNum_butaca(), getAllSesionFromID(connect, id_sesion).getNum_sala()));
        }
        return butacas;
    }

    public Venta getVenta(Realm connect, int num_venta){
        return connect.where(Venta.class).equalTo("num_venta", num_venta).findFirst();
    }
    public void updateVenta(Realm connect, Venta sala){
        connect.beginTransaction();
        connect.copyToRealmOrUpdate(sala);
        connect.commitTransaction();
    }
    public void deleteVenta(Realm connect, Venta venta){
        Venta v;
        if ((v = getVenta(connect, venta.getNum_venta())) != null){
            connect.beginTransaction();
            v.deleteFromRealm();
            connect.commitTransaction();
        }
    }


}
