package es.alexbonet.tetsingrealm.model.enums;

public enum UserType {
    ADMINISTRADOR("ADMINISTRADOR"),
    EMPLEADO("EMPLEADO"),
    CLIENTE("CLIENTE");

    private final String string;

    private UserType(String string){
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
