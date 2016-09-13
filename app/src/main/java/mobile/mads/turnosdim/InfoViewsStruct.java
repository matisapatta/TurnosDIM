package mobile.mads.turnosdim;


import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alan on 11/9/2016.
 */

public class InfoViewsStruct implements Parcelable {
    private String txtTitulo;
    private String txtTexto;
    private String txtSubTexto;
    private int imgIcono;
    public InfoViewsStruct(){

    }

    public InfoViewsStruct (String txtTitulo, String txtTexto, String txtSubTexto, int imgIcono){
        this.imgIcono = imgIcono;
        this.txtTexto = txtTexto;
        this.txtTitulo = txtTitulo;
        this.txtSubTexto = txtSubTexto;
    }

    public int getImgIcono() {
        return imgIcono;
    }

    public String getTxtTexto() {
        return txtTexto;
    }

    public String getTxtTitulo() {
        return txtTitulo;
    }

    public String getTxtSubTexto() {
        return txtSubTexto;
    }

    public void setImgIcono(int imgIcono) {
        this.imgIcono = imgIcono;
    }

    public void setTxtTexto(String txtTexto) {
        this.txtTexto = txtTexto;
    }

    public void setTxtTitulo(String txtTitulo) {
        this.txtTitulo = txtTitulo;
    }

    public void setTxtSubTexto(String txtSubTexto) {
        this.txtSubTexto = txtSubTexto;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(imgIcono);
        out.writeString(txtTitulo);
        out.writeString(txtTexto);
        out.writeString(txtSubTexto);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<InfoViewsStruct> CREATOR = new Parcelable.Creator<InfoViewsStruct>() {
        public InfoViewsStruct createFromParcel(Parcel in) {
            return new InfoViewsStruct (in);
        }

        public InfoViewsStruct[] newArray(int size) {
            return new InfoViewsStruct[size];
        }
    };

    private InfoViewsStruct(Parcel in){
        this.imgIcono = in.readInt();
        this.txtTitulo = in.readString();
        this.txtTexto = in.readString();
        this.txtSubTexto = in.readString();

    }
}
