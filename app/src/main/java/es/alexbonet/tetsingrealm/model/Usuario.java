package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

//TODO EL ID SE QUEDA???
public class Usuario extends RealmObject implements Serializable {
    private String id;
    @PrimaryKey
    private String dni;
    private String nombre;
    private String apellidos;
    @Required
    private String userName;
    @Required
    private String pswd;
    @Required
    private String tipo;

    public Usuario() {
    }

    public Usuario(String id, String dni, String nombre, String apellidos, String userName, String pswd, String tipo) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.userName = userName;
        this.pswd = pswd;
        this.tipo = tipo;
    }

    //Contructor sin id
    public Usuario(String dni, String nombre, String apellidos, String userName, String pswd, String tipo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.userName = userName;
        this.pswd = pswd;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
