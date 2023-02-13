package es.alexbonet.tetsingrealm.controller;

import java.util.UUID;

import es.alexbonet.tetsingrealm.model.Usuario;
import io.realm.Realm;
import io.realm.RealmResults;

public class UserController {

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

        connect.executeTransaction(transactionRealm -> {
            transactionRealm.insert(u);
        });
    }

    public void deleteUser(Realm connect){
        Usuario yetAnotherUsuario = getAllUsers(connect).get(0);
        String yetAnotherUserDNI = yetAnotherUsuario.getDni();
        connect.executeTransaction( transactionRealm -> {
            Usuario innerYetAnotherUsuario = transactionRealm.where(Usuario.class).equalTo("dni", yetAnotherUserDNI).findFirst();
            innerYetAnotherUsuario.deleteFromRealm();
        });
    }
}
