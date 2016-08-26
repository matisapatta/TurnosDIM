package mobile.mads.turnosdim;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mati on 8/19/16.
 */

public class TurnosStruct  implements Parcelable {

    private int id;
    private String idTurno;
    private String fechaTurno;
    private String horaTurno;
    private String medico;
    private String especialidad;
    private String centro;
    private String consultorio;
    private String cobertura;
    private String preparacion;
    private boolean esConsulta;
    private ObjectStruct practicas;

    public TurnosStruct(){

    }

    public TurnosStruct(int id, String idTurno, String fechaTurno, String horaTurno, String medico, String especialidad,
        String centro, String consultorio, String cobertura, String preparacion, boolean esConsulta, ObjectStruct practicas){

        this.id = id;
        this.idTurno = idTurno;
        this.fechaTurno = fechaTurno;
        this.horaTurno = horaTurno;
        this.medico = medico;
        this.especialidad = especialidad;
        this.centro = centro;
        this.consultorio = consultorio;
        this.cobertura = cobertura;
        this.preparacion = preparacion;
        this.esConsulta = esConsulta;
        this.practicas = practicas;
    }

    public TurnosStruct(int id, String idTurno, String fechaTurno, String horaTurno, String medico, String especialidad,
                        String centro, String consultorio, String cobertura, String preparacion, String esConsulta){

        this.id = id;
        this.idTurno = idTurno;
        this.fechaTurno = fechaTurno;
        this.horaTurno = horaTurno;
        this.medico = medico;
        this.especialidad = especialidad;
        this.centro = centro;
        this.consultorio = consultorio;
        this.cobertura = cobertura;
        this.preparacion = preparacion;
        if(esConsulta.toLowerCase().equals("true")){
            this.esConsulta = true;
        } else {
            this.esConsulta = false;
        }
    }

    public String getIdTurno() {
        return idTurno;
    }

    public String getFechaTurno() {
        return fechaTurno;
    }

    public String getHoraTurno() {
        return horaTurno;
    }

    public String getMedico() {
        return medico;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getCentro() {
        return centro;
    }

    public String getConsultorio() {
        return consultorio;
    }

    public String getCobertura() {
        return cobertura;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public boolean isEsConsulta() {
        return esConsulta;
    }

    public String getEsConsulta(){
        if(this.esConsulta){
            return "true";
        } else {
            return "false";
        }
    }

    public ObjectStruct getPracticas() {
        return practicas;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public void setFechaTurno(String fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public void setHoraTurno(String horaTurno) {
        this.horaTurno = horaTurno;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public void setConsultorio(String consultorio) {
        this.consultorio = consultorio;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public void setEsConsulta(boolean esConsulta) {
        this.esConsulta = esConsulta;
    }

    public void setPracticas(ObjectStruct practicas) {
        this.practicas = practicas;
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(id);
        out.writeString(idTurno);
        out.writeString(fechaTurno);
        out.writeString(horaTurno);
        out.writeString(medico);
        out.writeString(especialidad);
        out.writeString(centro);
        out.writeString(consultorio);
        out.writeString(cobertura);
        out.writeString(preparacion);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<TurnosStruct> CREATOR = new Parcelable.Creator<TurnosStruct>() {
        public TurnosStruct createFromParcel(Parcel in) {
            return new TurnosStruct (in);
        }

        public TurnosStruct[] newArray(int size) {
            return new TurnosStruct[size];
        }
    };

    private TurnosStruct(Parcel in){
        this.id = in.readInt();
        this.idTurno = in.readString();
        this.fechaTurno = in.readString();
        this.horaTurno = in.readString();
        this.medico = in.readString();
        this.especialidad = in.readString();
        this.centro = in.readString();
        this.consultorio = in.readString();
        this.cobertura = in.readString();
        this.preparacion = in.readString();

    }
}
