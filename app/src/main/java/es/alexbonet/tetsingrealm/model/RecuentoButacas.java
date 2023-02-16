package es.alexbonet.tetsingrealm.model;

import java.io.Serializable;
import java.util.List;

public class RecuentoButacas implements Serializable {
    List<Butaca> list;

    public RecuentoButacas(List<Butaca> list){
        this.list = list;
    }

    public List<Butaca> getButacas() {
        return list;
    }
}
