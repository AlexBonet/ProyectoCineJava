package es.alexbonet.tetsingrealm.db;

import java.sql.Timestamp;
import java.util.Date;

import es.alexbonet.tetsingrealm.model.Entrada;
import es.alexbonet.tetsingrealm.model.Film;
import es.alexbonet.tetsingrealm.model.Sala;
import es.alexbonet.tetsingrealm.model.Venta;
import es.alexbonet.tetsingrealm.model.enums.SalaType;
import es.alexbonet.tetsingrealm.model.Sesion;
import es.alexbonet.tetsingrealm.model.enums.UserType;
import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;

public class IniciarDB {
    private final Controller c;

    public IniciarDB() {
        c = new Controller();
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
            c.createUser(connect, u);
        }

        //    public Film(String titulo, String descrip, int duracion, int edad_min, String genero, boolean en_carteleta) {
        Film [] films = new Film[]{
                new Film("Avatar 2", "Avatar 2", 192, 12, "Aventura", true, "https://cdn.kinepolis.es/images/ES/65459BAD-CA99-4711-A97B-E049A5FA94D2/HO00003725/0000005757/Avatar:_El_Sentido_del_Agua.jpg"),
                new Film("El gato con botas 2", "El gato con botas 2", 102, 7, "Amimacion", true,"https://cdn.kinepolis.es/images/ES/65459BAD-CA99-4711-A97B-E049A5FA94D2/HO00003822/0000005786/El_Gato_con_Botas:_El_%C3%9Altimo_Deseo.jpg"),
                new Film("EL PEOR VECINO DEL MUNDO", "Un venino muy malo", 125, 12, "Comedia", true, "https://cdn.kinepolis.es/images/ES/65459BAD-CA99-4711-A97B-E049A5FA94D2/HO00004057/0000005405/Modelo_77.jpg"),
                new Film("ASTÉRIX Y OBÉLIX: EL REINO MEDIO", "ASTÉRIX Y OBÉLIX", 111, 7, "Comedia", true,"https://cdn.kinepolis.es/images/ES/65459BAD-CA99-4711-A97B-E049A5FA94D2/HO00004249/0000006077/Ast%C3%A9rix_y_Ob%C3%A9lix:_El_Reino_Medio.jpg"),
                new Film("LA BALLENA", "(THE WHALE)", 117, 16, "Drama", true, "https://cdn.kinepolis.es/images/ES/65459BAD-CA99-4711-A97B-E049A5FA94D2/HO00004244/0000006009/La_Ballena_(The_Whale).jpg"),
                new Film("TADEO JONES 3. LA TABLA ESMERALDA", "TADEO JONES 3", 89, 0, "Animacion", false,"https://cdn.kinepolis.es/images/ES/65459BAD-CA99-4711-A97B-E049A5FA94D2/HO00004246/0000006033/El_Asombroso_Mauricio.jpg")
        };
        for (Film f : films) {
            c.createFilm(connect, f);
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
            c.createSala(connect, s);
        }

        //    public Sesion(int num_sala, String titulo_peli, String hora_empieza, int ocupacion) {
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
            c.createSesion(connect, s);
        }

        //     public Entrada(int num_entrada, int num_fila, int num_butaca, int num_sala) {
        Entrada [] entradas = new Entrada[]{
                new Entrada(0,0,0,"0")
        };

        for (Entrada e : entradas) {
            c.createEntrada(connect, e);
        }

        //     public Venta(int num_venta, int num_entrada, int importe, String nombre_empleado, Date hora) {
        Venta [] ventas = new Venta[]{
            new Venta(0,0,0,"admin",new Date(23,1,17,0,0,0))
        };

        for (Venta v : ventas) {
            c.createVenta(connect, v);
        }

    }
}
