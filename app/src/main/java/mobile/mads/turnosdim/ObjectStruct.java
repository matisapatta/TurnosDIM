package mobile.mads.turnosdim;

import static android.R.attr.id;

/**
 * Created by mati on 8/19/16.
 */

public class ObjectStruct {
    private long idObj;
    private String descripcion;

    public ObjectStruct(){

    }

    public ObjectStruct(long idObj, String descripcion){
        this.idObj = idObj;
        this.descripcion = descripcion;
    }

    public long getIdObj() {
        return idObj;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setIdObj(long idObj) {
        this.idObj = idObj;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
