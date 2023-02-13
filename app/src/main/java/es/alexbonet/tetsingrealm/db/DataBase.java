package es.alexbonet.tetsingrealm.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DataBase {

    private static DataBase instance = new DataBase();
    public  static DataBase getInstance(){ return  instance;}

    private Realm con;

    public Realm conectar(Context context){
        if (con == null ) {
            Realm.init(context);
            String name = "nomDb5";
            RealmConfiguration config = new RealmConfiguration.Builder().name(name).build();
            con = Realm.getInstance(config);
        }
        return con;
    }
}
