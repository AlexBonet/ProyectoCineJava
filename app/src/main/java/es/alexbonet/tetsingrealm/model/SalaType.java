package es.alexbonet.tetsingrealm.model;

public enum SalaType {
    NORMAL("2D"), TRESD("3D"), CUATRODX("4DX");

    private final String string;

    private SalaType(String string){
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
