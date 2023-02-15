package es.alexbonet.tetsingrealm.db;

import android.content.Context;

import es.alexbonet.tetsingrealm.controller.FilmController;
import es.alexbonet.tetsingrealm.controller.SalaController;
import es.alexbonet.tetsingrealm.controller.SesionController;
import es.alexbonet.tetsingrealm.controller.UserController;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.SalaType;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.UserType;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class IniciarDB {
    private final UserController userController;
    private final FilmController filmController;
    private final SalaController salaController;
    private final SesionController sesionController;
    //MES CONTROLERS

    public IniciarDB() {
        userController = new UserController();
        filmController = new FilmController();
        salaController = new SalaController();
        sesionController = new SesionController();
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

        //    public Sala(int num_sala, int filas, int columnas, String tipo_sala) {
        Sala [] salas = new Sala[]{
                new Sala(1,7,7, SalaType.NORMAL.getString()),
                new Sala(2,7,7, SalaType.NORMAL.getString()),
                new Sala(3,7,7, SalaType.NORMAL.getString()),
                new Sala(4,7,7, SalaType.TRESD.getString()),
                new Sala(5,7,7, SalaType.TRESD.getString()),
                new Sala(6,7,7, SalaType.CUATRODX.getString())
        };

        for (Sala s : salas) {
            salaController.createSala(connect, s);
        }

        //    public Sesion(int num_sala, String titulo_peli, String hora_empieza, int ocupacion) { //TODO la ocupacion se podria posar de un altra forma
        Sesion [] sesions = new Sesion[]{
                //SALA 1 2D
                new Sesion(1,"El gato con botas 2","16:00",112),
                new Sesion(1,"El gato con botas 2","18:00",112),
                new Sesion(1,"El gato con botas 2","20:00",112),
                new Sesion(1,"LA BALLENA","22:00",127),
                //SALA 2 2D
                new Sesion(2,"EL PEOR VECINO DEL MUNDO","16:30",135),
                new Sesion(2,"EL PEOR VECINO DEL MUNDO","19:00",135),
                new Sesion(2,"Avatar 2","22:00",202),
                //SALA 3 2D
                new Sesion(3,"ASTÉRIX Y OBÉLIX: EL REINO MEDIO","16:00",121),
                new Sesion(3,"LA BALLENA","18:30",127),
                new Sesion(3,"ASTÉRIX Y OBÉLIX: EL REINO MEDIO","21:00",121),
                //SALA 4 3D
                new Sesion(4,"El gato con botas 2","16:30",112),
                new Sesion(4,"ASTÉRIX Y OBÉLIX: EL REINO MEDIO","18:30",121),
                new Sesion(4,"Avatar 2","22:00",121),
                //SALA 5 3D
                new Sesion(5,"Avatar 2","16:00",202),
                new Sesion(5,"Avatar 2","20:00",202),
                //SALA 6 4DX
                new Sesion(6,"Avatar 2","17:00",202),
                new Sesion(6,"Avatar 2","21:00",202),
        };

        for (Sesion s : sesions) {
            sesionController.createSesion(connect, s);
        }

    }
}
