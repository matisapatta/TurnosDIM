package mobile.mads.turnosdim;

import java.util.Date;

/**
 * Created by mati on 8/19/16.
 */

public class TurnosStruct {

    private long idTurno;
    private Date fechaTurno;
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

    public TurnosStruct(long idTurno, Date fechaTurno, String horaTurno, String medico, String especialidad,
        String centro, String consultorio, String cobertura, String preparacion, boolean esConsulta, ObjectStruct practicas){

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

    public long getIdTurno() {
        return idTurno;
    }

    public Date getFechaTurno() {
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

    public ObjectStruct getPracticas() {
        return practicas;
    }

    public void setIdTurno(long idTurno) {
        this.idTurno = idTurno;
    }

    public void setFechaTurno(Date fechaTurno) {
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
}
