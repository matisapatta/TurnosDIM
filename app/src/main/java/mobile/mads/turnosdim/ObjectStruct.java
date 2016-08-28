package mobile.mads.turnosdim;

import android.os.Parcel;
import android.os.Parcelable;

import static android.R.attr.id;

/**
 * Created by mati on 8/19/16.
 */

public class ObjectStruct implements Parcelable {
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

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(id);
        out.writeString(idObj);
        out.writeString(descripcion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ObjectStruct> CREATOR = new Parcelable.Creator<ObjectStruct>() {
        public ObjectStruct createFromParcel(Parcel in) {
            return new ObjectStruct (in);
        }

        public ObjectStruct[] newArray(int size) {
            return new ObjectStruct[size];
        }
    };

    private ObjectStruct (Parcel in){
        this.id = in.readInt();
        this.idObj = in.readString();
        this.descripcion = in.readString();


    }
}
