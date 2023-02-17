package es.alexbonet.tetsingrealm.model;

public enum SalaType {
    NORMAL("2D",7), TRESD("3D",9), CUATRODX("4DX",11);

    private final String string;
    private final int preu;


    private SalaType(String string, int preu){
        this.string = string;
        this.preu = preu;
    }

    public String getString() {
        return string;
    }

    public int getPreu() {
        return preu;
    }
}
