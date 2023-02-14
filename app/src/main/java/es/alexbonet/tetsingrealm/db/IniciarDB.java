package es.alexbonet.tetsingrealm.db;

import android.content.Context;

import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.UserType;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class IniciarDB {
    private UserController userController;
    private FilmController filmController;
    //MES CONTROLERS


    public IniciarDB() {
    }

    public void init(Realm connect){
        Usuario [] usuarios = new Usuario[]{
                new Usuario("DNIADMIN","admin","admin","admin","admin", UserType.ADMINISTRADOR.getString()),
                new Usuario("11111111A","Bukayo","Saka","Saka7","123", UserType.EMPLEADO.getString()),
                new Usuario("22222222A","Eray","Comert","Comert","123", UserType.EMPLEADO.getString()),
                new Usuario("33333333A","Juan","Alberto","illo","123", UserType.CLIENTE.getString()),
                new Usuario("44444444A","Enrique","Iglesias","enig","123", UserType.CLIENTE.getString()),
                new Usuario("55555555A","Xavi","Garcia","xavi","123", UserType.CLIENTE.getString())
        };
        for (Usuario u : usuarios) {
            userController.createUser(connect, u);
        }

        //    public Film(String titulo, String descrip, int duracion, int edad_min, String genero, boolean en_carteleta) {
        Film [] films = new Film[]{
                new Film("Avatar 2", "Avatar 2", 192, 12, "Aventura", true),
                new Film("El gato con botas 2", "El gato con botas 2", 102, 7, "Amimacion", true),
                new Film("EL PEOR VECINO DEL MUNDO", "Un venino muy malo", 125, 12, "Comedia", true),
                new Film("ASTÉRIX Y OBÉLIX: EL REINO MEDIO", "ASTÉRIX Y OBÉLIX", 111, 7, "Comedia", true),
                new Film("LA BALLENA", "(THE WHALE)", 117, 16, "Drama", true),
                new Film("TADEO JONES 3. LA TABLA ESMERALDA", "TADEO JONES 3", 89, 0, "Animacion", false)
        };
        for (Film f : films) {
            filmController.createFilm(connect, f);
        }
    }
}
