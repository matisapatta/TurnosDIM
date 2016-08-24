package mobile.mads.turnosdim;

import static android.R.attr.id;

/**
 * Created by mati on 8/19/16.
 */

public class ObjectStruct {
    private int id;
    private String idObj;
    private String descripcion;

    public ObjectStruct(){

    }

    public ObjectStruct(String idObj, String descripcion){
        this.idObj = idObj;
        this.descripcion = descripcion;
    }

    public ObjectStruct(int id,String idObj, String descripcion){
        this.id = id;
        this.idObj = idObj;
        this.descripcion = descripcion;
    }

    public String getIdObj() {
        return idObj;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdObj(String idObj) {
        this.idObj = idObj;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
